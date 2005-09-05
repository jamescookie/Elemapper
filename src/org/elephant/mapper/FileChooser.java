package org.elephant.mapper;

import java.io.File;

import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * A dialog needed to pick the relevent type of files. Uses
 * {@link JFileChooser JFileChooser} and sets it up accordingly.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class FileChooser {
    public static final int SAVE = 1;
    public static final int SAVE_AS = 2;
    public static final int OPEN = 3;
    public static final int DIRECTORY = 4;

    private static final String XML = "xml";
    private static final String EXTENSION = "."+XML;

    private JFileChooser _chooser;
    private static Component _frame;
    private int _option;

    public FileChooser(int option, Component frame, File currentDir) {
        EleFileFilter xmlFilter = new EleFileFilter(XML, "XML Files");
        xmlFilter.setExtensionListInDescription(true);

        _chooser = new JFileChooser();
        _frame = frame;
        _option = option;

        if (_option == OPEN) {
            _chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        } else if (_option == DIRECTORY) {
            _chooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
            _chooser.setApproveButtonText("Export");
            _chooser.setDialogTitle("Choose a directory to export to");
        } else {
            _chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        }

        if (_option == DIRECTORY) {
            _chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            _chooser.setAcceptAllFileFilterUsed(false);
            _chooser.addChoosableFileFilter(xmlFilter);
            _chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }

        _chooser.setAccessory(null);
        _chooser.setFileView(null);
        _chooser.setMultiSelectionEnabled(false);
        _chooser.setCurrentDirectory(currentDir);
    }

    public File chooseFile() {
        File theFile = null;
        int result;
        int retval = _chooser.showDialog(_frame, null);

        if (retval == JFileChooser.APPROVE_OPTION) {
            theFile = _chooser.getSelectedFile();
            if (_option == OPEN) {
                if (!theFile.exists()) {
                    JOptionPane.showMessageDialog(_frame,
                            "That file does not exist. No action taken.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    theFile = null;
                }
            } else if (_option == DIRECTORY) {
                if (!theFile.isDirectory()) {
                    JOptionPane.showMessageDialog(_frame,
                            "That is not a valid directory.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    theFile = null;
                }
            } else {
                if (!theFile.toString().toLowerCase().endsWith(EXTENSION.toLowerCase())) {
                    theFile = new File(theFile.toString() + EXTENSION);
                }

                if (theFile.exists()) {
                    result = JOptionPane.showConfirmDialog(_frame,
                                    "Do you want to replace the existing " +
                                    theFile.getName() + "?");
                    if (result != JOptionPane.YES_OPTION) {
                        theFile = null;
                    }
                }
            }
        } else if (retval == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(_frame,
                    "An error occured. No file was chosen.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return theFile;
    }

}
