package org.elephant.mapper;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;

import org.elephant.mapper.ui.EleFrame;

/**
 * EleMapper is a tool used by wizards of <a href="http://www.elephant.org">Elephant Mud</a> to
 * help map an area. The LPC code can then be generated automatically, saving time and effort.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class EleMapper {
    boolean _packFrame = false;

    /** constructs the application */
    public EleMapper() {
        EleConstants.init();
        EleFrame frame = new EleFrame();
        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout
        if (_packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }
        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }


    /** main method */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }
        new EleMapper();
    }
}
