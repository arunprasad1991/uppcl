package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetProcessingFeeRequest implements Serializable{
    @SuppressWarnings("compatibility:-5468779565506935078")
    private static final long serialVersionUID = 1L;

    @JsonProperty("ActId") 
     private String actId;
    
    @JsonProperty("CaseFilter") 
     private String caseFilter;

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActId() {
        return actId;
    }

    public void setCaseFilter(String caseFilter) {
        this.caseFilter = caseFilter;
    }

    public String getCaseFilter() {
        return caseFilter;
    }
}
