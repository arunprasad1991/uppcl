package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import java.util.List;

public class GetNetMeterDetailsResponse implements Serializable{
    @SuppressWarnings("compatibility:-3249019397049355883")
    private static final long serialVersionUID = 1L;

    @JsonProperty("badgeNbr") 
     private String badgeNbr;
    
    @JsonProperty("serialNbr") 
     private String serialNbr;
    
    @JsonProperty("mtrConfigType") 
     private String mtrConfigType;
    
    @JsonProperty("meterConfigId") 
     private String meterConfigId;
    
    @JsonProperty("purposeOfSupply") 
     private String purposeOfSupply;
    
    @JsonProperty("category") 
     private String category;
    
    @JsonProperty("meterStatusInfo") 
     private String meterStatusInfo;
    
    @JsonProperty("maufacturingType") 
     private String maufacturingType;
    
    @JsonProperty("ResCd") 
     private String ResCd;
    
    @JsonProperty("PreviousReads") 
     private List<PreviousReadingsPOJO> previousReads;


    public void setBadgeNbr(String badgeNbr) {
        this.badgeNbr = badgeNbr;
    }

    public String getBadgeNbr() {
        return badgeNbr;
    }

    public void setSerialNbr(String serialNbr) {
        this.serialNbr = serialNbr;
    }

    public String getSerialNbr() {
        return serialNbr;
    }

    public void setMtrConfigType(String mtrConfigType) {
        this.mtrConfigType = mtrConfigType;
    }

    public String getMtrConfigType() {
        return mtrConfigType;
    }

    public void setMeterConfigId(String meterConfigId) {
        this.meterConfigId = meterConfigId;
    }

    public String getMeterConfigId() {
        return meterConfigId;
    }

    public void setPurposeOfSupply(String purposeOfSupply) {
        this.purposeOfSupply = purposeOfSupply;
    }

    public String getPurposeOfSupply() {
        return purposeOfSupply;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setMeterStatusInfo(String meterStatusInfo) {
        this.meterStatusInfo = meterStatusInfo;
    }

    public String getMeterStatusInfo() {
        return meterStatusInfo;
    }

    public void setMaufacturingType(String maufacturingType) {
        this.maufacturingType = maufacturingType;
    }

    public String getMaufacturingType() {
        return maufacturingType;
    }

    public void setResCd(String ResCd) {
        this.ResCd = ResCd;
    }

    public String getResCd() {
        return ResCd;
    }

    public void setPreviousReads(List<PreviousReadingsPOJO> previousReads) {
        this.previousReads = previousReads;
    }

    public List<PreviousReadingsPOJO> getPreviousReads() {
        return previousReads;
    }
}
