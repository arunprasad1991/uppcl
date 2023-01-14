package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.LoginDetails;
import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.request.GetOTPRequest;
import com.xx.uppcl.rest.request.ValidateBillRequest;
import com.xx.uppcl.rest.response.GetConsumerDetailsResponse;
import com.xx.uppcl.rest.response.GetOTPResponse;
import com.xx.uppcl.rest.response.ValidateBillResponse;
import com.xx.uppcl.rest.services.RestServices;

import com.xx.uppcl.services.DBServices;

import com.xx.uppcl.utils.Utils;

import java.io.Serializable;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletResponse;

public class Registration implements Serializable{
    private static final long serialVersionUID=106872L; 
    public Registration() {
        super();
    }
    
    private String accountNumber;
    private String billNumber;
    private List<SelectItem> securityQuestionsList;
    private String name;
    private String address;
    private String email;
    private String mobileNo;
    private String securityAnswer;
    private String securityQuestion;
    private String  password;
    private String accountValidationMsg;
    private  boolean accountValidationFlag;
    private String billNoValidationMsg;
    private  boolean billNoValidationFlag;
    private String mobileOTP;
    private String emailOTP;
    private String mobileValidationMsg;
    private  boolean mobileValidationFlag;
    private String emailValidationMsg;
    private  boolean emailValidationFlag;
    private String registerMsg;
    private String enteredMobileOTP;
    private String enteredEmailOTP;
    private String registerMessage;
    private String reEnteredpassword;
    private String passwordValidMsg;
    private String reEnteredpasswordValidMsg;
    private String securityAnswerValidMsg;
    private String emailOTPValidationMsg;
    private String mobileOTPValidationMsg;
    private boolean registerFlag=false;
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

    public void setRegisterFlag(boolean registerFlag) {
        this.registerFlag = registerFlag;
    }

    public boolean isRegisterFlag() {
        return registerFlag;
    }

    public void setEmailOTPValidationMsg(String emailOTPValidationMsg) {
        this.emailOTPValidationMsg = emailOTPValidationMsg;
    }

    public String getEmailOTPValidationMsg() {
        return emailOTPValidationMsg;
    }

    public void setMobileOTPValidationMsg(String mobileOTPValidationMsg) {
        this.mobileOTPValidationMsg = mobileOTPValidationMsg;
    }

    public String getMobileOTPValidationMsg() {
        return mobileOTPValidationMsg;
    }

    public void setSecurityAnswerValidMsg(String securityAnswerValidMsg) {
        this.securityAnswerValidMsg = securityAnswerValidMsg;
    }

    public String getSecurityAnswerValidMsg() {
        return securityAnswerValidMsg;
    }

    public void setReEnteredpassword(String reEnteredpassword) {
        this.reEnteredpassword = reEnteredpassword;
    }

    public String getReEnteredpassword() {
        return reEnteredpassword;
    }

    public void setPasswordValidMsg(String passwordValidMsg) {
        this.passwordValidMsg = passwordValidMsg;
    }

    public String getPasswordValidMsg() {
        return passwordValidMsg;
    }

    public void setReEnteredpasswordValidMsg(String reEnteredpasswordValidMsg) {
        this.reEnteredpasswordValidMsg = reEnteredpasswordValidMsg;
    }

    public String getReEnteredpasswordValidMsg() {
        return reEnteredpasswordValidMsg;
    }

    public void setRegisterMessage(String registerMessage) {
        this.registerMessage = registerMessage;
    }

    public String getRegisterMessage() {
        return registerMessage;
    }

    public void setRegisterMsg(String registerMsg) {
        this.registerMsg = registerMsg;
    }

    public String getRegisterMsg() {
        return registerMsg;
    }

    public void setEnteredMobileOTP(String enteredMobileOTP) {
        this.enteredMobileOTP = enteredMobileOTP;
    }

    public String getEnteredMobileOTP() {
        return enteredMobileOTP;
    }

    public void setEnteredEmailOTP(String enteredEmailOTP) {
        this.enteredEmailOTP = enteredEmailOTP;
    }

    public String getEnteredEmailOTP() {
        return enteredEmailOTP;
    }


    public void setMobileValidationMsg(String mobileValidationMsg) {
        this.mobileValidationMsg = mobileValidationMsg;
    }

    public String getMobileValidationMsg() {
        return mobileValidationMsg;
    }

    public void setMobileValidationFlag(boolean mobileValidationFlag) {
        this.mobileValidationFlag = mobileValidationFlag;
    }

    public boolean isMobileValidationFlag() {
        return mobileValidationFlag;
    }

    public void setEmailValidationMsg(String emailValidationMsg) {
        this.emailValidationMsg = emailValidationMsg;
    }

