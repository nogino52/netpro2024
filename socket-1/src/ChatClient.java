import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.Socket; //ネットワーク関連のパッケージを利用する
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class ChatClient {
    private static boolean isRunning = true;

    public static void receiveMessageAsync(Socket socket) {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ChatMessage message;
            while ((message = (ChatMessage) ois.readObject()) != null) {
                System.out.println(message.toString());
            }
        } catch (SocketException e) {
            System.out.println("サーバーから切断されました");
        }catch (Exception e) {
            e.printStackTrace();
        }

        isRunning = false;
    }

    private static void ClearLine() {
        System.out.print("\033[1A");
        System.out.print("\033[K");
    }

    private static void ClearScreen() {
        System.out.print("\033[H\033[2J");
    }

    public static void main(String arg[]) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ユーザ名を入力してください");
            String userName = scanner.next();
            System.out.println("ユーザー名: " + userName);
            
            var port = 5050;
            Socket socket = new Socket("localhost", port);
            System.out.println("接続されました");

            ClearScreen();
            System.out.println("Local Chat: " + port + " ユーザ: " + userName + " (exit で終了)");
            System.out.println("--------------------------------------------------");

            CompletableFuture.runAsync(() -> receiveMessageAsync(socket));

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            while(isRunning) {
                String messageText = scanner.nextLine();
                if(!isRunning) {
                    break;
                }
                if (messageText.isEmpty()) {
                    continue;
                }
                if (messageText.equals("exit")) {
                    break;
                }
                ClearLine();
                ChatMessage chatMessage = new ChatMessage(
                    userName,
                    null,
                    messageText
                );

                oos.writeObject(chatMessage);
                oos.flush();
            }
            scanner.close();
            oos.close();
            socket.close();

        }
        catch (BindException be) {
            be.printStackTrace();
            System.err.println("ポート番号が不正か、サーバが起動していません");
            System.err.println("サーバが起動しているか確認してください");
            System.err.println("別のポート番号を指定してください(6000など)");
        } catch (Exception e) {
            System.err.println("エラーが発生したのでプログラムを終了します");
            throw new RuntimeException(e);
        }
    }
}
