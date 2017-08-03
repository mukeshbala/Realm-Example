package com.example.orgware.myrealamdatabase;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Orgware on 8/1/2017.
 */

public class PojoItem extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String mobile;
    private String address;
    private int age;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
