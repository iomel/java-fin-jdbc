package final_project.demo;

import final_project.controllers.RoomController;
import final_project.dao.HotelDAO;
import final_project.dao.UserDAO;
import final_project.models.Hotel;
import final_project.models.Room;
import final_project.models.User;
import final_project.utils.Filter;
import final_project.utils.Session;

import java.util.Date;

public class DemoRoom {
    public static void main(String[] args) throws Exception{
        RoomController controller = new RoomController();
        HotelDAO hotels = new HotelDAO();
        UserDAO users = new UserDAO();
        User user =  users.getById(12);
        Hotel hotel1 = hotels.getById(307905891483779333L);
        Room room1 = new Room(3, 134,false,false, new Date(), hotel1);
        Room room2 = new Room(2, 164,true,false, new Date(), hotel1);
        Hotel hotel2 = hotels.getById(688945267384385311L);
        Room room3 = new Room(2, 124,false,false, new Date(), hotel2);
        Room room4 = new Room(2, 144,true,false, new Date(), hotel2);

        Filter emptyFilter = new Filter();
        Filter filter = new Filter(2,0,true,false,null,null,null,null);

        Session.startSession(user);
//        controller.addRoom(user,room1);
//        controller.addRoom(user,room2);
//        controller.addRoom(user,room3);
//        controller.addRoom(user,room4);

        for (Room room : controller.findRooms(emptyFilter))
            System.out.println(room.toString());
        System.out.println("***** NOT EMPTY FILTER *******");
        for (Room room : controller.findRooms(filter))
            System.out.println(room.toString());


        Session.resetSession();
//        controller.addRoom(room1);
//        controller.addRoom(room2);
//        controller.addRoom(room3);
//        controller.addRoom(room4);
//        controller.getRooms();

    }
}
