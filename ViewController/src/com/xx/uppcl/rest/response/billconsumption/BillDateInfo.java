package com.xx.uppcl.rest.response.billconsumption;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BillDateInfo implements Serializable{
    @SuppressWarnings("compatibility:1157887689108488314")
    private static final long serialVersionUID = 1L;

    public BillDateInfo() {
        super();
    }
    
    @JsonProperty("FromDate") 
    private String fromDate;
    
    @JsonProperty("ToDate") 
    private String toDate;
    
    @JsonProperty("BillMonth") 
    private String billMonth;

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public String getBillMonth() {
        return billMonth;
    }
}
