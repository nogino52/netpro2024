import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ObjectIOServer<TInput, TOutput> {
    private ServerSocket _serverSocket;

    public void start(int port) throws IOException {
        _serverSocket = new ServerSocket(port);
    }

    public void acceptClient(Class<TInput> type, Consumer<ClientHandler> onConnect, Predicate<TInput> onReceive) throws IOException, ClassNotFoundException {
        var socket = _serverSocket.accept();
        ClientHandler handler = new ClientHandler();
        try {
            handler.connect(socket);
            onConnect.accept(handler);

            while(true) {
                TInput obj = handler.receive(type);
                if(!onReceive.test(obj)) {
                    break;
                }
            }
        } finally {
            if(handler != null)
                handler.close();
            socket.close();
        }
    }
    
    public void close() throws IOException {
        if(_serverSocket != null)
            _serverSocket.close();
    }

    public class ClientHandler {
        private ObjectInputStream _ois;
        private ObjectOutputStream _oos;

        public void connect(Socket socket) throws IOException {
            _oos = new ObjectOutputStream(socket.getOutputStream());
            _ois = new ObjectInputStream(socket.getInputStream());
        }

        public void close() throws IOException {
            if(_ois != null)
                _ois.close();
            if(_oos != null)
                _oos.close();
        }

        public TInput receive(Class<TInput> type) throws IOException, ClassNotFoundException {
            var obj = _ois.readObject();
            
            if(obj != null && type.isInstance(obj)) {
                return type.cast(obj);
            }

            return null;
        }

        public void send(TOutput obj) throws IOException {
            _oos.writeObject(obj);
            _oos.flush();
        }
    }
}
