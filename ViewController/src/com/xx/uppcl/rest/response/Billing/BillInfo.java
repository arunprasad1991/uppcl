package com.xx.uppcl.rest.response.Billing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BillInfo implements Serializable{
    private static final long serialVersionUID=10268972L;
    public BillInfo() {
        super();
    }
    
    @JsonProperty("BillNo") 
    public String billNo;
    @JsonProperty("BillDueDate") 
    public String billDueDate;

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillDueDate(String billDueDate) {
        this.billDueDate = billDueDate;
    }

    public String getBillDueDate() {
        return billDueDate;
    }
}
