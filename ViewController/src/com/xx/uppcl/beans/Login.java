package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.LoginDetails;
import com.xx.uppcl.rest.request.GetOTPRequest;
import com.xx.uppcl.rest.response.GetOTPResponse;
import com.xx.uppcl.rest.services.RestServices;
import com.xx.uppcl.services.DBServices;
import com.xx.uppcl.utils.Utils;

import java.io.Serializable;

import java.net.InetAddress;

import java.net.UnknownHostException;

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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletResponse;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;

public class Login implements Serializable{
        private static final long serialVersionUID=1L; 
        private String accountNumber;
        private String password;
        private RichInputText enteredCaptchaBinding;
        private String loginErrorMessage;
        private String oneTimePassword;
        private boolean otpCheckBox=false;
        private String discomValue;
        private String otpSendMessage;
        private String captchaStr;
        private String enteredCaptcha;
        private String captchaAnswer;
        private boolean captchaValidated=false;
        private String captchaValidationMessage;
        private String validOTP;
        private String accNoValidationMsg;
        private String passwordValidationMsg;
        private String otpValidationMsg;
        private List<SelectItem> discomList = new ArrayList<SelectItem>();
        private String discomValidationMessage;
        
    Utils utils = new Utils();


    public void setDiscomValidationMessage(String discomValidationMessage) {
        this.discomValidationMessage = discomValidationMessage;
    }

    public String getDiscomValidationMessage() {
        return discomValidationMessage;
    }

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

    public void setAccNoValidationMsg(String accNoValidationMsg) {
        this.accNoValidationMsg = accNoValidationMsg;
    }

    public String getAccNoValidationMsg() {
        return accNoValidationMsg;
    }

    public void setPasswordValidationMsg(String passwordValidationMsg) {
        this.passwordValidationMsg = passwordValidationMsg;
    }

    public String getPasswordValidationMsg() {
        return passwordValidationMsg;
    }

    public void setOtpValidationMsg(String otpValidationMsg) {
        this.otpValidationMsg = otpValidationMsg;
    }

    public String getOtpValidationMsg() {
        return otpValidationMsg;
    }

    public void setValidOTP(String validOTP) {
        this.validOTP = validOTP;
    }

    public String getValidOTP() {
        return validOTP;
    }

    public void setCaptchaValidationMessage(String captchaValidationMessage) {
        this.captchaValidationMessage = captchaValidationMessage;
    }

    public String getCaptchaValidationMessage() {
        return captchaValidationMessage;
    }

    public void setCaptchaAnswer(String captchaAnswer) {
        this.captchaAnswer = captchaAnswer;
    }

    public String getCaptchaAnswer() {
        return captchaAnswer;
    }

    public void setCaptchaValidated(boolean captchaValidated) {
        this.captchaValidated = captchaValidated;
    }

    public boolean isCaptchaValidated() {
        return captchaValidated;
    }

    public void setEnteredCaptcha(String enteredCaptcha) {
        this.enteredCaptcha = enteredCaptcha;
    }

    public String getEnteredCaptcha() {
        return enteredCaptcha;
    }


    public Login() {
            super();
        }

    public void setCaptchaStr(String captchaStr) {
        this.captchaStr = captchaStr;
    }

    public String getCaptchaStr() {
        return captchaStr;
    }

