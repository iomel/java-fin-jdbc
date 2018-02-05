package final_project.utils;

import final_project.models.User;

public class Session {
    private static User loggedUser;

    public static void resetSession(){
        loggedUser = null ;
    }
    public static User getUser() {
        return loggedUser;
    }

    public static void startSession (User user) {
        loggedUser = user;
    }

    public static boolean checkSession (User user, UserType userType) {
        return  (loggedUser.equals(user) && loggedUser.getUserType() == UserType.ADMIN);
    }


}
