package com.xx.uppcl.beans;

import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.request.UpdateConsumerDetailsRequest;
import com.xx.uppcl.rest.response.GetConsumerDetailsResponse;
import com.xx.uppcl.rest.response.UpdateConsumerDetailsResponse;
import com.xx.uppcl.rest.services.RestServices;

import com.xx.uppcl.services.DBServices;
import com.xx.uppcl.utils.Utils;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

public class UpdateProfile implements Serializable{
        private static final long serialVersionUID=18324435L;
    private RichPopup updateProfilePopUp;
    DBServices dbService = new DBServices();

    public UpdateProfile() {
        super();
    }
    
    private String name;
    private String address;
    private String email;
    private String mobileNo;
    private String emailValidationMsg;
    private String mobileNoValidationMsg;
    private String accountNo;
    private String personId;
    private String discomValue;


    public void setDiscomValue(String discomValue) {
        this.discomValue = discomValue;
    }

    public String getDiscomValue() {
        return discomValue;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
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

    public void setEmailValidationMsg(String emailValidationMsg) {
        this.emailValidationMsg = emailValidationMsg;
    }

    public String getEmailValidationMsg() {
        return emailValidationMsg;
    }

    public void setMobileNoValidationMsg(String mobileNoValidationMsg) {
        this.mobileNoValidationMsg = mobileNoValidationMsg;
    }

    public String getMobileNoValidationMsg() {
        return mobileNoValidationMsg;
    }

    public String updateProfileAction() {
        // Add event code here...
        String retValue = null;
        Utils utils = new Utils();
        boolean flag=false;
        mobileNoValidationMsg=null;
        emailValidationMsg=null;
        if(mobileNo==null || mobileNo.isEmpty()){
            mobileNoValidationMsg="Please enter the mobile no.";
        }else if(!utils.isValidMobileNo(mobileNo)){
            mobileNoValidationMsg="Please enter the valid mobile no.";
        }
        else if(email==null || email.isEmpty()){
            emailValidationMsg="Please enter the email address.";
        }else if(!utils.validateEmail(email)){
            emailValidationMsg="Please enter the valid email address.";
        }
        else{
            RestServices rest=new RestServices();
            UpdateConsumerDetailsRequest req=new UpdateConsumerDetailsRequest();
            req.setEmail(email);
            req.setMobileNo(mobileNo);
            req.setKno(accountNo);
            req.setPersonId(personId);
            UpdateConsumerDetailsResponse response=rest.updateProfile(req,discomValue);
            if(response!=null && "TRUE".equalsIgnoreCase(response.getStatus())){
                
                dbService.updateProfileDetails(accountNo, mobileNo, email, discomValue);
                flag=true;
                RichPopup.PopupHints hints=new RichPopup.PopupHints();
                updateProfilePopUp.show(hints);
            }else{
                retValue = "toError";
            }
            
        }
        
        return retValue;
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
    
    public void loadPage(){
        Map paramSession=new HashMap();
        paramSession=ADFContext.getCurrent().getSessionScope(); 
        if(paramSession.get("loggedInAccountNo")!=null){
            accountNo=paramSession.get("loggedInAccountNo").toString();            
        }  
        if(accountNo==null || accountNo.isEmpty()){
            accountNo="7637640512";
        }
        if(paramSession.get("loggedInUserDiscom")!=null){
            discomValue=paramSession.get("loggedInUserDiscom").toString();
        }
        if(discomValue==null || discomValue.isEmpty()){
            discomValue="DVVNL";
        }
        RestServices rest=new RestServices();
        GetConsumerDetailsRequest request=new GetConsumerDetailsRequest();
        request.setKno(accountNo);
        GetConsumerDetailsResponse response=rest.getConsumerDetails(request, discomValue);
        if(response!=null){
            name=response.getConsumerName();
            String addr="";
            if(response.getBillingAddress()!=null){
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
            }
            email=response.getEmailAddress();
            mobileNo=response.getMobileNumber();
            personId=response.getPersonID();
        }
    }

    public void setUpdateProfilePopUp(RichPopup updateProfilePopUp) {
        this.updateProfilePopUp = updateProfilePopUp;
    }

    public RichPopup getUpdateProfilePopUp() {
        return updateProfilePopUp;
    }
}
