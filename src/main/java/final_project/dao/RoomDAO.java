package final_project.dao;

import final_project.models.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.TreeSet;

public class RoomDAO extends GeneralDAO {
    private HotelDAO hotels = new HotelDAO();

    public Room addRoom(Room room) throws Exception {

        try(Connection connection = getConnection();
            PreparedStatement checkHotelStatement = connection.prepareStatement("SELECT * FROM HOTELS WHERE ID = ?");
            PreparedStatement checkDuplicateStatement = connection.prepareStatement("SELECT * FROM ROOMS WHERE ID = ?");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ROOMS VALUES (?, ?, ?, ?, ?, ?, ?)")){
            checkHotelStatement.setLong(1, room.getHotel().getId());
            checkDuplicateStatement.setLong(1, room.getId());

            if (checkDuplicateStatement.executeUpdate() > 0)
                throw new SQLException("Room with such ID is registered already! ID: " + room.getId());
            if (checkHotelStatement.executeUpdate() == 0)
                throw new SQLException("There is no such hotel (ID:" + room.getHotel().getId() + ")");

            statement.setLong(1, room.getId());
            statement.setInt(2, room.getNumberOfGuests());
            statement.setDouble(3, room.getPrice());
            statement.setString(4, String.valueOf(room.isBreakfastIncluded()).toUpperCase());
            statement.setString(5, String.valueOf(room.isPetsAllowed()).toUpperCase());
            statement.setDate(6, new java.sql.Date(room.getDateAvailableFrom().getTime()));
            statement.setLong(7, room.getHotel().getId());

            int result = statement.executeUpdate();
            if(result == 0)
                throw new SQLException();

        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue to save room ID: " + room.getId());
        }

        return room;
    }

    public void deleteRoom(long id) throws Exception {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ROOMS WHERE ID = ?")) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue with deleting room ID: " + id);
        }
    }

    public TreeSet<Room> getAll() throws Exception {
        TreeSet<Room> rooms = new TreeSet<>();
        try(Connection connection = getConnection();
            PreparedStatement roomStatement = connection.prepareStatement("SELECT * FROM ROOMS") ) {
            ResultSet result = roomStatement.executeQuery();
            if(result.isBeforeFirst())
                while (result.next()) {
                    long room_Id = result.getLong(1);
                    int guestsNum = result.getInt(2);
                    double price = result.getDouble(3);
                    boolean breakfast = Boolean.valueOf(result.getString(4));
                    boolean pets = Boolean.valueOf(result.getString(5));
                    Date dateFrom = new Date(result.getDate(6).getTime());
                    long hotelId = result.getLong(7);

                    Room room = new Room(guestsNum, price, breakfast, pets, dateFrom, hotels.getHotelById(hotelId));
                    room.setId(room_Id);
                    rooms.add(room);
                }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with selecting all rooms");
        }
        return rooms;
    }

    public Room getRoomByID(long id) throws SQLException{
        Room room = null;
        try(Connection connection = getConnection();
            PreparedStatement roomStatement = connection.prepareStatement("SELECT * FROM ROOMS WHERE ID = ?") ) {
            roomStatement.setLong(1, id);
            ResultSet result = roomStatement.executeQuery();
            if(result.isBeforeFirst())
                while (result.next()) {
                    long room_Id = result.getLong(1);
                    int guestsNum = result.getInt(2);
                    double price = result.getDouble(3);
                    boolean breakfast = Boolean.valueOf(result.getString(4));
                    boolean pets = Boolean.valueOf(result.getString(5));
                    Date dateFrom = new Date(result.getDate(6).getTime());
                    long hotelId = result.getLong(7);

                    room = new Room(guestsNum, price, breakfast, pets, dateFrom, hotels.getHotelById(hotelId));
                    room.setId(room_Id);
                }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with searching room by ID: " + id);
        }
        return room;
    }

}
