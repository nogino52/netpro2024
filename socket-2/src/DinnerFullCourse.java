public class DinnerFullCourse {
    private Dish[] list = new Dish[5];

    public DinnerFullCourse() {
        list[0] = new Dish("グレープフルーツとアボカドのサラダ", 10);
        list[1] = new Dish("ベーコンと玉ねぎのコンソメスープ", 2);
        list[2] = new Dish("ホタテとアスパラのバターソテー", 20);
        list[3] = new Dish("牛肉の赤ワイン煮込み", 30);
        list[4] = new Dish("チョコレートケーキ", 40);
    }

    public void eatAll() {
        var builder = new StringBuilder();
        for (var i = 0; i < list.length; i++) {
            appendDish(builder, list[i]);
            if (i < list.length - 1) {
                builder.append(",");
            }
        }

        System.out.println("たかしへ、本日のフルコースは " + builder.toString() + " です");
    }

    private void appendDish(StringBuilder builder, Dish dish)
    {
        builder.append(dish.getName());
        builder.append("=");
        builder.append(dish.getValue());
    }

    public static void main(String[] args) {
		DinnerFullCourse fullcourse = new DinnerFullCourse();
        fullcourse.eatAll();
    }
}