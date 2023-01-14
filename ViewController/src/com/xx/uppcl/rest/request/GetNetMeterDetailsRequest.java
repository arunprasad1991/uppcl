package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetNetMeterDetailsRequest implements Serializable{
    @SuppressWarnings("compatibility:-7891579769091906251")
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
