package com.itcast.pojo;

import lombok.Data;

import java.io.Serializable;


@Data
public class User implements Serializable {
    private int id;
    private String name;

    /*@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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
    }*/
}