    public String loginAction() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                                       InvalidAlgorithmParameterException, IllegalBlockSizeException,
                                       BadPaddingException {

            boolean loginFlag=true;
            boolean loginValidationFlag=true;
            String loginRedirect=null;
            loginErrorMessage=null;
            accNoValidationMsg=null;
            passwordValidationMsg=null;
            otpSendMessage=null;
            captchaValidationMessage=null;
            discomValidationMessage=null;
            
            Map paramSession=new HashMap();
            paramSession= ADFContext.getCurrent().getSessionScope();
            
            paramSession.put("loggedInUserName", null);
            paramSession.put("loggedInAccountNo", null);
            paramSession.put("loggedInUserDiscom", null);
        
            paramSession.put("lastLoginTime", null);
            paramSession.put("lastPassChangeTime", null);
            paramSession.put("lastRegisterTime", null);
            paramSession.put("profilePicName", null);
            paramSession.put("profilePicContent", null);
            
            if(discomValue==null|| discomValue.isEmpty()){
                discomValidationMessage="Please select the discom from the drop down.";
                loginValidationFlag=false;
            }
            else if(accountNumber==null|| accountNumber.isEmpty()){
                accNoValidationMsg="Please enter the account number.";
                loginValidationFlag=false;
            }
            else if(password==null && oneTimePassword==null){
                passwordValidationMsg="Please enter the password/OTP.";
                loginValidationFlag=false;
            }
            else if(enteredCaptcha==null || enteredCaptcha.isEmpty()){
                captchaValidationMessage="Please validate the captcha to proceed.";
                loginValidationFlag=false;
            }
            else if(!captchaAnswer.equalsIgnoreCase(enteredCaptcha)){
                captchaValidationMessage="Captcha enetered is incorrect.";
                loginValidationFlag=false;
            }
            if(loginValidationFlag && accountNumber!=null && accountNumber.length()>0){
                LoginDetails loginDetails=new LoginDetails();
                DBServices dbServices=new DBServices();
                loginDetails=dbServices.validateUserAccount(accountNumber,discomValue);
                if(!loginDetails.getValidAccount()){
                    loginFlag=false;            
                    accNoValidationMsg="Invalid Account Number";
                }else if(otpCheckBox){
                    if(oneTimePassword!=null && validOTP!=null && oneTimePassword.equalsIgnoreCase(validOTP)){
                        loginFlag=true;
                        loginDetails.setAuthenticationSuccessful(true);
                    }
                    else{
                        loginFlag=false; 
                        passwordValidationMsg="OTP Entered in incorrect.";
                    }
                }
                else{
                    loginDetails=dbServices.authenticUser(accountNumber, utils.encryptPass(dbServices.getProperty("SALT_KEY"),password) , discomValue);
                    if(loginDetails!=null && loginDetails.getAuthenticationSuccessful()){
                        loginFlag=true;
                    }
                    else{
                        loginFlag=false;
                        passwordValidationMsg="Password entered is incorrect.";
                    }
                }
                if(loginFlag){                     
                                    
                    paramSession.put("isLoggedInSuccess", "Y");
                    paramSession.put("loggedInUserName", loginDetails.getLoggedInUserName());
                    paramSession.put("loggedInAccountNo", accountNumber);
                    paramSession.put("loggedInUserDiscom", loginDetails.getLoggedInUserDiscom());
                    
                    //get last login
//                    paramSession.put("lastLoginTime", dbServices.getLastLogin(accountNumber));
                    paramSession.put("lastLoginTime", loginDetails.getLastLoggedInDate());
                    paramSession.put("lastPassChangeTime", loginDetails.getChangePasswordLastDate());
                    paramSession.put("lastRegisterTime", loginDetails.getRegisteredLastDate());
                    
                    paramSession.put("profilePicName", loginDetails.getProfilePicName());
                    paramSession.put("profilePicContent", loginDetails.getProfilePicContent());
                    
                    System.out.println("Last Register --> "+loginDetails.getRegisteredLastDate());
                    System.out.println("Last Change Pass --> "+loginDetails.getChangePasswordLastDate());
                    System.out.println("Last Login --> "+loginDetails.getLastLoggedInDate());
                    System.out.println("Profile Pic Name --> "+loginDetails.getProfilePicName());
                    System.out.println("Profile Pic Content --> "+loginDetails.getProfilePicContent());
                    //update last login
                    dbServices.updateLastLogin(accountNumber, discomValue);
                    
                    ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
                    HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
                    InetAddress ip;
                    String hostname; 
                    String url = "";
                    try {
                    ip = InetAddress.getLocalHost();
                                hostname = ip.getHostName();
                                System.out.println("Your current IP address : " + ip);
                                System.out.println("Your current Hostname : " + hostname);
                        if("dmsjumpbox".equalsIgnoreCase(hostname))     {
                           url = "http://127.0.0.1:8101/TestLogin-ViewController-context-root/faces/Account.jspx";  
                        }
                        else{
                            url = dbServices.getProperty("MYACCOUNT_URL");
                        }
                        paramSession.put("isCurrentTab", "myaccount");
                        ectx.redirect(url);
                        FacesContext.getCurrentInstance().responseComplete();
                    }
                    catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                     }     
                }
                
            }           
        return loginRedirect;
    }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public void setEnteredCaptchaBinding(RichInputText enteredCaptchaBinding) {
            this.enteredCaptchaBinding = enteredCaptchaBinding;
        }

        public RichInputText getEnteredCaptchaBinding() {
            return enteredCaptchaBinding;
        }

        public void setLoginErrorMessage(String loginErrorMessage) {
            this.loginErrorMessage = loginErrorMessage;
        }

        public String getLoginErrorMessage() {
            return loginErrorMessage;
        }

        public void setOneTimePassword(String oneTimePassword) {
            this.oneTimePassword = oneTimePassword;
        }

        public String getOneTimePassword() {
            return oneTimePassword;
        }

        public void setOtpCheckBox(boolean otpCheckBox) {
            this.otpCheckBox = otpCheckBox;
        }

        public boolean isOtpCheckBox() {
            return otpCheckBox;
        }

        public void otpCheckBoxValueChange(ValueChangeEvent valueChangeEvent) {
            Boolean checkBox=(Boolean)valueChangeEvent.getNewValue();
            if(checkBox){
                otpCheckBox=true;
                sendOTP();
            }
            else{
                otpCheckBox=false;
            }
        }

    public void setDiscomValue(String discomValue) {
        this.discomValue = discomValue;
    }

    public String getDiscomValue() {
        return discomValue;
    }

    
    public void sendOTP(){
        discomValidationMessage=null;
        accNoValidationMsg=null;
        if(discomValue==null || discomValue.isEmpty()){
            discomValidationMessage="Please select the discom from the drop down.";
        }
        else if(accountNumber==null || accountNumber.isEmpty()){
            accNoValidationMsg="Please enter the account number.";
        }
        else if(accountNumber!=null){
            LoginDetails loginDetails=new LoginDetails();
            DBServices dbServices=new DBServices();
            loginDetails=dbServices.validateUserAccount(accountNumber,discomValue);
            if(loginDetails!=null && loginDetails.getValidAccount()){
                GetOTPRequest request=new GetOTPRequest(); 
                request.setMobileNo(loginDetails.getMobileNo());
                request.setMode("BOTH");
                request.setSource("WSS");
                request.setEmail(loginDetails.getEmail());
                RestServices service=new RestServices();
                GetOTPResponse response=service.getOTP(request,discomValue);
                validOTP=response.getOtp();
                if(response!=null){
                    otpSendMessage= response.getResMsg();
                }
            }
        }
        else{
            otpSendMessage= "Account not registered. Please click on Register Here link below.";
        }
    }

    public void setOtpSendMessage(String otpSendMessage) {
        this.otpSendMessage = otpSendMessage;
    }

    public String getOtpSendMessage() {
        return otpSendMessage;
    }
    
    public String generateCaptcha(){
                
        Long a=Math.round((Math.random() * 10));
        Long b=Math.round((Math.random() * 10));
        Long c=0L;
        String captcha="";
        if(a>b){
            c=a-b;
            captcha=a.toString()+" - "+b.toString();
        }else{
            c=a+b;
            captcha=a.toString()+" + "+b.toString();
        }                       
        this.captchaStr=captcha;
        return c.toString();
    }
    
    public String getHeaderText(){
        captchaAnswer=generateCaptcha();
        return "Welcome to UPPCL";
    }
    
    public boolean validateCaptcha(){
        if(captchaAnswer.equalsIgnoreCase(enteredCaptcha)){
            captchaValidated=true;
            captchaValidationMessage="Captcha entered is Correct.";
        }
        else{
            captchaValidated=false;
            captchaValidationMessage="Captcha entered is Incorrect";        
        }
        return captchaValidated;
    }
    
    public void refreshCaptcha(ActionEvent event){
        captchaAnswer=generateCaptcha();
        enteredCaptcha=null;
    }
        
    public void redirectToRegister(ActionEvent event){
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
        String url = "http://10.0.0.192:8021/webcenter/portal/uppcl/pages_register";
        
        try {
            ectx.redirect(url);
            FacesContext.getCurrentInstance().responseComplete();
        }
        catch (Exception ex) {
            ex.printStackTrace();
         }
    }
    
    public void redirectToLogin(ActionEvent event){
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
        String url = "http://10.0.0.192:8021/webcenter/portal/uppcl/pages_login";
        try {
            ectx.redirect(url);
            FacesContext.getCurrentInstance().responseComplete();
        }
        catch (Exception ex) {
            ex.printStackTrace();
         }
    }

}
