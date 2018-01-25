package final_project.utils;

import final_project.models.Room;

import java.util.Date;

public class Filter {
    private int numberOfGuests;
    private double price;
    private boolean breakfastIncluded;
    private boolean petsAllowed;
    private Date dateAvailableFrom;
    private String hotelName;
    private String country;
    private String city;
    private boolean emptyFilter;

    public Filter() { emptyFilter = true; }

    public Filter(int numberOfGuests, double price, boolean breakfastIncluded,
                  boolean petsAllowed, Date dateAvailableFrom, String hotelName,
                  String country, String city) {
        emptyFilter = false;
        this.numberOfGuests = numberOfGuests;
        this.price = price;
        this.breakfastIncluded = breakfastIncluded;
        this.petsAllowed = petsAllowed;
        this.dateAvailableFrom = dateAvailableFrom;
        this.hotelName = hotelName;
        this.country = country;
        this.city = city;
    }

    public boolean match(Room room) {
        if (emptyFilter)
            return true;
        if(numberOfGuests !=0 && numberOfGuests != room.getNumberOfGuests())
            return false;
        if(price != 0 && price != room.getPrice())
            return false;
        if (breakfastIncluded != room.isBreakfastIncluded())
            return false;
        if(petsAllowed != room.isPetsAllowed())
            return false;
        if(dateAvailableFrom != null && dateAvailableFrom.getTime() < room.getDateAvailableFrom().getTime())
            return false;
        if(hotelName != null && !hotelName.equals(room.getHotel().getHotelName()))
            return false;
        if(country != null && !country.equals(room.getHotel().getCountry()))
            return false;
        if(city != null && !city.equals(room.getHotel().getCity()))
            return false;
        return true;
    }
}
