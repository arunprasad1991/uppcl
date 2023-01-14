package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import java.util.List;

public class GetSRStatusResponse implements Serializable{
    @SuppressWarnings("compatibility:2956162989872302615")
    private static final long serialVersionUID = 1L;

    @JsonProperty("CaseStatus")
    private List<ServiceReqStatusPOJO> caseStatus;


    public void setCaseStatus(List<ServiceReqStatusPOJO> caseStatus) {
        this.caseStatus = caseStatus;
    }

    public List<ServiceReqStatusPOJO> getCaseStatus() {
        return caseStatus;
    }
}
