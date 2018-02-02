package final_project.dao;

import final_project.models.Hotel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelDAO extends GeneralDAO<Hotel> {

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

    public void deleteHotel(long id) throws SQLException{
        delete("HOTELS", id);
    }

    public ArrayList<Hotel> getAll(Connection connection) throws SQLException {
        ArrayList<Hotel> hotels = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM HOTELS")) {
            ResultSet result = statement.executeQuery();
            while (result.next())
                hotels.add(buildItem(result));
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with selecting all hotels");
        }
        return hotels;
    }

    public Hotel getById(long id) throws SQLException {
        return getById("HOTELS", id);
    }

    public Hotel getById(Connection connection, long id) throws SQLException {
        return getById(connection, "HOTELS", id);
    }

    public ArrayList<Hotel> getByName(String name) throws SQLException {
        ArrayList<Hotel> hotels = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement hotelStatement = connection.prepareStatement("SELECT * FROM HOTELS WHERE HOTEL_NAME = ?") ) {
            hotelStatement.setString(1, name);
            ResultSet hotelResult = hotelStatement.executeQuery();
            while (hotelResult.next())
                hotels.add(buildItem(hotelResult));
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with searching room by NAME: " + name);
        }
        return hotels;
    }
    public ArrayList<Hotel> getByCity(String city) throws SQLException {
        ArrayList<Hotel> hotels = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement hotelStatement = connection.prepareStatement("SELECT * FROM HOTELS WHERE CITY = ?") ) {
            hotelStatement.setString(1, city);
            ResultSet hotelResult = hotelStatement.executeQuery();
            while (hotelResult.next())
                hotels.add(buildItem(hotelResult));
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with searching room by CITY: " + city);
        }
        return hotels;
    }

    protected Hotel buildItem(ResultSet result) throws SQLException {
        long id = result.getLong(1);
        String hotelName = result.getString(2);
        String country = result.getString(3);
        String city = result.getString(4);
        String street = result.getString(5);
        Hotel hotel = new Hotel(hotelName, country, city, street);
        hotel.setId(id);
        return hotel;
    }

}
