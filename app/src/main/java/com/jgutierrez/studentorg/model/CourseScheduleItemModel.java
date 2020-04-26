package com.jgutierrez.studentorg.model;

import java.io.Serializable;


public class CourseScheduleItemModel implements Serializable {
    private String time;
    private String class_room;

    public String getTime() {
        return time;
    }

    public String getClassRoom() {
        return class_room;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setClassRoom(String class_room) {
        this.class_room = class_room;
    }
}