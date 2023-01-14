package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OnlineLoadExtChargesResponse implements Serializable{
    @SuppressWarnings("compatibility:-3720180796108966664")
    private static final long serialVersionUID = 1L;

    @JsonProperty("ResCode")
    private String resCode;
    
    @JsonProperty("ResMsg")
    private String resMsg;
    
    @JsonProperty("SgstFee")
    private String sgstFee;
    
    @JsonProperty("SecurityFee")
    private String securityFee;
    
    @JsonProperty("SysLoadingChrgs")
    private String sysLoadingChrgs;
    
    @JsonProperty("CgstFee")
    private String cgstFee;
    
    @JsonProperty("ProcessingFee")
    private String processingFee;
    
    @JsonProperty("TotalChrg")
    private String totalChrg;

    

    public void setSgstFee(String sgstFee) {
        this.sgstFee = sgstFee;
    }

    public String getSgstFee() {
        return sgstFee;
    }

    public void setSecurityFee(String securityFee) {
        this.securityFee = securityFee;
    }

    public String getSecurityFee() {
        return securityFee;
    }

    public void setSysLoadingChrgs(String sysLoadingChrgs) {
        this.sysLoadingChrgs = sysLoadingChrgs;
    }

    public String getSysLoadingChrgs() {
        return sysLoadingChrgs;
    }

    public void setCgstFee(String cgstFee) {
        this.cgstFee = cgstFee;
    }

    public String getCgstFee() {
        return cgstFee;
    }

    public void setProcessingFee(String processingFee) {
        this.processingFee = processingFee;
    }

    public String getProcessingFee() {
        return processingFee;
    }

    public void setTotalChrg(String totalChrg) {
        this.totalChrg = totalChrg;
    }

    public String getTotalChrg() {
        return totalChrg;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getResMsg() {
        return resMsg;
    }
}
