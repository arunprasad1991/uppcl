package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SendEmailResponse implements Serializable{
    
    private static final long serialVersionUID=101687784522L;     
     public SendEmailResponse() {
        super();
    }
    @JsonProperty("Result")
    private String result;
    @JsonProperty("ErrCode")
    private String errorCode;
    @JsonProperty("ErrDesc")
    private String errorDec;


    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorDec(String errorDec) {
        this.errorDec = errorDec;
    }

    public String getErrorDec() {
        return errorDec;
    }
}
