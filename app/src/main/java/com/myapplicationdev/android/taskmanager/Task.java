package com.myapplicationdev.android.taskmanager;

import java.io.Serializable;

/**
 * Created by 15017117 on 25/5/2017.
 */

public class Task implements Serializable {
    private int id;
    private String taskName;
    private String taskDesc;

    public Task(int id, String taskName,String taskDesc){
        this.id = id;
        this.taskName = taskName;
        this.taskDesc = taskDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
}
