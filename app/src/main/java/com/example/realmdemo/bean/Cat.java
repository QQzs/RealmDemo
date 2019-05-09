package com.example.realmdemo.bean;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * @Author: zs
 * @Date: 2019-05-09 10:39
 * @Description:
 */
@RealmClass
public class Cat implements RealmModel {

    @PrimaryKey
    private int id;
    private String name;

    public Cat() {
    }

    public Cat(int id, String name) {
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
}
