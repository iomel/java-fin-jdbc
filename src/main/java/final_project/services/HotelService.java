package final_project.services;

import final_project.dao.HotelDAO;
import final_project.models.Hotel;
import final_project.utils.Countries;
import final_project.utils.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.Arrays;

public class HotelService {
    private HotelDAO hotelDAO = new HotelDAO();

    public ArrayList<Hotel> findHotelByName(String name) throws Exception {
        if (name == null)
            throw new BadRequestException("findHotelByName error - hotel name is NULL!");
        return hotelDAO.getByName(name);
    }

    public ArrayList<Hotel> findHotelByCity(String city) throws Exception {
        if (city == null)
            throw new BadRequestException("findHotelByCity error - city is NULL!");
        return hotelDAO.getByCity(city);

    }
    public Hotel addHotel(Hotel hotel) throws Exception {
        validate(hotel);
        return hotelDAO.addHotel(hotel);
    }

    public void deleteHotel(Hotel hotel) throws Exception {

        if (hotel != null)
            hotelDAO.delete(hotel.getId());
    }

    private void validate(Hotel hotel)throws BadRequestException {
        nullCheck(hotel);
        countryCheck(hotel.getCountry());
    }

    private void nullCheck(Hotel hotel) throws BadRequestException {
        if (hotel == null)
            throw new BadRequestException("HotelService.nullCheck error - hotel is NULL!");

        if (hotel.getHotelName() == null
                || hotel.getCountry() == null
                || hotel.getCity() == null
                || hotel.getStreet() == null)
            throw new BadRequestException("HotelService.nullCheck error - hotel has empty parameter! Hotel ID: " + hotel.getId());
    }

    private void countryCheck(String country) throws BadRequestException {
        if(!Arrays.toString(Countries.values()).contains(country))
            throw new BadRequestException("HotelService.countryCheck error - used country is out of allowed countries scope! ::" + country);

        if (Countries.valueOf(country) == Countries.Russia || Countries.valueOf(country) == Countries.Iran)
            throw new BadRequestException("HotelService.countryCheck error - service is not allowed for the country " + country);
    }

}
