package com.jgutierrez.studentorg.model;

import java.io.Serializable;


public class SchedulesModel implements Serializable {
    private SchedulesItemModel[][] data;

    public SchedulesModel() {
        data = new SchedulesItemModel[13][8];
    }

    public SchedulesItemModel[][] getData() {
        return data;
    }

    public void setData(SchedulesItemModel[][] data) {
        this.data = data;
    }

}
