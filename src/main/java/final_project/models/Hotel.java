package final_project.models;

import final_project.utils.BaseEntity;

import java.util.Random;

public class Hotel implements BaseEntity, Comparable<Hotel> {

    private long id;
    private String hotelName;
    private String country;
    private String city;
    private String street;

    public Hotel(long id) {
        this.id = id;
    }

    public Hotel(String hotelName, String country, String city, String street) {
        long newId = new Random().nextLong();
        this.id = newId > 0 ? newId : newId * (-1);
        this.hotelName = hotelName;
        this.country = country;
        this.city = city;
        this.street = street;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Hotel o) {
        return Long.compare(this.getId(), o.getId());
    }

    @Override
    public String toString() {
        return id +"," + hotelName + "," + country +"," + city + "," + street + "\n";
    }

    public String getCountry() {
        return country;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

}