    public String getEmailValidationMsg() {
        return emailValidationMsg;
    }

    public void setEmailValidationFlag(boolean emailValidationFlag) {
        this.emailValidationFlag = emailValidationFlag;
    }

    public boolean isEmailValidationFlag() {
        return emailValidationFlag;
    }

    public void setAccountValidationMsg(String accountValidationMsg) {
        this.accountValidationMsg = accountValidationMsg;
    }

    public String getAccountValidationMsg() {
        return accountValidationMsg;
    }

    public void setAccountValidationFlag(boolean accountValidationFlag) {
        this.accountValidationFlag = accountValidationFlag;
    }

    public boolean isAccountValidationFlag() {
        return accountValidationFlag;
    }

    public void setBillNoValidationMsg(String billNoValidationMsg) {
        this.billNoValidationMsg = billNoValidationMsg;
    }

    public String getBillNoValidationMsg() {
        return billNoValidationMsg;
    }

    public void setBillNoValidationFlag(boolean billNoValidationFlag) {
        this.billNoValidationFlag = billNoValidationFlag;
    }

    public boolean isBillNoValidationFlag() {
        return billNoValidationFlag;
    }

    public void setMobileOTP(String mobileOTP) {
        this.mobileOTP = mobileOTP;
    }

    public String getMobileOTP() {
        return mobileOTP;
    }

    public void setEmailOTP(String emailOTP) {
        this.emailOTP = emailOTP;
    }

