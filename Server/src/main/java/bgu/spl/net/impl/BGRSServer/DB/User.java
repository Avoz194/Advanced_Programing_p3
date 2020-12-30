package bgu.spl.net.impl.BGRSServer.DB;

import java.util.ArrayList;

public class User {
    private String userName;
    private String pass;
    private boolean isAdmin;
    private boolean isLoggedIn;
    private ArrayList<Integer> listOfCoursesAttendTo; //TODO:consider different Datastucture

    public User(String userName, String pass,boolean isAdmin) {
        this.userName = userName;
        this.pass = pass;
        this.isAdmin = isAdmin;
        this.isLoggedIn = false; //user must logged in to flag true in logged in 
        listOfCoursesAttendTo = new ArrayList<Integer>(); // initializing new courses per students collection 
    }

    public String getUserName() {
        return userName;
    }

    public String getPass() {
        return pass;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public boolean isAttending(int course){return false;}//TODO:complete based on datastructure

    public void registerToCourse(int course){} //TODO:complete
    public void unregisterFromCourse(int course){}//TODO:complete
    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public ArrayList<Integer> getListOfCoursesAttendTo() {
        return listOfCoursesAttendTo;
    }

}
