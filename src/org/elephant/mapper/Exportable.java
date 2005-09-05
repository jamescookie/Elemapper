package org.elephant.mapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import org.jdom.Element;

/**
 * An interface that needs to be implemented by all objects that will be
 * saved/loaded and that will be exported to LPC.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public interface Exportable {
    String getType();
    Element writeToXML();
    void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException;
    void checkForExport() throws EleMapExportException;
//    public static EleMappable rebuild(EleMappable data, Element root);
}
