package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UpdateConsumerDetailsResponse implements Serializable{
    private static final long serialVersionUID=132268972L;
    public UpdateConsumerDetailsResponse() {
        super();
    }
    @JsonProperty("UpdateStatus")
    private String status;
    @JsonProperty("ErrCode")
    private String errorCode;
    @JsonProperty("ErrDesc")
    private String errorDec;


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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
