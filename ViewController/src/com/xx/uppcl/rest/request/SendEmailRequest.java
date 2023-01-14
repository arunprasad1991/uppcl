package com.xx.uppcl.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SendEmailRequest implements Serializable{
    
    private static final long serialVersionUID=101687784522L;     
     public SendEmailRequest() {
        super();
    }
    @JsonProperty("From")
    private String from;
    @JsonProperty("To")
    private String to;
    @JsonProperty("Subject")
    private String subject;
    @JsonProperty("ContentType")
    private String contentType;
    @JsonProperty("Content")
    private String content;


    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
