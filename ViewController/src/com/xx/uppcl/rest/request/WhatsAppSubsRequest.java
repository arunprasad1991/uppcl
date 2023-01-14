package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WhatsAppSubsRequest implements Serializable{
    @SuppressWarnings("compatibility:-7182461944451076577")
    private static final long serialVersionUID = 1L;

    @JsonProperty("Src") 
     private String src;
    
    @JsonProperty("MobNo") 
     private String mobNo;
    
    @JsonProperty("AcctId") 
     private String acctId;
    
    @JsonProperty("Discom") 
     private String discom;

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setDiscom(String discom) {
        this.discom = discom;
    }

    public String getDiscom() {
        return discom;
    }
}
