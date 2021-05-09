package com.example.homesewa.models.moreOptionModel;

public class MoreOptionsModel {
    private final Enum id;
    private final String name;
    private final Integer icon;

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
