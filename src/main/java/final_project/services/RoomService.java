package final_project.services;

import final_project.dao.RoomDAO;
import final_project.models.Room;
import final_project.utils.Filter;
import lesson22.userrepo.exception.BadRequestException;

import java.util.ArrayList;

public class RoomService {
    private RoomDAO roomDAO = new RoomDAO();

    public Room addRoom(Room room) throws Exception {
        validate(room);
        return roomDAO.addRoom(room);
    }

    public void deleteRoom(Room room) throws Exception {
        if(room != null)
            roomDAO.deleteRoom(room.getId());
    }

    public ArrayList<Room> findRooms(Filter filter) throws Exception{
        ArrayList<Room> filteredRooms = new ArrayList<>();
        for (Room room : roomDAO.getAll())
            if(filter.match(room))
                filteredRooms.add(room);
        return filteredRooms;
    }

    private void validate(Room room)throws Exception {
        if (room == null)
            throw new BadRequestException("RoomService.validate error - room is NULL!");

        if (room.toString().contains("null") || room.toString().contains(",,"))
            throw new BadRequestException("RoomService.validate error - room has empty parameter! Room ID: " + room.getId());
    }

}
