public class Dish {

    private String name = "noname";
    private int value = 0;

    public Dish() {
    }

    public Dish(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // public void cook(){}

}// Dishend