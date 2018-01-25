package final_project.controllers;

import final_project.models.Hotel;
import final_project.models.User;
import final_project.services.HotelService;
import final_project.utils.Session;
import final_project.utils.UserType;
import final_project.utils.exceptions.BadRequestException;

import java.util.ArrayList;

public class    HotelController {
    private HotelService hotelService = new HotelService();

    public ArrayList<Hotel> findHotelByName(String name) throws Exception {
        return hotelService.findHotelByName(name);
    }

    public ArrayList<Hotel> findHotelByCity(String city) throws Exception {
        return hotelService.findHotelByCity(city);
    }
    public Hotel addHotel(User user, Hotel hotel) throws Exception {
        if(!(user != null && user.equals(Session.getUser()) && user.getUserType() == UserType.ADMIN))
            throw new BadRequestException("User have no right to add hotel!");

        return hotelService.addHotel(hotel);
    }

    public void deleteHotel(User user, Hotel hotel) throws Exception {
        if(!(user != null && user.equals(Session.getUser()) && user.getUserType() == UserType.ADMIN))
            throw new BadRequestException("User have no right to delete hotel!");

        hotelService.deleteHotel(hotel);
    }

}
