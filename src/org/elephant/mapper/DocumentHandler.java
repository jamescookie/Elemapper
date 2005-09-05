package org.elephant.mapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

// JDOM classes
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * This class allows a map to be saved and loaded. It uses JDOM to create and
 * interpret XML.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class DocumentHandler {
    private File _file;
    private EleMappable _data;

    //----------------------------------------------------------
    // Accessors/Mutators
    //----------------------------------------------------------

    public void setData(EleMappable data) {_data = data;}
    public EleMappable getData() {return _data;}

    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public DocumentHandler(File file) {
        _file = file;
        if (_file == null) {
            throw new IllegalArgumentException("File provided is null.");
        }
    }

    //----------------------------------------------------------
    // Public methods
    //----------------------------------------------------------

    public void writeToXML() throws IOException {
        Document doc;
        DocType type;
        FileOutputStream out;
        XMLOutputter serializer = new XMLOutputter();

        if (_file.exists()) {
            if (_file.canWrite()) {
                _file.delete();
            } else {
                throw new IOException("Cannot write to file " + _file.getAbsolutePath());
            }
        }

        _file.createNewFile();

        type = new DocType(_data.getType());
        doc = new Document(_data.writeToXML(), type);
        // serialize it into a file
        out = new FileOutputStream(_file.getAbsolutePath());
        serializer.setFormat(Format.getPrettyFormat());
        serializer.output(doc, out);
        out.flush();
        out.close();
    }

    public void readFromXML() throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        Document doc;

        if (_file.exists()) {
            if (!_file.canRead()) {
                throw new IOException("Cannot read file " + _file.getAbsolutePath());
            }
        }

        doc = builder.build(_file);
        _data = EleMap.rebuild(doc.getRootElement());
    }
}
