package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OnlineLoadExtEligibltyRequest implements Serializable{
    @SuppressWarnings("compatibility:3709099689860146611")
    private static final long serialVersionUID = 1L;

    @JsonProperty("AcctId") 
     private String acctId;

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAcctId() {
        return acctId;
    }
}
