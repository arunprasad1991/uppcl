package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ValidateBillRequest implements Serializable{
    
    private static final long serialVersionUID=1016872L;     
 
       
    @JsonProperty("KNo")
    private String kno;
    @JsonProperty("BillNumber")
    private String billNo;
    @JsonProperty("Duration")
    private String duration;
    @JsonProperty("SBMBillFlag")
    private String sbmBillFlag;


    public void setKno(String kno) {
        this.kno = kno;
    }

    public String getKno() {
        return kno;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setSbmBillFlag(String sbmBillFlag) {
        this.sbmBillFlag = sbmBillFlag;
    }

    public String getSbmBillFlag() {
        return sbmBillFlag;
    }
}
