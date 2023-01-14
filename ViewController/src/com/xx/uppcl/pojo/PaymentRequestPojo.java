package com.xx.uppcl.pojo;

import java.io.Serializable;

public class PaymentRequestPojo implements Serializable{
    @SuppressWarnings("compatibility:7784633855069270671")
    private static final long serialVersionUID = 1L;

    public PaymentRequestPojo() {
        super();
    }
    
    private String accountNo;
    private String name;
    private String netPayableAmount;
    private String trackId;
    private String serviceRequestType;
    private String comments;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;
    private String charges1;
    private String charges2;
    private String requestDate;

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNetPayableAmount(String netPayableAmount) {
        this.netPayableAmount = netPayableAmount;
    }

    public String getNetPayableAmount() {
        return netPayableAmount;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setServiceRequestType(String serviceRequestType) {
        this.serviceRequestType = serviceRequestType;
    }

    public String getServiceRequestType() {
        return serviceRequestType;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam5(String param5) {
        this.param5 = param5;
    }

    public String getParam5() {
        return param5;
    }

    public void setCharges1(String charges1) {
        this.charges1 = charges1;
    }

    public String getCharges1() {
        return charges1;
    }

    public void setCharges2(String charges2) {
        this.charges2 = charges2;
    }

    public String getCharges2() {
        return charges2;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestDate() {
        return requestDate;
    }
}