    public String getEmailOTP() {
        return emailOTP;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setSecurityQuestionsList(List<SelectItem> securityQuestionsList) {
        this.securityQuestionsList = securityQuestionsList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public List<SelectItem> getSecurityQuestionsList() {
            List<SelectItem> questions=new ArrayList<SelectItem>();
            SelectItem s1=new SelectItem();
            s1.setLabel("What is your mother's maiden name?");
            s1.setValue("What is your mother's maiden name?");
            questions.add(s1);
            SelectItem s2=new SelectItem();
            s2.setLabel("What was your first pet?");
            s2.setValue("What was your first pet?");
            questions.add(s2);
            SelectItem s3=new SelectItem();
            s3.setLabel("What was the model of your first car?");
            s3.setValue("What was the model of your first car?");
            questions.add(s3);
            SelectItem s4=new SelectItem();
            s4.setLabel("In which city you were born?");
            s4.setValue("In which city you were born?");
            questions.add(s4);
            SelectItem s5=new SelectItem();
            s5.setLabel("What was your childhood nickname?");
            s5.setValue("What was your childhood nickname?");
            questions.add(s5);
            return questions;
    }

    public void securityQuestionValueChange(ValueChangeEvent valueChangeEvent) {
        String question=(String)valueChangeEvent.getNewValue();
        if(question!=null){
            securityQuestion=question;
        }
    }

    public void validateAccountNo(ActionEvent actionEvent) {
        accountValidationMsg=null;
        discomValidationMessage=null;
        boolean accountExist=false;
        if(accountNumber!=null){
            LoginDetails loginDetails=new LoginDetails();
            DBServices dbServices=new DBServices();
            loginDetails=dbServices.validateUserAccount(accountNumber,discomValue);
            accountExist=loginDetails.getValidAccount();
        }
        if(discomValue==null || discomValue.isEmpty()){
            discomValidationMessage="Please select the disccom from the drop down.";
        }
        else if(accountNumber==null || accountNumber.isEmpty()){
            accountValidationMsg="Please enter the account number.";
        }
        else if(accountExist){
            accountValidationMsg="Account Number already registered."; 
        }
        else{
            GetConsumerDetailsRequest request=new GetConsumerDetailsRequest(); 
            request.setKno(accountNumber);
            RestServices service=new RestServices();
            GetConsumerDetailsResponse response=service.getConsumerDetails(request,discomValue);
            if(response!=null && response.getKno()!=null && accountNumber.equalsIgnoreCase(response.getKno())){
                name=response.getConsumerName();
                String addr="";
                if(response.getBillingAddress().getAddressLine1()!= null){
                    addr=addr+response.getBillingAddress().getAddressLine1()+"\n";
                }
                if(response.getBillingAddress().getAddressLine2()!= null){
                    addr=addr+response.getBillingAddress().getAddressLine2()+"\n";
                }
                if(response.getBillingAddress().getAddressLine3()!= null){
                    addr=addr+response.getBillingAddress().getAddressLine3()+"\n";
                }
                if(response.getBillingAddress().getAddressLine4()!= null){
                    addr=addr+response.getBillingAddress().getAddressLine4()+"\n";
                }
                if(response.getBillingAddress().getCity()!= null){
                    addr=addr+response.getBillingAddress().getCity()+"\n";
                }
                if(response.getBillingAddress().getPinCode()!= null){
                    addr=addr+response.getBillingAddress().getPinCode()+"\n";
                }
                if(response.getBillingAddress().getState()!= null){
                    addr=addr+response.getBillingAddress().getState();
                }
                address=addr;
                mobileNo=response.getMobileNumber();
                email=response.getEmailAddress();
                accountValidationFlag=true;
                accountValidationMsg=null;
            }else if(response!=null && response.getErrorCode()!=null){
                accountValidationMsg="Account Number validation failed. Please enter a valid account number.";
            }
        }
    }

    public void validateBillNo(ActionEvent actionEvent) {
        billNoValidationMsg=null;
        accountValidationMsg=null;
        discomValidationMessage=null;
        
        if(discomValue==null || discomValue.isEmpty()){
            discomValidationMessage="Please select the disccom first from the drop down.";
        }
        else if(accountNumber==null || accountNumber.isEmpty()){
            accountValidationMsg="Please validate the account number befire validationg bill no.";
        }
        else if(billNumber==null|| billNumber.isEmpty()){
            billNoValidationMsg="Please enter the Bill No."; 
        }
        else{
            ValidateBillRequest  request=new ValidateBillRequest(); 
            request.setBillNo(billNumber);
            request.setKno(accountNumber);
            request.setDuration("100");
            request.setSbmBillFlag("FALSE");
            RestServices service=new RestServices();
            ValidateBillResponse response=service.validateBill(request,discomValue);
            if(response!=null && response.getBillValidationStatus()!=null && "TRUE".equalsIgnoreCase(response.getBillValidationStatus())){
                billNoValidationFlag=true;
                billNoValidationMsg=null;
            }
            else{
                billNoValidationFlag=false;
                billNoValidationMsg="Bill No. validation failed. Please enter a valid Bill No.";
            }
        }
    }

    public void validateMobileNo(ActionEvent actionEvent) {
        mobileValidationMsg=null;
        mobileOTPValidationMsg=null;
        mobileOTP=null;
        if(mobileNo==null || mobileNo.isEmpty()){
            mobileValidationMsg="Please enter the mobile number."; 
        }else if(mobileNo != null && !utils.isValidMobileNo(mobileNo)){
            mobileValidationMsg="Please enter valid mobile number."; 
        }
        else if(mobileNo!=null){
            GetOTPRequest request=new GetOTPRequest(); 
            request.setMobileNo(mobileNo);
            request.setMode("MOBILE");
            request.setSource("WSS");
            RestServices service=new RestServices();
            GetOTPResponse response=service.getOTP(request,discomValue);           
            if(response!=null && "0".equalsIgnoreCase(response.getResCode())){
                mobileOTP=response.getOtp();
                mobileValidationMsg=null;
            }
            else if(response!=null && "1".equalsIgnoreCase(response.getResCode())){
                mobileOTP=null;
                mobileValidationMsg="Invalid mobile number. OTP not sent successfully.";
            }
            else{
                mobileOTP=null;
                mobileValidationMsg="Some error occured in sending OTP. Please try again later.";
            }
        }
    }

    public void validateEmail(ActionEvent actionEvent) {
        emailValidationMsg=null;
        emailOTPValidationMsg=null;
        emailOTP=null;
        if(email==null|| email.isEmpty()){
            emailValidationMsg="Please enter the email id.";
        }else if(email != null && !utils.validateEmail(email)){
            emailValidationMsg="Please enter valid email id.";
        }
        else if(email!=null){
            GetOTPRequest request=new GetOTPRequest(); 
            request.setEmail(email);
            request.setMode("EMAIL");
            request.setSource("WSS");
            RestServices service=new RestServices();
            GetOTPResponse response=service.getOTP(request,discomValue);
                if(response!=null && "0".equalsIgnoreCase(response.getResCode())){
                    emailOTP=response.getOtp();
                    emailValidationMsg=null;
                }
                else if(response!=null && "1".equalsIgnoreCase(response.getResCode())){
                    emailOTP=null;
                    emailValidationMsg="Invalid email address. OTP not sent successfully.";
                }
                else{
                    emailOTP=null;
                    emailValidationMsg="Some error occured in sending OTP. Please try again later.";
                }
        }
    }



    public void verifyMobileOTP(ActionEvent actionEvent) {
        mobileOTPValidationMsg=null;
        mobileValidationMsg=null;
        if(enteredMobileOTP==null){
            mobileOTPValidationMsg="Please enter the OTP to verify mobile number.";
        }
        else{
            if(enteredMobileOTP!=null && mobileOTP!=null && enteredMobileOTP.equalsIgnoreCase(mobileOTP)){
                mobileValidationFlag=true;
            }
            else{
                mobileOTPValidationMsg="OTP entered is incorrect";
            }
        }
    }

    public void verifyEmailOTP(ActionEvent actionEvent) {
        emailValidationMsg=null;
        emailOTPValidationMsg=null;
        if(enteredEmailOTP==null){
            emailOTPValidationMsg="Please enter the OTP to verify Email";
        }
        else{
            if(enteredEmailOTP!=null && emailOTP!=null && enteredEmailOTP.equalsIgnoreCase(emailOTP)){
                emailValidationFlag=true;
            }
            else{
                emailOTPValidationMsg="OTP entered is incorrect";
            }
        }
    }

    public String continueAction() {

        accountValidationMsg=null;
        billNoValidationMsg=null;
        if(accountNumber==null){
            accountValidationMsg="Please enter the account number";
        }
        else if(!accountValidationFlag){
            accountValidationMsg="Please validate Account Number to proceed";
        }
        else if(billNumber==null){
            billNoValidationMsg="Please enter the bill number"; 
        }        
        else if(!billNoValidationFlag){
            billNoValidationMsg="Please validate Bill No to proceed"; 
        }
        else if(accountValidationFlag && billNoValidationFlag){
            return "continue";
        }
        return null;
    }
    
    public void registerUser(ActionEvent actionEvent) throws NoSuchAlgorithmException, NoSuchPaddingException,
                                                             InvalidKeyException, InvalidAlgorithmParameterException,
                                                             IllegalBlockSizeException, BadPaddingException {
        mobileValidationMsg=null;
        emailValidationMsg=null;
        passwordValidMsg=null;
        reEnteredpasswordValidMsg=null;
        securityAnswerValidMsg=null;
        registerMessage=null;
        if(!mobileValidationFlag){
            mobileValidationMsg="Please verify the mobile no to proceed.";
        }
        else if(!emailValidationFlag){
            emailValidationMsg="Please validate the email to proceed.";
        }
        else if(password==null || password.isEmpty()){
            passwordValidMsg="Please enter the password.";
        }
        else if(reEnteredpassword==null || reEnteredpassword.isEmpty()){
            reEnteredpasswordValidMsg="Please re-enter the password.";
        }
        else if(!reEnteredpassword.equalsIgnoreCase(password)){
            reEnteredpasswordValidMsg="Re-Entered password doesn't match.";
        }
        else if(securityAnswer==null || securityAnswer.isEmpty()){
            securityAnswerValidMsg="Please answer the security question.";
        }
        else{
            DBServices dbServices=new DBServices();
            boolean flag=dbServices.registerUser(accountNumber,utils.encryptPass(dbServices.getProperty("SALT_KEY"), password), name, name, mobileNo, email, securityQuestion, securityAnswer);
            if(flag){
                registerFlag=true;
                registerMessage="Registration is successful.";
            }
            else{
                registerFlag=false;
                registerMessage="Registration is not successful. Account already exist.";
            }
        }
    }

    public void redirectToLogin(ActionEvent actionEvent) {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
        DBServices dbServices = new DBServices();
        String url = dbServices.getProperty("LOGIN_URL");
//        String url = "http://10.0.0.192:8021/webcenter/portal/uppcl/pages_login";
        try {
            ectx.redirect(url);
            FacesContext.getCurrentInstance().responseComplete();
        }
        catch (Exception ex) {
            ex.printStackTrace();
         }
    }

    public void accountNoValueChange(ValueChangeEvent valueChangeEvent) {
        String newValue=null;
        String oldValue=null;
        if(valueChangeEvent.getNewValue()!=null){
             newValue=valueChangeEvent.getNewValue().toString();            
        }
        if(valueChangeEvent.getOldValue()!=null){
             oldValue=valueChangeEvent.getOldValue().toString();            
        }
        if(newValue!=null && oldValue!=null && !newValue.equalsIgnoreCase(oldValue)){
            accountValidationFlag=false;
            accountValidationMsg=null;
        }
    }

    public void billNoValueChange(ValueChangeEvent valueChangeEvent) {
        String newValue=null;
        String oldValue=null;
        if(valueChangeEvent.getNewValue()!=null){
             newValue=valueChangeEvent.getNewValue().toString();            
        }
        if(valueChangeEvent.getOldValue()!=null){
             oldValue=valueChangeEvent.getOldValue().toString();            
        }
        if(newValue!=null && oldValue!=null && !newValue.equalsIgnoreCase(oldValue)){
            billNoValidationFlag=false;
            billNoValidationMsg=null;
        }
    }
}
