package org.elephant.mapper;

import java.awt.Graphics;
import java.awt.Point;

/**
 * An interface that needs to be implemented by all objects that wish to
 * be present on the graphical map, or objects that contain those type of objects.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public interface EleMappable extends Exportable {
    void paint(Graphics g, long level);
    void paintLowerLevel(Graphics g, long level);
    void paintUpperLevel(Graphics g, long level);
    void paintOutline(Graphics g, long level, int x, int y);
    void shift(int y, int x);
    void zoom(boolean in);
    boolean contains(Point p, long level);
}
