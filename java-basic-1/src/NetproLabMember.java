import java.util.Random;

public class NetproLabMember {
    // i=0: 学生総数
    private static final int INDEX_TOTAL = 0;
    // i=1: 女子学生の割合(%)
    private static final int INDEX_FEMALE_RATE = 1;
    // i=2: 岩井研の配属人数
    private static final int INDEX_IWAI = 2;

    // [年数][i]
    private static int[][] createDummy(int year) {
        int[][] data = new int[year][3];
        var femaleRate = 20;
        var random = new Random();
        for(int i = 0; i < year; i++){
            data[i][INDEX_TOTAL] = 100 + random.nextInt(20) - 10;
            data[i][INDEX_FEMALE_RATE] = femaleRate;
            data[i][INDEX_IWAI] = 10 + random.nextInt(6) - 3;
            femaleRate++;
        }
        return data;
    }

    // n2Cr / n1Cr
    private static double calcCombinationRate(int n1, int n2, int r) {
        var totalCombination = combination(n1, r);
        var menCombination = combination(n2, r);
        return (double)menCombination / totalCombination;
    }

    // nCr
    public static long combination(int n, int r) {
        if(n < r) {
            return 0;
        }
        if(r == 0 || n == r) {
            return 1;
        }
        long result = 1;
        for (int i = 1; i <= r; i++) {
            result = result * (n - i + 1) / i;
        }
        return result;
    }

    public static void main(String[] args) {
        var data = createDummy(15);

        double totalRate = 1;
        for(int i = 0; i < data.length; i++) {
            var total = data[i][INDEX_TOTAL];
            var femaleRate = data[i][INDEX_FEMALE_RATE];
            var iwai = data[i][INDEX_IWAI];
            var menCount = total * (100 - femaleRate) / 100;
            
            var menOnlyRate = calcCombinationRate(total, menCount, iwai);
            totalRate *= menOnlyRate;
        }
        System.out.println("Rate: " + totalRate);
    }
}