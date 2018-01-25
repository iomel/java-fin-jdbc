package final_project.controllers;

import final_project.models.User;
import final_project.services.UserService;
import final_project.utils.Session;

public class UserController {
    private UserService userService = new UserService();

    public User registerUser(User user) throws Exception{
        return userService.registerUser(user);
    }

    public void login(String userName, String password) throws Exception {
        userService.login(userName, password);
    }

    public void logout() {
        Session.resetSession();
    }

}
