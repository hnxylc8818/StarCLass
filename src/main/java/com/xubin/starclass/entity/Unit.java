package com.xubin.starclass.entity;

import java.util.List;

/**
 * Unit entity. @author MyEclipse Persistence Tools
 */

public class Unit implements java.io.Serializable {

    // Fields

    private Integer uid;
    private String name;
    private Integer lid;
    private List<Part> parts;

    // Constructors

    /**
     * default constructor
     */
    public Unit() {
    }

    /**
     * full constructor
     */
    public Unit(String name, Integer lid) {
        this.name = name;
        this.lid = lid;
    }

    // Property accessors


    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public Integer getUid() {
        return this.uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLid() {
        return this.lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

}