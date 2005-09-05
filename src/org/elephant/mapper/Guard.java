package org.elephant.mapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import org.jdom.Element;

/**
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class Guard implements Exportable { // todo JPC extends LoadedObject?
    public static final String XML_GUARD = "GUARD";
    public static final String XML_LOADED_OBJECT_OBJECT = "LOADOBJOBJECT";
    public static final String XML_LOADED_OBJECT_MONSTER = "LOADOBJMONSTER";
    public static final String XML_LOADED_OBJECT_NAME = "LOADOBJNAME";
    public static final String XML_LOADED_OBJECT_MESSAGE = "LOADOBJMESSAGE";
    public static final String XML_LOADED_OBJECT_TRACK = "LOADOBJTRACK";
    public static final String XML_LOADED_OBJECT_UNIQUE = "LOADOBJUNIQUE";
    public static final String XML_LOADED_OBJECT_PRESENT = "LOADOBJPRESENT";

    private int _type;
    private int _loadType;
    private String _fileName;
    private String _message;

    //----------------------------------------------------------
    // Accessors/Mutators
    //----------------------------------------------------------

    public String getType() {return XML_GUARD;}

    public int getObjectType() {return _type;}
    public void setObjectType(int data) {_type = data;}

    public int getLoadType() {return _loadType;}
    public void setLoadType(int data) {_loadType = data;}

    public String getFileName() {return _fileName;}
    public void setFileName(String data) {_fileName = data;}

    public String getMessage() {return _message;}
    public void setMessage(String data) {_message = data;}

    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public Guard(int objectType, int loadType, String fileName, String message) {
        _type = objectType;
        _loadType = loadType;
        _fileName = fileName;
        _message = message;
    }

    //----------------------------------------------------------
    // Static functions
    //----------------------------------------------------------

    public static Exportable rebuild(EleMappable data, Element root) {
        Element tmp;
        LoadedObject object = null;
        int objectType = 0;
        int loadType = 0;
        String fileName = null;
        String message = null;

        if (root != null) {
//            tmp = root.getChild(XML_LOADED_OBJECT_OBJECT);
//            if (tmp != null) objectType = OBJECT_TYPE_OBJ;
//            tmp = root.getChild(XML_LOADED_OBJECT_MONSTER);
//            if (tmp != null) objectType = OBJECT_TYPE_MON;
//            tmp = root.getChild(XML_LOADED_OBJECT_NAME);
//            if (tmp != null) fileName = tmp.getText().trim();
//            tmp = root.getChild(XML_LOADED_OBJECT_MESSAGE);
//            if (tmp != null) message = tmp.getText().trim();
//            tmp = root.getChild(XML_LOADED_OBJECT_PRESENT);
//            if (tmp != null) loadType = LOAD_TYPE_PRESENT;
//            tmp = root.getChild(XML_LOADED_OBJECT_TRACK);
//            if (tmp != null) loadType = LOAD_TYPE_TRACK;
//            tmp = root.getChild(XML_LOADED_OBJECT_UNIQUE);
//            if (tmp != null) loadType = LOAD_TYPE_UNIQUE;
//
//            if (objectType != 0 && loadType != 0 && fileName != null && message != null) {
//                object = new LoadedObject(objectType, loadType, fileName, message);
//            }
        }

        return object;
    }

    //----------------------------------------------------------
    // Exportable Functions
    //----------------------------------------------------------

    public Element writeToXML() {
        Element obj = new Element(getType());

//        if (m_type == OBJECT_TYPE_MON) {
//            obj.addContent(new Element(XML_LOADED_OBJECT_MONSTER));
//        } else {
//            obj.addContent(new Element(XML_LOADED_OBJECT_OBJECT));
//        }
//
//        obj.addContent(EleUtils.createElement(XML_LOADED_OBJECT_NAME, m_fileName));
//        obj.addContent(EleUtils.createElement(XML_LOADED_OBJECT_MESSAGE, m_message));
//
//        if (m_loadType == LOAD_TYPE_PRESENT) {
//            obj.addContent(new Element(XML_LOADED_OBJECT_PRESENT));
//        } else if (m_loadType == LOAD_TYPE_TRACK) {
//            obj.addContent(new Element(XML_LOADED_OBJECT_TRACK));
//        } else {
//            obj.addContent(new Element(XML_LOADED_OBJECT_UNIQUE));
//        }

        return obj;
    }

    public void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException {
        String loadType = "";
        String objectType = "";

//        if (bw != null) {
//            if (m_type == OBJECT_TYPE_MON) {
//                objectType = EleConstants.DEFINE_MON_PATH;
//            } else if (m_type == OBJECT_TYPE_OBJ) {
//                objectType = EleConstants.DEFINE_OBJ_PATH;
//            }
//
//            if (m_loadType == LOAD_TYPE_PRESENT) {
//                loadType = "RT_PRESENT";
//            } else if (m_loadType == LOAD_TYPE_TRACK) {
//                loadType = "RT_TRACK";
//            } else if (m_loadType == LOAD_TYPE_UNIQUE) {
//                loadType = "RT_UNIQUE";
//            }
//
//            bw.write("    add_object("+objectType+" + \""+m_fileName+"\", \""+m_message+"\", "+loadType+");\n");
//        }
    }

    public void checkForExport() throws EleMapExportException {
//        if (m_fileName == null || m_fileName.length() < 1) {
//            throw new EleMapExportException("Loaded Object found with no name: "+this);
//        }
//        if ((m_type != OBJECT_TYPE_MON) && (m_type != OBJECT_TYPE_OBJ)) {
//            throw new EleMapExportException("Loaded Object found with an invalid type: "+this);
//        }
//        if ((m_loadType != LOAD_TYPE_PRESENT) && (m_loadType != LOAD_TYPE_TRACK) && (m_loadType != LOAD_TYPE_UNIQUE)) {
//            throw new EleMapExportException("Loaded Object found with an invalid load type: "+this);
//        }
    }
}
