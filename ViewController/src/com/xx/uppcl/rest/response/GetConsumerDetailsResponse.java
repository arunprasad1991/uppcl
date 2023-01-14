package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.io.Serializable;

public class GetConsumerDetailsResponse implements Serializable{
    private static final long serialVersionUID=1068972L; 
    
    @JsonProperty("KNumber")
    private String kno;
    @JsonProperty("ConsumerName")
    private String consumerName;
    @JsonProperty("HusbandOrFatherName")
    private String husbandOrFatherName;
    @JsonProperty("MotherName")
    private String motherName;
    @JsonProperty("SecurityDeposit")
    private String securityDeposit;
    @JsonProperty("AccountInfo")
    private String accountInfo;
    @JsonProperty("SanctionedLoadInBHP")
    private String sanctionedLoadInBHP;
    @JsonProperty("SanctionedLoadInKVA")
    private String sanctionedLoadInKVA;
    @JsonProperty("SanctionedLoadInKW")
    private String sanctionedLoadInKW;
    @JsonProperty("MobileNumber")
    private String mobileNumber;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;
    @JsonProperty("EmailAddress")
    private String emailAddress;
    @JsonProperty("DateOfBirth")
    private String dateOfBirth;  
    @JsonProperty("PremiseAddress")
    private AddressPOJO premiseAddress;
    @JsonProperty("BillingAddress")
    private AddressPOJO billingAddress;
    @JsonProperty("Category")
    private String category;
    @JsonProperty("SubDivision")
    private String subDivision;   
    @JsonProperty("Division")
    private String division;
    @JsonProperty("Discom")
    private String discom;
    @JsonProperty("ConnectionStatus")
    private String connectionStatus;
    @JsonProperty("OnlinebillingStatus")
    private String onlinebillingStatus;
    @JsonProperty("ConsumerType")
    private String consumerType;
    @JsonProperty("TypeOfPlace")
    private String typeOfPlace;
    @JsonProperty("TypeOfConnection")
    private String typeOfConnection;
    @JsonProperty("TypeOfPhase")
    private String typeOfPhase;
    @JsonProperty("PurposeOfSupply")
    private String purposeOfSupply;  
    @JsonProperty("TypeOfConnectionPoint")
    private String typeOfConnectionPoint;
    @JsonProperty("TypeOfMeter")
    private String typeOfMeter;
    @JsonProperty("PersonID")
    private String personID;
    @JsonProperty("CIN")
    private String cin;
    @JsonProperty("ChequeDshnrCount")
    private String chequeDshnrCount;
    @JsonProperty("ErrCode")
    private String errorCode;
    @JsonProperty("ErrDesc")
    private String errorDesc;   

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setHusbandOrFatherName(String husbandOrFatherName) {
        this.husbandOrFatherName = husbandOrFatherName;
    }

    public String getHusbandOrFatherName() {
        return husbandOrFatherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setSecurityDeposit(String securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public String getSecurityDeposit() {
        return securityDeposit;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setSanctionedLoadInBHP(String sanctionedLoadInBHP) {
        this.sanctionedLoadInBHP = sanctionedLoadInBHP;
    }

    public String getSanctionedLoadInBHP() {
        return sanctionedLoadInBHP;
    }

    public void setSanctionedLoadInKVA(String sanctionedLoadInKVA) {
        this.sanctionedLoadInKVA = sanctionedLoadInKVA;
    }

    public String getSanctionedLoadInKVA() {
        return sanctionedLoadInKVA;
    }

    public void setSanctionedLoadInKW(String sanctionedLoadInKW) {
        this.sanctionedLoadInKW = sanctionedLoadInKW;
    }

    public String getSanctionedLoadInKW() {
        return sanctionedLoadInKW;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setPremiseAddress(AddressPOJO premiseAddress) {
        this.premiseAddress = premiseAddress;
    }

    public AddressPOJO getPremiseAddress() {
        return premiseAddress;
    }

    public void setBillingAddress(AddressPOJO billingAddress) {
        this.billingAddress = billingAddress;
    }

    public AddressPOJO getBillingAddress() {
        return billingAddress;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDivision() {
        return division;
    }

    public void setDiscom(String discom) {
        this.discom = discom;
    }

    public String getDiscom() {
        return discom;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setOnlinebillingStatus(String onlinebillingStatus) {
        this.onlinebillingStatus = onlinebillingStatus;
    }

    public String getOnlinebillingStatus() {
        return onlinebillingStatus;
    }

    public void setConsumerType(String consumerType) {
        this.consumerType = consumerType;
    }

    public String getConsumerType() {
        return consumerType;
    }

    public void setTypeOfPlace(String typeOfPlace) {
        this.typeOfPlace = typeOfPlace;
    }

    public String getTypeOfPlace() {
        return typeOfPlace;
    }

    public void setTypeOfConnection(String typeOfConnection) {
        this.typeOfConnection = typeOfConnection;
    }

    public String getTypeOfConnection() {
        return typeOfConnection;
    }

    public void setTypeOfPhase(String typeOfPhase) {
        this.typeOfPhase = typeOfPhase;
    }

    public String getTypeOfPhase() {
        return typeOfPhase;
    }

    public void setPurposeOfSupply(String purposeOfSupply) {
        this.purposeOfSupply = purposeOfSupply;
    }

    public String getPurposeOfSupply() {
        return purposeOfSupply;
    }

    public void setTypeOfConnectionPoint(String typeOfConnectionPoint) {
        this.typeOfConnectionPoint = typeOfConnectionPoint;
    }

    public String getTypeOfConnectionPoint() {
        return typeOfConnectionPoint;
    }

    public void setTypeOfMeter(String typeOfMeter) {
        this.typeOfMeter = typeOfMeter;
    }

    public String getTypeOfMeter() {
        return typeOfMeter;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setChequeDshnrCount(String chequeDshnrCount) {
        this.chequeDshnrCount = chequeDshnrCount;
    }

    public String getChequeDshnrCount() {
        return chequeDshnrCount;
    }

    public void setKno(String kno) {
        this.kno = kno;
    }

    public String getKno() {
        return kno;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCin() {
        return cin;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getErrorDesc() {
        return errorDesc;
    }
}
