package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetPanDetailsResponse implements Serializable{
    @SuppressWarnings("compatibility:-1497681421292303196")
    private static final long serialVersionUID = 1L;

    public GetPanDetailsResponse() {
        super();
    }
    
    @JsonProperty("PAN") 
     private String pan;
    
    @JsonProperty("errorCode") 
     private String errorCode;
    
    @JsonProperty("errorMsg") 
     private String errorMsg;

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPan() {
        return pan;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
