package com.xx.uppcl.rest.response.billconsumption;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MeterReading implements Serializable{
    @SuppressWarnings("compatibility:-3136664951997277252")
    private static final long serialVersionUID = 1L;

    public MeterReading() {
        super();
    }
    
    @JsonProperty("StartReading") 
    private String startReading;
    
    @JsonProperty("EndReading") 
    private String endReading;
    
    @JsonProperty("MeasurementUnit") 
    private String measurementUnit;
    
    @JsonProperty("Units") 
    private String units;
    
    @JsonProperty("TimeofUse") 
    private String timeofUse;

    public void setStartReading(String startReading) {
        this.startReading = startReading;
    }

    public String getStartReading() {
        return startReading;
    }

    public void setEndReading(String endReading) {
        this.endReading = endReading;
    }

    public String getEndReading() {
        return endReading;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getUnits() {
        return units;
    }

    public void setTimeofUse(String timeofUse) {
        this.timeofUse = timeofUse;
    }

    public String getTimeofUse() {
        return timeofUse;
    }
}
