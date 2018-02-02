package final_project.dao;

import final_project.models.Hotel;
import final_project.models.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class RoomDAO extends GeneralDAO<Room> {
    private HotelDAO hotelDAO = new HotelDAO();

    public Room addRoom(Room room) throws Exception {

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ROOMS VALUES (?, ?, ?, ?, ?, ?, ?)")){

            if (getById(connection, room.getId()) != null)
                throw new SQLException("Room with such ID is registered already! ID: " + room.getId());
            if (hotelDAO.getById(connection, room.getHotel().getId()) == null)
                throw new SQLException("There is no such hotel (ID:" + room.getHotel().getId() + ")");

            statement.setLong(1, room.getId());
            statement.setInt(2, room.getNumberOfGuests());
            statement.setDouble(3, room.getPrice());
            statement.setString(4, String.valueOf(room.isBreakfastIncluded()).toUpperCase());
            statement.setString(5, String.valueOf(room.isPetsAllowed()).toUpperCase());
            statement.setDate(6, new java.sql.Date(room.getDateAvailableFrom().getTime()));
            statement.setLong(7, room.getHotel().getId());
    } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue to save room ID: " + room.getId());
        }

        return room;
    }

    public void deleteRoom(long id) throws SQLException {
        delete("ROOMS", id);
    }


    public ArrayList<Room> getAll(Connection connection) throws SQLException {
        ArrayList<Room> rooms = new ArrayList<>();
        try(PreparedStatement roomStatement = connection.prepareStatement("SELECT * FROM ROOMS") ) {
            ResultSet result = roomStatement.executeQuery();
            ArrayList<Hotel> hotels = hotelDAO.getAll(connection);
            while (result.next()) {
                Room room = buildItem(result);
                for (Hotel hotel : hotels)
                    if(hotel.getId() == room.getHotel().getId()) {
                        room.setHotel(hotel);
                        break;
                    }
                rooms.add(room);
            }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with selecting all rooms");
        }
        return rooms;
    }


    public Room getById(Connection connection, long id) throws SQLException{
        Room room = getById(connection, "ROOMS", id);
        room.setHotel(hotelDAO.getById(connection, room.getHotel().getId()));
        return room;
    }

    protected Room buildItem(ResultSet result) throws SQLException {
        long roomId = result.getLong(1);
        int guestsNum = result.getInt(2);
        double price = result.getDouble(3);
        boolean breakfast = Boolean.valueOf(result.getString(4));
        boolean pets = Boolean.valueOf(result.getString(5));
        Date dateFrom = new Date(result.getDate(6).getTime());
        long hotelId = result.getLong(7);
        Room room = new Room(guestsNum, price, breakfast, pets, dateFrom, new Hotel(hotelId));
        room.setId(roomId);

        return room;
    }

}
