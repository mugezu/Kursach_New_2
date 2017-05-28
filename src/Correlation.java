public class Correlation {
    private static int[] sequenc1;    //первая последовательность
    private static double[] result;    //результат вычисления AКФ
    private static int size;        //размер  последовательности


    public Correlation() {
        size = 0;
        result = null;
    }

    // метод установки последовательности
    public static int[] setSeq(int[] seq1) {
        size = seq1.length;
        int[] sequenc1 = new int[size];
        for (int i = 0; i < size; i++) {
            if (seq1[i] == 0) sequenc1[i] = -1;
            else sequenc1[i] = 1;
        }
        return sequenc1;
    }

    //метод доступа к  последовательности
    public int[] getSeq() {
        int[] seq1 = new int[size];
        for (int i = 0; i < size; i++) {
            if (sequenc1[i] == -1) seq1[i] = 0;
            else
                seq1[i] = 1;
        }
        return seq1;
    }


    //методы к полям класса
    public int getSize() {
        return size;
    }

    public double[] getResult() {
        return result;
    }

    //метод вычисления AКФ
    public static double[] calculate(int s1[],int s3[]) {
        int size1 = s1.length;
        result = new double[size1];
        double res;
        int[] s2 = new int[size1];
        for (int index = 0; index < size1; index++) {
            s2[index] = s3[index];
        }

        int[] sdvig = new int[size1];

        for (int i = 0; i < size1; i++) {
            res = UmnMatr.multiVect(s1, s2);
            result[i] = res / size1;

            sdvig[0] = s2[size1 - 1];
            for (int j = 1; j < size1; j++)
                sdvig[j] = s2[j - 1];

            for (int index = 0; index < size1; index++)
                s2[index] = sdvig[index];
        }
        return result;
    }
    public static double[] calculate(int s1[]){
        int size1 = s1.length;
        result = new double [size1];
        double res;
        int[] s2 = new int[size1];
        for (int index = 0; index < size1; index++) {
            s2[index] = s1[index];
        }

        int[] sdvig = new int[size1];

        for (int i = 0; i < size1; i++) {
            res = UmnMatr.multiVect(s1, s2);
            result[i] = res / size1;

            sdvig[0] = s2[size1 - 1];
            for (int j = 1; j < size1; j++)
                sdvig[j] = s2[j - 1];

            for (int index = 0; index < size1; index++)
                s2[index] = sdvig[index];
        }
        return result;
    }

}

