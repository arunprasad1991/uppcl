package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class VerifyOTPRequest implements Serializable{
    
    private static final long serialVersionUID=10166872L;     
 
    @JsonProperty("MobileNo")
    private String mobileNo;
    @JsonProperty("OTP")
    private String otp;
    @JsonProperty("VerifyMode")
    private String verifymode;


    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setVerifymode(String verifymode) {
        this.verifymode = verifymode;
    }

    public String getVerifymode() {
        return verifymode;
    }
}
