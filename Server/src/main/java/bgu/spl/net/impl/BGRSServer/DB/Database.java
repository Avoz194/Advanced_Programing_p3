package bgu.spl.net.impl.BGRSServer.DB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
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


    /**
     * @param pathCourses
     * @param readWriteLockCourses
     * @param readWriteLockUsers
     */
    //to prevent user from creating new Database
    private Database(String pathCourses, ReadWriteLock readWriteLockCourses, ReadWriteLock readWriteLockUsers) {
        // TODO: implement - make sure threadSafe singelton?
        this.pathCourses = pathCourses;
        coursesDB = new ConcurrentHashMap<Integer, Course>();
        usersDB = new ConcurrentHashMap<String, User>();
        this.readWriteLockCourses = readWriteLockCourses;
        this.readWriteLockUsers = readWriteLockUsers;
        courseOrder = new ArrayList<Integer>();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() { // singleton instance checker
        return SingletonHolder.instance;
    }

    /**
     * makes Course Object from string line.
     *
     * @param line
     * @return course object
     */
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
        return new Course(courseNum, courseName, kdamCoursesListInt, numOfMaxStudents); //course class to be implemented
    }

    /**
     * loades the courses from the file path {@code coursesFilePath }specified
     * into the Database, returns true if successful.
     *
     * @param coursesFilePath
     * @return true if succeed anf false if not
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean initialize(String coursesFilePath) {
        // TODO: add exception if already initialized
        Integer courseNum;
        Course course;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(coursesFilePath)); //create new buffer reader
            String line = reader.readLine(); // reads the first line of the txt file
            while (line != null) { //loop stops when there are no left lines
                String lineClone = line;
                course = strToCourse(lineClone); // makes a course object out of the line
                int pointer = line.indexOf('|');
                courseNum = Integer.parseInt(line.substring(0, pointer)); // put the new course in the hash map
                courseOrder.add(courseNum);
                coursesDB.putIfAbsent(courseNum, course);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) { //TODO: ask what should be here
            return false;
        }
        for (int courseNumber : coursesDB.keySet()) { //loop on every key on courses DB
            ArrayList<Integer> temp = coursesDB.get(courseNumber).getKdamCoursesList();
            temp.sort(Comparator.comparingInt(o -> courseOrder.indexOf(o))); // reordering the kdam list to the fixed order
            coursesDB.get(courseNumber).setKdamCoursesList(temp);
        }
        return true;
    }

    //DB functions:
    public boolean isUser(String userName) {
        return usersDB.containsKey(userName);
    }
    public boolean isCourse(int course) {
        return coursesDB.containsKey(course);
    }
    public boolean isAdmin(String userName) throws NoSuchElementException {
        if (!isUser(userName)) throw new NoSuchElementException("no such userName");
        return usersDB.get(userName).isAdmin();
    }

    /**
     * Try to register a new user if possible.
     * If already exist - return false;
     * else, create a new User entity and add to the userDb.
     *
     * @param userName
     * @param password
     * @param isAdmin
     * @return
     */
    public boolean registerNewUser(String userName, String password, boolean isAdmin) {
        if (isUser(userName)) return false;
        User userToRegister = new User(userName, password,isAdmin);
        usersDB.put(userName, userToRegister);
        return true;
    }//TODO:implement

    public boolean registerToCourse(String userName, int courseNumber) {
        return true;
    }//TODO:implement

    public boolean unRegisterFromCourse(String userName, int courseNumber) {
        return true;
    }//TODO:implement

    /**
     * Log in the user if possible and return the result:
     *
     * if @userName doesn't exists, password isn't correct or is already logged in - return false;
     * else - true
     * @param userName
     * @param password
     * @return
     */
    public boolean logInUser(String userName, String password) {
        if (!isUser(userName)) return false;
        User user1 = usersDB.get(userName);
        if ((user1.getPass().equals(password)) && (!user1.isLoggedIn())) {
            user1.setLoggedIn(true);
            return true;
        }
        return false;
    }

    /**
     * Log in the user if possible and return the result:
     *
     * if @userName doesn't exists, or is not logged in - return false;
     * else - true
     * @param userName
     * @return
     */
    public boolean logOutUser(String userName) { //TODO:imlement
        if (!isUser(userName)) return false;
        User user1 = usersDB.get(userName);
        if (user1.isLoggedIn()) {
            user1.setLoggedIn(false);
            return true;
        }
        return false;
    }

    public String getKdamForCourse(int courseNumber) throws NoSuchElementException {
       if(!isCourse(courseNumber)) throw new NoSuchElementException("No such Course");
       return coursesDB.get(courseNumber).getKdamCoursesList().toString(); //TODO:make sure good toString;
    }//TODO:implement

    public String getMyCourses(String userName) throws NoSuchElementException {
        if(!isUser(userName)) throw new NoSuchElementException("No such Course");
        return usersDB.get(userName).getListOfCoursesAttendTo().toString(); //TODO:make sure good toString
    }//TODO:implement

    public boolean isRegisteredForCourse(String userName, int courseNumber) throws NoSuchElementException {
        if(!isUser(userName)|(!isCourse(courseNumber))) throw new NoSuchElementException("No such Course/User");
       return usersDB.get(userName).isAttending(courseNumber);
    }//TODO:implement

    public String getCourseStat(int courseNumber) throws NoSuchElementException {
        return null;
    }//TODO:implement

    public String getStudentStat(String studentName) throws NoSuchElementException {
        return null;
    }//TODO:implement


}
