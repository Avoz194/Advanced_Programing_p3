package bgu.spl.net.impl.BGRSServer.DB;

import java.util.ArrayList;
import java.util.HashSet;

public class Course {
    //courseNum|courseName|KdamCoursesList|numOfMaxStudents
    private int courseNum;
    private String courseName;
    private ArrayList<Integer> KdamCoursesList;
    private int numOfMaxStudents;
    private int numOfAvailableSeats;
    private HashSet<String> listOfStudents;

    public Course(int courseNum, String courseName, ArrayList<Integer> kdamCoursesList, int numOfMaxStudents) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.KdamCoursesList = kdamCoursesList;
        this.numOfMaxStudents = numOfMaxStudents;
        this.numOfAvailableSeats = numOfMaxStudents;
        this.listOfStudents = new HashSet<String>();
    }


    public String getCourseName() {
        return courseName;
    }

    public ArrayList<Integer> getKdamCoursesList() {
        return KdamCoursesList;
    }

    public void setKdamCoursesList(ArrayList<Integer> kdamCoursesList) {
        KdamCoursesList = kdamCoursesList;
    }

    public int getNumOfMaxStudents() {
        return numOfMaxStudents;
    }

    public int getNumOfAvailableSeats() {
        return numOfAvailableSeats;
    }

    public HashSet<String> getListOfStudents() {
        return listOfStudents;
    }

    public boolean addStudent(String studentName) { //TODO:complete
        //Make sure there is place in the course (return false if not)
        //Add student
        //increase currentNum
        return false;
    }

    public void removeStudent(String studentName) { //TODO:complete

    }

}
