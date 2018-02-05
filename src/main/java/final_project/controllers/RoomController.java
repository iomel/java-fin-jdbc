package final_project.controllers;

import final_project.models.Room;
import final_project.models.User;
import final_project.services.RoomService;
import final_project.utils.Filter;
import final_project.utils.Session;
import final_project.utils.UserType;
import final_project.utils.exceptions.BadRequestException;

import java.util.ArrayList;

public class RoomController {
    private RoomService roomService = new RoomService();

    public Room addRoom(User user, Room room) throws Exception {
        if(!Session.checkSession(user, UserType.ADMIN))
            throw new BadRequestException("User have no right to add room!");

        return roomService.addRoom(room);
    }

    public void deleteRoom(User user, Room room) throws Exception {
        if(!Session.checkSession(user, UserType.ADMIN))
            throw new BadRequestException("User have no right to delete room!");

        roomService.deleteRoom(room);
    }

    public ArrayList<Room> findRooms(Filter filter) throws Exception {
        return roomService.findRooms(filter);
    }
}
