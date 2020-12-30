package bgu.spl.net.impl.BGRSServer.DB;

import java.util.Vector;

public class User {
    private String userName;
    private String pass;
    private boolean isAdmin;
    private boolean isLoggedIn;
    private Vector<Integer> listOfCoursesAttendTo; //TODO:consider different DataStructure

    public User(String userName, String pass, boolean isAdmin) {
        this.userName = userName;
        this.pass = pass;
        this.isAdmin = isAdmin;
        this.isLoggedIn = false; //user must logged in to flag true in logged in 
        listOfCoursesAttendTo = new Vector<Integer>(); // initializing new courses per students collection
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

    /**
     * Make sure the user is attending @course, search for the entity in the @listOfCoursesAttendTo
     * @param course
     * @return
     */
    public boolean isAttending(int course) {
        return listOfCoursesAttendTo.contains(course);
    }

    /**
     * Assuming the user isn't registered for the course, register him.
     *
     * @param course
     */
    public void registerToCourse(int course) {
       listOfCoursesAttendTo.add(course);
    }

    /**
     * Assuming the user is registered for the course, unregister him.
     *
     * @param course
     */
    public void unregisterFromCourse(int course) {
        listOfCoursesAttendTo.remove(course);
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public Vector<Integer> getListOfCoursesAttendTo() {
        return listOfCoursesAttendTo;
    }

}
