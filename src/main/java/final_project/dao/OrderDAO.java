package final_project.dao;

import final_project.models.Order;
import final_project.models.Room;
import final_project.models.User;
import final_project.utils.exceptions.BadRequestException;
import final_project.utils.exceptions.InternalServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrderDAO extends GeneralDAO<Order> {

    private RoomDAO roomDAO = new RoomDAO();
    private UserDAO userDAO = new UserDAO();
    private final static String ORDER_DB = "ORDERS";

    public OrderDAO() {
        super(ORDER_DB);
    }

    public long addOrder(long roomId, long userId, Date dateFrom, Date dateTo, double moneyPaid) throws BadRequestException, InternalServerException {
        long newOrderId = new Random().nextLong();
        newOrderId = newOrderId > 0 ? newOrderId : newOrderId * (-1);

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ORDERS VALUES (?, ?, ?, ?, ?, ?)")){

            if (roomDAO.getById(connection, roomId) == null
                    || userDAO.getById(connection, userId) == null)
                throw new BadRequestException("Wrong some order's instance(room : hotel : user)");

            statement.setLong(1, newOrderId);
            statement.setLong(2, userId);
            statement.setLong(3, roomId);
            statement.setDate(4, new java.sql.Date(dateFrom.getTime()));
            statement.setDate(5, new java.sql.Date(dateTo.getTime()));
            statement.setDouble(6, moneyPaid);
        } catch (SQLException e) {
            throw  new InternalServerException( e.getMessage() + " Issue to save new order ID: " + newOrderId);
        }
        return newOrderId;
    }

    public void delete(long roomId, long userId) throws InternalServerException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ORDERS WHERE R_ID = ? AND U_ID = ?")) {
            statement.setLong(1, roomId);
            statement.setLong(2, userId);
            if(statement.executeUpdate() == 0)
                throw new SQLException();
        } catch (SQLException e) {
            throw  new InternalServerException( e.getMessage() + " Issue with deleting order with Room ID: "
                    + roomId + " and User ID: " + userId + " from ORDERS");
        }
    }


    @Override
    protected Order buildItem(Connection connection, ResultSet result) throws SQLException, InternalServerException {
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
    protected ArrayList<Order> buildItemList(Connection connection, ResultSet result) throws SQLException, InternalServerException {
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
