package com.xx.uppcl.pojo;

import java.io.Serializable;

import java.util.Date;

public class LoginDetails implements Serializable{
        public LoginDetails() {
            super();
        }
        private static final long serialVersionUID=1L; 
        private String loggedInUserName;
        private Date lastLoggedInDate;
        private boolean authenticationSuccessful;
        private String mobileNo;
        private String email;
        private boolean validAccount;
        private String securityQuestion;
        private String securityAnswer;
        private String loggedInUserDiscom;
        
        private Date changePasswordLastDate;
        private Date registeredLastDate;
        private String profilePicName;
        private String profilePicContent;


    public void setLoggedInUserDiscom(String loggedInUserDiscom) {
        this.loggedInUserDiscom = loggedInUserDiscom;
    }

    public String getLoggedInUserDiscom() {
        return loggedInUserDiscom;
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

    public void setLoggedInUserName(String loggedInUserName) {
            this.loggedInUserName = loggedInUserName;
        }

        public String getLoggedInUserName() {
            return loggedInUserName;
        }

        public void setLastLoggedInDate(Date lastLoggedInDate) {
            this.lastLoggedInDate = lastLoggedInDate;
        }

        public Date getLastLoggedInDate() {
            return lastLoggedInDate;
        }

        public void setAuthenticationSuccessful(boolean authenticationSuccessful) {
            this.authenticationSuccessful = authenticationSuccessful;
        }

        public boolean getAuthenticationSuccessful() {
            return authenticationSuccessful;
        }

    public void setValidAccount(boolean validAccount) {
        this.validAccount = validAccount;
    }

    public boolean getValidAccount() {
        return validAccount;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setChangePasswordLastDate(Date changePasswordLastDate) {
        this.changePasswordLastDate = changePasswordLastDate;
    }

    public Date getChangePasswordLastDate() {
        return changePasswordLastDate;
    }

    public void setRegisteredLastDate(Date registeredLastDate) {
        this.registeredLastDate = registeredLastDate;
    }

    public Date getRegisteredLastDate() {
        return registeredLastDate;
    }

    public void setProfilePicName(String profilePicName) {
        this.profilePicName = profilePicName;
    }

    public String getProfilePicName() {
        return profilePicName;
    }

    public void setProfilePicContent(String profilePicContent) {
        this.profilePicContent = profilePicContent;
    }

    public String getProfilePicContent() {
        return profilePicContent;
    }
}
