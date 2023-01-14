package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UpdateConsumerDetailsRequest implements Serializable{
    private static final long serialVersionUID=1764068972L;
    public UpdateConsumerDetailsRequest() {
        super();
    }
    @JsonProperty("KNumber")
    private String kno;
    @JsonProperty("MobileNumber")
    private String mobileNo;
    @JsonProperty("EmailAddress")
    private String email;
    @JsonProperty("PersonID")
    private String personId;


    public void setKno(String kno) {
        this.kno = kno;
    }

    public String getKno() {
        return kno;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }
}
