package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ServiceReqStatusPOJO implements Serializable{
    @SuppressWarnings("compatibility:3334864040101392470")
    private static final long serialVersionUID = 1L;

    @JsonProperty("ErrCode")
    private String errCode;
    
    @JsonProperty("ErrDesc")
    private String errDesc;
    
    @JsonProperty("CaseId")
    private String caseId;
    
    @JsonProperty("CaseCreationDateTime")
    private String caseCreationDateTime;
    
    @JsonProperty("CaseDesc")
    private String caseDesc;
    
    @JsonProperty("CaseStatus")
    private String caseStatus;
    
    @JsonProperty("Remarks")
    private String remarks;
    
    @JsonProperty("Comments")
    private String comments;

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseCreationDateTime(String caseCreationDateTime) {
        this.caseCreationDateTime = caseCreationDateTime;
    }

    public String getCaseCreationDateTime() {
        return caseCreationDateTime;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }

    public String getCaseDesc() {
        return caseDesc;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }
}
