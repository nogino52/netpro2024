import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ObjectIOClient<TInput, TOutput> {
    private boolean _isConnected = false;
    private Socket _socket;
    private ObjectInputStream _ois;
    private ObjectOutputStream _oos;

    public boolean isConnected() {
        return _isConnected;
    }

    public void connect(String host, int port) {
        if(_isConnected)
        {
            throw new RuntimeException("すでに接続されています");
        }
        
        try {
            _socket = new Socket(host, port);

            _oos = new ObjectOutputStream(_socket.getOutputStream());
            _ois = new ObjectInputStream(_socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        _isConnected = true;
    }

    public void disconnect() throws IOException {
        close();
    }

    public void receiveContinuously(Class<TInput> type, Predicate<TInput> onReceive) throws IOException, ClassNotFoundException {
        while(_isConnected) {
            var obj = receive(type);
            if(!onReceive.test(obj)) {
                break;
            }
        }
    }

    public void receiveContinuouslyAsync(Class<TInput> type, Consumer<TInput> onReceive) {
        try {
            while(_isConnected) {
                var obj = receive(type);
                CompletableFuture.runAsync(() -> onReceive.accept(obj));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public TInput receive(Class<TInput> type) throws IOException, ClassNotFoundException {
        if(_ois == null)
        {
            throw new IOException("接続が確立されていません");
        }

        var obj = _ois.readObject();
        
        if(obj != null && type.isInstance(obj)) {
            return type.cast(obj);
        }

        return null;
    }

    public void send(TOutput obj) throws IOException {
        if(_oos == null)
        {
            throw new IOException("接続が確立されていません");
        }

        _oos.writeObject(obj);
        _oos.flush();
    }

    public void close() throws IOException {
        if(_oos != null)
        {
            try {
                _oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            _oos = null;
        }
        if(_ois != null)
        {
            try {
                _ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            _ois = null;
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
