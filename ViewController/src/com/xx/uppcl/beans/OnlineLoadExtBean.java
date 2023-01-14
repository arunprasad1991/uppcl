package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.Account;
import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.request.OnlineLoadExtChargesRequest;
import com.xx.uppcl.rest.request.OnlineLoadExtEligibltyRequest;
import com.xx.uppcl.rest.response.GetConsumerDetailsResponse;
import com.xx.uppcl.rest.response.OnlineLoadExtChargesResponse;
import com.xx.uppcl.rest.response.OnlineLoadExtEligibltyResponse;
import com.xx.uppcl.rest.services.RestServices;

import com.xx.uppcl.services.DBServices;

import com.xx.uppcl.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;

public class OnlineLoadExtBean {
    public OnlineLoadExtBean() {
    }
    
    Map paramSession= ADFContext.getCurrent().getSessionScope();
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();
    
    Utils utils = new Utils();


    public void resetFields(){
        Account accountDetails = new Account();
        pageFlowParam.put("accountsDetails", accountDetails);
        
        pageFlowParam.put("selectedNewLoad", null);
        pageFlowParam.put("loadExtCharges", null);
        pageFlowParam.put("securityFees", null);
        
        
    }
    
    public void resetLOV(){
        pageFlowParam.put("accountsList", null);
        pageFlowParam.put("currentLoadList", null);
        
    }
    
    public String checkLoadExtensionEligibility() {
        resetFields();
        resetLOV();
       String primaryAccNo = null;
        String retValue = null;
        
        paramSession.put("isCurrentTab", "consumerservices");
        
        List<SelectItem> accountsList = new ArrayList<SelectItem>();
        Account accountDetails = new Account();
       
        // get primary account no
        if(paramSession.get("loggedInAccountNo") != null){
            primaryAccNo = (String) paramSession.get("loggedInAccountNo");
        }
        
        //get linked accounts with primary acc no
        if(paramSession.get("accountsListFromSession") != null){
            accountsList =(List<SelectItem>) paramSession.get("accountsListFromSession");
        }
        System.out.println("account list size--> "+accountsList.size());
        pageFlowParam.put("accountsList", accountsList);
        
        if(paramSession.get("accountsDetailsFromSession") != null){
            accountDetails =(Account) paramSession.get("accountsDetailsFromSession");
        }
        pageFlowParam.put("accountsDetails", accountDetails);
        
        //call eligibility service
        if("true".equals(getLoadEligibility(primaryAccNo))){
               retValue = "toinitLOV";
        }else if("false".equals(getLoadEligibility(primaryAccNo))){
            retValue = "toLoadExtension";
        }else{
            retValue= "toError";
        }
        
        
        return retValue;
    }
    
    public String getLoadEligibility(String accNo){
        String discomVal = null;
        String businessValidMsg = null;
        String eligibleFlag = null;
        
        pageFlowParam.put("isEligible", false);
        pageFlowParam.put("businessValidMsg", null);
        pageFlowParam.put("businessValidCase3", false);
        
        //set request
        OnlineLoadExtEligibltyRequest request=new OnlineLoadExtEligibltyRequest();
        request.setAcctId(accNo);
        
        RestServices service=new RestServices();
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        OnlineLoadExtEligibltyResponse response = service.onlineLoadExtEligiblty(request,discomVal);
        
            if(response != null && response.getResCode() != null){
                if("0".equals(response.getResCode())){
                    //success case
                    pageFlowParam.put("isEligible", true);
                    eligibleFlag = "true";
                
                }else if("1".equals(response.getResCode())){
                    //business validation case
                    eligibleFlag = "false";
                    businessValidMsg = response.getResMsg();
                    pageFlowParam.put("businessValidMsg", businessValidMsg);
                    
                }else if("2".equals(response.getResCode())){
                    eligibleFlag = "false";
                    businessValidMsg = response.getResMsg();
                    pageFlowParam.put("businessValidMsg", businessValidMsg);
                    
                }else if("3".equals(response.getResCode())){
                    eligibleFlag = "false";
                    businessValidMsg = response.getResMsg();
                    pageFlowParam.put("businessValidMsg", businessValidMsg);
                    pageFlowParam.put("businessValidCase3", true);
                    
                }else{
                    eligibleFlag = "error";
                }
                
            }
            
        return eligibleFlag;    
    }
    
