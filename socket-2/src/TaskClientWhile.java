import java.net.BindException;
import java.util.Scanner;

public class TaskClientWhile {

    public static void main(String arg[]) {
        var client = new ObjectIOClient<ITask, ITask>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ポートを入力してください(5000など) → ");
            int port = scanner.nextInt();
            System.out.println("localhostの" + port + "番ポートに接続を要求します");
            
            client.connect("localhost", port);

            System.out.println("接続されました");

            while(true) {
                System.out.println("指定の数値以下の最大の素数を計算します");
                System.out.println("2以上の整数を入力してください ↓ ");
                String executeNumber = scanner.next();

                var task = (ITask)new LargestPrimeTask();
                task.setExecNumber(Integer.parseInt(executeNumber));

                client.send(task);

                if(task.getExecNumber() <= 1) {
                    break;
                }

                task = client.receive(ITask.class);

                if(task.getResult() == -1) {
                    System.out.println(executeNumber + "以下の素数が見つかりませんでした");
                }
                else
                {
                    System.out.println(executeNumber + "以下の最大の素数は " + task.getResult() + " です");
                }
            }
            scanner.close();

        } // エラーが発生したらエラーメッセージを表示してプログラムを終了する
        catch (BindException be) {
            be.printStackTrace();
            System.err.println("ポート番号が不正か、サーバが起動していません");
            System.err.println("サーバが起動しているか確認してください");
            System.err.println("別のポート番号を指定してください(6000など)");
        } catch (Exception e) {
            System.err.println("エラーが発生したのでプログラムを終了します");
            throw new RuntimeException(e);
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
