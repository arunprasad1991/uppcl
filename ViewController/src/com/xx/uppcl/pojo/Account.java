package com.xx.uppcl.pojo;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable{
        private static final long serialVersionUID=8334434L;
    public Account() {
        super();
    }
    private String accountNo;
    private String name;
    private String address;
    private String division;
    private String personID;
    private String email;
    private String mobileNo;
    private String connectionStatus;
    private String discom;
    private String subDivision;
    private String outStandingAmount;
    private String supplyType;
    private String currentLoad;
    private Float sancLoad;
    private Float securityAmount;
    private String meterNum;
    private String premiseAddr;
    List<BillDetail> billDetails;
    private String phoneNum;


    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setDiscom(String discom) {
        this.discom = discom;
    }

    public String getDiscom() {
        return discom;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDivision() {
        return division;
    }

    public void setOutStandingAmount(String outStandingAmount) {
        this.outStandingAmount = outStandingAmount;
    }

    public String getOutStandingAmount() {
        return outStandingAmount;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setCurrentLoad(String currentLoad) {
        this.currentLoad = currentLoad;
    }

    public String getCurrentLoad() {
        return currentLoad;
    }

    public void setSancLoad(Float sancLoad) {
        this.sancLoad = sancLoad;
    }

    public Float getSancLoad() {
        return sancLoad;
    }

    public void setSecurityAmount(Float securityAmount) {
        this.securityAmount = securityAmount;
    }

    public Float getSecurityAmount() {
        return securityAmount;
    }

    public void setMeterNum(String meterNum) {
        this.meterNum = meterNum;
    }

    public String getMeterNum() {
        return meterNum;
    }

    public void setPremiseAddr(String premiseAddr) {
        this.premiseAddr = premiseAddr;
    }

    public String getPremiseAddr() {
        return premiseAddr;
    }

    public void setBillDetails(List<BillDetail> billDetails) {
        this.billDetails = billDetails;
    }

    public List<BillDetail> getBillDetails() {
        return billDetails;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}
