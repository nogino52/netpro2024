import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class CSVReaderExample {
    
    public static void main(String[] args) {
        String csvFile =  System.getProperty("user.dir") + "/data/file.csv";
        String line = "";
        String csvSplitBy = ","; // CSVファイルの区切り文字を指定してください
        HashSet<String> wards = new HashSet<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // 1行をカンマで分割して単語の配列を取得
                String[] words = line.split(csvSplitBy);

                wards.add(words[3]);
            }//while end
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String ward : wards) {
            System.out.println(ward);
        }
    }//main end
}//class end
