package final_project.services;

import final_project.dao.OrderDAO;
import final_project.dao.RoomDAO;
import final_project.dao.UserDAO;
import final_project.models.Order;
import final_project.models.Room;
import final_project.models.User;

import java.util.Date;

public class OrderService {
    private UserDAO userDAO = new UserDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private OrderDAO orderDAO = new OrderDAO();

    public void bookRoom(long roomId, long userId) throws Exception {
        Room room = roomDAO.getRoomByID(roomId);
        User user = userDAO.getUserByID(userId);
        Order newOrder = new Order(user, room, new Date(), new Date(), 0);
        orderDAO.addOrder(newOrder);
    }

    public void cancelReservation(long roomId, long userId) throws Exception {
        for (Order order : orderDAO.getAll())
            if (order.getRoom().getId() == roomId && order.getUser().getId() == userId)
                orderDAO.deleteOrder(order.getId());
    }

}
