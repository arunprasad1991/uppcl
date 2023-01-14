package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.LoginDetails;
import com.xx.uppcl.services.DBServices;

import com.xx.uppcl.utils.Utils;

import java.io.Serializable;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

public class ChangePassword implements Serializable{
        private static final long serialVersionUID=13324323L;
    private RichPopup changePasswordPopUp;

    public ChangePassword() {
        super();
    }
    private String accountNumber;
    private String oldPassword;
    private String newPassword;
    private String newPassword2;
    private String oldPasswordValidationMsg;
    private String newPasswordValidationMsg;
    private String newPassword2ValidationMsg;
    private String changePasswordPopUpMsg;
    private String discomValue;
    
    Utils utils = new Utils();


    public void setDiscomValue(String discomValue) {
        this.discomValue = discomValue;
    }

    public String getDiscomValue() {
        return discomValue;
    }


    public void setChangePasswordPopUpMsg(String changePasswordPopUpMsg) {
        this.changePasswordPopUpMsg = changePasswordPopUpMsg;
    }

    public String getChangePasswordPopUpMsg() {
        return changePasswordPopUpMsg;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setOldPasswordValidationMsg(String oldPasswordValidationMsg) {
        this.oldPasswordValidationMsg = oldPasswordValidationMsg;
    }

    public String getOldPasswordValidationMsg() {
        return oldPasswordValidationMsg;
    }

    public void setNewPasswordValidationMsg(String newPasswordValidationMsg) {
        this.newPasswordValidationMsg = newPasswordValidationMsg;
    }

    public String getNewPasswordValidationMsg() {
        return newPasswordValidationMsg;
    }

    public void setNewPassword2ValidationMsg(String newPassword2ValidationMsg) {
        this.newPassword2ValidationMsg = newPassword2ValidationMsg;
    }

    public String getNewPassword2ValidationMsg() {
        return newPassword2ValidationMsg;
    }
    
    public void loadPage(){
        Map paramSession=new HashMap();
        paramSession=ADFContext.getCurrent().getSessionScope(); 
        String accountNo=null;
        if(paramSession.get("loggedInAccountNo")!=null){
            accountNo=paramSession.get("loggedInAccountNo").toString();
            accountNumber=accountNo;            
        }  
        if(accountNumber==null || accountNumber.isEmpty()){
            accountNumber="7637640512";
        }
        if(paramSession.get("loggedInUserDiscom")!=null){
            discomValue=paramSession.get("loggedInUserDiscom").toString();
        }
        if(discomValue==null || discomValue.isEmpty()){
            discomValue="DVVNL";
        }
    }
    
    public void changeAccountPassword(ActionEvent event) throws NoSuchAlgorithmException, NoSuchPaddingException,
                                                                InvalidKeyException, InvalidAlgorithmParameterException,
                                                                IllegalBlockSizeException, BadPaddingException {
        DBServices db=new DBServices();
        LoginDetails loginDetails= new LoginDetails();
        boolean flag=false;
        oldPasswordValidationMsg=null;
        newPasswordValidationMsg=null;
        newPassword2ValidationMsg=null;
        if(oldPassword!=null  && !oldPassword.isEmpty()){
            
            loginDetails= db.authenticUser(accountNumber, utils.encryptPass(db.getProperty("SALT_KEY"), oldPassword) ,discomValue);
        }
        if(oldPassword==null || oldPassword.isEmpty()){
            oldPasswordValidationMsg="Please enter the existing password.";
        }
        else if(loginDetails==null || !loginDetails.getValidAccount()){            
            oldPasswordValidationMsg="Entered existing password is incorrect.";
        }
        else if(newPassword==null || newPassword.isEmpty()){
            newPasswordValidationMsg="Please enter the new password.";
        }
        else if(newPassword2==null || newPassword2.isEmpty()){
            newPassword2ValidationMsg="Please re-enter the new password.";
        }
        else if(!newPassword2.equalsIgnoreCase(newPassword)){
            newPassword2ValidationMsg="Re-Entered password doesn't match.";
        }       
        else{
            flag=db.resetPassword(accountNumber, utils.encryptPass(db.getProperty("SALT_KEY"), newPassword), discomValue);
            if(flag){
                changePasswordPopUpMsg="Password changed suceesfully.";
            }
            RichPopup.PopupHints hints=new RichPopup.PopupHints();
            changePasswordPopUp.show(hints);
            
        }
    }

    public void setChangePasswordPopUp(RichPopup changePasswordPopUp) {
        this.changePasswordPopUp = changePasswordPopUp;
    }

    public RichPopup getChangePasswordPopUp() {
        return changePasswordPopUp;
    }

    public void okActionListener(ActionEvent actionEvent) {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
        String url = "http://10.0.0.192:8021/webcenter/portal/uppcl/pages_myaccount";
        try {
            ectx.redirect(url);
            FacesContext.getCurrentInstance().responseComplete();
        }
        catch (Exception ex) {
            ex.printStackTrace();
         }
    }
}
