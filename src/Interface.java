import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Создание интерфейса. Сверху объявлены переменные на кнопки, и прочие
 * управляющие элементы.
 */
public class Interface {
    // элементы Управления.
    private JFrame frame;
    private int n;
    private int m;
    private JComboBox<Polynom> comboBox;
    private JComboBox<Polynom> comboBox3;
    private JButton bA;
    private JButton bS;
    private JButton bB;
    private JButton bH;
    private JButton bT;
    private JButton bG;
    private JButton bAPk;
    private JButton bP;
    private JButton bPA;
    private JButton bPB;
    private JButton bSave;
    private JCheckBox sopPoly = new JCheckBox();
    private JCheckBox privPoly = new JCheckBox();


    // Матрицы
    private Matrix A;
    private int[][] S;
    private Matrix B;
    private int unos;
    private Matrix TMP;
    private boolean Svis = false;
    private JFrame Sframe;
    private JPanel Spanel;
    private ArrayList<JLabel> Sl;
    private int T;
    private int T_exs;
    private StringBuffer MSRCondition;
    private int J;
    private int I;
    private int[] i_sequence;
    private int[] j_sequence;

    /**
     * В конструкторе происходит создание интерфейса, а также его запуск и
     * получение входных данных от пользователя.
     */
    public Interface() {
        TMP = new Matrix("nomen", 1, 1);
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);
        JTextField iField = new JTextField(5);
        JTextField jField = new JTextField(5);

        JPanel myPanel = new JPanel(new GridLayout(3, 1));
        JPanel Panel = new JPanel();

        Panel.add(new JLabel("Введите степени полиномов A и B:"));
        Panel.add(xField);
        Panel.add(Box.createHorizontalStrut(15)); // a spacer
        Panel.add(yField);
        JPanel Panel2 = new JPanel();

        Panel2.add(new JLabel("Введите строку и столбец матрицы:"));
        Panel2.add(iField);
        Panel2.add(Box.createHorizontalStrut(15)); // a spacer
        Panel2.add(jField);
        JPanel panelCheck = new JPanel();
        panelCheck.add(new JLabel("Сопряженные полиномы"));
        panelCheck.add(sopPoly);
        panelCheck.add(Box.createHorizontalStrut(15)); // a spacer
        panelCheck.add(new JLabel("Приметивные полиномы"));
        panelCheck.add(privPoly);
        myPanel.add(Panel);
        myPanel.add(Panel2);
        myPanel.add(panelCheck);

        UIManager ui = new UIManager();// Он создает интерфейс
        ColorUIResource pict = new ColorUIResource(255, 133, 133);
        ui.put("OptionPane.background", pict);
        ui.put("Panel.background", pict);
        sopPoly.setBackground(pict);
        privPoly.setBackground(pict);
        Panel.setBackground(pict);
        panelCheck.setBackground(pict);
        Panel2.setBackground(pict);
        myPanel.setBackground(pict);
        Object[] options1 = {"Дальше", "Отмена"};

        // Первое окно
        int result = JOptionPane.showOptionDialog(null, myPanel, "Степени полиномов", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, new ImageIcon("img/img.png"), options1, null);

        if (result == JOptionPane.OK_OPTION) {
            n = Integer.parseInt(xField.getText());
            m = Integer.parseInt(yField.getText());
            I = Integer.parseInt(iField.getText());
            J = Integer.parseInt(jField.getText());
        }

