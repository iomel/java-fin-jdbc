package final_project.dao;

import final_project.models.Order;
import final_project.utils.FilesIO;

import java.util.TreeSet;

public class OrderDAO extends GeneralDAO<Order> {

    private static final String PATH_DB = "D://Test//OrderDB.txt";

    public Order addOrder(Order order) throws Exception {
        return add(PATH_DB, order);
    }

    public void deleteOrder(long id) throws Exception{
        delete(PATH_DB, id);
    }

    public TreeSet<Order> getAll() throws Exception {
        TreeSet<Order> orders = new TreeSet<>();
        String[] loadedOrder = FilesIO.readFile(PATH_DB).split("\n");

        for(String order : loadedOrder)
            if(!order.isEmpty())
                orders.add(Order.stringToObject(order));
        return orders;
    }

}
