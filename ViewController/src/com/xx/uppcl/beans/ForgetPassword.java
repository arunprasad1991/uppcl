package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.LoginDetails;
import com.xx.uppcl.rest.request.GetOTPRequest;
import com.xx.uppcl.rest.response.GetOTPResponse;
import com.xx.uppcl.rest.services.RestServices;

import com.xx.uppcl.services.DBServices;

import com.xx.uppcl.utils.Utils;

import java.io.Serializable;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.ADFContext;

public class ForgetPassword implements Serializable{
        private static final long serialVersionUID=1L;
        
        private String accountNo;
        private String otpSentMsg;
        private String enteredSecurityAnswer;
        private boolean otpSentFlag=false;
        private String securityQuestion;
        private String securityAnswer;
        private String enteredPassword;
        private String enteredOTP;
        private String validOTP;
        private String validateMsg;
        private boolean validateFlag=false;
        private String reEnteredPassword;
        private String resetMessage;
        private String otpValidateMsg;
        private String securityAnswerValidMsg;
        private String enteredPasswordValidMsg;
        private String reEnteredPasswordValidMsg;
    private List<SelectItem> discomList = new ArrayList<SelectItem>();
    private String discomValidationMessage;
    private String discomValue;
    
    Utils utils = new Utils();


    public void setDiscomList(List<SelectItem> discomList) {
        this.discomList = discomList;
    }

    public List<SelectItem> getDiscomList() {
        DBServices ds = new DBServices();
        if(discomList.size()==0){
            List<String> list = new ArrayList<String>();
            list = ds.getDiscomList();
            for(String value : list){
                discomList.add(new SelectItem(value));
            }
        }  
        return discomList;
    }

    public void setDiscomValidationMessage(String discomValidationMessage) {
        this.discomValidationMessage = discomValidationMessage;
    }

    public String getDiscomValidationMessage() {
        return discomValidationMessage;
    }

    public void setDiscomValue(String discomValue) {
        this.discomValue = discomValue;
    }

    public String getDiscomValue() {
        return discomValue;
    }

    public void setEnteredPasswordValidMsg(String enteredPasswordValidMsg) {
        this.enteredPasswordValidMsg = enteredPasswordValidMsg;
    }

    public String getEnteredPasswordValidMsg() {
        return enteredPasswordValidMsg;
    }

    public void setReEnteredPasswordValidMsg(String reEnteredPasswordValidMsg) {
        this.reEnteredPasswordValidMsg = reEnteredPasswordValidMsg;
    }

    public String getReEnteredPasswordValidMsg() {
        return reEnteredPasswordValidMsg;
    }

    public void setOtpValidateMsg(String otpValidateMsg) {
        this.otpValidateMsg = otpValidateMsg;
    }

    public String getOtpValidateMsg() {
        return otpValidateMsg;
    }

    public void setSecurityAnswerValidMsg(String securityAnswerValidMsg) {
        this.securityAnswerValidMsg = securityAnswerValidMsg;
    }

    public String getSecurityAnswerValidMsg() {
        return securityAnswerValidMsg;
    }

    public void setResetMessage(String resetMessage) {
        this.resetMessage = resetMessage;
    }

    public String getResetMessage() {
        return resetMessage;
    }

    public void setReEnteredPassword(String reEnteredPassword) {
        this.reEnteredPassword = reEnteredPassword;
    }

    public String getReEnteredPassword() {
        return reEnteredPassword;
    }

    public void setValidateFlag(boolean validateFlag) {
        this.validateFlag = validateFlag;
    }

    public boolean isValidateFlag() {
        return validateFlag;
    }

    public void setEnteredOTP(String enteredOTP) {
        this.enteredOTP = enteredOTP;
    }

    public String getEnteredOTP() {
        return enteredOTP;
    }

    public void setValidOTP(String validOTP) {
        this.validOTP = validOTP;
    }

    public String getValidOTP() {
        return validOTP;
    }

    public void setValidateMsg(String validateMsg) {
        this.validateMsg = validateMsg;
    }

    public String getValidateMsg() {
        return validateMsg;
    }

    public void setEnteredPassword(String enteredPassword) {
        this.enteredPassword = enteredPassword;
    }

