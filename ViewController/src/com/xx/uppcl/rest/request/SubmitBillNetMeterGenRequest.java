package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitBillNetMeterGenRequest implements Serializable{
    @SuppressWarnings("compatibility:-862761380456482853")
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
    
    @JsonProperty("CumulativeEnergyKVAH") 
     private String cumulativeEnergyKVAH;
    
    @JsonProperty("MaximumDemandKVA") 
     private String maximumDemandKVA;
    
    @JsonProperty("CumulativeEnergyKWHE") 
     private String cumulativeEnergyKWHE;
    
    @JsonProperty("MaximumDemandKVE") 
     private String maximumDemandKVE;
    
    @JsonProperty("CumulativeEnergyKVAHE") 
     private String cumulativeEnergyKVAHE;
    
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

    public void setCumulativeEnergyKVAH(String cumulativeEnergyKVAH) {
        this.cumulativeEnergyKVAH = cumulativeEnergyKVAH;
    }

    public String getCumulativeEnergyKVAH() {
        return cumulativeEnergyKVAH;
    }

    public void setMaximumDemandKVA(String maximumDemandKVA) {
        this.maximumDemandKVA = maximumDemandKVA;
    }

    public String getMaximumDemandKVA() {
        return maximumDemandKVA;
    }

    public void setCumulativeEnergyKWHE(String cumulativeEnergyKWHE) {
        this.cumulativeEnergyKWHE = cumulativeEnergyKWHE;
    }

    public String getCumulativeEnergyKWHE() {
        return cumulativeEnergyKWHE;
    }

    public void setMaximumDemandKVE(String maximumDemandKVE) {
        this.maximumDemandKVE = maximumDemandKVE;
    }

    public String getMaximumDemandKVE() {
        return maximumDemandKVE;
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

    public void setCumulativeEnergyKVAHE(String cumulativeEnergyKVAHE) {
        this.cumulativeEnergyKVAHE = cumulativeEnergyKVAHE;
    }

    public String getCumulativeEnergyKVAHE() {
        return cumulativeEnergyKVAHE;
    }
}
