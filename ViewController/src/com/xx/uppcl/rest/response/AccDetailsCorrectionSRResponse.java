package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AccDetailsCorrectionSRResponse implements Serializable{
    @SuppressWarnings("compatibility:-4484035463341715148")
    private static final long serialVersionUID = 1L;

    @JsonProperty("ResCode")
    private String ResCode;
    
    @JsonProperty("ResMsg")
    private String ResMsg;
    
    @JsonProperty("CaseId")
    private String CaseId;

    public void setResCode(String ResCode) {
        this.ResCode = ResCode;
    }

    public String getResCode() {
        return ResCode;
    }

    public void setResMsg(String ResMsg) {
        this.ResMsg = ResMsg;
    }

    public String getResMsg() {
        return ResMsg;
    }

    public void setCaseId(String CaseId) {
        this.CaseId = CaseId;
    }

    public String getCaseId() {
        return CaseId;
    }
}
