package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class InitiatePaymentResponse implements Serializable{
    @SuppressWarnings("compatibility:-7550959976679515673")
    private static final long serialVersionUID = 1L;

    @JsonProperty("ResCode")
    private String resCode;
    
    @JsonProperty("ResMsg")
    private String resMsg;

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getResMsg() {
        return resMsg;
    }
}
