package com.greenstar.hsteam.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class QATTCForm {
    @NonNull
    @PrimaryKey
    private String providerCode;
    private String supervisorCode;
    private String supervisorName;
    private String QAMCode;
    private String QAMName;
    private String dateOfAssessment;
    private String providerName;
    private String region;
    private String mobileDate;

    @NonNull
    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(@NonNull String providerCode) {
        this.providerCode = providerCode;
    }

    public String getSupervisorCode() {
        return supervisorCode;
    }

    public void setSupervisorCode(String supervisorCode) {
        this.supervisorCode = supervisorCode;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getQAMCode() {
        return QAMCode;
    }

    public void setQAMCode(String QAMCode) {
        this.QAMCode = QAMCode;
    }

    public String getQAMName() {
        return QAMName;
    }

    public void setQAMName(String QAMName) {
        this.QAMName = QAMName;
    }

    public String getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(String dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMobileDate() {
        return mobileDate;
    }

    public void setMobileDate(String mobileDate) {
        this.mobileDate = mobileDate;
    }
}
