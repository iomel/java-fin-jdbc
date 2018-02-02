package final_project.models;

import final_project.utils.BaseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Order implements BaseEntity, Comparable<Order> {

    private long id;
    private User user;
    private Room room;
    private Date dateFrom;
    private Date dateTo;
    private double moneyPaid;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-MMM-yyyy");

    public Order(User user, Room room, Date dateFrom, Date dateTo, double moneyPaid) {
        long newId = new Random().nextLong();
        this.id = newId > 0 ? newId : newId * (-1);
        this.user = user;
        this.room = room;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.moneyPaid = moneyPaid;
    }

    @Override
    public int compareTo(Order o) {
        return Long.compare(this.getId(), o.getId());
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return id +
                "," + user.toString().replaceAll(",", ":").replace("\n","") +
                "," + room.toString().replaceAll(",", ":").replace("\n","") +
                "," + simpleDateFormat.format(dateFrom) + "," + simpleDateFormat.format(dateTo) +
                "," + moneyPaid + "\n";
//        Date dateTo = dateFormat.parse(params[4]);

    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public double getMoneyPaid() {
        return moneyPaid;
    }

}
