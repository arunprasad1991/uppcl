package com.xx.uppcl.rest.response.billconsumption;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.xx.uppcl.rest.response.Billing.BillInfo;

import java.io.Serializable;

public class ConsumptionDetails implements Serializable{
    @SuppressWarnings("compatibility:2581521069091440621")
    private static final long serialVersionUID = 1L;

    public ConsumptionDetails() {
        super();
    }
    
    @JsonProperty("BillDateInfo")
    private BillDateInfo billDateInfo;
    
    @JsonProperty("BillInfo")
    private BillInfo billInfo;
    
    @JsonProperty("MeterReading")
    private MeterReading meterReading;
    
    @JsonProperty("UnitBilled")
    private UnitBilled unitBilled;
    
    @JsonProperty("SPId")
    private String spId;
    
    @JsonProperty("BillAmount")
    private String billAmount;
    
    @JsonProperty("MeterReadingDate")
    private String meterReadingDate;

    public void setBillDateInfo(BillDateInfo billDateInfo) {
        this.billDateInfo = billDateInfo;
    }

    public BillDateInfo getBillDateInfo() {
        return billDateInfo;
    }

    public void setBillInfo(BillInfo billInfo) {
        this.billInfo = billInfo;
    }

    public BillInfo getBillInfo() {
        return billInfo;
    }

    public void setMeterReading(MeterReading meterReading) {
        this.meterReading = meterReading;
    }

    public MeterReading getMeterReading() {
        return meterReading;
    }

    public void setUnitBilled(UnitBilled unitBilled) {
        this.unitBilled = unitBilled;
    }

    public UnitBilled getUnitBilled() {
        return unitBilled;
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
