package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetPanDetailsRequest implements Serializable{
    @SuppressWarnings("compatibility:-4439855495904368021")
    private static final long serialVersionUID = 1L;

    @JsonProperty("KNumber") 
     private String kNumber;
    
    @JsonProperty("Source") 
     private String source;
    
    @JsonProperty("OpMode") 
     private String opMode;

    public void setKNumber(String kNumber) {
        this.kNumber = kNumber;
    }

    public String getKNumber() {
        return kNumber;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getOpMode() {
        return opMode;
    }
}
