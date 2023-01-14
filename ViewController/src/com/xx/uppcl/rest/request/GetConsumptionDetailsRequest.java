package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import oracle.security.idm.SearchParameters;

public class GetConsumptionDetailsRequest implements Serializable{
    @SuppressWarnings("compatibility:4984384357349782644")
    private static final long serialVersionUID = 1L;

    public GetConsumptionDetailsRequest() {
        super();
    }
    

    @JsonProperty("KNumber")
    private String knumber;
    
    @JsonProperty("SearchParameters") 
    public SearchParamPojo searchParameters;

    public void setKnumber(String knumber) {
        this.knumber = knumber;
    }

    public String getKnumber() {
        return knumber;
    }

    public void setSearchParameters(SearchParamPojo searchParameters) {
        this.searchParameters = searchParameters;
    }

    public SearchParamPojo getSearchParameters() {
        return searchParameters;
    }




}
