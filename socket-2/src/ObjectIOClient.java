import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectIOClient<TInput, TOutput> {
    private Socket _socket;
    private ObjectInputStream _ois;
    private ObjectOutputStream _oos;

    public void connect(String host, int port) throws IOException {
        _socket = new Socket(host, port);

        _oos = new ObjectOutputStream(_socket.getOutputStream());
        _ois = new ObjectInputStream(_socket.getInputStream());
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
            _oos.close();
            _oos = null;
        }
        if(_ois != null)
        {
            _ois.close();
            _ois = null;
        }
        if(_socket != null)
        {
            _socket.close();
            _socket = null;
        }
    }
}
