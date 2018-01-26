package final_project.dao;

import final_project.models.Order;
import final_project.models.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.TreeSet;

public class OrderDAO extends GeneralDAO {

    private HotelDAO hotels = new HotelDAO();
    private RoomDAO rooms = new RoomDAO();
    private UserDAO users = new UserDAO();

    public Order addOrder(Order order) throws SQLException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ORDERS VALUES (?, ?, ?, ?, ?, ?)")){
            if (hotels.getHotelById(order.getRoom().getHotel().getId()) == null
                    || rooms.getRoomByID(order.getRoom().getId()) == null
                    || users.getUserByID(order.getUser().getId()) == null)
                throw new SQLException("Wrong some order's instance(room : hotel : user)");

            statement.setLong(1, order.getId());
            statement.setLong(2, order.getUser().getId());
            statement.setLong(3, order.getRoom().getId());
            statement.setDate(4, new java.sql.Date(order.getDateFrom().getTime()));
            statement.setDate(5, new java.sql.Date(order.getDateTo().getTime()));
            statement.setDouble(6, order.getMoneyPaid());

            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue to save order ID: " + order.getId());
        }
        return order;
    }

    public void deleteOrder(long id) throws SQLException{
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ORDERS WHERE ID = ?")) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue with deleting room ID: " + id);
        }
    }

    public TreeSet<Order> getAll() throws Exception {
        TreeSet<Order> orders = new TreeSet<>();
        try(Connection connection = getConnection();
            PreparedStatement roomStatement = connection.prepareStatement("SELECT * FROM ORDERS") ) {
            ResultSet result = roomStatement.executeQuery();
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

}
