package com.xx.uppcl.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import java.util.Date;

public class MeterReadingsPOJO implements Serializable{
    @SuppressWarnings("compatibility:-6497084124093395111")
    private static final long serialVersionUID = 1L;

    private String seq;
    private String uomCD;
    private String fullScale;
    private String digitsLeft;
    private String digitsRight;
    private String prevReading;
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
