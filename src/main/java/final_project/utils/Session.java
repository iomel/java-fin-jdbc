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


}
