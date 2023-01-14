package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UpdatePanDetailsRequest implements Serializable{
    @SuppressWarnings("compatibility:-2796590594445188077")
    private static final long serialVersionUID = 1L;

    public UpdatePanDetailsRequest() {
        super();
    }
    
    @JsonProperty("KNumber") 
     private String kNumber;
    
    @JsonProperty("PAN") 
     private String pan;
    
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

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPan() {
        return pan;
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
