package final_project.dao;

import final_project.models.User;
import final_project.utils.FilesIO;

import java.util.TreeSet;

public class UserDAO extends GeneralDAO<User> {

    private static final String PATH_DB = "D://Test//UserDB.txt";

    public User addUser(User user) throws Exception {

        return add(PATH_DB, user);
    }

    public void deleteUser(long id) throws Exception{
        delete(PATH_DB, id);
    }

    public TreeSet<User> getAll() throws Exception {
        TreeSet<User> users = new TreeSet<>();
        String[] loadedUsers = FilesIO.readFile(PATH_DB).split("\n");

        for(String user : loadedUsers)
            if(!user.isEmpty())
                users.add(User.stringToObject(user));
        return users;
    }

    public User getUserByID(long id) throws Exception {
        for(User user : getAll())
            if(user.getId() == id)
                return user;
        return null;
    }

}
