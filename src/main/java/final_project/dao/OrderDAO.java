package final_project.dao;

import final_project.models.Order;
import final_project.models.Room;
import final_project.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class OrderDAO extends GeneralDAO<Order> {

    private RoomDAO roomDAO = new RoomDAO();
    private UserDAO userDAO = new UserDAO();
    private HotelDAO hotelDAO = new HotelDAO();

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

    public void deleteOrder(long id) throws SQLException{
        delete("ORDERS", id);
    }

    public ArrayList<Order> getAll(Connection connection) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORDERS")) {
            TreeMap<Long, User> usersList = prepareUserList(connection);
            TreeMap<Long, Room> roomsList = prepareRoomList(connection);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long orderId = result.getLong(1);
                long userId = result.getLong(2);
                long roomId = result.getLong(3);
                Date dateFrom = new Date(result.getDate(4).getTime());
                Date dateTo = new Date(result.getDate(5).getTime());
                double moneyPaid = result.getDouble(6);

                Order order = new Order(usersList.get(userId), roomsList.get(roomId), dateFrom, dateTo, moneyPaid);
                order.setId(orderId);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with selecting all roomDAO");
        }

        return orders;
    }

    public Order getById (Connection connection, long id) throws SQLException{
        Order order = null;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ID = ? ") ) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long orderId = result.getLong(1);
                long userId = result.getLong(2);
                long roomId = result.getLong(3);
                Date dateFrom = new Date(result.getDate(4).getTime());
                Date dateTo = new Date(result.getDate(5).getTime());
                double moneyPaid = result.getDouble(6);
                order = new Order(userDAO.getById(connection, userId), roomDAO.getById(connection, roomId), dateFrom, dateTo, moneyPaid);
                order.setId(orderId);
            }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with selecting all roomDAO");
        }
        return order;
    }

    private TreeMap<Long, User> prepareUserList(Connection connection) throws SQLException{
        TreeMap<Long, User> usersList = new TreeMap<>();
        ArrayList<Long> userIdList = new ArrayList<>();
        PreparedStatement userStatement = connection.prepareStatement("SELECT U_ID FROM ORDERS");
        ResultSet result = userStatement.executeQuery();
        while (result.next())
            userIdList.add(result.getLong(1));

        for (User user : userDAO.getAll(connection))
            if(userIdList.contains(user.getId()))
                usersList.put(user.getId(), user);

        return usersList;
    }

    private TreeMap<Long, Room> prepareRoomList(Connection connection) throws SQLException{
        TreeMap<Long, Room> roomsList = new TreeMap<>();
        ArrayList<Long> roomIdList = new ArrayList<>();
        PreparedStatement roomStatement = connection.prepareStatement("SELECT R_ID FROM ORDERS");
        ResultSet result = roomStatement.executeQuery();
        while (result.next())
            roomIdList.add(result.getLong(1));

        for (Room room : roomDAO.getAll(connection))
            if (roomIdList.contains(room.getId()))
                roomsList.put(room.getId(), room);

        return roomsList;
    }


}
