package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetMeterDetailsResponse implements Serializable{
    @SuppressWarnings("compatibility:8884067263363316787")
    private static final long serialVersionUID = 1L;

    @JsonProperty("MeterStatus") 
     private String meterStatus;
    
    @JsonProperty("SupplyType") 
     private String supplyType;
    
    @JsonProperty("PurposeOfSupply") 
     private String purposeOfSupply;
    
    @JsonProperty("LeftDigit") 
     private String leftDigit;
    
    @JsonProperty("RightDigit") 
     private String rightDigit;
    
    @JsonProperty("LeftDigitMD") 
     private String leftDigitMD;
    
    @JsonProperty("RightDigitMD") 
     private String rightDigitMD;
    
    @JsonProperty("MeterSerialNumber") 
     private String meterSerialNumber;
    
    @JsonProperty("MeterConfigType") 
     private String meterConfigType;
    
    @JsonProperty("ManufacturerCode") 
     private String manufacturerCode;
    
    @JsonProperty("BadgeNumber") 
     private String badgeNumber;
    
    @JsonProperty("PreviousReadKWH") 
     private String previousReadKWH;
    
    @JsonProperty("PreviousReadDateTime") 
     private String previousReadDateTime;
    
    @JsonProperty("ErrCode") 
     private String errCode;
    
    @JsonProperty("ErrDesc") 
     private String errDesc;

    public void setMeterStatus(String meterStatus) {
        this.meterStatus = meterStatus;
    }

    public String getMeterStatus() {
        return meterStatus;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setPurposeOfSupply(String purposeOfSupply) {
        this.purposeOfSupply = purposeOfSupply;
    }

    public String getPurposeOfSupply() {
        return purposeOfSupply;
    }

    public void setLeftDigit(String leftDigit) {
        this.leftDigit = leftDigit;
    }

    public String getLeftDigit() {
        return leftDigit;
    }

    public void setRightDigit(String rightDigit) {
        this.rightDigit = rightDigit;
    }

    public String getRightDigit() {
        return rightDigit;
    }

    public void setLeftDigitMD(String leftDigitMD) {
        this.leftDigitMD = leftDigitMD;
    }

    public String getLeftDigitMD() {
        return leftDigitMD;
    }

    public void setRightDigitMD(String rightDigitMD) {
        this.rightDigitMD = rightDigitMD;
    }

    public String getRightDigitMD() {
        return rightDigitMD;
    }

    public void setMeterSerialNumber(String meterSerialNumber) {
        this.meterSerialNumber = meterSerialNumber;
    }

    public String getMeterSerialNumber() {
        return meterSerialNumber;
    }

    public void setMeterConfigType(String meterConfigType) {
        this.meterConfigType = meterConfigType;
    }

    public String getMeterConfigType() {
        return meterConfigType;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public void setPreviousReadKWH(String previousReadKWH) {
        this.previousReadKWH = previousReadKWH;
    }

    public String getPreviousReadKWH() {
        return previousReadKWH;
    }

    public void setPreviousReadDateTime(String previousReadDateTime) {
        this.previousReadDateTime = previousReadDateTime;
    }

    public String getPreviousReadDateTime() {
        return previousReadDateTime;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    public String getErrDesc() {
        return errDesc;
    }
}
