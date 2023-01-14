package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UpdatePanDetailsResponse implements Serializable{
    @SuppressWarnings("compatibility:3701140968097103364")
    private static final long serialVersionUID = 1L;

    public UpdatePanDetailsResponse() {
        super();
    }
    
    @JsonProperty("CCDd") 
     private String ccdd;
    
    @JsonProperty("errorCode") 
     private String errorCode;
    
    @JsonProperty("errorMsg") 
     private String errorMsg;

    public void setCcdd(String ccdd) {
        this.ccdd = ccdd;
    }

    public String getCcdd() {
        return ccdd;
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
