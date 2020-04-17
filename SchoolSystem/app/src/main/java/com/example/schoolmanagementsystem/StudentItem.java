package com.example.schoolmanagementsystem;

//simple class to hold data for each student
public class StudentItem {
    private int imgResource;
    private String studentName;

    public StudentItem(int imgResource, String studentName) {
        this.imgResource = imgResource;
        this.studentName = studentName;
    }

    public int getImgResource() {
        return imgResource;
    }

    public String getStudentName() {
        return studentName;
    }
}
