public class MyExceptionHoliday {
    private static void testHoliday(int[] holidays, int date) throws NoHolidayException {
        for (var holiday : holidays) {
            if (holiday == date) {
                System.out.println("5月" + date + "日は休日です");
                return;
            }
        }
        throw new NoHolidayException();
    }

    public static void main(String[] args) {
        final int[] holidays = {
            3, // 憲法記念日
            4, // みどりの日
            5, // こどもの日
            6, // 振替休日
            11, 12,
            19, 20,
            25, 26
        };

        var scanner = new java.util.Scanner(System.in);
        try {
            while(true) {
                System.out.println("5月の日付を入力してください");
                try {
                    var date = scanner.nextInt();
                    if(date < 1) {
                        System.out.println("終了します");
                        break;
                    }
                    if(date > 31) {
                        System.out.println("1から31の間で入力してください");
                        continue;
                    }

                    testHoliday(holidays, date);
                } catch (NoHolidayException e) {
                    e.printStackTrace();
                }
            }
        }
        finally {
            scanner.close();
        }
    }
}