    public String getAccountListAndDetails() {
        System.out.println("getAccountListAndDetails :: Starts");
        resetLOV();
        resetFields();
        String primaryAccNo = null;
        String primaryUserName = null;
        
        
        List<SelectItem> accountsList = new ArrayList<SelectItem>();
        Account accountDetails = new Account();


        // get primary account no
        if(paramSession.get("loggedInAccountNo") != null){
            primaryAccNo = (String) paramSession.get("loggedInAccountNo");
        }
        
        //get primary user name
        if(paramSession.get("loggedInUserName") != null){
            primaryUserName = (String) paramSession.get("loggedInUserName");
        }
        
        //get linked accounts with primary acc no
        if(primaryAccNo != null){
            accountsList = getLinkedAccount(primaryAccNo);
        }
        System.out.println("account list size--> "+accountsList.size());
        
        pageFlowParam.put("accountsList", accountsList);
        
        //get consumer details for primary account no
        if(primaryAccNo != null){
            accountDetails = getConsumerAccDetails(primaryAccNo);
        }
        pageFlowParam.put("accountsDetails", accountDetails);
        
        System.out.println("getAccountListAndDetails :: Ends");
        return "continue";
    }
    
    public List<SelectItem> getLinkedAccount(String accNo){
        System.out.println("getLinkedAccount  :: Starts");
        DBServices services=new DBServices();
        List<SelectItem> accountList = new ArrayList<SelectItem>();
        
        List<String> linkedAccounts = services.getLinkedAccounts(accNo);
        
        accountList.add(new SelectItem(accNo , accNo));
        
        if(linkedAccounts.size() > 0){
            for(String acc : linkedAccounts){
                accountList.add(new SelectItem(acc , acc));
            }
        }
        
        System.out.println("getLinkedAccount  :: Ends");
        return accountList;
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
        float finalSancLoad = 0f;
        
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
            finalSancLoad = sancLoadInKW;
        }else if(sancLoadInKVA > 0){
            currentLoad = String.valueOf(sancLoadInKVA) + " " +"KVA";
            finalSancLoad = sancLoadInKVA;
        }else if(sancLoadInBHP > 0){
            currentLoad = String.valueOf(sancLoadInBHP) + " " +"BHP";
            finalSancLoad = sancLoadInBHP;
        }
        accDetails.setCurrentLoad(currentLoad);
        accDetails.setSancLoad(finalSancLoad);
        
        //todo mapping for suply type
        
        
        accDetails.setMobileNo(response.getMobileNumber());
        
        System.out.println("Acc No - "+accDetails.getAccountNo());
        System.out.println("currentLoad - "+accDetails.getCurrentLoad());
        System.out.println("Mobile Number - "+accDetails.getMobileNo());
        System.out.println("Address - "+accDetails.getAddress());
        
        
        
        return accDetails;
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

    public String initializeLOV() {
        
        pageFlowParam.put("currentLoadList", getCurrentLoadLOV());
        
        return "toLoadExtension";
    }
    
    public List<SelectItem> getCurrentLoadLOV(){
        List<SelectItem> currLoadList = new ArrayList<SelectItem>();
        
        Account accDetails = new Account();
        
        if(pageFlowParam.get("accountsDetails") != null){
            accDetails =(Account) pageFlowParam.get("accountsDetails");
            int currentLoad = Math.round(accDetails.getSancLoad());
            
            if(currentLoad <= 4){
                for(int i = currentLoad+1 ; i <= 5 ; i++){
                    currLoadList.add(new SelectItem(Float.valueOf(String.valueOf(i)) , String.valueOf(i)));
                }
                
            }else  if(currentLoad >= 5 && currentLoad <= 9){
                for(int i = currentLoad+1 ; i <= 10 ; i++){
                    currLoadList.add(new SelectItem(Float.valueOf(String.valueOf(i)) , String.valueOf(i)));
                }
            }else  if(currentLoad >= 10 && currentLoad <= 20){
                for(int i = currentLoad+1 ; i <= 21 ; i++){
                    currLoadList.add(new SelectItem(Float.valueOf(String.valueOf(i)) , String.valueOf(i)));
                }
            }
        }
        
        return currLoadList;
    }

    public void onNewLoadValueChange(ValueChangeEvent valueChangeEvent) {
        String newLoad = null;
        pageFlowParam.put("loadExtCharges", null);
        pageFlowParam.put("securityFees", null);
        pageFlowParam.put("loadErrMsg", null);
        
        System.out.println("New Load --> "+pageFlowParam.get("selectedNewLoad"));
        System.out.println("valueChangeEvent  :: New Load --> "+valueChangeEvent.getNewValue());
        //get new load value 
        if(valueChangeEvent.getNewValue() != null){
            newLoad = String.valueOf(valueChangeEvent.getNewValue());
            
            //get account id
            Account acctDetails = new Account();
            acctDetails = (Account) pageFlowParam.get("accountsDetails");
            
            getLoadExtCharges(acctDetails.getAccountNo(), newLoad);
        }
    }
    
