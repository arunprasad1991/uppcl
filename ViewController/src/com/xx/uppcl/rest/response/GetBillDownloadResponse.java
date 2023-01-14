package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetBillDownloadResponse implements Serializable{
    @SuppressWarnings("compatibility:-6907361137685354587")
    private static final long serialVersionUID = 1L;

    @JsonProperty("ResCode")
    private String resCode;
    
    @JsonProperty("ResMsg")
    private String resMsg;
    
    @JsonProperty("ReportContents")
    private String reportContent;
    
    @JsonProperty("ReportContentType")
    private String reportContentType ;
    

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

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContentType(String reportContentType) {
        this.reportContentType = reportContentType;
    }

    public String getReportContentType() {
        return reportContentType;
    }
}
