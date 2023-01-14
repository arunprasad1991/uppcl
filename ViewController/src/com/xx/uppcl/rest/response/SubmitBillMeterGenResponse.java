package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SubmitBillMeterGenResponse implements Serializable{
    @SuppressWarnings("compatibility:-1219636276640014068")
    private static final long serialVersionUID = 1L;

    @JsonProperty("Result") 
     private String result;
    
    @JsonProperty("ResultMessage") 
     private String resultMessage;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
