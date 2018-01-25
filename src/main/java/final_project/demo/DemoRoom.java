package final_project.demo;

import final_project.controllers.RoomController;
import final_project.models.Hotel;
import final_project.models.Room;
import final_project.utils.Filter;

import java.util.Date;

public class DemoRoom {
    public static void main(String[] args) throws Exception{
        RoomController controller = new RoomController();
        Hotel hotel1 = new Hotel("IBIS","BG", "Varna", "Maevska");
        Room room1 = new Room(3, 134,false,false, new Date(), hotel1);
        Room room2 = new Room(2, 164,true,false, new Date(), hotel1);
        Hotel hotel2 = new Hotel("IBIS","UK", "London", "Maevska");
        Room room3 = new Room(2, 124,false,false, new Date(), hotel2);
        Room room4 = new Room(2, 144,true,false, new Date(), hotel2);

        Filter emptyFilter = new Filter();
        Filter filter = new Filter(2,0,true,false,null,null,null,null);

        controller.findRooms(emptyFilter);
        controller.findRooms(filter);
//        controller.addRoom(room1);
//        controller.addRoom(room2);
//        controller.addRoom(room3);
//        controller.addRoom(room4);
//        controller.getRooms();

    }
}