        if (result != JOptionPane.OK_OPTION || n > 11 || m > 11 || n < 1 || m < 1 || I >= n || J >= m || I < 0 || J < 0) {
            JOptionPane.showMessageDialog(frame, "Введите степень от 1 до 11", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }

        // Количество едениц
        unos = n > m ? m : n;

        // Второе окно.
        JTextField xField2 = new JTextField(5);
        JPanel myPanel2 = new JPanel();
        myPanel2.add(new JLabel("Количество едениц на главной диагонале: "));
        myPanel2.add(xField2);
        int result2 = JOptionPane.showOptionDialog(null, myPanel2, "Количество", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, new ImageIcon("img/img.png"), options1, null);

        int u = 0;
        if (result2 == JOptionPane.OK_OPTION) {
            u = Integer.parseInt(xField2.getText());
        } else {
            System.exit(0);
        }

        if (u < 1 || u > unos) {
            JOptionPane.showMessageDialog(frame, "Введите число от 1 до " + unos, "Ошибка!", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
        unos = u;

        // Создание управляющих элементов главного окна
        frame = new JFrame();
        frame.setResizable(false);
        frame.setLocation(100, 100);
        frame.setTitle("АМЛПМ/Пилипчук Анна");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));

        comboBox = new JComboBox<Polynom>();
        comboBox3 = new JComboBox<Polynom>();
        comboBox.setForeground(pict);
        comboBox3.setForeground(pict);
        System.out.println(privPoly.isSelected());
        for (Polynom p : UmnMatr.read()) {
            if (p.getExp() == n) {
                if (privPoly.isSelected() == true) {
                    if (p.getT() == 'H' || p.getT() == 'E' || p.getT() == 'F' || p.getT() == 'G')
                        comboBox.addItem(p);
                } else comboBox.addItem(p);
            }
            if (p.getExp() == m) {
                if (privPoly.isSelected() == true) {
                    if (p.getT() == 'H' || p.getT() == 'E' || p.getT() == 'F' || p.getT() == 'G')
                        comboBox3.addItem(p);
                } else comboBox3.addItem(p);
            }
        }

        // Создание кнопок.
        bA = new JButton("A");
        bA.setBounds(0, 0, 0, 0);
        bS = new JButton("S");
        bS.setBounds(500, 500, 500, 500);
        bB = new JButton("B");
        bB.setBounds(500, 500, 500, 500);
        bH = new JButton("Хэмминг");
        bAPk = new JButton("T(теор)");
        bP = new JButton("T(эксп)");
        bPA = new JButton("T(теор матр. А)");
        bPB = new JButton("T(теор матр. B)");
        bG = new JButton("АКФ");
        bT = new JButton("Итог");
        bSave = new JButton("Сохранить состояния");

        bA.setForeground(pict);

        bS.setForeground(pict);
        bB.setForeground(pict);
        bH.setForeground(pict);
        bAPk.setForeground(pict);
        bP.setForeground(pict);
        bG.setForeground(pict);
        bT.setForeground(pict);
        bPA.setForeground(pict);
        bPB.setForeground(pict);
        bSave.setForeground(pict);

        panel.add(bA);
        panel.add(bB);
        panel.add(bS);
        panel.add(comboBox);
        panel.add(comboBox3);
        panel.add(bH);
        panel.add(bG);
        panel.add(bAPk);
        panel.add(bP);
        panel.add(bPA);
        panel.add(bPB);
        panel.add(bT);
        panel.add(bSave);


        // Создание матриц
        System.out.println("Setting matrix A [" + n + "][" + n + "]");
        A = new Matrix("A", n, n);
        B = new Matrix("B", m, m);

        if (sopPoly.isSelected() == true) {
            Polynom p = (Polynom) comboBox.getSelectedItem();
            A.setPol(p.getSop(), true);
            p = (Polynom) comboBox3.getSelectedItem();
            B.setPol(p.getSop(), false);
        } else {
            A.setPol((Polynom) comboBox.getSelectedItem(), true);
            B.setPol((Polynom) comboBox3.getSelectedItem(), false);
        }
        int[][] MTR = A.getMatr();

        S = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                S[i][j] = i == j && unos-- > 0 ? 1 : 0;
                System.out.print(S[i][j] + " ");
            }
            System.out.println();
        }

        // Создание окна суммы
        Sframe = new JFrame("S");
        Sframe.setResizable(true);
        Sframe.setLocation(100, 300);
        Sframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Spanel = new JPanel(new GridLayout(n, m, 20, 5));
        Sl = new ArrayList<JLabel>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                JLabel lab = new JLabel();
                lab.setText(Integer.toString(S[i][j]));
                Sl.add(lab);
                Spanel.add(Sl.get(i * m + j));
            }
        }

        Sframe.getContentPane().add(Spanel);
        Sframe.pack();


        T = A.T() * B.T();
        System.out.println("S PERIOD: " + T);

        // Присваивание обработчиков событий на соответствующие кнопки.

        System.out.println("A:");
        bA.addActionListener(new ButtonEventListener(A));
        System.out.println("S:");
        bS.addActionListener(new SEventListener());
        System.out.println("B:");
        bB.addActionListener(new ButtonEventListener(B));
        bT.addActionListener(new SchetEventListener());
        bH.addActionListener(new HemEventListener());
        bAPk.addActionListener(new PeriodEventListener());
        bP.addActionListener(new PeriodExpEventListener());
        bPA.addActionListener(new PeriodAExpEventListener());
        bPB.addActionListener(new PeriodBExpEventListener());
        bG.addActionListener(new AkfEventListener());
        bSave.addActionListener(new SaveFile());

        comboBox.addActionListener(new BoxEventListener(comboBox, A, true));
        comboBox3.addActionListener(new BoxEventListener(comboBox3, B, false));

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.pack();
    }

    /**
     * Показывает матрицу B
     */
    private class ButtonEventListener implements ActionListener {
        Matrix m;

        public ButtonEventListener(Matrix ma) {
            this.m = ma;
        }

        public void actionPerformed(ActionEvent e) {
            m.show();
        }
    }

    /**
     * Показывает матрицу S
     */
    private class SEventListener implements ActionListener {
        public SEventListener() {
        }

        public void actionPerformed(ActionEvent e) {
            Svis = !Svis;
            Sframe.setVisible(Svis);
        }
    }

    public void updateS() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Sl.get(i * m + j).setText(Integer.toString(S[i][j]));
            }
        }
    }

    /**
     * Считает итог. С помощью умножения матриц и метода выше ^
     */
    private class SchetEventListener implements ActionListener {
        int[][] s1 = new int[n][m];

        public void actionPerformed(ActionEvent e) {
            s1 = UmnMatr.multi(A.getMatr(), S);
            s1 = UmnMatr.multi(s1, B.getMatr());
            S = s1;
            updateS();
            Svis = true;
            Sframe.setVisible(Svis);
        }
    }

    /**
     * Печатает матрицу в консоль.
     */
    public void printr(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Считает вес Хемминга... Алгоритм в actionPerformed
     */
    private class HemEventListener implements ActionListener {
        int[][] s1 = new int[n][m];
        int[] pr = new int[T];
        int k = 0;
        int i = 0;

        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    s1[i][j] = S[i][j];
                }
            }

            while (i < T) {
                s1 = UmnMatr.multi(A.getMatr(), s1);
                s1 = UmnMatr.multi(s1, B.getMatr());
                pr[k] = s1[0][1];
                k++;
                i++;
            }
            int f = 0;
            for (int k = 0; k < T; k++)
                f += pr[k];
            JOptionPane.showMessageDialog(frame, "Вес Хэмминга: " + f, "", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Рисует график.
     */
    private class AkfEventListener implements ActionListener {
        int[][] s1 = new int[n][m];

        public void actionPerformed(ActionEvent e) {
            int k = 0;
            int i = 0;
            if (T_exs == 0) {
                JOptionPane.showConfirmDialog(null, "Расчеты не были проведены, нажмите на кнопку " + bP.getText(), "Ошибка", JOptionPane.CLOSED_OPTION);
                return;
            }
            for (int ind = 0; ind < n; ind++) {
                for (int j = 0; j < m; j++) {
                    s1[ind][j] = S[ind][j];
                }
            }
            i_sequence = new int[T_exs];
            j_sequence = new int[T_exs];
            System.out.println(Matrix.MatrixToString(A.getMatr()));
            System.out.println(Matrix.MatrixToString(B.getMatr()));
            while (i < T_exs) {
                s1 = UmnMatr.multi(A.getMatr(), s1);
                s1 = UmnMatr.multi(s1, B.getMatr());
                int dec = 0;
                for (int j = n - 1; j >= 0; j--) {
                    dec += s1[j][J];
                }
                j_sequence[i] = dec;
                dec=0;
                for (int j = m - 1; j >= 0; j--) {
                    dec += s1[I][j] ;
                }
                i_sequence[i] = dec;
                i++;
            }
            // pисуем график
            XYSeries series = new XYSeries(" ");
            double[] a = Correlation.calculate(j_sequence);
            for (int j = 0; j < T_exs; j++)
                series.add(j, a[j]);

            // настройки окна графика
            XYDataset xyDataset = new XYSeriesCollection(series);
            JFreeChart chart = ChartFactory.createXYLineChart("АКФ", "t", "R(t)", xyDataset, PlotOrientation.VERTICAL,
                    true, true, true);
            JFrame frame = new JFrame("АКФ");
            // Помещаем график на фрейм
            frame.getContentPane().add(new ChartPanel(chart));
            frame.setSize(400, 300);
            frame.setVisible(true);

             series = new XYSeries(" ");
             a = Correlation.calculate(i_sequence);
            for (int j = 0; j < T_exs; j++)
                series.add(j, a[j]);

            // настройки окна графика
             xyDataset = new XYSeriesCollection(series);
             chart = ChartFactory.createXYLineChart("АКФ", "t", "R(t)", xyDataset, PlotOrientation.VERTICAL,
                    true, true, true);
             frame = new JFrame("АКФ");
            // Помещаем график на фрейм
            frame.getContentPane().add(new ChartPanel(chart));
            frame.setSize(400, 300);
            frame.setVisible(true);

            series = new XYSeries(" ");
            a = Correlation.calculate(i_sequence,j_sequence);
            for (int j = 0; j < T_exs; j++)
                series.add(j, a[j]);

            // настройки окна графика
            xyDataset = new XYSeriesCollection(series);
            chart = ChartFactory.createXYLineChart("ВКФ", "t", "R(t)", xyDataset, PlotOrientation.VERTICAL,
                    true, true, true);
            frame = new JFrame("АКФ");
            // Помещаем график на фрейм
            frame.getContentPane().add(new ChartPanel(chart));
            frame.setSize(400, 300);
            frame.setVisible(true);
        }


    }

 /*   private class Akf2EventListener implements ActionListener {
        int[][] s1 = new int[n][m];
        int T = (int) Math.pow(2, n) - 1;
        int[] pr = new int[T];
        double[] res = new double[T];
        int k = 0;
        int i = 0;

        public void actionPerformed(ActionEvent e) {
            // создаем первый массив
            while (i < T) {
                s1 = UmnMatr.multi(A.getMatr(), TMP.getMatr());
                s1 = UmnMatr.multi(s1, B.getMatr());
                TMP.setMatr(s1);
                TMP.update();
                pr[k] = TMP.getMatr()[0][1];
                k++;
                i++;
            }

            // pисуем график
            XYSeries series = new XYSeries(" ");

            for (int i = 0; i < T; i++)
                series.add(i, akf(i));

            // настройки окна графика
            XYDataset xyDataset = new XYSeriesCollection(series);
            JFreeChart chart = ChartFactory.createXYLineChart("АКФ", "t", "R(t)", xyDataset, PlotOrientation.VERTICAL,
                    true, true, true);
            JFrame frame = new JFrame("АКФ");
            // Помещаем график на фрейм
            frame.getContentPane().add(new ChartPanel(chart));
            frame.setSize(400, 300);
            frame.setVisible(true);
        }

        private double akf(int i) {
            res = Correlation.calculate(Correlation.setSeq(pr));
            return res[i];
        }

    }*/

    /// Далее идут обработчии события на боксы выбора. Вытягивают нужный полином
    /// в зависимости от действий пользователя.

    private class BoxEventListener implements ActionListener {
        Matrix m;
        JComboBox<Polynom> jComboBox;
        boolean top;

        public BoxEventListener(JComboBox<Polynom> jComboBox, Matrix m, boolean top) {
            this.m = m;
            this.jComboBox = jComboBox;
            this.top = top;
        }

        public void actionPerformed(ActionEvent event) {
            T_exs=0;
            Polynom p = (Polynom) jComboBox.getSelectedItem();
            if (sopPoly.isSelected() == true)
                m.setPol(p.getSop(), this.top);
            else m.setPol(p, this.top);
            m.update();
        }
    }

    private class Box21EventListener implements ActionListener {
        Matrix m;
        JComboBox<Integer> jComboBox;

        public Box21EventListener(JComboBox<Integer> jComboBox, Matrix m) {
            this.m = m;
            this.jComboBox = jComboBox;
        }

        public void actionPerformed(ActionEvent event) {
            m.setRang((Integer) jComboBox.getSelectedItem());
            m.update();
        }
    }

    private class Box31EventListener implements ActionListener {
        Matrix m2;
        JComboBox<Polynom> jComboBox;

        public Box31EventListener(JComboBox<Polynom> jComboBox, Matrix m) {
            this.m2 = m2;
            this.jComboBox = jComboBox;
        }

        public void actionPerformed(ActionEvent event) {
            m2.ObrMatr((Polynom) jComboBox.getSelectedItem());
            m2.update();
        }
    }

    // теоретический период
    private class PeriodEventListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            T = A.T() * B.T();
            String s = "Период:  " + T;
            JOptionPane.showMessageDialog(null, s, "Tеоретический период", JOptionPane.PLAIN_MESSAGE);
        }
    }

    // эксперuментальный период матрицы А
    private class PeriodExpEventListener implements ActionListener {
        int[][] s1 = new int[n][m];
        int[][] start = new int[n][m];
        boolean p;

        public void actionPerformed(ActionEvent e) {
            int k = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    s1[i][j] = start[i][j] = S[i][j];
                }
            }
            MSRCondition = new StringBuffer();
            do {
                s1 = UmnMatr.multi(A.getMatr(), s1);
                s1 = UmnMatr.multi(s1, B.getMatr());
                MSRCondition.append(k + ":" + System.lineSeparator() + Matrix.MatrixToString(s1));
                p = UmnMatr.same(start, s1);
                k++;
            } while (!p && k <= T);
            s1 = UmnMatr.multi(A.getMatr(), s1);
            s1 = UmnMatr.multi(s1, B.getMatr());
            T_exs = k;
            MSRCondition.append(0 + ":" + System.lineSeparator() + Matrix.MatrixToString(s1));
            String s;
            if (!p)
                s = "Ошибка, экспериментальный период получается больше теоретического (" + k + ")";
            else
                s = "Период:  " + k;

            JOptionPane.showMessageDialog(null, s, "Эксперuментальный период", JOptionPane.PLAIN_MESSAGE);
        }
    }

    // Теор. период матрицы A
    private class PeriodAExpEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String s;
            s = "Период:  " + A.T();

            JOptionPane.showMessageDialog(null, s, "Теоретический период А", JOptionPane.PLAIN_MESSAGE);
        }
    }

    //сохранение в файл
    private class SaveFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (MSRCondition == null) {
                JOptionPane.showConfirmDialog(null, "Расчеты не были проведены, нажмите на кнопку " + bP.getText(), "Ошибка", JOptionPane.CLOSED_OPTION);

            }
            FileWriter FW = null;
            try {
                FW = new FileWriter(new File("").getAbsolutePath() + "\\T=" + T + "_T_A=" + A.T() + "_T_B=" + B.T() + "_" + System.nanoTime() + ".txt", false);
                FW.write(MSRCondition.toString());
            } catch (
                    Exception c) {
                c.printStackTrace();
            } finally {
                try {
                    if (FW != null)
                        FW.close();
                } catch (IOException c) {
                    c.printStackTrace();
                }
            }
        }
    }

    // Теор. период матрицы B
    private class PeriodBExpEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String s;
            s = "Период:  " + B.T();

            JOptionPane.showMessageDialog(null, s, "Теоретический период В", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Interface mFrame = new Interface();
                mFrame.setVisible(true);
            }
        });
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);

    }
}
