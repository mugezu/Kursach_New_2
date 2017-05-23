import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//Умножением матриц, проверкой их на одинаковость
public class UmnMatr {

    public static int[][] multi(int[][] mA, int[][] mB) {
		/*
		 * int n = ma1.length; int[][] res = new int[n][n];
		 * 
		 * for (int i = 0; i < n; i++) { for (int j = 0; j < n; j++) { for (int
		 * k = 0; k < n; k++) { res[i][j] += ma1[i][k] * ma2[k][j]; res[i][j] %=
		 * 2; } } } return res;
		 */
        // System.out.println("MULTI");
        int m = mA.length;
        int n = mB[0].length;
        int o = mB.length;
        int[][] res = new int[m][n];

        // System.out.println("umnMatr mA["+mA.length+"]["+mA[0].length+"]: ");

		/*
		 * for (int i = 0; i < mA.length; i++) { for (int j = 0; j <
		 * mA[0].length; j++) { //System.out.print("mA["+i+"]["+j+"]: ");
		 * //System.out.println(mA[i][j]); } System.out.println(); }
		 * System.out.println();
		 * System.out.println("mB["+mB.length+"]["+mB[0].length+"]:");
		 * 
		 * for (int i = 0; i < mB.length; i++) { for (int j = 0; j <
		 * mB[0].length; j++) { System.out.print("mB["+i+"]["+j+"]: ");
		 * System.out.println(mB[i][j]); } System.out.println(); }
		 * System.out.println();
		 */

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    res[i][j] += mA[i][k] * mB[k][j];
                }
                res[i][j] = res[i][j] % 2;
            }
        }

        // for (int i = 0; i < res.length; i++) {
        // for (int j = 0; j < res[0].length; j++) {
        // System.out.format("%6d ", res[i][j]);
        // }
        // System.out.println();
        // }
        return res;
    }
//Проверка схожости матриц
    public static boolean same(int[][] ma1, int[][] ma2) {
        int n = ma1.length;
        int m = ma1[0].length;
        if(ma1.length!=ma2.length || ma1[0].length!=ma2[0].length)
            return false;
        //if()
        // int k = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (ma1[i][j] != ma2[i][j])return false;
            }
        }
        return true;
    }
//умножает вектора
    public static int multiVect(int[] a1, int[] a2) {
        int res = 0;
        int i = 0;

        for (int n : a1) {
            res += n * a2[i];
            i++;
        }
        return res;
    }

    public static List<Polynom> read() {
        int n = 0;
        String[] a;
        List<Polynom> list = new ArrayList<Polynom>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("polinom.txt"));
            String str = "";
            while ((str = in.readLine()) != null) {
                if (str.contains("Exponent")) {
                    a = str.split(" ");
                    n = Integer.parseInt(a[1]);
                } else {
                    list.add(new Polynom(str, n));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}