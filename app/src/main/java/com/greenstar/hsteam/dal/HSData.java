package com.greenstar.hsteam.dal;

import com.greenstar.hsteam.model.Providers;

import java.io.Serializable;
import java.util.List;

public class HSData implements Serializable {
    private String name;
    private String code;
    private String AMName;
    private String region;

    List<Providers> providers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAMName() {
        return AMName;
    }

    public void setAMName(String AMName) {
        this.AMName = AMName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Providers> getProviders() {
        return providers;
    }

    public void setProviders(List<Providers> providers) {
        this.providers = providers;
    }
}
