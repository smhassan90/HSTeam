package com.greenstar.hsteam.dal;

import com.greenstar.hsteam.model.ApprovalQTVForm;
import com.greenstar.hsteam.model.Area;
import com.greenstar.hsteam.model.Dashboard;
import com.greenstar.hsteam.model.Providers;
import com.greenstar.hsteam.model.Question;

import java.io.Serializable;
import java.util.List;

public class HSData implements Serializable {
    private String name;
    private String code;
    private String AMName;
    private String AMCode;
    private String region;
    private Dashboard dashboard;
    private int isQTVAllowed;
    private int isQATAllowed;

    List<Providers> providers;
    List<ApprovalQTVForm> qtvForms;
    List<Question> questions;
    List<Area> areas;

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

    public List<ApprovalQTVForm> getQtvForms() {
        return qtvForms;
    }

    public void setQtvForms(List<ApprovalQTVForm> qtvForms) {
        this.qtvForms = qtvForms;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public String getAMCode() {
        return AMCode;
    }

    public void setAMCode(String AMCode) {
        this.AMCode = AMCode;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public int getIsQTVAllowed() {
        return isQTVAllowed;
    }

    public void setIsQTVAllowed(int isQTVAllowed) {
        this.isQTVAllowed = isQTVAllowed;
    }

    public int getIsQATAllowed() {
        return isQATAllowed;
    }

    public void setIsQATAllowed(int isQATAllowed) {
        this.isQATAllowed = isQATAllowed;
    }
}
