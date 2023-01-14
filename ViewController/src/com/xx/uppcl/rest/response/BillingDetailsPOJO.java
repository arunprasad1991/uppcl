package com.xx.uppcl.rest.response;

public class BillingDetailsPOJO {
    public BillingDetailsPOJO() {
        super();
    }
    private String BillNo;
    private String BillDueDate;
    private String BillMonthYear;
    private String BillAmount;
    private String BillIssuedDate;
    private String PaymentDate;
    private String PaymentMade;

    public void setBillNo(String BillNo) {
        this.BillNo = BillNo;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillDueDate(String BillDueDate) {
        this.BillDueDate = BillDueDate;
    }

    public String getBillDueDate() {
        return BillDueDate;
    }

    public void setBillMonthYear(String BillMonthYear) {
        this.BillMonthYear = BillMonthYear;
    }

    public String getBillMonthYear() {
        return BillMonthYear;
    }

    public void setBillAmount(String BillAmount) {
        this.BillAmount = BillAmount;
    }

    public String getBillAmount() {
        return BillAmount;
    }

    public void setBillIssuedDate(String BillIssuedDate) {
        this.BillIssuedDate = BillIssuedDate;
    }

    public String getBillIssuedDate() {
        return BillIssuedDate;
    }

    public void setPaymentDate(String PaymentDate) {
        this.PaymentDate = PaymentDate;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentMade(String PaymentMade) {
        this.PaymentMade = PaymentMade;
    }

    public String getPaymentMade() {
        return PaymentMade;
    }
}
