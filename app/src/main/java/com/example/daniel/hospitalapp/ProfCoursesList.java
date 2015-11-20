package com.example.daniel.hospitalapp;

/**
 * Created by Daniel on 17/11/2015.
 */
public class ProfCoursesList {
    private String Course_Name;
    private String Start_Date;
    private String End_Date;

    public ProfCoursesList(String cn, String sd, String ed){
        this.Course_Name=cn;
        this.Start_Date=sd;
        this.End_Date=ed;
    }

    public String getCourse_Name() {
        return Course_Name;
    }

    public void setCourse_Name(String course_Name) {
        Course_Name = course_Name;
    }

    public String getStart_Date() {
        return Start_Date;
    }

    public void setStart_Date(String start_Date) {
        Start_Date = start_Date;
    }

    public String getEnd_Date() {
        return End_Date;
    }

    public void setEnd_Date(String end_Date) {
        End_Date = end_Date;
    }
}
