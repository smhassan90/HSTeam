package com.greenstar.hsteam.model;

import java.util.List;

public class SyncObject {
    List<QTVForm> qtvForms;
    List<QATFormHeader> qatFormHeaders;
    List<QATFormQuestion> qatFormQuestions;
    List<QATAreaDetail> qatAreaDetails;


    public List<QTVForm> getQtvForms() {
        return qtvForms;
    }

    public void setQtvForms(List<QTVForm> qtvForms) {
        this.qtvForms = qtvForms;
    }

    public List<QATFormHeader> getQatFormHeaders() {
        return qatFormHeaders;
    }

    public void setQatFormHeaders(List<QATFormHeader> qatFormHeaders) {
        this.qatFormHeaders = qatFormHeaders;
    }

    public List<QATFormQuestion> getQatFormQuestions() {
        return qatFormQuestions;
    }

    public void setQatFormQuestions(List<QATFormQuestion> qatFormQuestions) {
        this.qatFormQuestions = qatFormQuestions;
    }

    public List<QATAreaDetail> getQatAreaDetails() {
        return qatAreaDetails;
    }

    public void setQatAreaDetails(List<QATAreaDetail> qatAreaDetails) {
        this.qatAreaDetails = qatAreaDetails;
    }
}
