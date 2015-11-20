package com.example.daniel.hospitalapp;

/**
 * Created by Daniel on 19/11/2015.
 */
public class StInCList {
    private String Student_Name;
    private byte[] photo;

    public StInCList(String name,byte[] photo){
        this.Student_Name=name;
        this.photo=photo;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
