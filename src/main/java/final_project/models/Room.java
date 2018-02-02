package final_project.models;

import final_project.utils.BaseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Room implements BaseEntity, Comparable<Room> {

    private long id;
    private int numberOfGuests;
    private double price;
    private boolean breakfastIncluded;
    private boolean petsAllowed;
    private Date dateAvailableFrom;
    private Hotel hotel;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-MMM-yyyy");

    public Room(long id) {
        this.id = id;
    }

    public Room(int numberOfGuests, double price, boolean breakfastIncluded, boolean petsAllowed, Date dateAvailableFrom, Hotel hotel) {
        long newId = new Random().nextLong();
        this.id = newId > 0 ? newId : newId * (-1);
        this.numberOfGuests = numberOfGuests;
        this.price = price;
        this.breakfastIncluded = breakfastIncluded;
        this.petsAllowed = petsAllowed;
        this.dateAvailableFrom = dateAvailableFrom;
        this.hotel = hotel;
    }

    @Override
    public long getId() {
        return id;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isBreakfastIncluded() {
        return breakfastIncluded;
    }

    public boolean isPetsAllowed() {
        return petsAllowed;
    }

    public Date getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public int compareTo(Room o) {
        return Long.compare(this.getId(), o.getId());
    }

    @Override
    public String toString() {
        return id + "," + numberOfGuests + "," + price + "," + breakfastIncluded + "," + petsAllowed +
                "," + simpleDateFormat.format(dateAvailableFrom) +"," + hotel.toString().replaceAll(",", ";");
    }

    public double getPrice() {
        return price;
    }

    public Hotel getHotel() {
        return hotel;
    }

}
