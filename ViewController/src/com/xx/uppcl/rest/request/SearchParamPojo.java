package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SearchParamPojo implements Serializable{
    @SuppressWarnings("compatibility:4974350688055196351")
    private static final long serialVersionUID = 1L;

    public SearchParamPojo() {
        super();
    }
    
    @JsonProperty("DateRange") 
    public DateRangePojo dateRange;

    public void setDateRange(DateRangePojo dateRange) {
        this.dateRange = dateRange;
    }

    public DateRangePojo getDateRange() {
        return dateRange;
    }
}
