package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetMeterDetailsRequest implements Serializable{
    @SuppressWarnings("compatibility:6065548100161452347")
    private static final long serialVersionUID = 1L;

    @JsonProperty("AccountNumber") 
     private String accountNumber;

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
