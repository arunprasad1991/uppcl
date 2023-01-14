package com.xx.uppcl.rest.response.Billing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BillDetails implements Serializable{
    private static final long serialVersionUID=14068972L;
    public BillDetails() {
        super();
    }
    
    @JsonProperty("BillInfo") 
    private BillInfo billInfo;
    @JsonProperty("BillMonthYear") 
    private String billMonthYear;
    @JsonProperty("BillAmount") 
    private String billAmount;
    @JsonProperty("BillIssuedDate") 
    private String billIssuedDate;
    @JsonProperty("PaymentDate")
    private String paymentDate;
    @JsonProperty("PaymentMade")
    private String paymentMade;

    public void setBillInfo(BillInfo billInfo) {
        this.billInfo = billInfo;
    }

    public BillInfo getBillInfo() {
        return billInfo;
    }

    public void setBillMonthYear(String billMonthYear) {
        this.billMonthYear = billMonthYear;
    }

    public String getBillMonthYear() {
        return billMonthYear;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillIssuedDate(String billIssuedDate) {
        this.billIssuedDate = billIssuedDate;
    }

    public String getBillIssuedDate() {
        return billIssuedDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentMade(String paymentMade) {
        this.paymentMade = paymentMade;
    }

    public String getPaymentMade() {
        return paymentMade;
    }
}
