package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ValidateBillResponse implements Serializable{
    
    private static final long serialVersionUID=10316872L;     
 
    
    @JsonProperty("ResCd")
    private String resCd;
    @JsonProperty("ResMsg")
    private String resMsg;
    @JsonProperty("BillValidationStatus")
    private String billValidationStatus;


    public void setResCd(String resCd) {
        this.resCd = resCd;
    }

    public String getResCd() {
        return resCd;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setBillValidationStatus(String billValidationStatus) {
        this.billValidationStatus = billValidationStatus;
    }

    public String getBillValidationStatus() {
        return billValidationStatus;
    }
}
