package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetOTPResponse implements Serializable{
    
    private static final long serialVersionUID=11016872L;     
 
   @JsonProperty("MobileNo")
    private String MobileNo;
   
    @JsonProperty("Email")
    private String Email;
    
    @JsonProperty("ResCode")
    private String ResCode;
    
    @JsonProperty("ResMsg")
    private String ResMsg;
    
    @JsonProperty("OTP")
    private String otp;


    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setMobileNo(String MobileNo) {
        this.MobileNo = MobileNo;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setResCode(String ResCode) {
        this.ResCode = ResCode;
    }

    public String getResCode() {
        return ResCode;
    }

    public void setResMsg(String ResMsg) {
        this.ResMsg = ResMsg;
    }

    public String getResMsg() {
        return ResMsg;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getEmail() {
        return Email;
    }
}
