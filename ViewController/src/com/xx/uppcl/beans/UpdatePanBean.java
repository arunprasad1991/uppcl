package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.Account;
import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.request.GetPanDetailsRequest;
import com.xx.uppcl.rest.request.UpdatePanDetailsRequest;
import com.xx.uppcl.rest.response.GetConsumerDetailsResponse;
import com.xx.uppcl.rest.response.GetPanDetailsResponse;
import com.xx.uppcl.rest.response.UpdatePanDetailsResponse;
import com.xx.uppcl.rest.services.RestServices;
import com.xx.uppcl.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.context.AdfFacesContext;

public class UpdatePanBean {
    public UpdatePanBean() {
    }

    Map paramSession= ADFContext.getCurrent().getSessionScope();
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();
    Utils utils = new Utils();
    private RichPopup messagePopupBind;
    
    public String findLabel(String key){
        String value = utils.getLabelValueForKey(key);
        System.out.println("Key --> "+key+ " " + "Value --> "+value); 
        return value;
    }

    public void onAccountSelValueChange(ValueChangeEvent valueChangeEvent) {
        // get selected value
        String selectedValue = null;
        
        if(valueChangeEvent.getNewValue() != null){
            selectedValue = (String) valueChangeEvent.getNewValue();
        }
        
        if(selectedValue != null){
            // call customer service
            pageFlowParam.put("accountsDetails", getConsumerAccDetails(selectedValue));
        }
    }
    
    public Account getConsumerAccDetails(String accNo){
        System.out.println("getConsumerAccDetails  :: Starts");
        Account accDetails = new Account();
        String discomVal = null;
        //set request
        GetConsumerDetailsRequest request=new GetConsumerDetailsRequest();
        request.setKno(accNo);
        
        RestServices service=new RestServices();
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        GetConsumerDetailsResponse response = service.getConsumerDetails(request,discomVal);
        
        if(response != null && response.getKno()!=null && accNo.equalsIgnoreCase(response.getKno())){
            accDetails = mapToAccountResponse(response);
        }
        
        System.out.println("getConsumerAccDetails  :: Ends");
        return accDetails;
    }
    
    public Account mapToAccountResponse(GetConsumerDetailsResponse response){
        Account accDetails = new Account();
        
        accDetails.setAccountNo(response.getKno());
        accDetails.setName(response.getConsumerName());
        accDetails.setOutStandingAmount(response.getAccountInfo());
        
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
        accDetails.setAddress(addr);
        
        String currentLoad = null;
        float sancLoadInKW = 0f;
        float sancLoadInKVA = 0f;
        float sancLoadInBHP = 0f;
        if(response.getSanctionedLoadInKW() != null){
            //convert to number
            sancLoadInKW = Float.parseFloat(response.getSanctionedLoadInKW());
        }
        if(response.getSanctionedLoadInKVA() != null){
            //convert to number
            sancLoadInKVA = Float.parseFloat(response.getSanctionedLoadInKVA());
        }
        if(response.getSanctionedLoadInBHP() != null){
            //convert to number
            sancLoadInBHP = Float.parseFloat(response.getSanctionedLoadInBHP());
        }
        if(sancLoadInKW > 0){
            currentLoad = String.valueOf(sancLoadInKW) + " " +"KW";
        }else if(sancLoadInKVA > 0){
            currentLoad = String.valueOf(sancLoadInKVA) + " " +"KVA";
        }else if(sancLoadInBHP > 0){
            currentLoad = String.valueOf(sancLoadInBHP) + " " +"BHP";
        }
        accDetails.setCurrentLoad(currentLoad);
        
        //todo mapping for suply type
        accDetails.setSupplyType(response.getCategory());
        
        accDetails.setMobileNo(response.getMobileNumber());
        accDetails.setEmail(response.getEmailAddress());
        accDetails.setDivision(response.getDivision());
        accDetails.setDiscom(response.getDiscom());
        
        //todo mapping for meter number
        accDetails.setMeterNum(response.getCin());
        
        System.out.println("Acc No - "+accDetails.getAccountNo());
        System.out.println("currentLoad - "+accDetails.getCurrentLoad());
        System.out.println("Mobile Number - "+accDetails.getMobileNo());
        System.out.println("Address - "+accDetails.getAddress());
        
        
        
        return accDetails;
    }
    
    public String updatePanDetails() {
        System.out.println("updatePanDetails starts");
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("panNumberErrMsg", null);
        pageFlowParam.put("updatePanValidErrMsg",null);
        pageFlowParam.put("successMsg", null);
        
        String updatePanValidErrMsg = null;
        String successMsg = null;
        String retValue = null;
        String discomVal = null;
        
        // get all the entered values
        Account actDetails = new Account();
        
        if(pageFlowParam.get("accountsDetails") != null){
            actDetails = (Account) pageFlowParam.get("accountsDetails");
        }
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        //validation check
        if(!validatePanFields(actDetails.getAccountNo())){
            return null;
        }
        
        //set request
        UpdatePanDetailsRequest request=new UpdatePanDetailsRequest();
        request.setKNumber(actDetails.getAccountNo());
        request.setPan((String) pageFlowParam.get("panNumber"));
        request.setSource("PORTAL");
        request.setOpMode("U");
        
        RestServices service=new RestServices();
        
        UpdatePanDetailsResponse response = service.updatePanNumber(request,discomVal);
        
        if(response != null){
            if ("0".equals(response.getErrorCode())) {
                successMsg = utils.getLabelValueForKey("UP_BUS_VALID_SUCCESS_MSG");
                pageFlowParam.put("successMsg", successMsg);
                retValue = "toMessage";
            }else if("1".equals(response.getErrorCode())){
                updatePanValidErrMsg =  utils.getLabelValueForKey("UP_PAN_UPDATE_ERR_MSG");
                pageFlowParam.put("updatePanValidErrMsg",updatePanValidErrMsg);
            }else{
                retValue = "toError";
            }
        } else {
            retValue = "toError";
        }
        
        if(updatePanValidErrMsg != null){
             RichPopup.PopupHints hints=new RichPopup.PopupHints();
             messagePopupBind.show(hints);
        }
        
        
        
        System.out.println("updatePanDetails ends");
        return retValue;
    }
    
