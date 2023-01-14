package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccDetailsCorrectionSRRequest implements Serializable{
    @SuppressWarnings("compatibility:-761935113353324797")
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("RequestType") 
     private String requestType;
    
    @JsonProperty("CorrectionType") 
     private String correctionType;
    
    @JsonProperty("ChangeReason") 
     private String changeReason;
    
    @JsonProperty("ActId") 
     private String actId;
    
    @JsonProperty("BillId") 
     private String billId;
    
    @JsonProperty("UName") 
     private String uName;
    
    @JsonProperty("UFathName") 
     private String uFathName;
    
    @JsonProperty("UMothName") 
     private String uMothName;
    
    @JsonProperty("UHusbName") 
     private String uHusbName;
    
    @JsonProperty("UHouseNo") 
     private String uHouseNo;
    
    @JsonProperty("UBuildName") 
     private String uBuildName;
    
    @JsonProperty("UAreaName") 
     private String uAreaName;
    
    @JsonProperty("UCity") 
     private String uCity;
    
    @JsonProperty("UDistrict") 
     private String uDistrict;
    
    @JsonProperty("UPin") 
     private String uPin;
    
    @JsonProperty("SerialNbr") 
     private String serialNbr;
    
    @JsonProperty("MeterComplaintType") 
     private String meterComplaintType;
    
    @JsonProperty("COTReason") 
     private String cotReason;

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setCorrectionType(String correctionType) {
        this.correctionType = correctionType;
    }

    public String getCorrectionType() {
        return correctionType;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActId() {
        return actId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillId() {
        return billId;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUName() {
        return uName;
    }

    public void setUFathName(String uFathName) {
        this.uFathName = uFathName;
    }

    public String getUFathName() {
        return uFathName;
    }

    public void setUMothName(String uMothName) {
        this.uMothName = uMothName;
    }

    public String getUMothName() {
        return uMothName;
    }

    public void setUHusbName(String uHusbName) {
        this.uHusbName = uHusbName;
    }

    public String getUHusbName() {
        return uHusbName;
    }

    public void setUHouseNo(String uHouseNo) {
        this.uHouseNo = uHouseNo;
    }

    public String getUHouseNo() {
        return uHouseNo;
    }

    public void setUBuildName(String uBuildName) {
        this.uBuildName = uBuildName;
    }

    public String getUBuildName() {
        return uBuildName;
    }

    public void setUAreaName(String uAreaName) {
        this.uAreaName = uAreaName;
    }

    public String getUAreaName() {
        return uAreaName;
    }

    public void setUCity(String uCity) {
        this.uCity = uCity;
    }

    public String getUCity() {
        return uCity;
    }

    public void setUDistrict(String uDistrict) {
        this.uDistrict = uDistrict;
    }

    public String getUDistrict() {
        return uDistrict;
    }

    public void setUPin(String uPin) {
        this.uPin = uPin;
    }

    public String getUPin() {
        return uPin;
    }

    public void setSerialNbr(String serialNbr) {
        this.serialNbr = serialNbr;
    }

    public String getSerialNbr() {
        return serialNbr;
    }

    public void setMeterComplaintType(String meterComplaintType) {
        this.meterComplaintType = meterComplaintType;
    }

    public String getMeterComplaintType() {
        return meterComplaintType;
    }

    public void setCotReason(String cotReason) {
        this.cotReason = cotReason;
    }

    public String getCotReason() {
        return cotReason;
    }
}