    public void getLoadExtCharges(String accNo, String loadValue){
        String discomVal = null;
        String businessValidMsg = null;
        
        
        //set request
        OnlineLoadExtChargesRequest request=new OnlineLoadExtChargesRequest();
        request.setAcctId(accNo);
        request.setLoad(loadValue);
        
        RestServices service=new RestServices();
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        OnlineLoadExtChargesResponse response = service.onlineLoadExtCharges(request,discomVal);
        
            if(response != null && response.getResCode() != null){
                if("1".equals(response.getResCode())){
                    //success case
                    pageFlowParam.put("loadExtCharges", response.getTotalChrg());
                    pageFlowParam.put("securityFees", response.getSecurityFee());
                
                }else{
                    pageFlowParam.put("loadErrMsg", utils.getLabelValueForKey("SOMETHING_WENT_WRONG"));
                }
                
            }else{
                pageFlowParam.put("loadErrMsg", utils.getLabelValueForKey("SOMETHING_WENT_WRONG"));
            }
               
    }
    
    
    public String continueToConfirm() {
        System.out.println("continueToConfirm :: Starts");
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("loadErrMsg", null);
        pageFlowParam.put("extChargeErrMsg", null);
        pageFlowParam.put("secFeeErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
        String retVal = null;
        
        System.out.println("Selected New Load --> "+pageFlowParam.get("selectedNewLoad"));
        System.out.println("Loading Charges --> "+pageFlowParam.get("loadExtCharges"));
        System.out.println("Security Fee --> "+pageFlowParam.get("securityFees"));
        System.out.println("Reason --> "+pageFlowParam.get("comment"));
        
        // get all the entered values
        Account actDetails = new Account();
        
        if(pageFlowParam.get("accountsDetails") != null){
            actDetails = (Account) pageFlowParam.get("accountsDetails");
        }
        
        //validation check
        if(!validateLoadExtFields(actDetails.getAccountNo())){
            retVal =  null;
        }else {
            retVal= "toSummary";
        }
        
        
        System.out.println("submitNameCorrection :: Ends");
        return retVal;
    }
    
    public boolean validateLoadExtFields(String actNumber){
        String loadErrMsg = null;
        String extChargeErrMsg = null;
        String secFeeErrMsg = null;
        String reasonErrMsg = null;
        
        boolean validFlag = true;
        String actNumErrMsg = null;
        if(actNumber == null || (actNumber != null && actNumber.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
        if(pageFlowParam.get("selectedNewLoad") == null){
            validFlag = false;
            loadErrMsg = utils.getLabelValueForKey("OLE_SELECT_NEW_LOAD_ERR_MSG");
            pageFlowParam.put("loadErrMsg", loadErrMsg);
        }
        if(pageFlowParam.get("loadExtCharges") == null || (pageFlowParam.get("loadExtCharges") != null && ((String)pageFlowParam.get("loadExtCharges")).isEmpty())){
            validFlag = false;
            extChargeErrMsg = utils.getLabelValueForKey("OLE_LOAD_EXT_CHARGES_ERR_MSG");;
            pageFlowParam.put("extChargeErrMsg", extChargeErrMsg);
        }
        if(pageFlowParam.get("securityFees") == null || (pageFlowParam.get("securityFees") != null && ((String)pageFlowParam.get("securityFees")).isEmpty())){
            validFlag = false;
            secFeeErrMsg = utils.getLabelValueForKey("OLE_SECURITY_FEE_ERR_MSG");;
            pageFlowParam.put("secFeeErrMsg", secFeeErrMsg);
        }
        if(pageFlowParam.get("comment") == null || (pageFlowParam.get("comment") != null && ((String)pageFlowParam.get("comment")).isEmpty())){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("OLE_COMMENTS_ERR_MSG");;
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }
        
        return validFlag;
    }

    public String submitOnlineLoadExtension() {
        // Add event code here...
        return "toBilDesk";
    }
    
    public String findLabel(String key){
        String value = utils.getLabelValueForKey(key);
        System.out.println("Key --> "+key+ " " + "Value --> "+value); 
        return value;
    }
}
