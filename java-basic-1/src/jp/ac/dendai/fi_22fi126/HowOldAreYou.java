package jp.ac.dendai.fi_22fi126;

// C言語では、#include に相当する
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class HowOldAreYou {

    private static int AgeIn(int age, int year) {
        var dateTime = LocalDateTime.now();
        var ageInYear = year - dateTime.getYear() + age;
        return ageInYear;
    }

    private static int BirthYear(int age) {
        var dateTime = LocalDateTime.now();
        var birthYear = dateTime.getYear() - age;
        return birthYear;
    }

    private static String WesternToJapanese(int year) throws IllegalArgumentException {
        var eraNames = new String[] {"明治", "大正", "昭和", "平成", "令和"};
        var eraYears = new int[] {1868, 1912, 1926, 1989, 2019, Integer.MAX_VALUE};

        var eraIndex = -1;
        for(int i = 0; i < eraYears.length; i++) {
            if(year < eraYears[i]) {
                eraIndex = i - 1;
                break;
            }
        }
        if(eraIndex < 0) {
            throw new IllegalArgumentException("年号が見つかりません。");
        }
        
        var era = new StringBuilder();
        era.append(eraNames[eraIndex]);
        era.append(year - eraYears[eraIndex] + 1);
        era.append("年");

        return era.toString();
    }

    private static void OutputAge(int age) {
        System.out.println("2030年の時点でのあなたの年齢は" + AgeIn(age, 2030) + "歳です。");
        System.out.println("あなたは" + WesternToJapanese(BirthYear(age)) + "生まれです。");
    }

    private static boolean InputAge(BufferedReader reader) {
        try {
            System.out.println("何歳ですか?");
            String line = reader.readLine();

            if(line.equals("q") || line.equals("e")) {
                return false;
            }

            int age = Integer.parseInt(line);

            if(age < 0) {
                System.out.println("正の数を入力してください。");
                return true;
            }
            if(120 <= age) {
                System.out.println("入力された数値が大きすぎます。");
                return true;
            }

            OutputAge(age);
            System.out.println("--------------------");
        }
        catch(IOException e) {
            System.out.println(e);
        }
        return true;
    }

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(InputAge(reader)) {
        }
    }
}