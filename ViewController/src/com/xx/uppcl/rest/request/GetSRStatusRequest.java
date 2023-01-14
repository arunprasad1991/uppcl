package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetSRStatusRequest implements Serializable{
    @SuppressWarnings("compatibility:-4988584687685895455")
    private static final long serialVersionUID = 1L;

    @JsonProperty("AccountId") 
     private String accountId;
    
    @JsonProperty("FromDate") 
     private String fromDate;
    
    @JsonProperty("ToDate") 
     private String toDate;
    
    @JsonProperty("RequestType") 
     private String requestType;

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }
}
