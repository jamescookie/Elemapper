package org.elephant.mapper.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.elephant.mapper.EleConstants;

public class Options extends JDialog {
    private JPanel pnlOptions = new JPanel();
    private JPanel pnlLayout1 = new JPanel();
    private JButton btnok = new JButton();
    private JButton btnCancel = new JButton();
    private JPanel pnlLayout2 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JButton btnSelectColour = new JButton();
    private JButton btnRoomColour = new JButton();
    private JButton btnExitColour = new JButton();
    private JButton btnBackGroundColour = new JButton();
    private JButton btnLowerLevelColour = new JButton();
    private JButton btnUpperLevelColour = new JButton();

    public static void create(Frame frame) {
        Options options = new Options(frame, "Options", true);
        options.setLocationRelativeTo(frame);
        options.setVisible(true);
    }

    private Options(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            validate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setSize(new Dimension(218, 334));

        pnlOptions.setLayout(borderLayout1);
        btnok.setText("OK");
        btnok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnok_actionPerformed(e);
            }
        });
        btnCancel.setToolTipText("");
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        btnSelectColour.setText("Select Colour");
        btnSelectColour.setBackground(EleConstants.SELECT_COLOUR);
        btnSelectColour.setPreferredSize(new Dimension(200, 40));
        btnSelectColour.setFocusPainted(false);
        btnSelectColour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setColour(btnSelectColour, "selected items");
            }
        });
        btnRoomColour.setText("Room Colour");
        btnRoomColour.setBackground(EleConstants.ROOM_COLOUR);
        btnRoomColour.setPreferredSize(new Dimension(200, 40));
        btnRoomColour.setFocusPainted(false);
        btnRoomColour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setColour(btnRoomColour, "rooms");
            }
        });
        btnExitColour.setText("Exit Colour");
        btnExitColour.setBackground(EleConstants.EXIT_COLOUR);
        btnExitColour.setPreferredSize(new Dimension(200, 40));
        btnExitColour.setFocusPainted(false);
        btnExitColour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setColour(btnExitColour, "exits");
            }
        });
        btnBackGroundColour.setText("Background Colour");
        btnBackGroundColour.setBackground(EleConstants.BACKGROUND_COLOUR);
        btnBackGroundColour.setPreferredSize(new Dimension(200, 40));
        btnBackGroundColour.setFocusPainted(false);
        btnBackGroundColour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setColour(btnBackGroundColour, "the background");
            }
        });
        btnLowerLevelColour.setText("Lower Level Colour");
        btnLowerLevelColour.setBackground(EleConstants.LOWER_LEVEL_COLOUR);
        btnLowerLevelColour.setPreferredSize(new Dimension(200, 40));
        btnLowerLevelColour.setFocusPainted(false);
        btnLowerLevelColour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setColour(btnLowerLevelColour, "the lower level");
            }
        });
        btnUpperLevelColour.setText("Upper Level Colour");
        btnUpperLevelColour.setBackground(EleConstants.UPPER_LEVEL_COLOUR);
        btnUpperLevelColour.setPreferredSize(new Dimension(200, 40));
        btnUpperLevelColour.setFocusPainted(false);
        btnUpperLevelColour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setColour(btnUpperLevelColour, "the upper level");
            }
        });

        pnlLayout1.add(btnok, null);
        pnlLayout1.add(btnCancel, null);
        pnlLayout2.add(btnSelectColour, null);
        pnlLayout2.add(btnRoomColour, null);
        pnlLayout2.add(btnExitColour, null);
        pnlLayout2.add(btnBackGroundColour, null);
        pnlLayout2.add(btnLowerLevelColour, null);
        pnlLayout2.add(btnUpperLevelColour, null);
        pnlOptions.add(pnlLayout1, BorderLayout.SOUTH);
        pnlOptions.add(pnlLayout2, BorderLayout.CENTER);
        getContentPane().add(pnlOptions);
    }

    private void setColour(JButton button, String what) {
        Color c = JColorChooser.showDialog(
            this,
            "Choose a default colour for "+what,
            button.getBackground()
        );
        if (c != null) {
            button.setBackground(c);
        }
    }

    void btnok_actionPerformed(ActionEvent e) {
        EleConstants.setSelectColour(btnSelectColour.getBackground());
        EleConstants.setRoomColour(btnRoomColour.getBackground());
        EleConstants.setExitColour(btnExitColour.getBackground());
        EleConstants.setBackGroundColour(btnBackGroundColour.getBackground());
        EleConstants.setLowerLevelColour(btnLowerLevelColour.getBackground());
        EleConstants.setUpperLevelColour(btnUpperLevelColour.getBackground());
        EleConstants.save();
        setVisible(false);
    }
}
