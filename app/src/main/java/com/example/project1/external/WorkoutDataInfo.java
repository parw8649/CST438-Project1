package com.example.project1.external;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class WorkoutDataInfo {

    private Long id;

    private String name;

    @SerializedName("creation_date")
    private String creationDate;

    private String description;

    public WorkoutDataInfo(String name, String description) {
        this.name = name;
        this.creationDate = new Date().toString();
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}