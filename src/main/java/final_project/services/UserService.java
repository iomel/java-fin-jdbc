package final_project.services;

import final_project.dao.UserDAO;
import final_project.models.User;
import final_project.utils.Countries;
import final_project.utils.Session;
import final_project.utils.exceptions.BadRequestException;

import java.util.Arrays;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public User registerUser(User user) throws Exception{
        validate(user);
        return userDAO.addUser(user);
    }

    public void login(String name, String password) throws Exception {
        User user = userDAO.getByTextField("U_NAME", name).get(0);

        if (name == null || password == null)
            throw new BadRequestException("UserService.isRegistered error - name or password is NULL!");

        if (user.getPassword().equals(password))
            Session.startSession(user);
    }

    private void validate(User user)throws Exception{
        nullCheck(user);
        countryCheck(user.getCountry());

        if(user.getAge() < 18)
            throw new BadRequestException("UserService.validate error - user is under 18Y. Access denied!");
    }

    private void nullCheck(User user) throws BadRequestException{
        if (user == null)
            throw new BadRequestException("UserService.nullCheck error - user is NULL!");

        if (user.getUserName() == null || user.getUserType() == null || user.getPassword() == null || user.getCountry() == null)
            throw new BadRequestException("UserService.nullCheck error - user has empty parameter! User ID: " + user.getId());
    }

    private void countryCheck(String country) throws BadRequestException{
        if(!Arrays.toString(Countries.values()).contains(country))
            throw new BadRequestException("UserService.countryCheck error - used country is out of allowed countries scope! ::" + country);

        if (Countries.valueOf(country) == Countries.Russia || Countries.valueOf(country) == Countries.Iran)
            throw new BadRequestException("UserService.countryCheck error - service is not allowed for the country " + country);
    }
}
