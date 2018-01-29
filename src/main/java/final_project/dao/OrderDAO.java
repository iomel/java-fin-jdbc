package final_project.dao;

import final_project.models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.TreeSet;

public class OrderDAO extends GeneralDAO {

    private RoomDAO rooms = new RoomDAO();
    private UserDAO users = new UserDAO();

    public Order addOrder(Order order) throws SQLException {

        try(Connection connection = getConnection();
            PreparedStatement checkHotelStatement = connection.prepareStatement("SELECT * FROM HOTELS WHERE ID = ?");
            PreparedStatement checkUserStatement = connection.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
            PreparedStatement checkRoomStatement = connection.prepareStatement("SELECT * FROM ROOMS WHERE ID = ?");
            PreparedStatement checkOrderStatement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ID = ?");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ORDERS VALUES (?, ?, ?, ?, ?, ?)")){

            checkHotelStatement.setLong(1, order.getRoom().getHotel().getId());
            checkUserStatement.setLong(1, order.getUser().getId());
            checkRoomStatement.setLong(1, order.getRoom().getId());
            checkOrderStatement.setLong(1, order.getId());

            if(checkOrderStatement.executeUpdate() != 0)
                throw new SQLException("Order with such ID is registered already! ID: " + order.getId());
            if (checkHotelStatement.executeUpdate() != 0 || checkRoomStatement.executeUpdate() != 0
                    || checkUserStatement.executeUpdate() != 0)
                throw new SQLException("Wrong some order's instance(room : hotel : user)");

            statement.setLong(1, order.getId());
            statement.setLong(2, order.getUser().getId());
            statement.setLong(3, order.getRoom().getId());
            statement.setDate(4, new java.sql.Date(order.getDateFrom().getTime()));
            statement.setDate(5, new java.sql.Date(order.getDateTo().getTime()));
            statement.setDouble(6, order.getMoneyPaid());

            if(statement.executeUpdate() == 0)
                throw new SQLException();
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue to save order ID: " + order.getId());
        }
        return order;
    }

    public void deleteOrder(long id) throws SQLException{
        delete("ORDERS", id);
    }

    public TreeSet<Order> getAll() throws Exception {
        TreeSet<Order> orders = new TreeSet<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORDERS") ) {
            ResultSet result = statement.executeQuery();
            if(result.isBeforeFirst())
                while (result.next()) {
                    long orderId = result.getLong(1);
                    long userId = result.getLong(2);
                    long roomId = result.getLong(3);
                    Date dateFrom = new Date(result.getDate(4).getTime());
                    Date dateTo = new Date(result.getDate(5).getTime());
                    double moneyPaid = result.getDouble(6);
                    Order order = new Order(users.getUserByID(userId), rooms.getRoomByID(roomId), dateFrom, dateTo, moneyPaid);
                    orders.add(order);
                }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with selecting all rooms");
        }

        return orders;
    }

    public Order getOrderById (long id) throws SQLException{
        Order order = null;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ID = ? ") ) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if(result.isBeforeFirst())
                while (result.next()) {
                    long orderId = result.getLong(1);
                    long userId = result.getLong(2);
                    long roomId = result.getLong(3);
                    Date dateFrom = new Date(result.getDate(4).getTime());
                    Date dateTo = new Date(result.getDate(5).getTime());
                    double moneyPaid = result.getDouble(6);
                    Order newOrder = new Order(users.getUserByID(userId), rooms.getRoomByID(roomId), dateFrom, dateTo, moneyPaid);
                }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with selecting all rooms");
        }
        return order;
    }
}
