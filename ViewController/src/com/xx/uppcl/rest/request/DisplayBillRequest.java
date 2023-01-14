package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class DisplayBillRequest implements Serializable{
    
    private static final long serialVersionUID=166781522L;
    public DisplayBillRequest() {
        super();
    }
    
    @JsonProperty("Acctd")
    private String accountNo;


    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }
}
