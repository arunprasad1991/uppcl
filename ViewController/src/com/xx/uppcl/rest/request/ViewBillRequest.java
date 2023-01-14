package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ViewBillRequest implements Serializable{
    
    private static final long serialVersionUID=145554522L; 
    public ViewBillRequest() {
        super();
    }
    @JsonProperty("FileFormat")
    private String fileFormat;
    @JsonProperty("ReportName")
    private String reportName;
    @JsonProperty("FromBillId")
    private String fromBillId;
    @JsonProperty("ToBillId")
    private String toBillId;


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

    public void setFromBillId(String fromBillId) {
        this.fromBillId = fromBillId;
    }

    public String getFromBillId() {
        return fromBillId;
    }

    public void setToBillId(String toBillId) {
        this.toBillId = toBillId;
    }

    public String getToBillId() {
        return toBillId;
    }

}
