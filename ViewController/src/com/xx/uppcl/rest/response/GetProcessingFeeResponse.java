package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetProcessingFeeResponse implements Serializable{
    @SuppressWarnings("compatibility:5047581246477132103")
    private static final long serialVersionUID = 1L;

    @JsonProperty("ResCode")
    private String resCode;
    
    @JsonProperty("ResMsg")
    private String resMsg;
    
    @JsonProperty("Processfee")
    private String processfee;

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

    public void setProcessfee(String processfee) {
        this.processfee = processfee;
    }

    public String getProcessfee() {
        return processfee;
    }
}
