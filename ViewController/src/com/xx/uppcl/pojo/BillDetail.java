package com.xx.uppcl.pojo;

import java.io.Serializable;

public class BillDetail implements Serializable{
        private static final long serialVersionUID=1843725674L;
    public BillDetail() {
        super();
    }
    
    private String billNo;
    private String billDueDate;
    private String billMonthYear;
    private String billAmount;
    private String billIssuedDate;
    private String paymentDate;
    private String paymentMade;
    private String totalPaymentDue;


    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillDueDate(String billDueDate) {
        this.billDueDate = billDueDate;
    }

    public String getBillDueDate() {
        return billDueDate;
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

    public void setTotalPaymentDue(String totalPaymentDue) {
        this.totalPaymentDue = totalPaymentDue;
    }

    public String getTotalPaymentDue() {
        return totalPaymentDue;
    }
}
