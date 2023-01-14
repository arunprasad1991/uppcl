package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class GetOTPRequest implements Serializable{
    
    private static final long serialVersionUID=10168722L;     
 
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Source")
    private String source;
    @JsonProperty("Mode")
    private String mode;
    @JsonProperty("MobileNo")
    private String mobileNo;


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

}
