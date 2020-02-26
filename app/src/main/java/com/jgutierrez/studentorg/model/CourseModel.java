package com.jgutierrez.studentorg.model;

import java.io.Serializable;
import java.util.List;


public class CourseModel implements Serializable {
    private String course_id;
    private String course_name;
    private String color_hex;
    private List<CourseScheduleItemModel> schedules;
    private String lecturer_name;
    private String lecturer_email;
    private String lecturer_phone;

    public String getCourseId() {
        return course_id;
    }

    public String getCourseName() {
        return course_name;
    }

    public String getColorHex() {
        return color_hex;
    }
    public String getLecturerName() {
        return lecturer_name;
    }
    public String getLecturerEmail() {
        return lecturer_email;
    }
    public String getLecturerPhone() {
        return lecturer_phone;
    }

    public List<CourseScheduleItemModel> getSchedules() {
        return schedules;
    }

    public void setCourseId(String course_id) {
        this.course_id = course_id;
    }

    public void setCourseName(String course_name) {
        this.course_name = course_name;
    }

    public void setColorHex(String color_hex) {
        this.color_hex = color_hex;
    }

    public void setSchedules(List<CourseScheduleItemModel> schedules) {
        this.schedules = schedules;
    }

    public void setLecturerName(String lecturer_name) {
        this.lecturer_name = lecturer_name;
    }

    public void setLecturerEmail(String lecturer_email) {
        this.lecturer_email = lecturer_email;
    }

    public void setLecturerPhone(String lecturer_phone) {
        this.lecturer_phone = lecturer_phone;
    }

}
