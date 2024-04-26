import java.util.Random;
import java.util.stream.Stream;

public class HeikinCKadai {
    public static void main(String[] args) {
        var kamokuName = "数学";

        var kamokus = new Kamoku[100];
        var random = new Random();
        for (int i = 0; i < kamokus.length; i++) {
            var score = random.nextInt(101);
            kamokus[i] = new Kamoku(kamokuName, score, i);
        }

        var sum = 0;
        for (var kamoku : kamokus) {
            sum += kamoku.getScore();
        }
        var average = (double) sum / kamokus.length;
        System.out.println(kamokuName + "の平均点: " + average + "点");

        System.out.println("合格者一覧");
        Stream.of(kamokus)
            .filter(kamoku -> kamoku.getScore() >= 80)
            .sorted((k1, k2) -> k1.getScore() - k2.getScore())
            .forEach(kamoku -> System.out.println(kamoku.getStudentId() + "番: " + kamoku.getScore() + "点"));

    }
}
