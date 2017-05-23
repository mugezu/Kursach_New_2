import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//представление матрицы
public class Matrix {
    private JFrame frame;
    private JPanel panel;
    private List<JLabel> l;
    private int[][] matr;
    private int[][] obr2;
    private boolean vis;
    private String n;
    private int sr;
    private int sc;
    private int T;

    public Matrix(String name, int sizeRow, int sizeCol) {


        System.out.println("in constructor [" + sizeRow + "][" + sizeCol + "]");
        this.n = name;
        this.sr = sizeRow;
        this.sc = sizeCol;
        frame = new JFrame(n);
        frame.setResizable(true);
        frame.setLocation(100, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel(new GridLayout(sr, sc, 20, 5));
        matr = new int[sr][sc];


        l = new ArrayList<JLabel>();
        for (int i = 0; i < sr * sc; i++) {
            JLabel lab = new JLabel();
            lab.setText("*");

            l.add(lab);
            panel.add(l.get(i));
        }
        frame.getContentPane().add(panel);
        frame.pack();
        System.out.println("Constructor is almost done [" + this.sr + "][" + this.sc + "]");
    }


    public int[][] getMatr() {
        return matr;
    }

    public int[][] getObrMatr() {
        return obr2;
    }

    public void setMatr(int[][] matr) {
        this.matr = matr;
    }

    public void update() {
        for (int i = 0; i < sr; i++) {
            for (int j = 0; j < sc; j++) {
                l.get(i * sc + j).setText(Integer.toString(matr[i][j]));
            }
        }

    }

    public void show() {
        vis = true;

        update();
        frame.setVisible(vis);
    }

    public void hide() {
        vis = false;
        frame.setVisible(vis);
    }

    public boolean isVis() {
        return vis;
    }

    public void setPol(Polynom p, boolean top) {

        System.out.println();
        System.out.println("New matrix will be [" + sr + "][" + sc + "]");

        matr = new int[sr][sc];

        char[] temp = p.getBinom();
        System.out.println("polynom (" + p.getJ() + ")");
        for (int i = 0; i < temp.length; i++) {
            System.out.print(temp[i] + " ");
        }


        int[] firstRow = new int[temp.length - 1];
        for (int i = 1; i < temp.length; i++) {
            firstRow[i - 1] = Character.getNumericValue((temp[i]));
        }

        if (top == true) {
            matr[0] = firstRow;
            for (int i = 1; i < matr.length; i++) {
                for (int j = 0; j < matr[i].length; j++) {
                    if (i == j + 1) {
                        matr[i][j] = 1;
                    } else
                        matr[i][j] = 0;
                }
            }
        } else {
            for (int i = 0; i < matr.length; i++) {
                matr[i][0] = firstRow[i];
                for (int j = 1; j < matr[i].length; j++) {
                    if (i + 1 == j) {
                        matr[i][j] = 1;
                    } else
                        matr[i][j] = 0;
                }
            }
        }
        System.out.println("New matrix:");
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr[0].length; j++) {
                System.out.print(matr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        this.T = (int) ((Math.pow(2, matr.length) - 1) / nod((int) (Math.pow(2, matr.length) - 1), p.getJ()));
        System.out.println("PERIOD = " + this.T);
    }

    public int T() {
        return this.T;
    }

    public int nod(int a, int b) {
        return b == 0 ? a : nod(b, a % b);
    }

    public int nok(int a, int b) {
        return a / nod(a, b) * b;
    }

    public void setRang(Integer i) {
        matr = new int[sr][sc];
        for (int j = 0; j < i; j++)
            matr[j][j] = 1;
    }

    //Обращает матрицу в на основе полинома
    public void ObrMatr(Polynom p) {
        matr = new int[sr][sc];
        int[][] obr2 = new int[sr][sc];
        int[][] obr = new int[sr][sc];
        int[][] s2 = new int[sr][sc];
        char[] temp = p.getBinom();

        for (int i = 1; i < sc; i++)
            matr[i][i - 1] = 1;

        for (int i = 0; i < sc; i++)
            matr[0][i] = 0;

        for (int i = temp.length - 1; i > 0; i--)
            matr[0][i + sc - temp.length] = temp[i] == '1' ? 1 : 0;

        for (int i = 0; i < sr; i++)
            s2[i][i] = 1;

        // 1ю строку - в конец, остальные поднимаем
        for (int j = 0; j < sr; j++) {
            obr[sr - 1][j] = matr[0][j];
            obr2[sr - 1][j] = s2[0][j];
        }

        for (int i = 0; i < sr - 1; i++)
            for (int j = 0; j < sr; j++) {
                obr[i][j] = matr[i + 1][j];
                obr2[i][j] = s2[i + 1][j];
            }

        // получаем единичную матрицу(обратную)
        // проверяем последнюю строку на наличие "1" кроме крайнего элемента
        for (int j = 0; j < sc - 1; j++)
            if (obr[sr - 1][j] == 1) { // j - номер строки
                for (int i = 0; i < sr; i++) {
                    obr[sr - 1][i] = (obr[sr - 1][i] + obr[j][i]) % 2;
                    obr2[sr - 1][i] = (obr2[sr - 1][i] + obr2[j][i]) % 2;
                }
            }
        for (int i = 0; i < sr; i++)
            for (int j = 0; j < sr; j++)
                matr[i][j] = obr2[i][j];
    }

    public static String MatrixToString(int[][] matrix) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < matrix.length; i++, sb.append(System.lineSeparator()))
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j] + " ");
            }
        return sb.toString();
    }
}