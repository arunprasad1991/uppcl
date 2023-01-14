package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.xx.uppcl.rest.response.Billing.BillDetails;

import java.io.Serializable;

import java.util.ArrayList;

public class GetBillingDetailsResponse implements Serializable{
    private static final long serialVersionUID=10648972L;
    
    public GetBillingDetailsResponse() {
        super();
    }
    @JsonProperty("BillDetails") 
    private ArrayList<BillDetails> billDetails;

    @JsonProperty("ResCd")
    private String ResCode;
    
    @JsonProperty("ResMsg")
    private String ResMsg;
    
    public void setBillDetails(ArrayList<BillDetails> billDetails) {
        this.billDetails = billDetails;
    }

    public ArrayList<BillDetails> getBillDetails() {
        return billDetails;
    }
}
