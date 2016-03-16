package com.apps.my.appointmate_server;

public class Schedule {
    private int id;
    private String name;
    private String timetable;

    public Schedule() {
    }

    public Schedule(int id, String name, String timetable) {
        this.id = id;
        this.name = name;
        this.timetable = timetable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public int getId() {
        return id;
    }

    public String getTimetable() {
        return timetable;
    }

    public String getName() {
        return name;
    }
}