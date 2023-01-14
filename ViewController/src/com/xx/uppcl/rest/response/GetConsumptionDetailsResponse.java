package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.xx.uppcl.rest.response.Billing.BillDetails;

import com.xx.uppcl.rest.response.billconsumption.ConsumptionDetails;

import java.io.Serializable;

import java.util.ArrayList;

public class GetConsumptionDetailsResponse implements Serializable{
    private static final long serialVersionUID=1068972L;
    public GetConsumptionDetailsResponse() {
        super();
    }
 
    @JsonProperty("ConsumptionDetails") 
    private ArrayList<ConsumptionDetails> consumptionDetails;
    
    @JsonProperty("ResCd")
    private String ResCode;
    
    @JsonProperty("ResMsg")
    private String ResMsg;

    public void setConsumptionDetails(ArrayList<ConsumptionDetails> consumptionDetails) {
        this.consumptionDetails = consumptionDetails;
    }

    public ArrayList<ConsumptionDetails> getConsumptionDetails() {
        return consumptionDetails;
    }

    public void setResCode(String ResCode) {
        this.ResCode = ResCode;
    }

    public String getResCode() {
        return ResCode;
    }

    public void setResMsg(String ResMsg) {
        this.ResMsg = ResMsg;
    }

    public String getResMsg() {
        return ResMsg;
    }
}
