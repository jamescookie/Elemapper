package org.elephant.mapper.ui;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.elephant.mapper.EleConstants;
import org.elephant.mapper.EleUtils;
import org.elephant.mapper.Exit;
import org.elephant.mapper.Room;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class ExitTabs extends JTabbedPane {
    private static final int DEFAULT_EXIT_PANE = 0;

    JPanel pnlExitMiniMap = new JPanel();

    private JPanel pnlExitSpecifics = new JPanel();
    private JPanel pnlExitDoor = new JPanel();
    private JPanel pnlExitInvisible = new JPanel();
    private JPanel pnlExitLock = new JPanel();
    private JPanel pnlExitRoomNumbers = new JPanel();
    private JPanel pnlExitSpecificsLayout1 = new JPanel();
    private JPanel pnlExitSpecificsLayout2 = new JPanel();
    private JPanel pnlExitSpecificsLayout3 = new JPanel();
    private JPanel pnlExitSpecificsLayout4 = new JPanel();
    private JPanel pnlExitSpecificsLayout5 = new JPanel();
    private JPanel pnlExitSpecificsLayout6 = new JPanel();
    private JLabel lblExitRoom1 = new JLabel();
    private JLabel lblExitRoom2 = new JLabel();
    private JLabel[] lblExitDirection = new JLabel[10];
    private JTabbedPane tabbedPaneExitSpecifics = new JTabbedPane();
    private ButtonGroup buttonGroupExitInvis = new ButtonGroup();
    private ButtonGroup buttonGroupExitDoor = new ButtonGroup();
    private ButtonGroup buttonGroupExitLock = new ButtonGroup();
    private JRadioButton optNotInvis = new JRadioButton();
    private JRadioButton optInvis = new JRadioButton();
    private JRadioButton optNotDoor = new JRadioButton();
    private JRadioButton optDoor = new JRadioButton();
    private JRadioButton optNotLockable = new JRadioButton();
    private JRadioButton optLockable = new JRadioButton();
    private JCheckBox chkInvisibleRoom1 = new JCheckBox();
    private JCheckBox chkInvisibleRoom2 = new JCheckBox();
    private JCheckBox chkConcealedRoom1 = new JCheckBox();
    private JCheckBox chkConcealedRoom2 = new JCheckBox();
    private JCheckBox chkSameKeys = new JCheckBox();
    private JCheckBox chkSameDoorName = new JCheckBox();
    private JCheckBox chkSameDoorDesc = new JCheckBox();
    private BorderLayout borderLayout9 = new BorderLayout();
    private BorderLayout borderLayout10 = new BorderLayout();
    private BorderLayout borderLayout11 = new BorderLayout();
    private BorderLayout borderLayout13 = new BorderLayout();
    private BorderLayout borderLayout14 = new BorderLayout();
    private BorderLayout borderLayout15 = new BorderLayout();
    private BorderLayout borderLayout16 = new BorderLayout();
    private XYLayout xYLayout2 = new XYLayout();
    private XYLayout xYLayout3 = new XYLayout();
    private XYLayout xYLayout4 = new XYLayout();
    private XYLayout xYLayout5 = new XYLayout();
    private JTextField txtKeyId1 = new JTextField();
    private JTextField txtKeyId2 = new JTextField();
    private JTextField txtDoorName1 = new JTextField();
    private JTextField txtDoorName2 = new JTextField();
    private JTextField txtDoorDescRoom1 = new JTextField();
    private JTextField txtDoorDescRoom2 = new JTextField();
    private JTextField txtDoorOpenDescRoom1 = new JTextField();
    private JTextField txtDoorOpenDescRoom2 = new JTextField();
    private JTextField txtDoorClosedDescRoom1 = new JTextField();
    private JTextField txtDoorClosedDescRoom2 = new JTextField();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JLabel jLabel9 = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JLabel jLabel28 = new JLabel();
    private JLabel jLabel36 = new JLabel();
    private JLabel jLabel37 = new JLabel();
    private JLabel jLabel38 = new JLabel();
    private JLabel jLabel39 = new JLabel();
    private JLabel jLabel40 = new JLabel();
    private JLabel jLabel41 = new JLabel();

    public ExitTabs(EleFrame eleFrame) {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit(eleFrame);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit(final EleFrame eleFrame) throws Exception {
        for (int i = 0; i < 10; i++) {
            lblExitDirection[i] = new JLabel(EleConstants.DIRECTION_ABBREV[i]);
        }
        Border borderInset5 = BorderFactory.createEmptyBorder(5,5,5,5);

        // mini map
        pnlExitMiniMap = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                eleFrame.paintMiniExitMap(this, g);
            }
        };

        // listeners
        optNotInvis.setText("This exit is NOT invisible");
        optNotInvis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optNotInvis_actionPerformed(e);
            }
        });
        optInvis.setText("This exit is invisible");
        optInvis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optInvis_actionPerformed(e);
            }
        });
        chkInvisibleRoom1.setText("Invisible from Room A");
        chkInvisibleRoom1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkInvisibleRoom1_actionPerformed(e);
            }
        });
        chkInvisibleRoom2.setText("Invisible from Room B");
        chkInvisibleRoom2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkInvisibleRoom2_actionPerformed(e);
            }
        });
        optNotDoor.setText("This exit is NOT a door");
        optNotDoor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optNotDoor_actionPerformed(e);
            }
        });
        optDoor.setText("This exit is a door");
        optDoor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optDoor_actionPerformed(e);
            }
        });
        optNotLockable.setText("This door is NOT Lockable");
        optNotLockable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optNotLockable_actionPerformed(e);
            }
        });
        optLockable.setText("This door is Lockable");
        optLockable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optLockable_actionPerformed(e);
            }
        });
        chkSameDoorName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkSameDoorName_actionPerformed(e);
            }
        });
        chkSameDoorDesc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkSameDoorDesc_actionPerformed(e);
            }
        });
        chkSameKeys.setText("Use same Key ID for both rooms");
        chkSameKeys.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkSameKeys_actionPerformed(e);
            }
        });

        // Misc
        pnlExitMiniMap.setBorder(BorderFactory.createLoweredBevelBorder());
        buttonGroupExitInvis.add(optNotInvis);
        buttonGroupExitInvis.add(optInvis);
        buttonGroupExitDoor.add(optNotDoor);
        buttonGroupExitDoor.add(optDoor);
        buttonGroupExitLock.add(optNotLockable);
        buttonGroupExitLock.add(optLockable);
        lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_NORTH)].setHorizontalAlignment(SwingConstants.CENTER);
        lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_SOUTH)].setHorizontalAlignment(SwingConstants.CENTER);
        pnlExitMiniMap.setBackground(EleConstants.BACKGROUND_COLOUR);
        jLabel5.setText("Key ID from Room A");
        jLabel6.setText("Room A is Room Number ");
        jLabel7.setText("Room B is Room Number ");
        jLabel9.setText("From Room A");
        jLabel10.setText("From Room B");
        jLabel28.setText("Key ID from Room B");
        jLabel36.setText("Name");
        jLabel37.setText("Use same");
        jLabel39.setText("Door description");
        jLabel38.setText("Concealed");
        jLabel40.setText("Open description");
        jLabel41.setText("Closed description");

        // Layout
        pnlExitRoomNumbers.setLayout(xYLayout5);
        pnlExitRoomNumbers.add(jLabel6, new XYConstraints(5, 5, 150, -1));
        pnlExitRoomNumbers.add(jLabel7, new XYConstraints(5, 25, 150, -1));
        pnlExitRoomNumbers.add(lblExitRoom1, new XYConstraints(153, 5, 34, -1));
        pnlExitRoomNumbers.add(lblExitRoom2, new XYConstraints(153, 25, 34, -1));
        pnlExitInvisible.setLayout(xYLayout2);
        pnlExitInvisible.add(optNotInvis, new XYConstraints(5, 5, -1, -1));
        pnlExitInvisible.add(optInvis, new XYConstraints(5, 25, -1, -1));
        pnlExitInvisible.add(chkInvisibleRoom1, new XYConstraints(25, 45, -1, -1));
        pnlExitInvisible.add(chkInvisibleRoom2, new XYConstraints(25, 65, -1, -1));
        pnlExitLock.setLayout(xYLayout4);
        pnlExitLock.add(optNotLockable,   new XYConstraints(5, 5, -1, -1));
        pnlExitLock.add(optLockable, new XYConstraints(5, 25, -1, -1));
        pnlExitLock.add(jLabel5, new XYConstraints(24, 50, -1, -1));
        pnlExitLock.add(txtKeyId1, new XYConstraints(137, 50, 99, 18));
        pnlExitLock.add(chkSameKeys, new XYConstraints(24, 69, -1, 15));
        pnlExitLock.add(jLabel28, new XYConstraints(24, 85, -1, -1));
        pnlExitLock.add(txtKeyId2, new XYConstraints(137, 85, 99, 18));
        pnlExitDoor.setLayout(xYLayout3);
        pnlExitDoor.add(optNotDoor, new XYConstraints(5, 5, -1, -1));
        pnlExitDoor.add(optDoor, new XYConstraints(5, 25, -1, -1));
        pnlExitDoor.add(jLabel9, new XYConstraints(45, 45, -1, -1));
        pnlExitDoor.add(jLabel37, new XYConstraints(148, 45, -1, -1));
        pnlExitDoor.add(jLabel10, new XYConstraints(235, 45, -1, -1));
        pnlExitDoor.add(jLabel36, new XYConstraints(10, 55, -1, -1)); // name
        pnlExitDoor.add(txtDoorName1, new XYConstraints(10, 70, 140, 18));
        pnlExitDoor.add(chkSameDoorName, new XYConstraints(160, 70, -1, 15));
        pnlExitDoor.add(txtDoorName2, new XYConstraints(190, 70, 140, 18));
        pnlExitDoor.add(jLabel38, new XYConstraints(10, 87, -1, -1)); // concealed
        pnlExitDoor.add(chkConcealedRoom1, new XYConstraints(6, 102, -1, 15));
        pnlExitDoor.add(chkConcealedRoom2, new XYConstraints(186, 102, -1, 15));
        pnlExitDoor.add(jLabel39, new XYConstraints(10, 117, -1, -1)); // door desc
        pnlExitDoor.add(txtDoorDescRoom1, new XYConstraints(10, 134, 140, 18));
        pnlExitDoor.add(chkSameDoorDesc, new XYConstraints(160, 134, -1, 15));
        pnlExitDoor.add(txtDoorDescRoom2, new XYConstraints(190, 134, 140, 18));
        pnlExitDoor.add(jLabel40, new XYConstraints(10, 151, -1, -1)); // door open desc
        pnlExitDoor.add(txtDoorOpenDescRoom1, new XYConstraints(10, 166, 140, 18));
        pnlExitDoor.add(txtDoorOpenDescRoom2, new XYConstraints(190, 166, 140, 18));
        pnlExitDoor.add(jLabel41, new XYConstraints(10, 183, -1, -1)); // door closed desc
        pnlExitDoor.add(txtDoorClosedDescRoom1, new XYConstraints(10, 198, 140, 18));
        pnlExitDoor.add(txtDoorClosedDescRoom2, new XYConstraints(190, 198, 140, 18));
        pnlExitSpecificsLayout4.setLayout(borderLayout14);
        pnlExitSpecificsLayout4.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_EAST)], BorderLayout.EAST);
        pnlExitSpecificsLayout4.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_WEST)], BorderLayout.WEST);
        pnlExitSpecificsLayout4.add(pnlExitMiniMap, BorderLayout.CENTER);
        pnlExitSpecificsLayout5.setLayout(borderLayout15);
        pnlExitSpecificsLayout5.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_SOUTH)], BorderLayout.CENTER);
        pnlExitSpecificsLayout5.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_SOUTHWEST)], BorderLayout.WEST);
        pnlExitSpecificsLayout5.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_SOUTHEAST)], BorderLayout.EAST);
        pnlExitSpecificsLayout6.setLayout(borderLayout13);
        pnlExitSpecificsLayout6.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_NORTHWEST)], BorderLayout.WEST);
        pnlExitSpecificsLayout6.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_NORTHEAST)], BorderLayout.EAST);
        pnlExitSpecificsLayout6.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_NORTH)], BorderLayout.CENTER);
        pnlExitSpecificsLayout3.setLayout(borderLayout16);
        pnlExitSpecificsLayout3.add(pnlExitSpecificsLayout4, BorderLayout.CENTER);
        pnlExitSpecificsLayout3.add(pnlExitSpecificsLayout5, BorderLayout.SOUTH);
        pnlExitSpecificsLayout3.add(pnlExitSpecificsLayout6, BorderLayout.NORTH);
        pnlExitSpecificsLayout1.setBorder(borderInset5);
        pnlExitSpecificsLayout1.setLayout(borderLayout9);
        pnlExitSpecificsLayout1.add(pnlExitSpecificsLayout3, BorderLayout.CENTER);
        pnlExitSpecificsLayout1.add(pnlExitRoomNumbers, BorderLayout.NORTH);
        tabbedPaneExitSpecifics.add(pnlExitDoor, "Door");
        tabbedPaneExitSpecifics.add(pnlExitLock, "Locking");
        tabbedPaneExitSpecifics.add(pnlExitInvisible, "Invisible");
        pnlExitSpecificsLayout2.setBorder(borderInset5);
        pnlExitSpecificsLayout2.setLayout(borderLayout11);
        pnlExitSpecificsLayout2.add(tabbedPaneExitSpecifics, BorderLayout.CENTER);
        pnlExitSpecifics.setBorder(borderInset5);
        pnlExitSpecifics.setLayout(borderLayout10);
        pnlExitSpecifics.add(pnlExitSpecificsLayout1, BorderLayout.WEST);
        pnlExitSpecifics.add(pnlExitSpecificsLayout2, BorderLayout.CENTER);
        // Add To this
        setBorder(borderInset5);
        add(pnlExitSpecifics, "Specifics");
    }

    //-------------------------------------------------
    // Option/Checkbox functions
    //-------------------------------------------------

    void optNotInvis_actionPerformed(ActionEvent e) {
        chkInvisibleRoom1.setSelected(true);
        chkInvisibleRoom2.setSelected(true);
        chkInvisibleRoom1.setEnabled(false);
        chkInvisibleRoom2.setEnabled(false);
    }

    void optInvis_actionPerformed(ActionEvent e) {
        chkInvisibleRoom1.setEnabled(true);
        chkInvisibleRoom2.setEnabled(true);
    }

    void chkInvisibleRoom1_actionPerformed(ActionEvent e) {
        chkExitAttribute_actionPerformed(chkInvisibleRoom1, chkInvisibleRoom2, optNotInvis);
    }

    void chkInvisibleRoom2_actionPerformed(ActionEvent e) {
        chkExitAttribute_actionPerformed(chkInvisibleRoom1, chkInvisibleRoom2, optNotInvis);
    }

    void optNotDoor_actionPerformed(ActionEvent e) {
        optNotLockable.doClick();
        txtDoorName1.setText("");
        txtDoorName1.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorName1.setEnabled(false);
        chkSameDoorName.setSelected(true);
        chkSameDoorName.setEnabled(false);
        txtDoorName2.setText("");
        txtDoorName2.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorName2.setEnabled(false);
        chkConcealedRoom1.setSelected(false);
        chkConcealedRoom1.setEnabled(false);
        chkConcealedRoom2.setSelected(false);
        chkConcealedRoom2.setEnabled(false);
        txtDoorDescRoom1.setText("");
        txtDoorDescRoom1.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorDescRoom1.setEnabled(false);
        chkSameDoorDesc.setSelected(true);
        chkSameDoorDesc.setEnabled(false);
        txtDoorDescRoom2.setText("");
        txtDoorDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorDescRoom2.setEnabled(false);
        txtDoorOpenDescRoom1.setText("");
        txtDoorOpenDescRoom1.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorOpenDescRoom1.setEnabled(false);
        txtDoorOpenDescRoom2.setText("");
        txtDoorOpenDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorOpenDescRoom2.setEnabled(false);
        txtDoorClosedDescRoom1.setText("");
        txtDoorClosedDescRoom1.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorClosedDescRoom1.setEnabled(false);
        txtDoorClosedDescRoom2.setText("");
        txtDoorClosedDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorClosedDescRoom2.setEnabled(false);
        optLockable.setEnabled(false);
        optNotLockable.setEnabled(false);
    }

    void optDoor_actionPerformed(ActionEvent e) {
        chkSameDoorName.setEnabled(true);
        txtDoorName1.setEnabled(true);
        txtDoorName1.setBackground(EleConstants.ENABLED_COLOUR);
        chkConcealedRoom1.setEnabled(true);
        chkConcealedRoom2.setEnabled(true);
        chkSameDoorDesc.setEnabled(true);
        txtDoorDescRoom1.setEnabled(true);
        txtDoorDescRoom1.setBackground(EleConstants.ENABLED_COLOUR);
        txtDoorOpenDescRoom1.setEnabled(true);
        txtDoorOpenDescRoom1.setBackground(EleConstants.ENABLED_COLOUR);
        txtDoorClosedDescRoom1.setEnabled(true);
        txtDoorClosedDescRoom1.setBackground(EleConstants.ENABLED_COLOUR);
        optLockable.setEnabled(true);
        optNotLockable.setEnabled(true);
    }

    void optNotLockable_actionPerformed(ActionEvent e) {
        txtKeyId1.setText("");
        txtKeyId1.setBackground(EleConstants.DISABLED_COLOUR);
        txtKeyId1.setEnabled(false);
        txtKeyId2.setText("");
        txtKeyId2.setBackground(EleConstants.DISABLED_COLOUR);
        txtKeyId2.setEnabled(false);
        chkSameKeys.setSelected(true);
        chkSameKeys.setEnabled(false);
    }

    void optLockable_actionPerformed(ActionEvent e) {
        txtKeyId1.setEnabled(true);
        txtKeyId1.setBackground(EleConstants.ENABLED_COLOUR);
        chkSameKeys.setEnabled(true);
    }

    void chkSameDoorName_actionPerformed(ActionEvent e) {
        if (chkSameDoorName.isSelected()) {
            txtDoorName2.setEnabled(false);
            txtDoorName2.setBackground(EleConstants.DISABLED_COLOUR);
            txtDoorName2.setText("");
        } else {
            txtDoorName2.setEnabled(true);
            txtDoorName2.setBackground(EleConstants.ENABLED_COLOUR);
        }
    }

    void chkSameDoorDesc_actionPerformed(ActionEvent e) {
        if (chkSameDoorDesc.isSelected()) {
            txtDoorDescRoom2.setEnabled(false);
            txtDoorDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
            txtDoorDescRoom2.setText("");
            txtDoorOpenDescRoom2.setEnabled(false);
            txtDoorOpenDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
            txtDoorOpenDescRoom2.setText("");
            txtDoorClosedDescRoom2.setEnabled(false);
            txtDoorClosedDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
            txtDoorClosedDescRoom2.setText("");
        } else {
            txtDoorDescRoom2.setEnabled(true);
            txtDoorDescRoom2.setBackground(EleConstants.ENABLED_COLOUR);
            txtDoorOpenDescRoom2.setEnabled(true);
            txtDoorOpenDescRoom2.setBackground(EleConstants.ENABLED_COLOUR);
            txtDoorClosedDescRoom2.setEnabled(true);
            txtDoorClosedDescRoom2.setBackground(EleConstants.ENABLED_COLOUR);
        }
    }

    void chkSameKeys_actionPerformed(ActionEvent e) {
        if (chkSameKeys.isSelected()) {
            txtKeyId2.setEnabled(false);
            txtKeyId2.setBackground(EleConstants.DISABLED_COLOUR);
            txtKeyId2.setText("");
        } else {
            txtKeyId2.setEnabled(true);
            txtKeyId2.setBackground(EleConstants.ENABLED_COLOUR);
        }
    }

    /** Used by all Exit checkboxes, because they all perform the same function. */
    void chkExitAttribute_actionPerformed(JCheckBox room1, JCheckBox room2, JRadioButton notOption) {
        if (!room1.isSelected() && !room2.isSelected()) {
            notOption.doClick();
        }
    }


    void saveCurrentExitSettings(Exit exit, JPanel pnlProperties, boolean viewProperties) {
        if (optNotInvis.isSelected()) {
            exit.setInvisible(EleConstants.EXIT_OPTION_NO);
        } else {
            if (chkInvisibleRoom1.isSelected()) {
                if (chkInvisibleRoom2.isSelected()) {
                    exit.setInvisible(EleConstants.EXIT_OPTION_YES);
                } else {
                    exit.setInvisible(EleConstants.EXIT_OPTION_ROOM1);
                }
            } else {
                exit.setInvisible(EleConstants.EXIT_OPTION_ROOM2);
            }
        }

        if (optNotDoor.isSelected()) {
            exit.setDoor(EleConstants.EXIT_OPTION_NO);
        } else {
            exit.setDoor(EleConstants.EXIT_OPTION_YES);
            exit.setDoorName1(txtDoorName1.getText().toLowerCase());
            if (chkSameDoorName.isSelected()) {
                exit.setDoorName2(txtDoorName1.getText().toLowerCase());
            } else {
                exit.setDoorName2(txtDoorName2.getText().toLowerCase());
            }

            exit.setDoorDescription1(txtDoorDescRoom1.getText().toLowerCase());
            exit.setDoorOpenDescription1(txtDoorOpenDescRoom1.getText().toLowerCase());
            exit.setDoorClosedDescription1(txtDoorClosedDescRoom1.getText().toLowerCase());
            if (chkSameDoorDesc.isSelected()) {
                exit.setDoorDescription2(txtDoorDescRoom1.getText().toLowerCase());
                exit.setDoorOpenDescription2(txtDoorOpenDescRoom1.getText().toLowerCase());
                exit.setDoorClosedDescription2(txtDoorClosedDescRoom1.getText().toLowerCase());
            } else {
                exit.setDoorDescription2(txtDoorDescRoom2.getText().toLowerCase());
                exit.setDoorOpenDescription2(txtDoorOpenDescRoom2.getText().toLowerCase());
                exit.setDoorClosedDescription2(txtDoorClosedDescRoom2.getText().toLowerCase());
            }

            if (chkConcealedRoom1.isSelected()) {
                if (chkConcealedRoom2.isSelected()) {
                    exit.setConcealed(EleConstants.EXIT_OPTION_YES);
                } else {
                    exit.setConcealed(EleConstants.EXIT_OPTION_ROOM1);
                }
            } else {
                if (chkConcealedRoom2.isSelected()) {
                    exit.setConcealed(EleConstants.EXIT_OPTION_ROOM2);
                }
            }

            if (optNotLockable.isSelected()) {
                exit.setLockable(false);
            } else {
                exit.setLockable(true);
                exit.setKeyFromRoom1(txtKeyId1.getText());
                if (chkSameKeys.isSelected()) {
                    exit.setKeyFromRoom2(txtKeyId1.getText());
                } else {
                    exit.setKeyFromRoom2(txtKeyId2.getText());
                }
            }
        }

        clearExitSettings(pnlProperties, viewProperties);
    }

    void clearExitSettings(JPanel pnlProperties, boolean viewProperties) {
        pnlProperties.remove(this);
        if (viewProperties) {
            pnlProperties.paint(pnlProperties.getGraphics());
        }

        optNotInvis.doClick();
        optNotDoor.doClick();

        for (int i = 0; i < 10; i++) {
            lblExitDirection[i].setText(EleConstants.DIRECTION_ABBREV[i]);
        }
    }

    void updateExitSettings(Exit exit, JPanel pnlProperties, boolean viewProperties) {
        boolean door = true;
        Room room1 = exit.getRoom1();
        Room room2 = exit.getRoom2();
        String room1Number = String.valueOf(room1.getRoomNumber());
        String room2Number = String.valueOf(room2.getRoomNumber());
        long exitLevel = exit.getExitLevel();

//        lblExitNumber.setText(String.valueOf(_currentExit.getExitNumber()));
        lblExitRoom1.setText(room1Number);
        lblExitRoom2.setText(room2Number);

        if (exit.isVerticalExit()) {
            for (int i = 0; i < 10; i++) {
                lblExitDirection[i].setText("");
            }
            lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_NORTH)].setText((exitLevel==room1.getLevel()?room1Number:room2Number) + " (up)");
            lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_SOUTH)].setText((exitLevel!=room1.getLevel()?room1Number:room2Number) + " (down)");
        } else {
            lblExitDirection[EleUtils.translateDirectionToIndex(EleUtils.getOppositeDirection(exit.getDirection(room1)))].setText(room1Number);
            lblExitDirection[EleUtils.translateDirectionToIndex(EleUtils.getOppositeDirection(exit.getDirection(room2)))].setText(room2Number);
        }

        switch (exit.getInvisible()) {
            case EleConstants.EXIT_OPTION_NO:
                optNotInvis.doClick();
                break;
            case EleConstants.EXIT_OPTION_YES:
                optInvis.doClick();
                break;
            case EleConstants.EXIT_OPTION_ROOM1:
                optInvis.doClick();
                chkInvisibleRoom2.doClick();
                break;
            case EleConstants.EXIT_OPTION_ROOM2:
                optInvis.doClick();
                chkInvisibleRoom1.doClick();
                break;
        }

        switch (exit.getDoor()) {
            case EleConstants.EXIT_OPTION_NO:
                door = false;
                optNotLockable.doClick();
                optNotDoor.doClick();
                break;
            case EleConstants.EXIT_OPTION_YES:
                optDoor.doClick();
                break;
        }

        if (door) {
            txtDoorName1.setText(exit.getDoorName1());
            if (!exit.isDoorSame()) {
                chkSameDoorName.doClick();
                txtDoorName2.setText(exit.getDoorName2());
            }

            txtDoorDescRoom1.setText(exit.getDoorDescription1());
            txtDoorOpenDescRoom1.setText(exit.getDoorOpenDescription1());
            txtDoorClosedDescRoom1.setText(exit.getDoorClosedDescription1());
            if (!exit.isDoorDescriptionSame()) {
                chkSameDoorDesc.doClick();
                txtDoorDescRoom2.setText(exit.getDoorDescription2());
                txtDoorOpenDescRoom2.setText(exit.getDoorOpenDescription2());
                txtDoorClosedDescRoom2.setText(exit.getDoorClosedDescription2());
            }

            switch (exit.getConcealed()) {
                case EleConstants.EXIT_OPTION_YES:
                    chkConcealedRoom1.doClick();
                    chkConcealedRoom2.doClick();
                    break;
                case EleConstants.EXIT_OPTION_ROOM1:
                    chkConcealedRoom1.doClick();
                    break;
                case EleConstants.EXIT_OPTION_ROOM2:
                    chkConcealedRoom2.doClick();
                    break;
            }

            if (exit.isLockable()) {
                optLockable.doClick();
                txtKeyId1.setText(exit.getKeyFromRoom1());
                if (!exit.isKeySame()) {
                    chkSameKeys.doClick();
                    txtKeyId2.setText(exit.getKeyFromRoom2());
                }
            } else {
                optNotLockable.doClick();
            }
        }

        if (pnlProperties.getComponentCount() == 0) {
            setSize(pnlProperties.getSize());
            setSelectedIndex(DEFAULT_EXIT_PANE);
            pnlProperties.add(this, BorderLayout.CENTER);
        }

        if (viewProperties) {
            pnlProperties.paint(pnlProperties.getGraphics());
        }
    }

}
