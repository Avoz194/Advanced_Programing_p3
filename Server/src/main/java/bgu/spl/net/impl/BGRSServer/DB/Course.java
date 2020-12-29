package bgu.spl.net.impl.BGRSServer.DB;

import java.util.ArrayList;
import java.util.HashSet;

public class Course {
    //courseNum|courseName|KdamCoursesList|numOfMaxStudents
    private int courseNum;
    private String courseName;
    private ArrayList<Integer> KdamCoursesList;
    private int numOfMaxStudents;
    private int currentNumOfStudents = 0; //TODO: make sure to update as a part of register/unRegister

    private HashSet<String> setOfStudents;

    public Course(int courseNum, String courseName, ArrayList<Integer> kdamCoursesList, int numOfMaxStudents) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.KdamCoursesList = kdamCoursesList;
        this.numOfMaxStudents = numOfMaxStudents;
        this.setOfStudents = new HashSet<String>();
    }

    public int getCourseNum() {
        return courseNum;
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

    public void setNumOfMaxStudents(int numOfMaxStudents) {
        this.numOfMaxStudents = numOfMaxStudents;
    }

    public HashSet<String> getSetOfStudents() {
        return setOfStudents;
    }

    public void setSetOfStudents(HashSet<String> setOfStudents) {
        this.setOfStudents = setOfStudents;
    }
}
