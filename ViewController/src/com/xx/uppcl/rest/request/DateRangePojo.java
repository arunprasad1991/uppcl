package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class DateRangePojo implements Serializable{
    @SuppressWarnings("compatibility:-3113664991055640556")
    private static final long serialVersionUID = 1L;

    public DateRangePojo() {
        super();
    }
    
    @JsonProperty("FromDate") 
    public String fromDate;
    
    @JsonProperty("ToDate") 
    public String toDate;

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
}
