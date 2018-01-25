package final_project.demo;

import final_project.controllers.HotelController;
import final_project.dao.UserDAO;
import final_project.models.Hotel;
import final_project.utils.Session;

public class DemoHotel {
    public static void main(String[] args) throws Exception{
        HotelController controller = new HotelController();
        UserDAO userDAO = new UserDAO();
        Hotel hotel1 = new Hotel("Ibis", "Ukraine", "Kiev", "Khreshatic" );
//        Hotel hotel1 = new Hotel("UK", "London", "Piccadilly");
//        controller.addHotel(hotel1);
        System.out.println(userDAO.getUserByID(1113).toString());
        Session.startSession(userDAO.getUserByID(1113));
        controller.addHotel(userDAO.getUserByID(1113), hotel1);

        controller.deleteHotel(userDAO.getUserByID(1113), hotel1);


    }

}
