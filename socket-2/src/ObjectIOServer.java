import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ObjectIOServer<TInput, TOutput> {
    private ServerSocket _serverSocket;

    public void start(int port) throws IOException {
        _serverSocket = new ServerSocket(port);
    }

    public ClientHandler connectClient() throws IOException {
        var socket = _serverSocket.accept();
        var handler = new ClientHandler();
        handler.connect(socket);
        return handler;
    }
    
    public void close() throws IOException {
        if(_serverSocket != null)
            _serverSocket.close();
    }

    public class ClientHandler implements IConnectionHandler<TInput> {
        private Socket _socket;
        private ObjectInputStream _ois;
        private ObjectOutputStream _oos;
        private boolean _isConnected = false;

        @Override
        public boolean isConnected() {
            return _isConnected;
        }

        @Override
        public TInput receive(Class<TInput> type) throws IOException, ClassNotFoundException {
            var obj = _ois.readObject();
            
            if(obj != null && type.isInstance(obj)) {
                return type.cast(obj);
            }

            return null;
        }

        public void connect(Socket socket) throws IOException {
            _socket = socket;
            _oos = new ObjectOutputStream(socket.getOutputStream());
            _ois = new ObjectInputStream(socket.getInputStream());
            _isConnected = true;
        }

        public void send(TOutput obj) throws IOException {
            _oos.writeObject(obj);
            _oos.flush();
        }

        public void close() {
            if(_ois != null)
            {
                try {
                    _ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                _ois = null;
            }
            if(_oos != null)
            {
                try {
                    _oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                _oos = null;
            }
            if(_socket != null)
            {
                try {
                    _socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                _socket = null;
            }
            _isConnected = false;
        }
    }
}
