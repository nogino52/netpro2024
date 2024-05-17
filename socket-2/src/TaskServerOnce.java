import java.io.IOException;
import java.net.BindException;
import java.util.Scanner;


public class TaskServerOnce {
    private ObjectIOServer<ITask, ITask>.ClientHandler handler;

    public void onConnect(ObjectIOServer<ITask, ITask>.ClientHandler handler) {
        System.out.println("クライアントが接続しました");
        
        this.handler = handler;
    }

    public boolean onReceive(ITask task) {
        System.out.println("TaskRunnerServer: " + task);

        if(task.getExecNumber() <= 1) {
            return false;
        }

        task.exec();

        try {
            handler.send(task);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static void main(String arg[]) {
        var server = new ObjectIOServer<ITask, ITask>();
        try {
            var taskRunner = new TaskServerWhile();
            
            /* 通信の準備をする */
            Scanner scanner = new Scanner(System.in);
            System.out.print("ポートを入力してください(5000など) → ");
            int port = scanner.nextInt();
            scanner.close();
            System.out.println("localhostの" + port + "番ポートで待機します");
            
            /* サーバーを起動してクライアントからの接続を待つ */
            server.start(port);
            var handler = server.connectClient();
            taskRunner.onConnect(handler);
            handler.receiveContinuously(ITask.class, taskRunner::onReceive);

        } // エラーが発生したらエラーメッセージを表示してプログラムを終了する
        catch (BindException be) {
            be.printStackTrace();
            System.out.println("ポート番号が不正、ポートが使用中です");
            System.err.println("別のポート番号を指定してください(6000など)");
        } catch (Exception e) {
            System.err.println("エラーが発生したのでプログラムを終了します");
            throw new RuntimeException(e);
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
