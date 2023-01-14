package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SubmitBillMeterGenRequest implements Serializable{
    @SuppressWarnings("compatibility:-797208104389918605")
    private static final long serialVersionUID = 1L;

    @JsonProperty("AccountId") 
     private String accountId;
    
    @JsonProperty("BadgeNumber") 
     private String badgeNumber;
    
    @JsonProperty("MeterSerialNumber") 
     private String meterSerialNumber;
    
    @JsonProperty("ManufacturerCode") 
     private String manufacturerCode;
    
    @JsonProperty("CumulativeEnergyKWH") 
     private String cumulativeEnergyKWH;
    
    @JsonProperty("MaximumDemandKW") 
     private String maximumDemandKW;
    
    @JsonProperty("WSSMeterReadDateTime") 
     private String wSSMeterReadDateTime;
    
    @JsonProperty("EmailId") 
     private String emailId;
    
    @JsonProperty("MobileNo") 
     private String mobileNo;
    
    @JsonProperty("Remarks") 
     private String remarks;

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public void setMeterSerialNumber(String meterSerialNumber) {
        this.meterSerialNumber = meterSerialNumber;
    }

    public String getMeterSerialNumber() {
        return meterSerialNumber;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setCumulativeEnergyKWH(String cumulativeEnergyKWH) {
        this.cumulativeEnergyKWH = cumulativeEnergyKWH;
    }

    public String getCumulativeEnergyKWH() {
        return cumulativeEnergyKWH;
    }

    public void setMaximumDemandKW(String maximumDemandKW) {
        this.maximumDemandKW = maximumDemandKW;
    }

    public String getMaximumDemandKW() {
        return maximumDemandKW;
    }

    public void setWSSMeterReadDateTime(String wSSMeterReadDateTime) {
        this.wSSMeterReadDateTime = wSSMeterReadDateTime;
    }

    public String getWSSMeterReadDateTime() {
        return wSSMeterReadDateTime;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }
}
