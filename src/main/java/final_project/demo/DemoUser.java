package final_project.demo;

import final_project.controllers.UserController;
import final_project.models.User;
import final_project.utils.UserType;

public class DemoUser {
    public static void main(String[] args) throws Exception{
        UserController controller = new UserController();
        User user1 = new User("Olga", "aaaa11", "RU", 17, UserType.USER);
        controller.registerUser(user1);

        controller.login(user1.getUserName(), user1.getPassword());
//        controller.deleteUser(1632404418);
        controller.logout();
    }
}
