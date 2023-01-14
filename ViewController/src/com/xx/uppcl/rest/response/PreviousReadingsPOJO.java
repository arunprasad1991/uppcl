package com.xx.uppcl.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PreviousReadingsPOJO implements Serializable{
    @SuppressWarnings("compatibility:6712214939030497915")
    private static final long serialVersionUID = 1L;

    @JsonProperty("seq")
    private String seq;
    
    @JsonProperty("uomCD")
    private String uomCD;
    
    @JsonProperty("fullScale")
    private String fullScale;
    
    @JsonProperty("digitsLeft")
    private String digitsLeft;
    
    @JsonProperty("digitsRight")
    private String digitsRight;
    
    @JsonProperty("PrevReading")
    private String prevReading;
    
    @JsonProperty("PrevReadDate")
    private String prevReadDate;

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getSeq() {
        return seq;
    }

    public void setUomCD(String uomCD) {
        this.uomCD = uomCD;
    }

    public String getUomCD() {
        return uomCD;
    }

    public void setFullScale(String fullScale) {
        this.fullScale = fullScale;
    }

    public String getFullScale() {
        return fullScale;
    }

    public void setDigitsLeft(String digitsLeft) {
        this.digitsLeft = digitsLeft;
    }

    public String getDigitsLeft() {
        return digitsLeft;
    }

    public void setDigitsRight(String digitsRight) {
        this.digitsRight = digitsRight;
    }

    public String getDigitsRight() {
        return digitsRight;
    }

    public void setPrevReading(String prevReading) {
        this.prevReading = prevReading;
    }

    public String getPrevReading() {
        return prevReading;
    }

    public void setPrevReadDate(String prevReadDate) {
        this.prevReadDate = prevReadDate;
    }

    public String getPrevReadDate() {
        return prevReadDate;
    }
}
