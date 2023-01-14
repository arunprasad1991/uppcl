package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ViewBillResponse implements Serializable{
    
    private static final long serialVersionUID=1922214522L;
    public ViewBillResponse() {
        super();
    }
    @JsonProperty("ReportContents")
    private String fileFormat;
    @JsonProperty("ReportContentType")
    private String reportName;
    @JsonProperty("ResCd")
    private String respCode;    
    @JsonProperty("ResMsg")
    private String respMsg;


    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportName() {
        return reportName;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getRespMsg() {
        return respMsg;
    }
}
