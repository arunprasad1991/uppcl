package com.xx.uppcl.pojo;

import java.io.Serializable;

public class ConsumpDetailsPOJO implements Serializable{
    @SuppressWarnings("compatibility:-7656728932223031128")
    private static final long serialVersionUID = 1L;

    public ConsumpDetailsPOJO() {
        super();
    }
    
    private String billNum;
    private String billDueDate;
    
    private String fromDate;
    private String toDate;
    private String billMonth;
    
    private String meterReadsSartReading;
    
    private String meterReadEndReading;
    private String meterReadMeasurementUnit;
    private String meterReadUnits;
    private String meterReadTimeofUse;
    
    private String unitsBilled;
    private String unitBillMeasurementUnit;
    private String unitBillTimeofUse;
    
    private String spId;
    private String billAmount;
    private String meterReadingDate;


    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillDueDate(String billDueDate) {
        this.billDueDate = billDueDate;
    }

    public String getBillDueDate() {
        return billDueDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setMeterReadEndReading(String meterReadEndReading) {
        this.meterReadEndReading = meterReadEndReading;
    }

    public String getMeterReadEndReading() {
        return meterReadEndReading;
    }

    public void setMeterReadMeasurementUnit(String meterReadMeasurementUnit) {
        this.meterReadMeasurementUnit = meterReadMeasurementUnit;
    }

    public String getMeterReadMeasurementUnit() {
        return meterReadMeasurementUnit;
    }

    public void setMeterReadUnits(String meterReadUnits) {
        this.meterReadUnits = meterReadUnits;
    }

    public String getMeterReadUnits() {
        return meterReadUnits;
    }

    public void setMeterReadTimeofUse(String meterReadTimeofUse) {
        this.meterReadTimeofUse = meterReadTimeofUse;
    }

    public String getMeterReadTimeofUse() {
        return meterReadTimeofUse;
    }

    public void setUnitsBilled(String unitsBilled) {
        this.unitsBilled = unitsBilled;
    }

    public String getUnitsBilled() {
        return unitsBilled;
    }

    public void setUnitBillMeasurementUnit(String unitBillMeasurementUnit) {
        this.unitBillMeasurementUnit = unitBillMeasurementUnit;
    }

    public String getUnitBillMeasurementUnit() {
        return unitBillMeasurementUnit;
    }

    public void setUnitBillTimeofUse(String unitBillTimeofUse) {
        this.unitBillTimeofUse = unitBillTimeofUse;
    }

    public String getUnitBillTimeofUse() {
        return unitBillTimeofUse;
    }

    public void setMeterReadsSartReading(String meterReadsSartReading) {
        this.meterReadsSartReading = meterReadsSartReading;
    }

    public String getMeterReadsSartReading() {
        return meterReadsSartReading;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getSpId() {
        return spId;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setMeterReadingDate(String meterReadingDate) {
        this.meterReadingDate = meterReadingDate;
    }

    public String getMeterReadingDate() {
        return meterReadingDate;
    }
}
