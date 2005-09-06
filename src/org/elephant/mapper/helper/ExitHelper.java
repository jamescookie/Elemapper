package org.elephant.mapper.helper;

import java.awt.Graphics;

import org.elephant.mapper.Exit;

/**
 * @author UKJamesCook
 */
public class ExitHelper {
    private Exit _currentExit;

    public Exit getExit() {
        return _currentExit;
    }

    public void setExit(Exit exit) {
        _currentExit = exit;
    }

    public boolean hasExit() {
        return _currentExit != null;
    }

    public void clearExit() {
        setExit(null);
    }

    public void deSelect(Graphics g, long level, boolean showUpper, boolean showLower) {
        if (hasExit()) {
            _currentExit.deSelect(g, level, showUpper, showLower);
            clearExit();
        }
    }
}
