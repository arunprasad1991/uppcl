package com.xx.uppcl.rest.response.billconsumption;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UnitBilled implements Serializable{
    @SuppressWarnings("compatibility:811335430629984870")
    private static final long serialVersionUID = 1L;

    public UnitBilled() {
        super();
    }
    
    @JsonProperty("UnitsBilled") 
    private String unitsBilled;
    
    @JsonProperty("MeasurementUnit") 
    private String measurementUnit;
    
    @JsonProperty("TimeofUse") 
    private String timeofUse;

    public void setUnitsBilled(String unitsBilled) {
        this.unitsBilled = unitsBilled;
    }

    public String getUnitsBilled() {
        return unitsBilled;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setTimeofUse(String timeofUse) {
        this.timeofUse = timeofUse;
    }

    public String getTimeofUse() {
        return timeofUse;
    }
}
