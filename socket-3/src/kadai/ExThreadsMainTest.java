package kadai;

public class ExThreadsMainTest extends Thread {
    private String prefix;

    public static void main(String[] args) {
        
        for(int i = 0; i < 26; i++) {
            ExThreadsMainTest exThreadsMainTest = new ExThreadsMainTest(String.valueOf((char)(97 + i)));

            Thread thread = new Thread(exThreadsMainTest);
            thread.start();
        }
        
    }

    public ExThreadsMainTest(String prefix) {
        this.prefix = prefix;
    }

    public void run() {
        // この try-catch ブロックは、0 から 9 までの値を 1000 ミリ秒間隔で出力するループを実行します。
        try {
            for(int i = 0; i < 10; i++) {
                System.out.println(prefix + (i + 1));

                // スレッドを 1000 ミリ秒間一時停止します。
                Thread.sleep(1000);  // ミリ秒単位のスリープ時間
            }
        }
        catch(InterruptedException e) {
            // スレッドが中断された場合は、例外を出力します。
            System.err.println(e);
        }
    }
}
