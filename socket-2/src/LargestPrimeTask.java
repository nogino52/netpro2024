import java.io.Serializable;

public class LargestPrimeTask implements Serializable, ITask {
    private int execNumber;
    private int result;

    @Override
    public void setExecNumber(int x) {
        execNumber = x;
    }

    @Override
    public int getExecNumber() {
        return execNumber;
    }

    private static int largetPrimeBelow(int n) {
        for(int i = n; i >= 2; i--) {
            if(isPrime(i)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isPrime(int n) {
        if(n <= 1) {
            return false;
        }
        if(n <= 3) {
            return true;
        }

        if(n%2 == 0 || n%3 == 0) {
            return false;
        }

        for(int i = 5; i*i <= n; i += 6) {
            if(n%i == 0 || n%(i+2) == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void exec() {
        result = largetPrimeBelow(execNumber);
    }

    @Override
    public int getResult() {
        return result;
    }
}