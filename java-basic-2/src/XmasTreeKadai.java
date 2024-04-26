public class XmasTreeKadai {
    public static void OutputTree(int maxLeafWidth, int trunkWidth, int trunkHeight, char leafChar, char trunkChar, char snowChar) {
        var n = maxLeafWidth;
        var insertSpace = n % 2 == 0 ? true : false;
        // 葉を描画
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                System.out.print((i+j) % 2 == 0 ? snowChar : ' ');
            }
            if(insertSpace) {
                System.out.print(' ');
            }

            for (int j = 0; j < i * 2 + 2; j++) {
                System.out.print(leafChar);
            }
            System.out.println();
        }

        // 幹を描画
        for (int i = 0; i < trunkHeight; i++) {
            for (int j = 0; j < n - trunkWidth / 2; j++) {
                System.out.print(' ');
            }
            for (int j = 0; j < trunkWidth; j++) {
                System.out.print(trunkChar);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        var scanner = new java.util.Scanner(System.in);
        try {
            while(true) {
                System.out.println("葉の最大幅, 幹の幅, 幹の高さ, 葉の文字, 幹の文字, 雪の文字をカンマ区切りで入力してください");
                var input = scanner.nextLine();
                if (input.equals("exit")) {
                    System.out.println("終了します");
                    break;
                }
                var inputArgs = input.split(",");
                try
                {
                    var maxLeafWidth = Integer.parseInt(inputArgs[0]);
                    var trunkWidth = Integer.parseInt(inputArgs[1]);
                    var trunkHeight = Integer.parseInt(inputArgs[2]);
                    var leafChar = inputArgs[3].charAt(0);
                    var trunkChar = inputArgs[4].charAt(0);
                    var snowChar = inputArgs[5].charAt(0);
                    System.out.println("葉の最大幅: " + maxLeafWidth);
                    System.out.println("幹の幅: " + trunkWidth);
                    System.out.println("幹の高さ: " + trunkHeight);
                    System.out.println("葉の文字: " + leafChar);
                    System.out.println("幹の文字: " + trunkChar);
                    System.out.println("雪の文字: " + snowChar);
                    OutputTree(maxLeafWidth, trunkWidth, trunkHeight, leafChar, trunkChar, snowChar);
                }
                catch (Exception e)
                {
                    System.out.println("引数が不正です");
                }
            }
        } finally {
            scanner.close();
        }
    }
}
