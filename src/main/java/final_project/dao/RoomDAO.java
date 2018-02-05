package final_project.dao;

import final_project.models.Hotel;
import final_project.models.Room;
import final_project.utils.exceptions.BadRequestException;
import final_project.utils.exceptions.InternalServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class RoomDAO extends GeneralDAO<Room> {
    private HotelDAO hotelDAO = new HotelDAO();
    private final static String ROOM_DB = "ROOMS";

    public RoomDAO() {
        super(ROOM_DB);
    }

    public Room addRoom(Room room) throws BadRequestException, InternalServerException {

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ROOMS VALUES (?, ?, ?, ?, ?, ?, ?)")){

            if (getById(connection, room.getId()) != null)
                throw new BadRequestException("Room with such ID is registered already! ID: " + room.getId());
            if (hotelDAO.getById(connection, room.getHotel().getId()) == null)
                throw new BadRequestException("There is no such hotel (ID:" + room.getHotel().getId() + ")");

            statement.setLong(1, room.getId());
            statement.setInt(2, room.getNumberOfGuests());
            statement.setDouble(3, room.getPrice());
            statement.setString(4, String.valueOf(room.isBreakfastIncluded()).toUpperCase());
            statement.setString(5, String.valueOf(room.isPetsAllowed()).toUpperCase());
            statement.setDate(6, new java.sql.Date(room.getDateAvailableFrom().getTime()));
            statement.setLong(7, room.getHotel().getId());
    } catch (SQLException e) {
            throw  new InternalServerException( e.getMessage() + " Issue to save room ID: " + room.getId());
        }
        return room;
    }

    @Override
    protected Room buildItem(Connection connection, ResultSet result) throws SQLException, InternalServerException {
        long roomId = result.getLong(1);
        int guestsNum = result.getInt(2);
        double price = result.getDouble(3);
        boolean breakfast = Boolean.valueOf(result.getString(4));
        boolean pets = Boolean.valueOf(result.getString(5));
        Date dateFrom = new Date(result.getDate(6).getTime());
        long hotelId = result.getLong(7);
        Room room = new Room(guestsNum, price, breakfast, pets, dateFrom, hotelDAO.getById(connection, hotelId));
        room.setId(roomId);
        return room;
    }

    @Override
    protected ArrayList<Room> buildItemList(Connection connection, ResultSet result) throws SQLException, InternalServerException {
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Hotel> hotels = hotelDAO.getAll(connection);
        while (result.next()) {
            Room room = buildItem(connection, result);
            for (Hotel hotel : hotels)
                if(hotel.getId() == room.getHotel().getId()) {
                    room.setHotel(hotel);
                    break;
                }
            rooms.add(room);
        }
        return rooms;
    }
}
