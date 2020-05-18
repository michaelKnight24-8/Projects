package com.example.schoolmanagementsystem;

public class AssignmentsItem {
    private String title, dueDate, section;

    public AssignmentsItem(String title, String dueDate, String section) {
        this.title = title;
        this.section = section;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getSection() { return section; }
}