package final_project.demo;

import final_project.controllers.OrderController;
import final_project.dao.HotelDAO;
import final_project.dao.RoomDAO;
import final_project.dao.UserDAO;
import final_project.models.Hotel;
import final_project.models.Order;
import final_project.models.Room;
import final_project.models.User;
import final_project.utils.Session;
import final_project.utils.UserType;

import java.util.Date;

public class DemoOrder {
    public static void main(String[] args) throws Exception {
        OrderController controller = new OrderController();
        UserDAO users = new UserDAO();
        User user =  users.getUserByID(12);
        RoomDAO rooms = new RoomDAO();
        Room room1 = rooms.getRoomByID(8053289996896765002L);

        Order order1 = new Order(user,room1, new Date(), new Date(), 134);
        Session.startSession(user);
//        controller.addOrder(order1);
        controller.bookRoom(room1.getId(), user.getId(), 111);
//        controller.cancelReservation(room1.getId(), user1.getId(), hotel1.getId());
        Session.resetSession();
    }
}
