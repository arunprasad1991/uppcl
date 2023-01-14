package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class InitiatePaymentRequest implements Serializable{
    @SuppressWarnings("compatibility:-6801646823509427682")
    private static final long serialVersionUID = 1L;

    @JsonProperty("TrackId") 
     private String trackId;
    
    @JsonProperty("ServiceRequestType") 
     private String serviceRequestType;
    
    @JsonProperty("AccountNo") 
     private String accountNo;
    
    @JsonProperty("COMMENTS") 
     private String comments;
    
    @JsonProperty("Param1") 
     private String param1;
    
    @JsonProperty("Param2") 
     private String param2;
    
    @JsonProperty("Param3") 
     private String param3;
    
    @JsonProperty("Param4") 
     private String param4;
    
    @JsonProperty("Param5") 
     private String param5;
    
    @JsonProperty("Charges1") 
     private String charges1;
    
    @JsonProperty("Charges2") 
     private String charges2;
    
    @JsonProperty("Request_Date") 
     private String request_Date;

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setServiceRequestType(String serviceRequestType) {
        this.serviceRequestType = serviceRequestType;
    }

    public String getServiceRequestType() {
        return serviceRequestType;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam5(String param5) {
        this.param5 = param5;
    }

    public String getParam5() {
        return param5;
    }

    public void setCharges1(String charges1) {
        this.charges1 = charges1;
    }

    public String getCharges1() {
        return charges1;
    }

    public void setCharges2(String charges2) {
        this.charges2 = charges2;
    }

    public String getCharges2() {
        return charges2;
    }

    public void setRequest_Date(String request_Date) {
        this.request_Date = request_Date;
    }

    public String getRequest_Date() {
        return request_Date;
    }
}
