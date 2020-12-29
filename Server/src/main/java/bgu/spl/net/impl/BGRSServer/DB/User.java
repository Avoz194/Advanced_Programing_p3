package bgu.spl.net.impl.BGRSServer.DB;

import java.util.ArrayList;

public class User {
    private String userName;
    private String pass;
    private boolean isAdmin;
    private boolean isLoggedIn;
    private ArrayList<Integer> listOfCoursesAttendTo;

    public User(String userName, String pass) {
        this.userName = userName;
        this.pass = pass;
        this.isAdmin = false; // initializing user as not admin
        this.isLoggedIn = false; //user must logged in to flag true in logged in 
        listOfCoursesAttendTo = new ArrayList<Integer>(); // initializing new courses per students collection 
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public ArrayList<Integer> getListOfCoursesAttendTo() {
        return listOfCoursesAttendTo;
    }

    public void setListOfCoursesAttendTo(ArrayList<Integer> listOfCoursesAttendTo) {
        this.listOfCoursesAttendTo = listOfCoursesAttendTo;
    }
}
