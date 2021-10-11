package com.hatt.shoppingcartdemo.model;

import com.hatt.shoppingcartdemo.entity.Colour;

public class ColourInfo {
    private Integer colourId;
    private String name;
    private Integer priority;

    public ColourInfo() {
    }

    public ColourInfo(Colour colour) {
        this.colourId = colour.getColourId();
        this.name = colour.getName();
        this.priority = colour.getPriority();
    }

    // Using in JPA/Hibernate query
    public ColourInfo(Integer colourId, String name, Integer priority) {
        this.colourId = colourId;
        this.name = name;
        this.priority = priority;
    }

    public Integer getColourId() {
        return colourId;
    }

    public void setColourId(Integer colourId) {
        this.colourId = colourId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

}