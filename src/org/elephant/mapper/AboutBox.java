package org.elephant.mapper;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.borland.jbcl.layout.VerticalFlowLayout;

/**
 * A dialog box to explain EleMapper.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class AboutBox extends JDialog implements ActionListener {
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel insetsPanel1 = new JPanel();
    private JPanel insetsPanel2 = new JPanel();
    private JPanel insetsPanel3 = new JPanel();
    private JButton button1 = new JButton();
    private JLabel imageLabel = new JLabel();
    private JLabel label1 = new JLabel();
    private JLabel label2 = new JLabel();
    private JLabel label3 = new JLabel();
    private JLabel label4 = new JLabel();
    private JLabel label5 = new JLabel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private BorderLayout borderLayout2 = new BorderLayout();
    private FlowLayout flowLayout1 = new FlowLayout();
    private String product = "EleMapper";
    private String version = EleConstants.VERSION;
    private String copyright = "Copyright (c) 2001-2004";
    private String author = "Havoc (aka James Cook)";
    private String comments = "This product was designed to enable the immortals " +
                              "on Elephant Mud to quickly generate areas without " +
                              "having to touch any LPC code.";
    private String eleImage = "";
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();

    public AboutBox(Frame parent, String imagePath) {
        super(parent);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            eleImage = imagePath;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pack();
    }

    /**Component initialization*/
    private void jbInit() throws Exception {
        String[] tmp;
        JLabel[] labels;
        imageLabel.setIcon(new ImageIcon(AboutBox.class.getResource(eleImage)));
        setTitle("About");
        setResizable(false);
        panel1.setLayout(borderLayout1);
        panel2.setLayout(borderLayout2);
        insetsPanel1.setLayout(flowLayout1);
        insetsPanel2.setLayout(flowLayout1);
        insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label1.setText("Product      : " + product);
        label2.setText("Version      : " + version);
        label3.setText("Copyright   : " + copyright);
        label4.setText("Author        : " + author);
        label5.setText("Description : ");
        tmp = EleUtils.breakString(comments, 50);
        labels = new JLabel[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            labels[i] = new JLabel(tmp[i]);
        }
        insetsPanel3.setLayout(verticalFlowLayout1);
        insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
        button1.setText("OK");
        button1.addActionListener(this);
        verticalFlowLayout1.setHgap(0);
        verticalFlowLayout1.setVgap(0);
        insetsPanel2.add(imageLabel, null);
        panel2.add(insetsPanel2, BorderLayout.WEST);
        getContentPane().add(panel1, null);
        insetsPanel3.add(label1, null);
        insetsPanel3.add(label2, null);
        insetsPanel3.add(label3, null);
        insetsPanel3.add(label4, null);
        insetsPanel3.add(label5, null);
        for (final JLabel label : labels) {
            insetsPanel3.add(label, null);
        }
        panel2.add(insetsPanel3, BorderLayout.CENTER);
        insetsPanel1.add(button1, null);
        panel1.add(insetsPanel1, BorderLayout.SOUTH);
        panel1.add(panel2, BorderLayout.NORTH);
    }

    /**Overridden so we can exit when window is closed*/
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            cancel();
        }
        super.processWindowEvent(e);
    }

    /**Close the dialog*/
    void cancel() {
        dispose();
    }

    /**Close the dialog on a button event*/
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            cancel();
        }
    }
}