    public String getEnteredPassword() {
        return enteredPassword;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setOtpSentFlag(boolean otpSentFlag) {
        this.otpSentFlag = otpSentFlag;
    }

    public boolean isOtpSentFlag() {
        return otpSentFlag;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setOtpSentMsg(String otpSentMsg) {
        this.otpSentMsg = otpSentMsg;
    }

    public String getOtpSentMsg() {
        return otpSentMsg;
    }


    public void setEnteredSecurityAnswer(String enteredSecurityAnswer) {
        this.enteredSecurityAnswer = enteredSecurityAnswer;
    }

    public String getEnteredSecurityAnswer() {
        return enteredSecurityAnswer;
    }

    public void sendOTP(ActionEvent actionEvent) {
        otpSentMsg=null;
        LoginDetails loginDetails=new LoginDetails();
        DBServices dbServices=new DBServices();
        if(discomValue!=null && accountNo!=null && !discomValue.isEmpty() && !accountNo.isEmpty()){
            loginDetails=dbServices.validateUserAccount(accountNo,discomValue);
        }
        if(discomValue==null || discomValue.isEmpty()){
            discomValidationMessage="Please select the discom from the drop down";  
        }
        else if(accountNo==null || accountNo.isEmpty()){
            otpSentMsg="Please enter the Account Number";  
        }
        else if(!loginDetails.getValidAccount()){
                otpSentFlag=false;            
                otpSentMsg="Invalid Account Number";
        }
        else
        {
            GetOTPRequest request=new GetOTPRequest(); 
            request.setMobileNo(loginDetails.getMobileNo());
            request.setEmail(loginDetails.getEmail());
            request.setMode("Both");
            request.setSource("WSS");
            RestServices service=new RestServices();
            GetOTPResponse response=service.getOTP(request,discomValue);
            if(response!=null){
                otpSentFlag=true; 
                otpSentMsg= response.getResMsg();
                if(response!=null && "0".equalsIgnoreCase(response.getResCode())){
                    securityQuestion=loginDetails.getSecurityQuestion();
                    securityAnswer=loginDetails.getSecurityAnswer();
                    validOTP=response.getOtp();
                }
            }
        }
    }

    public void validateOTP(ActionEvent actionEvent) {
        otpSentMsg=null;
        otpValidateMsg=null;
        securityAnswerValidMsg=null;
        if(enteredOTP==null){
            otpValidateMsg="Please enter the OTP.";
            validateFlag=false;
        }    
        else if(validOTP!=null && enteredOTP!=null && !validOTP.equalsIgnoreCase(enteredOTP)){
            otpValidateMsg="OTP entered is incorrect.";
            validateFlag=false;
        }
        else if(enteredSecurityAnswer==null){
            securityAnswerValidMsg="Please answer the security question.";
            validateFlag=false;
        }
        else if(!enteredSecurityAnswer.equalsIgnoreCase(securityAnswer)){
            securityAnswerValidMsg="Security answer is incorrect.";
            validateFlag=false;
        }
         
        else{
            validateMsg="Validation successful.";
            validateFlag=true;
        }
    }

    public void resetPassword(ActionEvent actionEvent) throws NoSuchAlgorithmException, NoSuchPaddingException,
                                                              InvalidKeyException, InvalidAlgorithmParameterException,
                                                              IllegalBlockSizeException, BadPaddingException {
        // Add event code here...
        enteredPasswordValidMsg=null;
        reEnteredPasswordValidMsg=null;
        resetMessage=null;
        boolean flag=false;
        if(enteredPassword==null){
            enteredPasswordValidMsg="Please enter the new password.";
        }
        else if(reEnteredPassword==null){
            reEnteredPasswordValidMsg="Please re-enter the new password.";
        }
        else if(!reEnteredPassword.equalsIgnoreCase(enteredPassword)){
            reEnteredPasswordValidMsg="Re-Entered password doesn't match.";
        }
        else{
            DBServices dbServices=new DBServices();
            
            flag=dbServices.resetPassword(accountNo, utils.encryptPass(dbServices.getProperty("SALT_KEY"), enteredPassword), discomValue);
            if(flag){
                resetMessage="Password reset is successful.";
            }
            else{
                resetMessage="Password reset is not successful.";
            }
        }              
        
    }

}
