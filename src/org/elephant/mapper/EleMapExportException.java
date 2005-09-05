package org.elephant.mapper;

/**
 * Exception thrown if any problems are encountered whilst exporting to LPC.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class EleMapExportException extends Exception {
    private EleMappable _badThing;

    public EleMapExportException() {
    }

    public EleMapExportException(String s) {
        super(s);
    }

    public EleMapExportException(String s, EleMappable ob) {
        super(s);
        _badThing = ob;
    }

    public EleMappable getErrorObject() {
        return _badThing;
    }
}