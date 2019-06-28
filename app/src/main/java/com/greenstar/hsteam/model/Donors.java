package com.greenstar.hsteam.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Donors {
    @NonNull
    @PrimaryKey
    private int code;
    private String name;

    @NonNull
    public int getCode() {
        return code;
    }

    public void setCode(@NonNull int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
