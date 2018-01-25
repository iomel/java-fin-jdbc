package final_project.dao;

import final_project.models.Room;
import final_project.utils.FilesIO;

import java.util.TreeSet;

public class RoomDAO extends GeneralDAO<Room> {

    private static final String PATH_DB = "D://Test//RoomDB.txt";

    public Room addRoom(Room room) throws Exception {
        return add(PATH_DB, room);
    }

    public void deleteRoom(long id) throws Exception {
        delete(PATH_DB, id);
    }

    public TreeSet<Room> getAll() throws Exception {
        TreeSet<Room> rooms = new TreeSet<>();
        String[] loadedRooms = FilesIO.readFile(PATH_DB).split("\n");

        for(String room : loadedRooms)
            if(!room.isEmpty())
                rooms.add(Room.stringToObject(room));
        return rooms;
    }

    public Room getRoomByID(long id) throws Exception{
        for(Room room : getAll())
            if(room.getId() == id)
                return room;
        return null;
    }

}
