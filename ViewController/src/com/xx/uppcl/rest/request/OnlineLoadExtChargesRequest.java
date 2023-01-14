package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OnlineLoadExtChargesRequest implements Serializable{
    @SuppressWarnings("compatibility:-5192706738543466815")
    private static final long serialVersionUID = 1L;

    @JsonProperty("AcctId") 
     private String acctId;
    
    @JsonProperty("Load") 
     private String load;

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getLoad() {
        return load;
    }
}