    public boolean validatePanFields(String actNumber){
        String panNumberErrMsg = null;
        String actNumErrMsg = null;
        String panNumber = null;
        String oldPanNumber = null;
        
        boolean validFlag = true;
        
        if(pageFlowParam.get("panNumber") != null){
            panNumber = (String) pageFlowParam.get("panNumber");
        }
        
        if(actNumber == null || (actNumber != null && actNumber.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
        if(panNumber == null || (panNumber != null && panNumber.isEmpty())){
            validFlag = false;
            panNumberErrMsg = utils.getLabelValueForKey("UP_PAN_NUM_ERR_MSG");
            pageFlowParam.put("panNumberErrMsg", panNumberErrMsg);
        }else if(panNumber.length() != 10){
            validFlag = false;
            panNumberErrMsg = utils.getLabelValueForKey("UP_PAN_NUM_LENGTH_ERR_MSG");
            pageFlowParam.put("panNumberErrMsg", panNumberErrMsg);
        }else if(!utils.isValidPanNumber(panNumber)){
            validFlag = false;
            panNumberErrMsg = utils.getLabelValueForKey("UP_PAN_NUM_INVALID_ERR_MSG");
            pageFlowParam.put("panNumberErrMsg", panNumberErrMsg);
        }else{
            //check old pannum
            oldPanNumber = getPanNumber(actNumber);
            if(oldPanNumber != null && "error".equals(oldPanNumber)){
                validFlag = false;
                panNumberErrMsg = utils.getLabelValueForKey("UP_GET_PAN_DETAILS_FETCH_ISSUE");
                pageFlowParam.put("panNumberErrMsg", panNumberErrMsg);
            }else if(oldPanNumber.equals(panNumber)){
                validFlag = false;
                panNumberErrMsg = utils.getLabelValueForKey("UP_PAN_NUM_CHECK_ERR_MSG");
                pageFlowParam.put("panNumberErrMsg", panNumberErrMsg);
            }
        }
        
        return validFlag;
    }
    
    public String getPanNumber(String actNumber){
     String panNumber = null;
     String discomVal = null;
     
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
     
        //set request
        GetPanDetailsRequest request=new GetPanDetailsRequest();
        request.setKNumber(actNumber);
        request.setSource("Test");
        request.setOpMode("R");
        
        RestServices service=new RestServices();
        
        GetPanDetailsResponse response = service.getPanDetails(request,discomVal);
        
        if(response != null){
            if ("0".equals(response.getErrorCode())) {
                panNumber = response.getPan();
            }else if("1".equals(response.getErrorCode())){
                panNumber = null;
            }else{
                panNumber = "error";
            }
        }
     
     
     return panNumber;
    }
    

    public String cancelRequest() {
        resetPanFields();
        return null;
    }

    public void setMessagePopupBind(RichPopup messagePopupBind) {
        this.messagePopupBind = messagePopupBind;
    }

    public RichPopup getMessagePopupBind() {
        return messagePopupBind;
    }

    public void closeMessagePopup(ActionEvent actionEvent) {
        getMessagePopupBind().hide();
    }


    public String initiatePanDetails() {
        System.out.println("initiatePanDetails :: Starts");
        //set current tab
        paramSession.put("isCurrentTab", "accounts");
        resetFields();
        
        List<SelectItem> accountsList = new ArrayList<SelectItem>();
        Account accountDetails = new Account();
        
        //get linked accounts with primary acc no
        if(paramSession.get("accountsListFromSession") != null){
            accountsList =(List<SelectItem>) paramSession.get("accountsListFromSession");
        }
        System.out.println("account list size--> "+accountsList.size());
        
        pageFlowParam.put("accountsList", accountsList);
        
        //get consumer details for primary account no
        if(paramSession.get("accountsDetailsFromSession") != null){
            accountDetails =(Account) paramSession.get("accountsDetailsFromSession");
        }
        pageFlowParam.put("accountsDetails", accountDetails);
        
        System.out.println("initiatePanDetails :: Ends");
        return "toPanUpdate";
    }
    
    public void resetFields(){
        Account accountDetails = new Account();
        pageFlowParam.put("accountsDetails", accountDetails);
        resetPanFields();
        
    }
    
    public void resetPanFields(){
        pageFlowParam.put("panNumber", null);
        pageFlowParam.put("panNumberErrMsg", null);
        pageFlowParam.put("updatePanValidErrMsg",null);
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("actNumErrMsg", null);
        
    }
}
