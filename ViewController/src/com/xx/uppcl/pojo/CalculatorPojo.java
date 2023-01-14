package com.xx.uppcl.pojo;

import java.io.Serializable;


public class CalculatorPojo implements Serializable{
    @SuppressWarnings("compatibility:-5582529021913103010")
    private static final long serialVersionUID = 1L;

    public CalculatorPojo() {
        super();
    }
    
    private String appliances;
    private String watt;
    private String noOfEquip;
    private String hoursUse;
    private String daysUse;
    private String totalLoad;
    private String totalConsumption;


    public CalculatorPojo(String appliances, String watt, String noOfEquip, String hoursUse, String daysUse,
                          String totalLoad, String totalConsumption) {
        this.appliances = appliances;
        this.watt = watt;
        this.noOfEquip = noOfEquip;
        this.hoursUse = hoursUse;
        this.daysUse = daysUse;
        this.totalLoad = totalLoad;
        this.totalConsumption = totalConsumption;
    }

    public void setAppliances(String appliances) {
        this.appliances = appliances;
    }

    public String getAppliances() {
        return appliances;
    }

    public void setWatt(String watt) {
        this.watt = watt;
    }

    public String getWatt() {
        return watt;
    }

    public void setNoOfEquip(String noOfEquip) {
        this.noOfEquip = noOfEquip;
    }

    public String getNoOfEquip() {
        return noOfEquip;
    }

    public void setHoursUse(String hoursUse) {
        this.hoursUse = hoursUse;
    }

    public String getHoursUse() {
        return hoursUse;
    }

    public void setDaysUse(String daysUse) {
        this.daysUse = daysUse;
    }

    public String getDaysUse() {
        return daysUse;
    }

    public void setTotalLoad(String totalLoad) {
        this.totalLoad = totalLoad;
    }

    public String getTotalLoad() {
        return totalLoad;
    }

    public void setTotalConsumption(String totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public String getTotalConsumption() {
        return totalConsumption;
    }
}
