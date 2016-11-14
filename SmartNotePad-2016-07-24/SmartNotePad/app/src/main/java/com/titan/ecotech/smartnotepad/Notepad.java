package com.titan.ecotech.smartnotepad;

/**
 * Created by User on 7/19/2016.
 */
public class Notepad {

    private int id;
    private String priority;
    private String title;
    private String details;
    private String time;
    private String taskdone;


    public Notepad(int id, String title, String details, String priority, String time, String taskdone) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.priority = priority;
        this.time = time;
        this.taskdone = taskdone;
    }

    public Notepad(String title, String details, String priority, String time, String taskdone) {
        this.title = title;
        this.details = details;
        this.priority = priority;
        this.time = time;
        this.taskdone = taskdone;
    }

    public Notepad(){

    }

    public int getId() {
        return id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTaskdone() {
        return taskdone;
    }

    public void setTaskdone(String taskdone) {
        this.taskdone = taskdone;
    }
}
