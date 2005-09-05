package org.elephant.mapper;

import java.util.Iterator;
import java.util.List;
import java.awt.Point;
import java.awt.Graphics;

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
public class EleMappableCollection<T extends EleMappable> extends EleExportableCollection<EleMappable> implements EleMappable {

    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public EleMappableCollection(String type) {
        super(type);
    }

    //----------------------------------------------------------
    // Static functions
    //----------------------------------------------------------

    public static EleMappableCollection<EleMappable> rebuild(EleMappable data, Element root) {
        EleMappableCollection<EleMappable> newCollection = null;
        List list;
        Iterator iter;

        if (root != null) {
            newCollection = new EleMappableCollection<EleMappable>(root.getName());

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
            }
        }

        return newCollection;
    }

    //----------------------------------------------------------
    // Overridden Functions
    //----------------------------------------------------------

    public boolean add(T o) {
        return super.add(o);
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

}
