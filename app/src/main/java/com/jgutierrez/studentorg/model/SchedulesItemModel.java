package com.jgutierrez.studentorg.model;

import java.io.Serializable;



public class SchedulesItemModel implements Serializable {
    private String time;
    private String course_key;
    private String color_hex;

    public String getCourseKey() {
        return course_key;
    }
    public String getColorHex() {
        return color_hex;
    }

    public void setCourseKey(String course_key) {
        this.course_key = course_key;
    }

    public void setColorHex(String color_hex) {
        this.color_hex = color_hex;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
