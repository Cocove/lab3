package lab3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class AboutFrame extends JFrame {
    static final int WIDTH=100;
    static final int HEIGHT=150;

    public AboutFrame(){
        super("О программе");
        setSize(WIDTH,HEIGHT);
        this.setResizable(false);
        Toolkit kit=Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,(kit.getScreenSize().height - HEIGHT)/2);

        Box hBoxClose=Box.createHorizontalBox();
        JButton btnClose= new JButton("Закрыть");
        btnClose.addActionListener(new ActionListener(){
                                       @Override
                                       public void actionPerformed(ActionEvent arg0) {
                                           setVisible(false);
                                       }
                                   }
        );
        hBoxClose.add(Box.createHorizontalGlue());
        hBoxClose.add(btnClose);
        hBoxClose.add(Box.createHorizontalGlue());

        Box hBoxData=Box.createVerticalBox();
        hBoxData.add(Box.createVerticalGlue());
        JLabel lblname= new JLabel("Guo ZhongYou");
        JLabel lblgroup= new JLabel("Группа 10");
        JLabel lblimage= new JLabel("");
        Image img=kit.getImage("icon.gif");
        ImageIcon icon =new ImageIcon();
        icon.setImage(img);
        lblimage.setIcon(icon);
        hBoxData.add(lblimage);
        hBoxData.add(lblname);
        hBoxData.add(lblgroup);
        hBoxData.add(Box.createVerticalGlue());

        Box hBoxContent=Box.createVerticalBox();
        hBoxContent.add(hBoxData);
        hBoxContent.add(hBoxClose);
        getContentPane().add(hBoxContent, BorderLayout.CENTER);
    }
}
