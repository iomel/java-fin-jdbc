package final_project.demo;

import final_project.controllers.HotelController;
import final_project.dao.UserDAO;
import final_project.models.Hotel;
import final_project.models.User;
import final_project.utils.Session;

public class DemoHotel {
    public static void main(String[] args) throws Exception{
        HotelController controller = new HotelController();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getById(12);

        Hotel hotel1 = new Hotel("Ibis", "Ukraine", "Kiev", "Khreshatic" );
        Hotel hotel2 = new Hotel("Holiday Inn", "Ukraine", "Kiev", "Maidan Nezalezhnosti" );
        Hotel hotel3 = new Hotel("Ibis", "UK", "London", "Picadily" );
        Hotel hotel4 = new Hotel("Hilton", "UK", "London", "2nd Ave" );

        //        Hotel hotel1 = new Hotel("UK", "London", "Piccadilly");
//        controller.addHotel(hotel1);
        System.out.println(user.toString());
        Session.startSession(user);
        controller.addHotel(user, hotel1);
        controller.addHotel(user, hotel2);
        controller.addHotel(user, hotel3);
        controller.addHotel(user, hotel4);

        Session.resetSession();

    }

}
