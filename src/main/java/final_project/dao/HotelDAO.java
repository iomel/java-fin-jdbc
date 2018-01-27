package final_project.dao;

import final_project.models.Hotel;
import final_project.models.User;
import final_project.utils.UserType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

public class HotelDAO extends GeneralDAO {

    public Hotel addHotel(Hotel hotel) throws Exception {

        try(Connection connection = getConnection();
            PreparedStatement checkHotelStatement = connection.prepareStatement("SELECT * FROM HOTELS WHERE ID = ?");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO HOTELS VALUES (?, ?, ?, ?, ?)")) {
            checkHotelStatement.setLong(1, hotel.getId());
            if (checkHotelStatement.executeUpdate() != 0)
                throw new SQLException("Hotel with such ID is registered already! ID: " + hotel.getId());

            statement.setLong(1, hotel.getId());
            statement.setString(2, hotel.getHotelName());
            statement.setString(3, hotel.getCountry());
            statement.setString(4, hotel.getCity());
            statement.setString(5, hotel.getStreet());

            int result = statement.executeUpdate();
            if(result == 0)
                throw new SQLException();

        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue to save hotel ID: " + hotel.getId());
        }
        return hotel;
    }

    public void deleteHotel(long id) throws Exception{
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM HOTELS WHERE ID = ?")) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue with deleting hotel ID: " + id);
        }
    }

    public TreeSet<Hotel> getAll() throws SQLException {
        TreeSet<Hotel> hotels = new TreeSet<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM HOTELS")) {
            ResultSet result = statement.executeQuery();
            if(result.isBeforeFirst())
                while (result.next()) {
                    long hotelId = result.getLong(1);
                    String name = result.getString(2);
                    String country = result.getString(3);
                    String city = result.getString(4);
                    String street = result.getString(5);

                    Hotel hotel = new Hotel(name, country, city, street);
                    hotel.setId(hotelId);
                    hotels.add(hotel);
                }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with selecting all hotels");
        }
        return hotels;
    }

    public Hotel getHotelById(long id) throws SQLException {
        Hotel hotel = null;
        try(Connection connection = getConnection();
            PreparedStatement hotelStatement = connection.prepareStatement("SELECT * FROM HOTELS WHERE ID = ?") ) {
            hotelStatement.setLong(1, id);
            ResultSet hotelResult = hotelStatement.executeQuery();
            if(hotelResult.isBeforeFirst())
                while (hotelResult.next()) {
                    String hotelName = hotelResult.getString(2);
                    String country = hotelResult.getString(3);
                    String city = hotelResult.getString(4);
                    String street = hotelResult.getString(5);
                    hotel = new Hotel(hotelName, country, city, street);
                    hotel.setId(id);
                }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with searching room by ID: " + id);
        }
        return hotel;
    }

}
