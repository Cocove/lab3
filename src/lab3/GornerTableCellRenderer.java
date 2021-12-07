package lab3;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private String needle = null;
    private Boolean spalindrome=false;
    private DecimalFormat formatter =(DecimalFormat)NumberFormat.getInstance();



    public GornerTableCellRenderer() {
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble =	formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        panel.add(label);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int col) {
        String formattedDouble = formatter.format(value);

       String Copy = formattedDouble.replace(".","");

        label.setText(formattedDouble);
        boolean palindrome=false;

        StringBuffer sb = new StringBuffer(Copy);

        sb.reverse();
        if(spalindrome){
            int count = 0;
            for(int i = 0;i<Copy.length();i++) {
                if(Copy.charAt(i) == sb.charAt(i)) {
                    count++;
                }
            }
            if(count == Copy.length())
            {
                palindrome = true;
            }
        }
        if ( needle!=null && needle.equals(formattedDouble)) {
            panel.setBackground(Color.RED);
        } else {
            panel.setBackground(Color.WHITE);
        }


        if(palindrome)
            panel.setBackground(Color.GREEN);
        if ( needle!=null && needle.equals(formattedDouble)&&palindrome) {
            panel.setBackground(Color.RED);
        }
        return panel;
    }

    public void setNeedle(String needle) {
        this.needle = needle;
    }

    public void setSpalindrome(boolean spalindrome) {
        this.spalindrome = spalindrome;
    }
}
