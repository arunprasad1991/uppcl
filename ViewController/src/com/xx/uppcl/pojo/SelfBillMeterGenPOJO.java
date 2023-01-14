package com.xx.uppcl.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

public class SelfBillMeterGenPOJO implements Serializable{
    @SuppressWarnings("compatibility:4802805815660877119")
    private static final long serialVersionUID = 1L;

    private String accountNo;
    private String meterStatus;
    private String supplyType;
    private String purposeOfSupply;
    private String leftDigit;
    private String rightDigit;
    private String leftDigitMD;
    private String rightDigitMD;
    private String meterSerialNumber;
    private String meterConfigType;
    private String manufacturerCode;
    private String badgeNumber;
    private String previousReadKWH;
    private String previousReadDateTime;
    
    private String meterConfigId;
    private String category;
    private String maufacturingType;
    private String ResCd;
    private List<MeterReadingsPOJO> previousReads;
    
    private String cumulativeEnergyKWH;
    private String maximumDemandKW;
    private String cumulativeEnergyKVAH;
    private String maximumDemandKVA;
    private String cumulativeEnergyKWHE;
    private String maximumDemandKVE;
    private String wSSMeterReadDateTime;
    private String emailId;
    private String mobileNo;
    private String remarks;
    
    //user entered fields for bill meter
    private String userEntMeterReading;
    private String userEntDemand;
    
    //user entered fields for bill net meter
    private String userEntReadingKwh;
    private String userEntReadingKwhe;
    private String userEntReadingKvh;
    private String userEntReadingKvhe;
    private String userEntMaxKvaDemand;
    private String userEntMaxKwDemand;
    
    private String userEntComments;
    


    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

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

    public void setMeterConfigId(String meterConfigId) {
        this.meterConfigId = meterConfigId;
    }

    public String getMeterConfigId() {
        return meterConfigId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
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

    public void setPreviousReads(List<MeterReadingsPOJO> previousReads) {
        this.previousReads = previousReads;
    }

    public List<MeterReadingsPOJO> getPreviousReads() {
        return previousReads;
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

    public void setUserEntMeterReading(String userEntMeterReading) {
        this.userEntMeterReading = userEntMeterReading;
    }

    public String getUserEntMeterReading() {
        return userEntMeterReading;
    }

    public void setUserEntDemand(String userEntDemand) {
        this.userEntDemand = userEntDemand;
    }

    public String getUserEntDemand() {
        return userEntDemand;
    }

    public void setUserEntReadingKwh(String userEntReadingKwh) {
        this.userEntReadingKwh = userEntReadingKwh;
    }

    public String getUserEntReadingKwh() {
        return userEntReadingKwh;
    }

    public void setUserEntReadingKwhe(String userEntReadingKwhe) {
        this.userEntReadingKwhe = userEntReadingKwhe;
    }

    public String getUserEntReadingKwhe() {
        return userEntReadingKwhe;
    }

    public void setUserEntReadingKvh(String userEntReadingKvh) {
        this.userEntReadingKvh = userEntReadingKvh;
    }

    public String getUserEntReadingKvh() {
        return userEntReadingKvh;
    }

    public void setUserEntReadingKvhe(String userEntReadingKvhe) {
        this.userEntReadingKvhe = userEntReadingKvhe;
    }

    public String getUserEntReadingKvhe() {
        return userEntReadingKvhe;
    }

    public void setUserEntMaxKvaDemand(String userEntMaxKvaDemand) {
        this.userEntMaxKvaDemand = userEntMaxKvaDemand;
    }

    public String getUserEntMaxKvaDemand() {
        return userEntMaxKvaDemand;
    }

    public void setUserEntComments(String userEntComments) {
        this.userEntComments = userEntComments;
    }

    public String getUserEntComments() {
        return userEntComments;
    }

    public void setUserEntMaxKwDemand(String userEntMaxKwDemand) {
        this.userEntMaxKwDemand = userEntMaxKwDemand;
    }

    public String getUserEntMaxKwDemand() {
        return userEntMaxKwDemand;
    }
}
