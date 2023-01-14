package com.xx.uppcl.pojo;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequestPojo implements Serializable{
    @SuppressWarnings("compatibility:-1988451911825417797")
    private static final long serialVersionUID = 1L;

    public ServiceRequestPojo() {
        super();
    }
    
    //name correction fields
    private String nameType;
    private String correctedName;
    
    // common to all
    private List<AttachmentPojo> attachmentDetails = new ArrayList<AttachmentPojo>();
    private String reasonOfChange;
    
    //address correction fields
    private String houseNo;
    private String city;
    private String buildingName;
    private String district;
    private String areaName;
    private String pin;
    
    //bill correction fields
    private String disputedBillNo;
    private String meterReadKWh;
    private String meterReadKVAh;
    private String demand;
    
    //meter complaint request fields
    private String meterComplaintType;
    private String meterSerialNumber;
    
    //connection transfer fields
    private String applicantName;
    private String applicantFathName;
    
    //category change request fields
    private String categoryType;
    
    //service req status fields
    private String caseId;
    private String creationDate;
    private String desc;
    private String reqStatus;
    private String srcomments;
    private String billId;
    private boolean checkBillId;

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getNameType() {
        return nameType;
    }

    public void setCorrectedName(String correctedName) {
        this.correctedName = correctedName;
    }

    public String getCorrectedName() {
        return correctedName;
    }

    public void setAttachmentDetails(List<AttachmentPojo> attachmentDetails) {
        this.attachmentDetails = attachmentDetails;
    }

    public List<AttachmentPojo> getAttachmentDetails() {
        return attachmentDetails;
    }

    public void setReasonOfChange(String reasonOfChange) {
        this.reasonOfChange = reasonOfChange;
    }

    public String getReasonOfChange() {
        return reasonOfChange;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public void setDisputedBillNo(String disputedBillNo) {
        this.disputedBillNo = disputedBillNo;
    }

    public String getDisputedBillNo() {
        return disputedBillNo;
    }

    public void setMeterReadKWh(String meterReadKWh) {
        this.meterReadKWh = meterReadKWh;
    }

    public String getMeterReadKWh() {
        return meterReadKWh;
    }

    public void setMeterReadKVAh(String meterReadKVAh) {
        this.meterReadKVAh = meterReadKVAh;
    }

    public String getMeterReadKVAh() {
        return meterReadKVAh;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getDemand() {
        return demand;
    }

    public void setMeterSerialNumber(String meterSerialNumber) {
        this.meterSerialNumber = meterSerialNumber;
    }

    public String getMeterSerialNumber() {
        return meterSerialNumber;
    }

    public void setMeterComplaintType(String meterComplaintType) {
        this.meterComplaintType = meterComplaintType;
    }

    public String getMeterComplaintType() {
        return meterComplaintType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantFathName(String applicantFathName) {
        this.applicantFathName = applicantFathName;
    }

    public String getApplicantFathName() {
        return applicantFathName;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getReqStatus() {
        return reqStatus;
    }

    public void setSrcomments(String srcomments) {
        this.srcomments = srcomments;
    }

    public String getSrcomments() {
        return srcomments;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillId() {
        return billId;
    }

    public void setCheckBillId(boolean checkBillId) {
        this.checkBillId = checkBillId;
    }

    public boolean isCheckBillId() {
        return checkBillId;
    }
}
