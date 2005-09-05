package org.elephant.mapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import java.awt.Graphics;
import java.awt.Point;

import org.jdom.Element;

/**
 * This class is used to hold a collection of {@link Room rooms}
 * and/or {@link Exit exits} so that generic functions can be
 * performed on them.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class EleCollection extends ArrayList<Exportable> implements EleMappable {
    private String _type;

    //----------------------------------------------------------
    // Accessors/Mutators
    //----------------------------------------------------------

    public String getType() {return _type;}

    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public EleCollection(String type) {
        _type = type;
    }

    public EleCollection(String type, int initialCapacity) {
        super(initialCapacity);
        _type = type;
    }

    public EleCollection(String type, Collection<Exportable> c) {
        super(c);
        _type = type;
    }

    //----------------------------------------------------------
    // Static functions
    //----------------------------------------------------------

    public static EleMappable rebuild(EleMappable data, Element root) {
        EleCollection newCollection = null;
        List list;
        Iterator iter;

        if (root != null) {
            newCollection = new EleCollection(root.getName());

            if (root.getChild(Room.XML_ROOM) != null) {
                list = root.getChildren(Room.XML_ROOM);
                iter = list.iterator();
                while (iter.hasNext()) {
                    newCollection.add(Room.rebuild(data, (Element) iter.next()));
                }
            } else if (root.getChild(Exit.XML_EXIT) != null) {
                list = root.getChildren(Exit.XML_EXIT);
                iter = list.iterator();
                while (iter.hasNext()) {
                    newCollection.add(Exit.rebuild(((EleMap) data).getRooms(), (Element) iter.next()));
                }
            } else if (root.getChild(Item.XML_ITEM) != null) {
                list = root.getChildren(Item.XML_ITEM);
                iter = list.iterator();
                while (iter.hasNext()) {
                    newCollection.add(Item.rebuild((Element) iter.next()));
                }
            } else if (root.getChild(LoadedObject.XML_LOADED_OBJECT) != null) {
                list = root.getChildren(LoadedObject.XML_LOADED_OBJECT);
                iter = list.iterator();
                while (iter.hasNext()) {
                    newCollection.add(LoadedObject.rebuild((Element) iter.next()));
                }
            } else if (root.getChild(Function.XML_FUNCTION) != null) {
                list = root.getChildren(Function.XML_FUNCTION);
                iter = list.iterator();
                while (iter.hasNext()) {
                    newCollection.add(Function.rebuild((Element) iter.next()));
                }
            }
        }

        return newCollection;
    }

    //----------------------------------------------------------
    // Public Functions
    //----------------------------------------------------------

    public EleMappable exists(Point p, long l) {
        EleMappable tmp;
        Iterator iter = iterator();

        while (iter.hasNext()) {
            tmp = (EleMappable) iter.next();
            if (tmp.contains(p, l)) {
                return tmp;
            }
        }
        return null;
    }

    //----------------------------------------------------------
    // EleMappable Functions
    //----------------------------------------------------------

    public void paint(Graphics g, long l) {
        Iterator iter = iterator();

        while (iter.hasNext()) {
            ((EleMappable) iter.next()).paint(g, l);
        }
    }

    public void paintLowerLevel(Graphics g, long l) {
        Iterator iter = iterator();

        while (iter.hasNext()) {
            ((EleMappable) iter.next()).paintLowerLevel(g, l);
        }
    }

    public void paintUpperLevel(Graphics g, long l) {
        Iterator iter = iterator();

        while (iter.hasNext()) {
            ((EleMappable) iter.next()).paintUpperLevel(g, l);
        }
    }

    public void paintOutline(Graphics g, long l, int x, int y) {
        Iterator iter = iterator();

        while (iter.hasNext()) {
            ((EleMappable) iter.next()).paintOutline(g, l, x, y);
        }
    }

    public void shift(int y, int x) {
        Iterator iter = iterator();

        while (iter.hasNext()) {
            ((EleMappable) iter.next()).shift(y, x);
        }
    }

    public void zoom(boolean in) {
        Iterator iter = iterator();

        while (iter.hasNext()) {
            ((EleMappable) iter.next()).zoom(in);
        }
    }

    public boolean contains(Point p, long l) {
        Iterator iter = iterator();

        while (iter.hasNext()) {
            if (((EleMappable) iter.next()).contains(p, l)) {
                return true;
            }
        }
        return false;
    }

    public Element writeToXML() {
        Element element = new Element(_type);
        Iterator iter = iterator();

        while (iter.hasNext()) {
            element.addContent(((Exportable) iter.next()).writeToXML());
        }

        return element;
    }

    public void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException {
        String tmp = "";
        Iterator iter = iterator();

        while (iter.hasNext()) {
            ((Exportable) iter.next()).export(directory, path, bw, tmp);
            tmp = seperator;
        }
    }

    public void checkForExport() throws EleMapExportException {
        Iterator iter = iterator();

        while (iter.hasNext()) {
            ((Exportable) iter.next()).checkForExport();
        }
    }

}
