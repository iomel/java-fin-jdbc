package final_project.dao;

import final_project.models.Hotel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelDAO extends GeneralDAO<Hotel> {
    private final static String HOTEL_DB = "HOTELS";

    public HotelDAO() {
        super(HOTEL_DB);
    }

    public Hotel addHotel(Hotel hotel) throws Exception {

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO HOTELS VALUES (?, ?, ?, ?, ?)")) {
            if (getById(connection, hotel.getId()) != null)
                throw new SQLException("Hotel with such ID is registered already! ID: " + hotel.getId());

            statement.setLong(1, hotel.getId());
            statement.setString(2, hotel.getHotelName());
            statement.setString(3, hotel.getCountry());
            statement.setString(4, hotel.getCity());
            statement.setString(5, hotel.getStreet());
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue to save hotel ID: " + hotel.getId());
        }
        return hotel;
    }

    @Override
    protected Hotel buildItem(Connection connection, ResultSet result) throws SQLException {
        long id = result.getLong(1);
        String hotelName = result.getString(2);
        String country = result.getString(3);
        String city = result.getString(4);
        String street = result.getString(5);
        Hotel hotel = new Hotel(hotelName, country, city, street);
        hotel.setId(id);
        return hotel;
    }

    @Override
    protected ArrayList<Hotel> buildItemList(Connection connection, ResultSet result) throws SQLException {
        ArrayList<Hotel> hotels = new ArrayList<>();
        while (result.next())
            hotels.add(buildItem(connection, result));
        return hotels;
    }

}
