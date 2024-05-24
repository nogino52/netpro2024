import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface IConnectionHandler<TInput> {
    boolean isConnected();
    TInput receive(Class<TInput> type) throws IOException, ClassNotFoundException;

    default void receiveContinuously(Class<TInput> type, Predicate<TInput> onReceive) throws IOException, ClassNotFoundException {
        while(isConnected()) {
            var obj = receive(type);
            if(!onReceive.test(obj)) {
                break;
            }
        }
    }

    default void receiveContinuouslyAsync(Class<TInput> type, Consumer<TInput> onReceive) {
        try {
            while(isConnected()) {
                var obj = receive(type);
                CompletableFuture.runAsync(() -> onReceive.accept(obj));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
