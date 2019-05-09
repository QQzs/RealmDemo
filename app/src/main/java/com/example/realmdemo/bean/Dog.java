package com.example.realmdemo.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @Author: zs
 * @Date: 2019-05-09 09:35
 * @Description:
 */
public class Dog extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String colorName;

    public Dog() {
    }

    public Dog(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + colorName + '\'' +
                '}';
    }
}
