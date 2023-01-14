package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SubmitBillNetMeterGenResponse implements Serializable{
    @SuppressWarnings("compatibility:2269754205659357894")
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
