import java.util.ArrayList;
import java.util.List;

public class PrimeCheck {
    public static boolean IsPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] primePairCounts = new int[10][10];
        List<List<Integer>> groupedPrimes = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            groupedPrimes.add(new ArrayList<Integer>());
        }
        
        var lastPrime = 2;
        for(int i = 3; i < 100000; i++) {
            if(!IsPrime(i))
                continue;

            int lastDigit = i % 10;
            groupedPrimes.get(lastDigit).add(i);
            primePairCounts[lastPrime % 10][lastDigit]++;
            lastPrime = i;
        }

        // 下一桁グループごとに素数を表示
        System.out.println("Grouped Primes by last digit");
        for(int i = 0; i < 10; i++) {
            System.out.println("[" + i + "]");
            for(int prime : groupedPrimes.get(i)) {
                System.out.print(prime + "\t");
            }
            System.out.println();
        }

        // 出現回数をランキング順に表示
        System.out.println("Prime Pair Counts Ranking");
        for(int rank = 0; rank < 100; rank++) {
            int maxCount = 0;
            int maxI = 0;
            int maxJ = 0;
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    if(primePairCounts[i][j] > maxCount) {
                        maxCount = primePairCounts[i][j];
                        maxI = i;
                        maxJ = j;
                    }
                }
            }
            if(maxCount == 0) {
                break;
            }
            System.out.println((rank + 1) + "位\t: [" + maxI + "-" + maxJ + "] = " + maxCount);
            primePairCounts[maxI][maxJ] = 0;
        }
    }
}
