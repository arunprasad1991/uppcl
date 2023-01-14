package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetBillingDetailsRequest implements Serializable{
    private static final long serialVersionUID=1068972L;
    public GetBillingDetailsRequest() {
        super();
    }
    
    DateRange DateRange = new DateRange();

    @JsonProperty("KNumber")
    private String knumber;
    
    @JsonProperty("SearchParameters") 
        public SearchParameters searchParameters;

    public void setKnumber(String knumber) {
        this.knumber = knumber;
    }

    public String getKnumber() {
        return knumber;
    }

    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    public SearchParameters getSearchParameters() {
        return searchParameters;
    }


    public class SearchParameters{
        @JsonProperty("DateRange") 
        public DateRange dateRange;

        public void setDateRange(GetBillingDetailsRequest.DateRange dateRange) {
            this.dateRange = dateRange;
        }

        public GetBillingDetailsRequest.DateRange getDateRange() {
            return dateRange;
        }

        public SearchParameters() {
            super();
        }
    } 
    
    public class DateRange{
        @JsonProperty("FromDate") 
        public String fromDate;
        @JsonProperty("ToDate") 
        public String toDate;
        
        public DateRange() {
            super();
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
    }

    
    
}
