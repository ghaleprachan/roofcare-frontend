package com.example.roofcare.models;

public class MoreOptionsModel {
    private Enum id;
    private String name;
    private Integer icon;

    public MoreOptionsModel(Enum id, String name, Integer icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public Enum getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getIcon() {
        return icon;
    }
}
