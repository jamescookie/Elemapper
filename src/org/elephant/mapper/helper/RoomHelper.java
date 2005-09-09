package org.elephant.mapper.helper;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

import org.elephant.mapper.Room;
import org.elephant.mapper.Item;
import org.elephant.mapper.EleHashtable;
import org.elephant.mapper.LoadedObject;
import org.elephant.mapper.Exportable;
import org.elephant.mapper.EleExportableCollection;
import org.elephant.mapper.Function;

/**
 * @author UKJamesCook
 */
public class RoomHelper {
    private Room currentRoom;
    private Room copiedRoom;

    public Room getRoom() {
        return currentRoom;
    }

    public void setRoom(Room room) {
        currentRoom = room;
    }

    public boolean hasRoom() {
        return currentRoom != null;
    }

    public Room getCopiedRoom() {
        return copiedRoom;
    }

    public boolean hasCopiedRoom() {
        return copiedRoom != null;
    }

    public void createCopyRoom(int roomSize, long roomNumber) {
        if (hasRoom()) {
            copiedRoom = currentRoom.clone(roomSize, roomNumber);
        }
    }

    public void updateCurrentRoomWithCopiedRoom() {
        if (hasRoom() && hasCopiedRoom()) {
            currentRoom.update(copiedRoom);
        }
    }

    public void makeCopiedRoomTheCurrentRoom() {
        currentRoom = copiedRoom;
    }

    public void clearRoom() {
        setRoom(null);
    }

    public void deSelect(Graphics g, long level, boolean showUpper, boolean showLower) {
        if (hasRoom()) {
            currentRoom.deSelect(g, level, showUpper, showLower);
            clearRoom();
        }
    }

    public void addItem(ArrayList<String> itemNames, String description) {
        int i;
        Item item;
        boolean found = false;
        if (itemNames.size() > 0) {
            for (i = 0; i < currentRoom.getItems().size(); i++) {
                if (currentRoom.getItem(i).containsNames(itemNames)) {
                    found = true;
                    break;
                }
            }

            item = new Item(itemNames, description);
            if (found) {
                currentRoom.getItems().set(i, item);
            } else {
                currentRoom.getItems().add(item);
            }
        }
    }

    public void removeItem(int index) {
        if (index > -1) {
            currentRoom.getItems().remove(index);
        }
    }

    public void removeSense(int index, EleHashtable senses) {
        if (index > -1) {
            if (senses != null) {
                senses.remove(index);
            }
        }
    }

    public void addObject(String fileName, boolean loadPresent, boolean loadTrack, boolean loadUnique, boolean isMonster, String enterMessage) {
        int loadType = 0;
        LoadedObject obj;
        boolean found = false;
        int i;
        EleExportableCollection<Exportable> loadedObjects;

        if (fileName != null && fileName.length() > 0) {
            if (loadPresent) {
                loadType = LoadedObject.LOAD_TYPE_PRESENT;
            } else if (loadTrack) {
                loadType = LoadedObject.LOAD_TYPE_TRACK;
            } else if (loadUnique) {
                loadType = LoadedObject.LOAD_TYPE_UNIQUE;
            }

            obj = new LoadedObject((isMonster ?LoadedObject.OBJECT_TYPE_MON:LoadedObject.OBJECT_TYPE_OBJ),
                                   loadType, fileName, enterMessage);

            loadedObjects = currentRoom.getLoadedObjects();
            for (i = 0; i < loadedObjects.size(); i++) {
                if (((LoadedObject) loadedObjects.get(i)).getFileName().equals(fileName)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                loadedObjects.set(i, obj);
            } else {
                loadedObjects.add(obj);
            }
        }
    }

    public void addFunction(String functionName, String returnType, String arguments, String body) {
        Function function;
        boolean found = false;
        int i;
        EleExportableCollection<Exportable> functions;

        if (functionName != null && functionName.length() > 0) {

            function = new Function(returnType, functionName, arguments, body);

            functions = currentRoom.getFunctions();
            for (i = 0; i < functions.size(); i++) {
                if (((Function) functions.get(i)).getName().equals(functionName)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                functions.set(i, function);
            } else {
                functions.add(function);
            }
        }
    }

    public void removeObject(LoadedObject object) {
        currentRoom.getLoadedObjects().remove(object);
    }

    public void removeFunction(Function function) {
        currentRoom.getFunctions().remove(function);
    }

    public void setColour(Color c) {
        if (hasRoom()) {
            currentRoom.setColour(c);
        }
    }

    public EleHashtable currentSense(boolean smells, boolean sounds) {
        return currentSense(smells, sounds, getRoom());
    }

    public static EleHashtable currentSense(boolean smells, boolean sounds, Room room) {
        EleHashtable senses = null;

        if (room != null) {
            if (smells) {
                senses = room.getSmells();
            } else if (sounds) {
                senses = room.getSounds();
            }
        }
        return senses;
    }

}
