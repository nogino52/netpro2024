import java.io.Serializable;

public class ChatMessage implements Serializable {
    String userName;
    String time;
    String message;

    public ChatMessage(String userName, String time, String message) {
        this.userName = userName;
        this.time = time;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public String SetTime(String time) {
        return this.time = time;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("[").append(time).append("] ");
        builder.append(userName).append(": ");
        builder.append(message);
        return builder.toString();
    }
}