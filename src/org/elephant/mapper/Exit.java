package org.elephant.mapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.awt.Graphics;
import java.awt.Point;

import org.jdom.Element;

/**
 * The <code>Exit</code> class defines an exit object, one of the two basic components
 * of the map. An exit defines a link between two {@link Room rooms}.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class Exit implements EleMappable {
    // Note: These must be kept in line with the DTD.
    public static final String XML_EXIT = "EXIT";
    public static final String XML_NUMBER = "EXITID";
    public static final String XML_LEVEL = "EXITLEVEL";
    public static final String XML_ROOM1 = "ROOM1";
    public static final String XML_ROOM2 = "ROOM2";
    public static final String XML_INVIS = "INVIS";
    public static final String XML_DOOR_DATA = "DOORDATA";
    public static final String XML_DOOR = "DOOR";
    public static final String XML_DOOR_NAME1 = "DOORNAME1";
    public static final String XML_DOOR_NAME2 = "DOORNAME2";
    public static final String XML_DOOR_DESC1 = "DOORDESC1";
    public static final String XML_DOOR_DESC2 = "DOORDESC2";
    public static final String XML_DOOR_OPEN_DESC1 = "DOOROPENDESC1";
    public static final String XML_DOOR_OPEN_DESC2 = "DOOROPENDESC2";
    public static final String XML_DOOR_CLOSED_DESC1 = "DOORCLOSEDDESC1";
    public static final String XML_DOOR_CLOSED_DESC2 = "DOORCLOSEDDESC2";
    public static final String XML_CONCEALED = "CONCEALED";
    public static final String XML_LOCKABLE = "LOCKABLE";
    public static final String XML_DOOR_KEY_ROOM1 = "DOORKEY";
    public static final String XML_DOOR_KEY_ROOM2 = "DOORKEY2";

    private Room _room1;
    private Room _room2;
    private int _direction1;
    private int _direction2;
    private boolean _selected = false;
    private long _exitNumber = 0;
    private long _level = 0;
    private Point _p1;
    private Point _p2;
    private int _invisible = EleConstants.EXIT_OPTION_NO;
    private int _door = EleConstants.EXIT_OPTION_NO;
    private int _concealed = EleConstants.EXIT_OPTION_NO;
    private boolean _lockable = false;
    private String _keyFromRoom1;
    private String _keyFromRoom2;
    private String _doorNameFromRoom1;
    private String _doorNameFromRoom2;
    private String _doorDescFromRoom1;
    private String _doorDescFromRoom2;
    private String _doorOpenDescFromRoom1;
    private String _doorOpenDescFromRoom2;
    private String _doorClosedDescFromRoom1;
    private String _doorClosedDescFromRoom2;

    //----------------------------------------------------------
    // Accessors/Mutators
    //----------------------------------------------------------

    public long getExitNumber() {return _exitNumber;}

    public long getExitLevel() {return _level;}

    public String getType() {return XML_EXIT;}

    public Room getRoom1() {return _room1;}

    public Room getRoom2() {return _room2;}

    public void setInvisible(int data) {_invisible = data;}
    public int getInvisible() {return _invisible;}

    public boolean isInvisible() {return _invisible != EleConstants.EXIT_OPTION_NO;}

    public void setConcealed(int data) {_concealed = data;}
    public int getConcealed() {return _concealed;}

    public boolean isConcealed() {return _concealed != EleConstants.EXIT_OPTION_NO;}

    public void setDoor(int data) {_door = data;}
    public int getDoor() {return _door;}

    public boolean isDoor() {return _door != EleConstants.EXIT_OPTION_NO;}

    public void setLockable(boolean data) {_lockable = data;}
    public boolean isLockable() {return _lockable;}

    public void setKeyFromRoom1(String data) {_keyFromRoom1 = data;}
    public String getKeyFromRoom1() {return _keyFromRoom1;}

    public void setKeyFromRoom2(String data) {_keyFromRoom2 = data;}
    public String getKeyFromRoom2() {return _keyFromRoom2;}

    public void setDoorName1(String data) {_doorNameFromRoom1 = data;}
    public String getDoorName1() {return _doorNameFromRoom1;}

    public void setDoorName2(String data) {_doorNameFromRoom2 = data;}
    public String getDoorName2() {return _doorNameFromRoom2;}

    public void setDoorDescription1(String data) {_doorDescFromRoom1 = data;}
    public String getDoorDescription1() {return _doorDescFromRoom1;}

    public void setDoorDescription2(String data) {_doorDescFromRoom2 = data;}
    public String getDoorDescription2() {return _doorDescFromRoom2;}

    public void setDoorOpenDescription1(String data) {_doorOpenDescFromRoom1 = data;}
    public String getDoorOpenDescription1() {return _doorOpenDescFromRoom1;}

    public void setDoorOpenDescription2(String data) {_doorOpenDescFromRoom2 = data;}
    public String getDoorOpenDescription2() {return _doorOpenDescFromRoom2;}

    public void setDoorClosedDescription1(String data) {_doorClosedDescFromRoom1 = data;}
    public String getDoorClosedDescription1() {return _doorClosedDescFromRoom1;}

    public void setDoorClosedDescription2(String data) {_doorClosedDescFromRoom2 = data;}
    public String getDoorClosedDescription2() {return _doorClosedDescFromRoom2;}

    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public Exit(Room r1, Room r2, long n, long l) {
        _room1 = r1;
        _room2 = r2;
        _exitNumber = n;
        _level = l;
        _direction1 = _room1.directionToRoom(_room2);
        _direction2 = _room2.directionToRoom(_room1);
        _p1 = _room1.getPointForDirection(_direction1);
        _p2 = _room2.getPointForDirection(_direction2);
        _room1.addExit(this, _direction1);
        _room2.addExit(this, _direction2);
    }

    public Exit(Room r1, Room r2, long n, long l, Graphics g) {
        this(r1, r2, n, l);
        _selected = true;
        paint(g);
    }

    //----------------------------------------------------------
    // Static functions
    //----------------------------------------------------------

    public static EleMappable rebuild(EleMappable data, Element root) {
        EleExportableCollection<Exportable> rooms = (EleExportableCollection<Exportable>) data;
        Exit e = null;
        Long number = null;
        Long level = null;
        Room r1 = null;
        Room r2 = null;
        Element tmp;

        if (root != null) {
            tmp = root.getChild(XML_NUMBER);
            if (tmp != null) number = Long.valueOf(tmp.getTextTrim());
            tmp = root.getChild(XML_LEVEL);
            if (tmp != null) level = Long.valueOf(tmp.getTextTrim());
            tmp = root.getChild(XML_ROOM1);
            if (tmp != null) r1 = Room.getRoomFromNumber(rooms, Long.valueOf(tmp.getTextTrim()));
            tmp = root.getChild(XML_ROOM2);
            if (tmp != null) r2 = Room.getRoomFromNumber(rooms, Long.valueOf(tmp.getTextTrim()));

            if (number != null && r1 != null && r2 != null && level != null) {
                e = new Exit(r1, r2, number, level);

                tmp = root.getChild(XML_INVIS);
                if (tmp != null) e.setInvisible(Integer.valueOf(tmp.getTextTrim()));

                root = root.getChild(XML_DOOR_DATA);
                if (root != null) {
                    tmp = root.getChild(XML_DOOR);
                    if (tmp != null) e.setDoor(Integer.valueOf(tmp.getTextTrim()));
                    tmp = root.getChild(XML_DOOR_NAME1);
                    if (tmp != null) e.setDoorName1(tmp.getTextTrim());
                    tmp = root.getChild(XML_DOOR_NAME2);
                    if (tmp != null) e.setDoorName2(tmp.getTextTrim());
                    tmp = root.getChild(XML_DOOR_DESC1);
                    if (tmp != null) e.setDoorDescription1(tmp.getTextTrim());
                    tmp = root.getChild(XML_DOOR_DESC2);
                    if (tmp != null) e.setDoorDescription2(tmp.getTextTrim());
                    tmp = root.getChild(XML_DOOR_OPEN_DESC1);
                    if (tmp != null) e.setDoorOpenDescription1(tmp.getTextTrim());
                    tmp = root.getChild(XML_DOOR_OPEN_DESC2);
                    if (tmp != null) e.setDoorOpenDescription2(tmp.getTextTrim());
                    tmp = root.getChild(XML_DOOR_CLOSED_DESC1);
                    if (tmp != null) e.setDoorClosedDescription1(tmp.getTextTrim());
                    tmp = root.getChild(XML_DOOR_CLOSED_DESC2);
                    if (tmp != null) e.setDoorClosedDescription2(tmp.getTextTrim());
                    tmp = root.getChild(XML_CONCEALED);
                    if (tmp != null) e.setConcealed(Integer.valueOf(tmp.getTextTrim()));
                    tmp = root.getChild(XML_LOCKABLE);
                    if (tmp != null) e.setLockable(Boolean.valueOf(tmp.getTextTrim()));
                    tmp = root.getChild(XML_DOOR_KEY_ROOM1);
                    if (tmp != null) e.setKeyFromRoom1(tmp.getTextTrim());
                    tmp = root.getChild(XML_DOOR_KEY_ROOM2);
                    if (tmp != null) e.setKeyFromRoom2(tmp.getTextTrim());
                }
            }
        }

        return e;
    }

    //----------------------------------------------------------
    // Public funstions
    //----------------------------------------------------------

    public void dispose() {
        _room1.removeExit(this, _direction1);
        _room2.removeExit(this, _direction2);
    }

    public void select(Graphics g) {
        _selected = true;
        paint(g);
    }

    public void deSelect(Graphics g, long l, boolean showUpper, boolean showLower) {
        _selected = false;
        if (_level == l) {
            paint(g, l);
        } else if ((_level == l + 1) && showUpper) {
            paintUpperLevel(g, l);
        } else if ((_level == l - 1) && showLower) {
            paintLowerLevel(g, l);
        }
    }

    public int getDirection(Room r) {
        if (r.equals(_room1))
            return _direction1;
        return _direction2;
    }

    public Room getOppositeRoom(Room r) {
        if (r.equals(_room1))
            return _room2;
        return _room1;
    }

    public boolean isVerticalExit() {
        return (_direction1 == EleConstants.DIRECTION_UP || _direction1 == EleConstants.DIRECTION_DOWN);
    }

    public boolean isKeySame() {
        return isLockable() && _keyFromRoom1.equals(_keyFromRoom2);
    }

    public boolean isDoorSame() {
        return isDoor() && _doorNameFromRoom1.equals(_doorNameFromRoom2);
    }

    public String getDoorName(Room r) {
        if (r.equals(_room1)) {
            return _doorNameFromRoom1;
        } else {
            return _doorNameFromRoom2;
        }
    }

    public boolean isDoorDescriptionSame() {
        return isDoor() && (_doorDescFromRoom1 != null && _doorDescFromRoom1.equals(_doorDescFromRoom2));
    }

    public String getDoorDescription(Room r) {
        if (r.equals(_room1)) {
            return _doorDescFromRoom1;
        } else {
            return _doorDescFromRoom2;
        }
    }

    public String getDoorOpenDescription(Room r) {
        if (r.equals(_room1)) {
            return _doorOpenDescFromRoom1;
        } else {
            return _doorOpenDescFromRoom2;
        }
    }

    public String getDoorClosedDescription(Room r) {
        if (r.equals(_room1)) {
            return _doorClosedDescFromRoom1;
        } else {
            return _doorClosedDescFromRoom2;
        }
    }

    public String getKeyID(Room r) {
        if (r.equals(_room1)) {
            return _keyFromRoom1;
        } else {
            return _keyFromRoom2;
        }
    }

    public boolean isInvisible(Room r) {
        if (r.equals(_room1)) {
            return _invisible == EleConstants.EXIT_OPTION_ROOM1;
        } else {
            return _invisible == EleConstants.EXIT_OPTION_ROOM2;
        }
    }

    public boolean isConcealed(Room r) {
        if (r.equals(_room1)) {
            return _concealed == EleConstants.EXIT_OPTION_YES || _concealed == EleConstants.EXIT_OPTION_ROOM1;
        } else {
            return _concealed == EleConstants.EXIT_OPTION_YES || _concealed == EleConstants.EXIT_OPTION_ROOM2;
        }
    }

    //----------------------------------------------------------
    // EleMappable Functions
    //----------------------------------------------------------

    public void paint(Graphics g, long l) {
        List<Point> v;

        if (g != null) {
            if (_selected) {
                g.setColor(EleConstants.SELECT_COLOUR);
            } else {
                g.setColor(EleConstants.EXIT_COLOUR);
            }

            if (isVerticalExit()) {
                if (l == _level) {
                    v = howToDrawDownExit(_room1);
                    g.fillPolygon(extractXCoords(v), extractYCoords(v), 3);
                } else if (l == _level - 1) {
                    //draw a point on the room
                    v = howToDrawUpExit(_room1);
                    g.fillPolygon(extractXCoords(v), extractYCoords(v), 3);
                }
            } else if (l == _level) {
                g.drawLine(_p1.x, _p1.y, _p2.x, _p2.y);
            }
        }
    }

    public void paintLowerLevel(Graphics g, long l) {
        if (g != null && l == _level - 1) {
            g.setColor(EleConstants.LOWER_LEVEL_COLOUR);
//            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public void paintUpperLevel(Graphics g, long l) {
        if (g != null && l == _level + 1) {
            g.setColor(EleConstants.UPPER_LEVEL_COLOUR);
//            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public void paintOutline(Graphics g, long l, int x, int y) {
        if (g != null && l == _level) {
            g.drawLine(_p1.x + x, _p1.y + y, _p2.x + x, _p2.y + y);
        }
    }

    public void shift(int y, int x) {
        _p1.x += x;
        _p1.y += y;
        _p2.x += x;
        _p2.y += y;
    }

    public void zoom(boolean in) {
        //NOTE: This MUST be called AFTER the rooms have been zoomed.
        _p1 = _room1.getPointForDirection(_direction1);
        _p2 = _room2.getPointForDirection(_direction2);
    }

    public boolean contains(Point p, long l) {
        if (l == _level) {
            if ((_room1.getExitRectangle(_direction1)).contains(p))
                return true;
            if ((_room2.getExitRectangle(_direction2)).contains(p))
                return true;
        }
        return false;
    }

    public Element writeToXML() {
        Element exit = new Element(getType());
        Element door = new Element(XML_DOOR_DATA);

        exit.addContent(EleUtils.createElement(XML_NUMBER, String.valueOf(_exitNumber)));
        exit.addContent(EleUtils.createElement(XML_LEVEL, String.valueOf(_level)));
        exit.addContent(EleUtils.createElement(XML_ROOM1, String.valueOf(_room1.getRoomNumber())));
        exit.addContent(EleUtils.createElement(XML_ROOM2, String.valueOf(_room2.getRoomNumber())));
        exit.addContent(EleUtils.createElement(XML_INVIS, String.valueOf(_invisible)));

        door.addContent(EleUtils.createElement(XML_DOOR, String.valueOf(_door)));
        door.addContent(EleUtils.createElement(XML_DOOR_NAME1, _doorNameFromRoom1));
        door.addContent(EleUtils.createElement(XML_DOOR_NAME2, _doorNameFromRoom2));
        door.addContent(EleUtils.createElement(XML_DOOR_DESC1, _doorDescFromRoom1));
        door.addContent(EleUtils.createElement(XML_DOOR_DESC2, _doorDescFromRoom2));
        door.addContent(EleUtils.createElement(XML_DOOR_OPEN_DESC1, _doorOpenDescFromRoom1));
        door.addContent(EleUtils.createElement(XML_DOOR_OPEN_DESC2, _doorOpenDescFromRoom2));
        door.addContent(EleUtils.createElement(XML_DOOR_CLOSED_DESC1, _doorClosedDescFromRoom1));
        door.addContent(EleUtils.createElement(XML_DOOR_CLOSED_DESC2, _doorClosedDescFromRoom2));
        door.addContent(EleUtils.createElement(XML_CONCEALED, String.valueOf(_concealed)));
        door.addContent(EleUtils.createElement(XML_LOCKABLE, String.valueOf(_lockable)));
        door.addContent(EleUtils.createElement(XML_DOOR_KEY_ROOM1, _keyFromRoom1));
        door.addContent(EleUtils.createElement(XML_DOOR_KEY_ROOM2, _keyFromRoom2));

        exit.addContent(door);

        return exit;

    }

    public void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException {
        //Not needed
    }

    public void checkForExport() throws EleMapExportException {
        if (_door != EleConstants.EXIT_OPTION_NO) {
            if (_doorNameFromRoom1 == null || _doorNameFromRoom1.length() < 1) {
                throw new EleMapExportException(getErrorStart()+"does not have a door name from Room A.", this);
            }
            if (_doorNameFromRoom2 == null || _doorNameFromRoom2.length() < 1) {
                throw new EleMapExportException(getErrorStart()+"does not have a door name from Room B.", this);
            }
            if (_lockable) {
                if (_keyFromRoom1 == null || _keyFromRoom1.length() < 1) {
                    throw new EleMapExportException(getErrorStart()+"is lockable but does not have a key ID from Room A.", this);
                }
                if (_keyFromRoom2 == null || _keyFromRoom2.length() < 1) {
                    throw new EleMapExportException(getErrorStart()+"is lockable but does not have a key ID from Room B.", this);
                }
            }
        }
    }

    //----------------------------------------------------------
    // Private Funtions
    //----------------------------------------------------------

    private void paint(Graphics g) {
        paint(g, _level);
    }

    private List<Point> howToDrawUpExit(Room r) {
        List<Point> tmp = new ArrayList<Point>();
        tmp.add(r.getPointForDirection(EleConstants.DIRECTION_NORTH));
        tmp.add(r.getPointForDirection(EleConstants.DIRECTION_NORTHEAST));
        tmp.add(r.getPointForDirection(EleConstants.DIRECTION_EAST));
        return tmp;
    }

    private List<Point> howToDrawDownExit(Room r) {
        List<Point> tmp = new ArrayList<Point>();
        tmp.add(r.getPointForDirection(EleConstants.DIRECTION_WEST));
        tmp.add(r.getPointForDirection(EleConstants.DIRECTION_SOUTHWEST));
        tmp.add(r.getPointForDirection(EleConstants.DIRECTION_SOUTH));
        return tmp;
    }

    private int[] extractXCoords(List<Point> v) {
        int[] tmp = new int[v.size()];
        for (int i = 0; i < v.size(); i++) {
            tmp[i] = (v.get(i)).x;
        }
        return tmp;
    }

    private int[] extractYCoords(List<Point> v) {
        int[] tmp = new int[v.size()];
        for (int i = 0; i < v.size(); i++) {
            tmp[i] = (v.get(i)).y;
        }
        return tmp;
    }

    private String getErrorStart() {
        return "Exit " + _exitNumber + " between rooms " + _room1.getRoomName() + " & " + _room2.getRoomName() + " ";
    }

}
