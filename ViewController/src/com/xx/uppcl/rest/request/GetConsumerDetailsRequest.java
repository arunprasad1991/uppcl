package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GetConsumerDetailsRequest implements Serializable{
    private static final long serialVersionUID=1016872L; 
   @JsonProperty("KNumber") 
    private String kno;


    public void setKno(String kno) {
        this.kno = kno;
    }

    public String getKno() {
        return kno;
    }
}
