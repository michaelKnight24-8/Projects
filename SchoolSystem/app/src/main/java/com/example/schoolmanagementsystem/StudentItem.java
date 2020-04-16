package com.example.schoolmanagementsystem;

//simple class to hold data for each student
public class StudentItem {
    private int imgResource;
    private String studentName;
    private int tag;

    public StudentItem(int imgResource, String studentName) {
        this.imgResource = imgResource;
        this.studentName = studentName;
    }

    public void setTag(int tag) { this.tag = tag; }

    public int getImgResource() {
        return imgResource;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getTag() { return tag; }
}
