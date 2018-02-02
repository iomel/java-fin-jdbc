package final_project.dao;

import final_project.models.Order;
import final_project.models.Room;
import final_project.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrderDAO extends GeneralDAO<Order> {

    private RoomDAO roomDAO = new RoomDAO();
    private UserDAO userDAO = new UserDAO();
    private HotelDAO hotelDAO = new HotelDAO();
    private final static String ORDER_DB = "ORDERS";

    public OrderDAO() {
        super(ORDER_DB);
    }

    public Order addOrder(Order order) throws SQLException {

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ORDERS VALUES (?, ?, ?, ?, ?, ?)")){

            if(getById(connection, order.getId()) != null)
                throw new SQLException("Order with such ID is registered already! ID: " + order.getId());
            if (hotelDAO.getById(connection, order.getRoom().getHotel().getId()) == null
                    || roomDAO.getById(connection, order.getRoom().getId()) == null
                    || userDAO.getById(connection, order.getUser().getId()) == null)
                throw new SQLException("Wrong some order's instance(room : hotel : user)");

            statement.setLong(1, order.getId());
            statement.setLong(2, order.getUser().getId());
            statement.setLong(3, order.getRoom().getId());
            statement.setDate(4, new java.sql.Date(order.getDateFrom().getTime()));
            statement.setDate(5, new java.sql.Date(order.getDateTo().getTime()));
            statement.setDouble(6, order.getMoneyPaid());
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue to save order ID: " + order.getId());
        }
        return order;
    }

    @Override
    protected Order buildItem(Connection connection, ResultSet result) throws SQLException {
        long orderId = result.getLong(1);
        long userId = result.getLong(2);
        long roomId = result.getLong(3);
        Date dateFrom = new Date(result.getDate(4).getTime());
        Date dateTo = new Date(result.getDate(5).getTime());
        double moneyPaid = result.getDouble(6);
        Order order = new Order(userDAO.getById(connection, userId), roomDAO.getById(connection, roomId), dateFrom, dateTo, moneyPaid);
        order.setId(orderId);
        return order;
    }

    @Override
    protected ArrayList<Order> buildItemList(Connection connection, ResultSet result) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<User> users = userDAO.getAll(connection);
        ArrayList<Room> rooms = roomDAO.getAll(connection);
        while (result.next()) {
            Order order = buildItem(connection, result);
            for (User user : users)
                if(user.getId() == order.getUser().getId()) {
                    order.setUser(user);
                    break;
                }
            for (Room room : rooms)
                if (room.getId() == order.getRoom().getId()) {
                    order.setRoom(room);
                    break;
                }
            orders.add(order);
        }
        return orders;
    }
}
