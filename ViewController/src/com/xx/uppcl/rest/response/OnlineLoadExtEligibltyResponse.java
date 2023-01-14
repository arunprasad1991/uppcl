package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OnlineLoadExtEligibltyResponse implements Serializable{
    @SuppressWarnings("compatibility:6164583387963329147")
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
