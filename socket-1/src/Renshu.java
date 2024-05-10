public class Renshu {
    public int doubleValue(int x) {
        return x * 2;
    }

    public int sumUpToN(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    public int sumFromPtoQ(int p, int q) {
        if(p > q) {
            return -1;
        }

        int sum = 0;
        for (int i = p; i <= q; i++) {
            sum += i;
        }
        return sum;
    }

    public int selectMaxValue(int[] a) {
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (max < a[i]) {
                max = a[i];
            }
        }
        return max;
    }

    public int selectMinValue(int[] a) {
        int min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min > a[i]) {
                min = a[i];
            }
        }
        return min;
    }

    public void swapArrayElements(int[] p, int i, int j) {
        int tmp = p[i];
        p[i] = p[j];
        p[j] = tmp;
    }

    public boolean swapTwoArrays(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            int tmp = a[i];
            a[i] = b[i];
            b[i] = tmp;
        }
        return true;
    }

    public int sumFromArrayIndex(int[] a, int index) {
        if (index < 0 || a.length <= index){
            return -1;
        }
        int sum = 0;
        for (int i = index; i < a.length; i++) {
            sum += a[i];
        }
        return sum;
    }

    public int selectMaxIndex(int[] a) {
        int max = a[0];
        int maxIndex = 0;
        for (int i = 1; i < a.length; i++) {
            if (max < a[i]) {
                max = a[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public int selectMinIndex(int[] a) {
        int min = a[0];
        int minIndex = 0;
        for (int i = 1; i < a.length; i++) {
            if (min > a[i]) {
                min = a[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

}
