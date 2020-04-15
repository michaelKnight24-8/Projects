package com.example.schoolmanagementsystem;

public class AssignmentsItem {
    private String title, dueDate;

    public AssignmentsItem(String title, String dueDate) {
        this.title = title;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }
}