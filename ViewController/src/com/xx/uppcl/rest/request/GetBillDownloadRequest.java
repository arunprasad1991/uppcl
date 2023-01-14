package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetBillDownloadRequest implements Serializable{
    @SuppressWarnings("compatibility:2147113227516304208")
    private static final long serialVersionUID = 1L;

    @JsonProperty("BillId")
    private String billId;
    
    @JsonProperty("Flag")
    private String flag;

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillId() {
        return billId;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
