class Kamoku {
    private final String name;
    private final int score;
    private final int studentId;

    Kamoku(String name, int score, int studentId) {
        this.name = name;
        this.score = score;
        this.studentId = studentId;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public int getStudentId() {
        return studentId;
    }
}