import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;


public class ChatServer {
    private static final Set<ObjectOutputStream> clientOOStreams = Collections.synchronizedSet(new HashSet<>());

    private static void handleClientAsync(Socket clientSocket) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            clientOOStreams.add(oos);

            var messageReader = new ObjectInputStream(clientSocket.getInputStream());

            ChatMessage message;
            
            sendSystemMessage("新しいユーザが入室しました (接続中: " + clientOOStreams.size() + "人)");
            while ((message = (ChatMessage) messageReader.readObject()) != null) {
                broadcastMessage(message);
                System.out.println("Received message: " + message.toString());
            }

            clientSocket.close();

        } catch(EOFException | SocketException e) {
            System.out.println("Client disconnected.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                clientOOStreams.remove(oos);
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            sendSystemMessage("ユーザが退室しました (接続中: " + clientOOStreams.size() + "人)");
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5050);
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");
                CompletableFuture.runAsync(() -> handleClientAsync(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void sendSystemMessage(String message) {
        broadcastMessage(new ChatMessage(
            "Server",
            null,
            message
        ));
    }
    
    private static void broadcastMessage(ChatMessage message) {
        for (ObjectOutputStream oos : clientOOStreams) {
            try {
                message.SetTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                oos.writeObject(message);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
