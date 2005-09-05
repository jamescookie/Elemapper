package org.elephant.mapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import org.jdom.CDATA;
import org.jdom.Element;

/**
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class Function implements Exportable {
    public static final String XML_FUNCTION = "FUNCTION";
    public static final String XML_FUNCTION_NAME = "FUNCTIONNAME";
    public static final String XML_FUNCTION_RETURN_TYPE = "FUNCTIONRETURN";
    public static final String XML_FUNCTION_ARGS = "FUNCTIONARGS";
    public static final String XML_FUNCTION_BODY = "FUNCTIONBODY";

    private String _name;
    private String _returnType;
    private String _args;
    private String _body;

    //----------------------------------------------------------
    // Accessors/Mutators
    //----------------------------------------------------------

    public String getName() {return _name;}
    public void setName(String data) {_name = data;}

    public String getReturnType() {return _returnType;}
    public void setReturnType(String data) {_returnType = data;}

    public String getArguments() {return _args;}
    public void setArguments(String data) {_args = data;}

    public String getBody() {return _body;}
    public void setBody(String data) {_body = data;}

    public String getType() {return XML_FUNCTION;}

    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public Function(String returnType, String name, String args, String body) {
        _returnType = returnType;
        _name = name;
        _args = args;
        _body = body;
    }

    //----------------------------------------------------------
    // Static functions
    //----------------------------------------------------------

    public static Exportable rebuild(Element root) {
        Function function = null;
        Element tmp;
        String returnType = null;
        String name = null;
        String args = null;
        String body = null;

        if (root != null) {
            tmp = root.getChild(XML_FUNCTION_RETURN_TYPE);
            if (tmp != null) returnType = tmp.getText().trim();
            tmp = root.getChild(XML_FUNCTION_NAME);
            if (tmp != null) name = tmp.getText().trim();
            tmp = root.getChild(XML_FUNCTION_ARGS);
            if (tmp != null) args = tmp.getText().trim();
            tmp = root.getChild(XML_FUNCTION_BODY);
            if (tmp != null) body = tmp.getText().trim();

            if (name != null) {
                function = new Function(returnType, name, args, body);
            }
        }

        return function;
    }

    //----------------------------------------------------------
    // Public Functions
    //----------------------------------------------------------

    public String getFunctionDeclaration() {
        return _returnType + " " + _name + "(" + (_args == null ? "" : _args) + ")";
    }

    public String toString() {
        // This is what is displayed in lists.
        return getName();
    }

    //----------------------------------------------------------
    // Exportable Functions
    //----------------------------------------------------------

    public Element writeToXML() {
        Element function = new Element(getType());

        function.addContent(EleUtils.createElement(XML_FUNCTION_RETURN_TYPE, _returnType));
        function.addContent(EleUtils.createElement(XML_FUNCTION_NAME, _name));
        function.addContent(EleUtils.createElement(XML_FUNCTION_ARGS, _args));
        Element codeElement = new Element(XML_FUNCTION_BODY);
        codeElement.addContent(new CDATA(_body));
        function.addContent(codeElement);

        return function;
    }

    public void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException {
        if (bw != null) {
            bw.write("\n" + getFunctionDeclaration() + "\n{\n");
            bw.write(_body);
            bw.write("\n}\n");
        }
    }

    public void checkForExport() throws EleMapExportException {
        if (_returnType == null || _returnType.length() < 1) {
            throw new EleMapExportException("Function found with no return type.");
        }
        if (_name == null || _name.length() < 1) {
            throw new EleMapExportException("Function found with no name.");
        }
        if (_body == null || _body.length() < 1) {
            throw new EleMapExportException("Function found with no body.");
        }
    }

}
