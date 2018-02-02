package final_project.demo;

import final_project.controllers.UserController;
import final_project.dao.UserDAO;
import final_project.models.User;
import final_project.utils.UserType;

public class DemoUser {
    public static void main(String[] args) throws Exception{
        UserController controller = new UserController();
        User user1 = new User("Petro", "aaddaa11", "Ukraine", 29, UserType.USER);
        User user2 = new User("Petya", "11ss", "Moldova", 24, UserType.USER);
        UserDAO udao = new UserDAO();
        for (User user : udao.getAll())
            System.out.println(user.toString());

//        udao.deleteUser(4646245056540919033L);

////        controller.registerUser(user1);
//        controller.registerUser(user2);
//
//        controller.login(user1.getUserName(), user1.getPassword());
////        controller.deleteUser(1632404418);
//        controller.logout();
    }
}
