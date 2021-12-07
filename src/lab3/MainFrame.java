package lab3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.io.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
    static final int WIDTH=940;
    static final int HEIGHT=480;
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    private GornerTableModel data;
    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();
    static Double coefficients[]=null;
    private AboutFrame Aframe=new AboutFrame();
    private JTextField from;
    private JTextField to;
    private JTextField step;
    private JLabel l1_1;
    private JLabel l1_2;
    private JLabel l1_3;
    private Box hBoxResult;
    private JButton btnCalc = null;
    private JButton btnClear = null;
    private JFileChooser fileChoose;
    private JMenuItem menuFileToText;
    private JMenuItem menuFileToGraphd;
    private JMenuItem menuFileToCsv;
    private JMenuItem menuTablePalindrome;
    private JMenuItem menuTableFind;
    private JMenuItem menuHelpAbout;

    public MainFrame(){
        super("Лаба3");
        setSize(WIDTH,HEIGHT);
        Toolkit kit=Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,(kit.getScreenSize().height - HEIGHT)/2);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Файл");
        JMenu menuTable = new JMenu("Таблица");
        JMenu menuHelp = new JMenu("Справка");

        fileChoose=new JFileChooser();
        menuFileToText =new JMenuItem("Сохранить в текстовый файл");
        menuFileToText.addActionListener(new ActionListener(){
                                             public void actionPerformed(ActionEvent ev) {
                                                 fileChoose.setCurrentDirectory(new File("~"));
                                                 if(fileChoose.showSaveDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION){
                                                     File file=fileChoose.getSelectedFile();
                                                     try {
                                                         PrintStream out=new PrintStream(file);
                                                         out.println("Результат таболурование по схеме горнера");
                                                         out.print("Многочлен: ");
                                                         for(int i=0;i<coefficients.length;i++){
                                                             out.print(coefficients[i]+"*X^"+(coefficients.length-i-1));
                                                             if(i!=coefficients.length-1)
                                                                 out.print("+");
                                                             else
                                                                 out.print("\n");
                                                         }
                                                         out.print("Интервал от: ");
                                                         out.print(from.getText());
                                                         out.print(" до: ");
                                                         out.print(to.getText());
                                                         out.print(" с шагом: ");
                                                         out.println(step.getText());
                                                         out.println("=========================");
                                                         for(int y=0;y<data.getRowCount();y++){
                                                             out.print("Значение в точке ");
                                                             out.print(formatter.format(data.getValueAt(y, 0)));
                                                             out.print(" равно ");
                                                             out.print(formatter.format(data.getValueAt(y, 1)));
                                                             out.print(", а не по горнеру ");
                                                             out.print(formatter.format(data.getValueAt(y, 2)));
                                                             out.print(" и разница состовляет:");
                                                             out.print(formatter.format(data.getValueAt(y, 3)));
                                                             out.print("\n");
                                                         }
                                                     } catch (FileNotFoundException e) {
                                                         System.out.println("Не удалось создать файл");
                                                     }
                                                 }
                                             }
                                         }
        );
        menuFileToGraphd =new JMenuItem("Сохранить данные для построения графика");
        menuFileToGraphd.addActionListener(new ActionListener(){
                                               public void actionPerformed(ActionEvent ev) {
                                                   fileChoose.setCurrentDirectory(new File("~"));
                                                   if(fileChoose.showSaveDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION){
                                                       File file=fileChoose.getSelectedFile();
                                                       try {
                                                           DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
                                                           for (int i = 0; i<data.getRowCount(); i++) {
                                                               out.writeDouble((Double)data.getValueAt(i,0));
                                                               out.writeDouble((Double)data.getValueAt(i,1));
                                                           }
                                                           out.close();
                                                       } catch (Exception e) {
                                                           System.out.println("Не удалость создать файл");
                                                       }
                                                   }
                                               }
                                           }
        );
        menuFileToCsv =new JMenuItem("Сохранить в CSV-файл");
        menuFileToCsv.addActionListener(new ActionListener(){
                                            public void actionPerformed(ActionEvent ev) {
                                                fileChoose.setCurrentDirectory(new File("~"));
                                                if(fileChoose.showSaveDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION){
                                                    File file=fileChoose.getSelectedFile();
                                                    try {
                                                        PrintStream out=new PrintStream(file);
                                                        for(int y=0;y<data.getRowCount();y++){
                                                            for(int u=0;u<data.getColumnCount();u++){
                                                                out.print(formatter.format(data.getValueAt(y, u)));
                                                                if(u!=data.getColumnCount()-1)
                                                                    out.print(",");
                                                                else
                                                                    out.print("\n");
                                                            }
                                                        }
                                                    } catch (FileNotFoundException e) {
                                                        System.out.println("Не удалось создать файл");
                                                    }
                                                }
                                            }
                                        }
        );
        menuTableFind =new JMenuItem("Найти значения многочлена");
        menuTableFind.addActionListener(new ActionListener(){
                                            public void actionPerformed(ActionEvent ev) {
                                                String value = JOptionPane.showInputDialog(MainFrame.this, "Введите значение для поиска",
                                                        "Поиск значения", JOptionPane.QUESTION_MESSAGE);
                                                renderer.setNeedle(value);
                                                getContentPane().repaint();
                                            }
                                        }
        );
        menuTablePalindrome =new JMenuItem("Найти палиндромы");
        menuTablePalindrome.addActionListener(new ActionListener(){
                                              public void actionPerformed(ActionEvent ev) {
                                                  renderer.setSpalindrome(true);
                                                  getContentPane().repaint();
                                              }
                                          }
        );


        menuHelpAbout =new JMenuItem("О программе");
        menuHelpAbout.addActionListener(new ActionListener(){
                                            public void actionPerformed(ActionEvent ev) {
                                                Aframe.setVisible(true);
                                            }
                                        }
        );

        menuFileToText.setEnabled(false);
        menuFileToGraphd.setEnabled(false);
        menuFileToCsv.setEnabled(false);
        menuTablePalindrome.setEnabled(false);
        menuTableFind.setEnabled(false);
        

        menuFile.add(menuFileToText);
        menuFile.add(menuFileToGraphd);
        menuFile.add(menuFileToCsv);
        menuTable.add(menuTablePalindrome);
        menuTable.add(menuTableFind);
        menuHelp.add(menuHelpAbout);
        menuBar.add(menuFile);
        menuBar.add(menuTable);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);
        ///////////
        
        l1_1 = new JLabel("X изменяется на интервале от:");
        l1_2 = new JLabel("до:");
        l1_3 = new JLabel("с шагом:");
        from =new JTextField("0.0",12);
        from.setMaximumSize(from.getPreferredSize());
        to =new JTextField("1.0",12);
        to.setMaximumSize(to.getPreferredSize());
        step =new JTextField("0.1",12);
        step.setMaximumSize(step.getPreferredSize());
        Box hBoxUp=Box.createHorizontalBox();
        hBoxUp.add(Box.createHorizontalGlue());
        hBoxUp.add(l1_1);
        hBoxUp.add(from);
        hBoxUp.add(l1_2);
        hBoxUp.add(to);
        hBoxUp.add(l1_3);
        hBoxUp.add(step);
        hBoxUp.setBorder(BorderFactory.createLineBorder(Color.black));
        hBoxUp.add(Box.createHorizontalGlue());

        btnCalc=new JButton("Вычислить");
        btnCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if((Double.parseDouble(from.getText()))-(Double.parseDouble(to.getText()))>0){
                    System.out.print("Error 1>2");
                    return;
                }
                data = new GornerTableModel(Double.parseDouble(from.getText()),
                        Double.parseDouble(to.getText()),Double.parseDouble(step.getText()),coefficients);
                renderer.setSpalindrome(false);
                renderer.setNeedle(null);
                JTable table = new JTable(data);
                table.setDefaultRenderer(Double.class, renderer);
                table.setRowHeight(30);
                hBoxResult.removeAll();
                hBoxResult.add(new JScrollPane(table));
                menuFileToText.setEnabled(true);
                menuFileToGraphd.setEnabled(true);
                menuFileToCsv.setEnabled(true);
                menuTablePalindrome.setEnabled(true);
                menuTableFind.setEnabled(true);
                getContentPane().validate();
            }
        });

        btnClear=new JButton("Очистить поля");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                from.setText("0.0");
                to.setText("1.0");
                step.setText("0.1");
                menuFileToText.setEnabled(false);
                menuFileToGraphd.setEnabled(false);
                menuFileToCsv.setEnabled(false);
                menuTablePalindrome.setEnabled(false);
                menuTableFind.setEnabled(false);
                hBoxResult.removeAll();
                hBoxResult.add(new JPanel());
                getContentPane().validate();
            }
        });


        Box hboxButtons=Box.createHorizontalBox();
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(btnCalc);
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(btnClear);
        hboxButtons.setBorder(BorderFactory.createLineBorder(Color.red));
        hboxButtons.add(Box.createHorizontalGlue());
        hBoxResult = Box.createHorizontalBox();
        hBoxResult.add(new JPanel());
        hBoxResult.setBorder(BorderFactory.createLineBorder(Color.cyan));
        getContentPane().add(hBoxUp, BorderLayout.NORTH);
        getContentPane().add(hBoxResult, BorderLayout.CENTER);
        getContentPane().add(hboxButtons, BorderLayout.SOUTH);


    }

    public static void main(String[] args){
        if(args.length==0){
            System.out.print("Невозможно табулировать многочлен, для \n" +
                    "которого не задано ни одного коэффициента!");
            System.exit(-1);
        }

        coefficients=new Double[args.length];
        int i=0;
        try {
// Перебрать аргументы, пытаясь преобразовать их в Double
            for (String arg: args) {
                coefficients[i++] = Double.parseDouble(arg);
            } }
        catch (NumberFormatException ex) {
// Если преобразование невозможно - сообщить об ошибке изавершиться
            System.out.println("Ошибка преобразования строки '" +
                    args[i] + "' в число типа Double");
            System.exit(-2);
        }
        MainFrame frame=new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
