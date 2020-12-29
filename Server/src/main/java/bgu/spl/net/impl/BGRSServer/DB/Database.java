package bgu.spl.net.impl.BGRSServer.DB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;

public class Database {
    private static class SingletonHolder {
        private static Database instance = new Database(getInstance().pathCourses, getInstance().readWriteLockCourses, getInstance().readWriteLockCourses);
    }

    private String pathCourses;
    private ConcurrentHashMap<Integer, Course> coursesDB;
    private ConcurrentHashMap<String, User> usersDB;
    private ReadWriteLock readWriteLockCourses;
    private ReadWriteLock readWriteLockUsers;
    private ArrayList<Integer> courseOrder;   //TODO: sure list is an object?


    //to prevent user from creating new Database
    private Database(String pathCourses, ReadWriteLock readWriteLockCourses, ReadWriteLock readWriteLockUsers) {
        // TODO: implement - make sure threadSafe singelton?
        pathCourses = this.pathCourses;
        coursesDB = new ConcurrentHashMap<Integer, Course>();
        usersDB = new ConcurrentHashMap<String, User>();
        readWriteLockCourses = this.readWriteLockCourses;
        readWriteLockUsers = this.readWriteLockUsers;
        courseOrder = new ArrayList<Integer>(); //TODO: initialize 0 or 1?

    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() { // singleton instance checker
        return SingletonHolder.instance;
    }

    //makes Course Object from string line.
    private Course strToCourse(String line) {
        int courseNum;
        String courseName;
        String kdamCoursesString;
        ArrayList<String> kdamCoursesList;
        ArrayList<Integer> kdamCoursesListInt;
        int numOfMaxStudents;
        //course number
        int pointer1 = line.indexOf('|');
        courseNum = Integer.parseInt(line.substring(0, pointer1)); //int value of string
        line = line.substring(pointer1);
        //course name
        int pointer2 = line.indexOf('|');
        courseName = line.substring(0, pointer2);
        line = line.substring(pointer2);
        //kdam courses list
        int pointer3 = line.indexOf('|');
        kdamCoursesString = line.substring(1, pointer3);
        kdamCoursesList = new ArrayList<String>(Arrays.asList(kdamCoursesString.split(",")));
        kdamCoursesListInt = new ArrayList<>(kdamCoursesList.size());
        for (int i = 0; i < kdamCoursesListInt.size(); i++) {
            kdamCoursesListInt.set(i, Integer.parseInt(kdamCoursesList.get(i)));
        }
        line = line.substring(pointer3);
        //number of students
        numOfMaxStudents = Integer.parseInt(line);
        return new Course(courseNum, courseName, kdamCoursesList, numOfMaxStudents); //course class to be implemented
    }

    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    boolean initialize(String coursesFilePath) throws IOException, InterruptedException {
        // TODO: implement
        Integer courseNum;
        Course course;
        String clone;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(coursesFilePath)); //create new buffer reader
            String line = reader.readLine(); // reads the first line of the txt file
            while (line != null) {
                course = strToCourse(line); // makes a course object out of the line
                int pointer = line.indexOf('|');
                courseNum = Integer.parseInt(line.substring(0, pointer)); // put the new course in the hash map
                courseOrder.add(courseNum);
                coursesDB.putIfAbsent(courseNum, course);
                // read next line
                line = reader.readLine();
            }
            reader.close();
            return true;
        } catch (IOException e) { //TODO: ask what should be here
            e.wait();
            return false;
        }
    }

    //DB functions:
    public boolean isAdmin(String userName) {//TODO:implement
        return true;
    } //TODO:imlement

    public boolean registerNewUser(String userName, String password, boolean isAdmin) {
        return true;
    }//TODO:implement

    public boolean registerToCourse(String userName, int courseNumber) {
        return true;
    }//TODO:implement

    public boolean unRegisterFromCourse(String userName, int courseNumber) {
        return true;
    }//TODO:implement

    public boolean logInUser(String userName, String password) {
        return true;
    }//TODO:imlement

    public boolean logOutUser(String userName) { //TODO:imlement
        return true;
    }

    public String getKdamForCourse(int courseNumber) throws NoSuchElementException {
        return null;
    }//TODO:implement

    public String getMyCourses(String userName) throws NoSuchElementException {
        return null;
    }//TODO:implement

    public boolean isRegisteredForCourse(String userName, int courseNumber) throws NoSuchElementException {
        return true;
    }//TODO:implement

    public String getCourseStat(int courseNumber) throws NoSuchElementException {
        return null;
    }//TODO:implement

    public String getStudentStat(String studentName) throws NoSuchElementException {
        return null;
    }//TODO:implement


}
