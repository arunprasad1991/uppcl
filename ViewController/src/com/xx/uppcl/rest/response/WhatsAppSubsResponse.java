package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WhatsAppSubsResponse implements Serializable{
    @SuppressWarnings("compatibility:-1098669263162471882")
    private static final long serialVersionUID = 1L;

    @JsonProperty("ResCode")
    private String ResCode;
    
    @JsonProperty("ResMsg")
    private String ResMsg;

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
}
