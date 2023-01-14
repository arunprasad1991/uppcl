package com.xx.uppcl.beans;

import com.xx.uppcl.utils.Utils;
import com.xx.uppcl.pojo.Account;
import com.xx.uppcl.pojo.AttachmentPojo;
import com.xx.uppcl.pojo.ServiceRequestPojo;
import com.xx.uppcl.rest.request.AccDetailsCorrectionSRRequest;
import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.request.SendEmailRequest;
import com.xx.uppcl.rest.response.AccDetailsCorrectionSRResponse;
import com.xx.uppcl.rest.response.GetConsumerDetailsResponse;
import com.xx.uppcl.rest.response.SendEmailResponse;
import com.xx.uppcl.rest.services.RestServices;
import com.xx.uppcl.services.DBServices;

import java.io.FileNotFoundException;
import java.io.IOException;


import java.io.InputStream;
import java.io.OutputStream;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.model.DataBinder;

import oracle.stellent.ridc.protocol.ServiceResponse;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class ServiceRequestBean {
    private RichInputFile uploadFileBind;
    Utils utils = new Utils();
    private RichPopup messagePopupBind;
    private String docDownloadDestUrl = null;
    private String docViewDestUrl = null;
    private RichPopup deletePopupBind;

    public ServiceRequestBean() {
    }
    
    Map paramSession= ADFContext.getCurrent().getSessionScope();
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();

    
    public void resetFields(){
        Account accountDetails = new Account();
        pageFlowParam.put("accountsDetails", accountDetails);
        pageFlowParam.put("caseId", null);
        resetNameFields();
        resetAddressFields();
        resetBillCorrectionFields();
        resetMeterComplaintFields();
        resetConnTransferFields();
        resetCatChangeFields();
        
    }
    
    public void resetLOV(){
        pageFlowParam.put("accountsList", null);
        
        pageFlowParam.put("typeOfNameList", null);
        pageFlowParam.put("documentTypeList", null);
        pageFlowParam.put("typeOfComplaintList", null);
        pageFlowParam.put("categoryList", null);
        
    }
    
    public String getAccountListAndDetails() {
        System.out.println("getAccountListAndDetails :: Starts");
        //set current tab
        paramSession.put("isCurrentTab", "consumerservices");
        resetLOV();
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
        
        System.out.println("getAccountListAndDetails :: Ends");
        return "continue";
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


    public String initializeLOV(){
        String returnVal = null;
        String serviceType = null;
        //get service type
        if(pageFlowParam.get("selectedCorrectionType") != null){
            serviceType = (String) pageFlowParam.get("selectedCorrectionType");
            
            //route based on service type
            if("NameCorrection".equals(serviceType)){
                pageFlowParam.put("typeOfNameList", getTypeOfNameLOV());
                pageFlowParam.put("documentTypeList", getIdentificationProofLOV());
                returnVal = "toNameCorrection";
            }else if("AddressCorrection".equals(serviceType)){
                pageFlowParam.put("documentTypeList", getAddressProofLOV());
                returnVal = "toAddressCorrection";
            }else if("BillCorrection".equals(serviceType)){
                returnVal = "toBillCorrection";
            }else if("MeterRelatedComplaint".equals(serviceType)){
                pageFlowParam.put("typeOfComplaintList", getTypeOfComplaintLOV());
                returnVal = "toMeterRelatedComplaint";
            }else if("ConnTransferRequest".equals(serviceType)){
                pageFlowParam.put("documentTypeList", getAddressProofLOV());
                returnVal = "toConnecionTransferReq";
            }else if("CatChangeRequest".equals(serviceType)){
                pageFlowParam.put("categoryList", getCategoryLOV());
                returnVal = "toCatChangeReq";
            }else if ("ConnDisconnRequest".equals(serviceType)){
                pageFlowParam.put("documentTypeList", getAddressProofLOV());
                returnVal= "toConnectDisconnectReq";
            }
        }
        
        
        
        return returnVal;
    }
    
    public List<SelectItem> getTypeOfNameLOV(){
        List<SelectItem> typeNameList = new ArrayList<SelectItem>();
        
        typeNameList.add(new SelectItem("CONS_NAME", "Consumer Name"));
        typeNameList.add(new SelectItem("FATH_NAME", "Father Name"));
        typeNameList.add(new SelectItem("MOTH_NAME", "Mother Name"));
        typeNameList.add(new SelectItem("HUSB_NAME", "Husband Name"));
        
        return typeNameList;
    }
    
    public List<SelectItem> getIdentificationProofLOV(){
        List<SelectItem> docTypeList = new ArrayList<SelectItem>();
        
        docTypeList.add(new SelectItem("DL", "DL"));
        docTypeList.add(new SelectItem("House Registry", "House Registry"));
        docTypeList.add(new SelectItem("Pan Card", "Pan Card"));
        docTypeList.add(new SelectItem("Passport", "Passport"));
        docTypeList.add(new SelectItem("Rashan Card", "Rashan Card"));
        docTypeList.add(new SelectItem("UID", "UID"));
        docTypeList.add(new SelectItem("Voter Id", "Voter Id"));
        
        return docTypeList;
    }
    
    public List<SelectItem> getAddressProofLOV(){
        List<SelectItem> docTypeList = new ArrayList<SelectItem>();
        
        docTypeList.add(new SelectItem("DL", "DL"));
        docTypeList.add(new SelectItem("House Registry", "House Registry"));
        docTypeList.add(new SelectItem("Passport", "Passport"));
        docTypeList.add(new SelectItem("Rashan Card", "Rashan Card"));
        docTypeList.add(new SelectItem("Voter Id", "Voter Id"));
        
        return docTypeList;
    }

    public List<SelectItem> getTypeOfComplaintLOV(){
        List<SelectItem> complainTypeList = new ArrayList<SelectItem>();
        
        complainTypeList.add(new SelectItem("COMPLETELY STOPPED/NO DISPLAY", "Completely Stopped / No Display"));
        complainTypeList.add(new SelectItem("BURNT OUT", "Burnt Out"));
        complainTypeList.add(new SelectItem("METER FAST", "Meter Fast"));
        complainTypeList.add(new SelectItem("METER SLOW/CREEPING/STUCK UP", "Meter Slow / Creeping / Stuck Up"));
        complainTypeList.add(new SelectItem("SEAL BROKEN", "Seal Broken"));
        complainTypeList.add(new SelectItem("LOST METER", "Lost Meter"));
        
        return complainTypeList;
    }
    
    public List<SelectItem> getCategoryLOV(){
        List<SelectItem> catList = new ArrayList<SelectItem>();
        
        catList.add(new SelectItem("AGRICULTURAL", "Agricultural"));
        catList.add(new SelectItem("COMMERCIAL", "Commercial"));
        catList.add(new SelectItem("GOVERNMENT", "Government"));
        catList.add(new SelectItem("INDUSTRIAL", "Industrial"));
        catList.add(new SelectItem("RESIDENTIAL", "Residential"));
        
        return catList;
    }
    
    public void setUploadFileBind(RichInputFile uploadFileBind) {
        this.uploadFileBind = uploadFileBind;
    }

    public RichInputFile getUploadFileBind() {
        return uploadFileBind;
    }

    public void onAccountSelValueChange(ValueChangeEvent valueChangeEvent) {
        // get selected value
        String selectedValue = null;
        
        if(valueChangeEvent.getNewValue() != null){
            selectedValue = (String) valueChangeEvent.getNewValue();
        }
        
        if(selectedValue != null){
            //reset fields if already exist
             resetFields();
            // call customer service
            pageFlowParam.put("accountsDetails", getConsumerAccDetails(selectedValue));
        }
    }

    public String submitNameCorrection() {
        System.out.println("submitNameCorrection :: Starts");
        
        pageFlowParam.put("headerName", null);
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("nameCorrectionValidErrMsg", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("typeOfNameErrMsg", null);
        pageFlowParam.put("correctedNameErrMsg", null);
        pageFlowParam.put("docTypeErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
        String nameCorrectionValidErrMsg = null;
        String successMsg = null;
        String retVal = null;
               
        // get all the entered values
        Account actDetails = new Account();
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        String discomVal = null;
        
        if(pageFlowParam.get("accountsDetails") != null){
            actDetails = (Account) pageFlowParam.get("accountsDetails");
        }
        
        if(pageFlowParam.get("srDetails") != null){
            srDetails = (ServiceRequestPojo) pageFlowParam.get("srDetails");
        }
        
        System.out.println("Type Of Name --> "+srDetails.getNameType());
        System.out.println("Corrected Name --> "+srDetails.getCorrectedName());
        System.out.println("Reason --> "+srDetails.getReasonOfChange());
        
        //validation check
        if(!validateNameCorrecFields(srDetails, actDetails.getAccountNo())){
            return null;
        }
        
        //call service
        //set request
        AccDetailsCorrectionSRRequest request=new AccDetailsCorrectionSRRequest();
        request.setRequestType(utils.getLabelValueForKey("DATA_CORT_REQ"));
        request.setCorrectionType(srDetails.getNameType());
        request.setChangeReason(srDetails.getReasonOfChange()+ "$" + srDetails.getAttachmentDetails().get(0).getDocId() + "$" +srDetails.getAttachmentDetails().get(0).getDocIdentificationType());
        request.setActId(actDetails.getAccountNo());
        
        if(utils.getLabelValueForKey("CONS_NAME").equals(srDetails.getNameType())){
            request.setUName(srDetails.getCorrectedName());
        }else if(utils.getLabelValueForKey("FATH_NAME").equals(srDetails.getNameType())){
            request.setUFathName(srDetails.getCorrectedName());
        }else if(utils.getLabelValueForKey("MOTH_NAME").equals(srDetails.getNameType())){
            request.setUMothName(srDetails.getCorrectedName());
        }else if(utils.getLabelValueForKey("HUSB_NAME").equals(srDetails.getNameType())){
            request.setUHusbName(srDetails.getCorrectedName());
        }
        
        RestServices service=new RestServices();
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        AccDetailsCorrectionSRResponse response = service.accDetailsCorrectionSR(request,discomVal);
        
        if(response != null && response.getResCode() != null){
            if("1".equals(response.getResCode())){
                //success case
                successMsg= utils.getLabelValueForKey("SUCCESS_MSG_TEXT_1") + " " + response.getCaseId() + " " + utils.getLabelValueForKey("SUCCESS_MSG_TEXT_2") ;
                pageFlowParam.put("successMsg", successMsg);
                pageFlowParam.put("caseId", response.getCaseId());
                pageFlowParam.put("headerName", utils.getLabelValueForKey("NAME_COR_HEADER_TEXT"));
                
                //insert attachment in db for tracking purpose, as services do not have provision to send attachment details in request
                System.out.println("Attachment Detrails size --> "+srDetails.getAttachmentDetails().size());
                if(srDetails.getAttachmentDetails().size()> 0){
                    DBServices dbservice = new DBServices();
                    dbservice.addAttachments(srDetails.getAttachmentDetails().get(0).getDocId(),(String)paramSession.get("loggedInAccountNo") , actDetails.getAccountNo(), 
                                             srDetails.getAttachmentDetails().get(0).getDocName(), srDetails.getAttachmentDetails().get(0).getDocExtension(),
                                             srDetails.getAttachmentDetails().get(0).getDocIdentificationType(), discomVal);
                }
                
                
                //send email on success
                try {
                    sendEmailConfirmation(utils.getLabelValueForKey("NAME_CORRECTION_EMAIL_SUBJECT"), actDetails, srDetails, utils.getLabelValueForKey("NAME_CORRECTION_EMAIL_BODY_HEAD"));
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }

                retVal = "toConfirmation";
            }else if("2".equals(response.getResCode()) || "4".equals(response.getResCode())){
                //business validation case
                nameCorrectionValidErrMsg = response.getResMsg();
            }else if("0".equals(response.getResCode())){
                //error / invalid case
                nameCorrectionValidErrMsg = response.getResMsg();
            }else{
                retVal = "toError";
            }
            if(nameCorrectionValidErrMsg != null){
                 RichPopup.PopupHints hints=new RichPopup.PopupHints();
                 messagePopupBind.show(hints);
            }
            pageFlowParam.put("nameCorrectionValidErrMsg", nameCorrectionValidErrMsg);
        }else{
            retVal = "toError";
        }
        System.out.println("submitNameCorrection :: Ends");
        return retVal;
    }
    
    public boolean validateNameCorrecFields(ServiceRequestPojo srDetails , String actNumber){
        String typeOfNameErrMsg = null;
        String correctedNameErrMsg = null;
        String attachmentErrMsg = null;
        String docTypeErrMsg = null;
        String reasonErrMsg = null;
        
        
        boolean validFlag = true;
        String actNumErrMsg = null;
        if(actNumber == null || (actNumber != null && actNumber.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
        if(srDetails.getNameType() == null || (srDetails.getNameType() != null && srDetails.getNameType().isEmpty())){
            validFlag = false;
            typeOfNameErrMsg = utils.getLabelValueForKey("TYPE_OF_NAME_ERR_MSG");
            pageFlowParam.put("typeOfNameErrMsg", typeOfNameErrMsg);
        }
        if(srDetails.getCorrectedName() == null || (srDetails.getCorrectedName() != null && srDetails.getCorrectedName().isEmpty())){
            validFlag = false;
            correctedNameErrMsg = utils.getLabelValueForKey("CORRECTED_NAME_ERR_MSG");
            pageFlowParam.put("correctedNameErrMsg", correctedNameErrMsg);
        }
        if(pageFlowParam.get("docType") == null || (pageFlowParam.get("docType") != null && ((String)pageFlowParam.get("docType")).isEmpty())){
            validFlag = false;
            docTypeErrMsg = utils.getLabelValueForKey("IDENTITY_TYPE_ERR_MSG");
            pageFlowParam.put("docTypeErrMsg", docTypeErrMsg);
        }
        if(srDetails.getAttachmentDetails() == null || (srDetails.getAttachmentDetails() != null && srDetails.getAttachmentDetails().size()== 0)){
            validFlag = false;
            attachmentErrMsg = utils.getLabelValueForKey("NAME_ATTACHMENT_ERR_MSG");
            if(uploadFileBind !=null){
                uploadFileBind.resetValue();
            }
            pageFlowParam.put("attachmentErrMsg", attachmentErrMsg);
        }
        if(srDetails.getReasonOfChange() == null || (srDetails.getReasonOfChange() != null && srDetails.getReasonOfChange().isEmpty())){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("NAME_REASON_ERR_MSG");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }else if(srDetails.getReasonOfChange() != null && srDetails.getReasonOfChange().length() < 80){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("MIN_80_CHAR_ERR_MSG_");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }
        
        return validFlag;
    }
    
    public void resetNameFields(){
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        pageFlowParam.put("srDetails", srDetails);
        
        if(uploadFileBind !=null){
            uploadFileBind.resetValue();
        }
        pageFlowParam.put("docType", null);
        pageFlowParam.put("documentId",null);
        pageFlowParam.put("documentContentId", null);
        pageFlowParam.put("docContType", null);
        pageFlowParam.put("docFileName", null);
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("nameCorrectionValidErrMsg", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("typeOfNameErrMsg", null);
        pageFlowParam.put("correctedNameErrMsg", null);
        pageFlowParam.put("docTypeErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
    }
    
    public String submitAddressCorrection() {
        pageFlowParam.put("headerName", null);
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("addressCorrectionValidErrMsg", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("houseNoErrMsg", null);
        pageFlowParam.put("cityErrMsg", null);
        pageFlowParam.put("buildNameErrMsg", null);
        pageFlowParam.put("districtErrMsg", null);
        pageFlowParam.put("areaNameErrMsg", null);
        pageFlowParam.put("pinErrMsg", null);
        pageFlowParam.put("docTypeErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
        String addressCorrectionValidErrMsg = null;
        String successMsg = null;
        String retVal = null;
               
        // get all the entered values
        Account actDetails = new Account();
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        String discomVal = null;
        
        if(pageFlowParam.get("accountsDetails") != null){
            actDetails = (Account) pageFlowParam.get("accountsDetails");
        }
        
        if(pageFlowParam.get("srDetails") != null){
            srDetails = (ServiceRequestPojo) pageFlowParam.get("srDetails");
        }
        
        //validation check
        if(!validateAddressCorrecFields(srDetails, actDetails.getAccountNo())){
            return null;
        }
        
        //call service
        //set request
        AccDetailsCorrectionSRRequest request=new AccDetailsCorrectionSRRequest();
        request.setRequestType(utils.getLabelValueForKey("DATA_CORT_REQ"));
        request.setCorrectionType("ADDRS");
        request.setChangeReason(srDetails.getReasonOfChange()+ "$" + srDetails.getAttachmentDetails().get(0).getDocId() + "$" +srDetails.getAttachmentDetails().get(0).getDocIdentificationType());
        request.setActId(actDetails.getAccountNo());
        request.setUHouseNo(srDetails.getHouseNo());
        request.setUCity(srDetails.getCity());
        request.setUBuildName(srDetails.getBuildingName());
        request.setUAreaName(srDetails.getAreaName());
        request.setUDistrict(srDetails.getDistrict());
        request.setUPin(srDetails.getPin());
        
        RestServices service=new RestServices();
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        AccDetailsCorrectionSRResponse response = service.accDetailsCorrectionSR(request,discomVal);
        
        if(response != null && response.getResCode() != null){
            if("1".equals(response.getResCode())){
                //success case
                successMsg= utils.getLabelValueForKey("SUCCESS_MSG_TEXT_1") + " " + response.getCaseId() + " " + utils.getLabelValueForKey("SUCCESS_MSG_TEXT_2") ;
                pageFlowParam.put("successMsg", successMsg);
                pageFlowParam.put("caseId", response.getCaseId());
                pageFlowParam.put("headerName", utils.getLabelValueForKey("ADDR_COR_HEADER_TEXT"));
                
                //insert attachment in db for tracking purpose, as services do not have provision to send attachment details in request
                if(srDetails.getAttachmentDetails().size()> 0){
                    DBServices dbservice = new DBServices();
                    dbservice.addAttachments(srDetails.getAttachmentDetails().get(0).getDocId(),(String)paramSession.get("loggedInAccountNo") , actDetails.getAccountNo(), 
                                             srDetails.getAttachmentDetails().get(0).getDocName(), srDetails.getAttachmentDetails().get(0).getDocExtension(),
                                             srDetails.getAttachmentDetails().get(0).getDocIdentificationType(), discomVal);
                }
                
                
                //send email on success
                try {
                    sendEmailConfirmation(utils.getLabelValueForKey("ADDR_CORRECTION_EMAIL_SUBJECT"), actDetails, srDetails, utils.getLabelValueForKey("ADDR_CORRECTION_EMAIL_BODY_HEAD"));
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }

                retVal = "toConfirmation";
            }else if("2".equals(response.getResCode()) || "4".equals(response.getResCode())){
                //business validation case
                addressCorrectionValidErrMsg = response.getResMsg();
            }else if("0".equals(response.getResCode())){
                //error / invalid case
                addressCorrectionValidErrMsg = response.getResMsg();
            }else{
                retVal = "toError";
            }
            
            if(addressCorrectionValidErrMsg != null){
                RichPopup.PopupHints hints=new RichPopup.PopupHints();
                messagePopupBind.show(hints);
//                utils.showInfoMessage(addressCorrectionValidErrMsg);
            }
            pageFlowParam.put("addressCorrectionValidErrMsg", addressCorrectionValidErrMsg);
        }else{
            retVal = "toError";
        }
        
        return retVal;
    }
    
    public boolean validateAddressCorrecFields(ServiceRequestPojo srDetails, String actNumber){
        String houseNoErrMsg = null;
        String cityErrMsg = null;
        String buildNameErrMsg = null;
        String districtErrMsg = null;
        String areaNameErrMsg = null;
        String pinErrMsg = null;
        String docTypeErrMsg = null;
        String attachmentErrMsg = null;
        String reasonErrMsg = null;
        
        boolean validFlag = true;
        String actNumErrMsg = null;
        if(actNumber == null || (actNumber != null && actNumber.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
        if(srDetails.getHouseNo() == null || (srDetails.getHouseNo() != null && srDetails.getHouseNo().isEmpty())){
            validFlag = false;
            houseNoErrMsg = utils.getLabelValueForKey("HOUSE_NO_ERR_MSG");
            pageFlowParam.put("houseNoErrMsg", houseNoErrMsg);
        }
        if(srDetails.getCity() == null || (srDetails.getCity() != null && srDetails.getCity().isEmpty())){
            validFlag = false;
            cityErrMsg = utils.getLabelValueForKey("CITY_ERR_MSG");
            pageFlowParam.put("cityErrMsg", cityErrMsg);
        }
        if(srDetails.getBuildingName() == null || (srDetails.getBuildingName() != null && srDetails.getBuildingName().isEmpty())){
            validFlag = false;
            buildNameErrMsg = utils.getLabelValueForKey("BUILDING_NAME_ERR_MSG");
            pageFlowParam.put("buildNameErrMsg", buildNameErrMsg);
        }
        if(srDetails.getDistrict() == null || (srDetails.getDistrict() != null && srDetails.getDistrict().isEmpty())){
            validFlag = false;
            districtErrMsg = utils.getLabelValueForKey("DISTRICT_ERR_MSG");
            pageFlowParam.put("districtErrMsg", districtErrMsg);
        }
        if(srDetails.getAreaName() == null || (srDetails.getAreaName() != null && srDetails.getAreaName().isEmpty())){
            validFlag = false;
            areaNameErrMsg = utils.getLabelValueForKey("AREA_NAME_ERR_MSG");
            pageFlowParam.put("areaNameErrMsg", areaNameErrMsg);
        }
        if(srDetails.getPin() == null || (srDetails.getPin() != null && srDetails.getPin().isEmpty())){
            validFlag = false;
            pinErrMsg = utils.getLabelValueForKey("PIN_ERR_MSG");
            pageFlowParam.put("pinErrMsg", pinErrMsg);
        }
        if(pageFlowParam.get("docType") == null || (pageFlowParam.get("docType") != null && ((String)pageFlowParam.get("docType")).isEmpty())){
            validFlag = false;
            docTypeErrMsg = utils.getLabelValueForKey("ADDRESS_TYPE_ERROR_MSG");
            pageFlowParam.put("docTypeErrMsg", docTypeErrMsg);
        }
        if(srDetails.getAttachmentDetails() == null || (srDetails.getAttachmentDetails() != null && srDetails.getAttachmentDetails().size()== 0)){
            validFlag = false;
            attachmentErrMsg = utils.getLabelValueForKey("ADDR_ATTACHMENT_ERR_MSG");
            if(uploadFileBind !=null){
                uploadFileBind.resetValue();
            }
            pageFlowParam.put("attachmentErrMsg", attachmentErrMsg);
        }
        if(srDetails.getReasonOfChange() == null || (srDetails.getReasonOfChange() != null && srDetails.getReasonOfChange().isEmpty())){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("ADDR_REASON_ERR_MSG");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }else if(srDetails.getReasonOfChange() != null && srDetails.getReasonOfChange().length() < 80){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("MIN_80_CHAR_ERR_MSG_");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }
        
        return validFlag;
    }

    public void resetAddressFields(){
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        pageFlowParam.put("srDetails", srDetails);
        
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("addressCorrectionValidErrMsg", null);
        
        if(uploadFileBind !=null){
            uploadFileBind.resetValue();
        }
        pageFlowParam.put("docType", null);
        pageFlowParam.put("documentId", null);
        pageFlowParam.put("documentContentId", null);
        pageFlowParam.put("docContType", null);
        pageFlowParam.put("docFileName", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("houseNoErrMsg", null);
        pageFlowParam.put("cityErrMsg", null);
        pageFlowParam.put("buildNameErrMsg", null);
        pageFlowParam.put("districtErrMsg", null);
        pageFlowParam.put("areaNameErrMsg", null);
        pageFlowParam.put("pinErrMsg", null);
        pageFlowParam.put("docTypeErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
    }
    
    public String submitBillCorrection() {
        pageFlowParam.put("headerName", null);
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("billCorrectionValidErrMsg", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("disputeBillNoErrMsg", null);
        pageFlowParam.put("meterReadKWhErrMsg", null);
        pageFlowParam.put("meterReadKVAhErrMsg", null);
        pageFlowParam.put("demandErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
        String billCorrectionValidErrMsg = null;
        String successMsg = null;
        String retVal = null;
               
        // get all the entered values
        Account actDetails = new Account();
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        String discomVal = null;
        
        if(pageFlowParam.get("accountsDetails") != null){
            actDetails = (Account) pageFlowParam.get("accountsDetails");
        }
        
        if(pageFlowParam.get("srDetails") != null){
            srDetails = (ServiceRequestPojo) pageFlowParam.get("srDetails");
        }
        
        //validation check
        if(!validateBillCorrecFields(srDetails, actDetails.getAccountNo())){
            return null;
        }
        
        //call service
        //set request
        AccDetailsCorrectionSRRequest request=new AccDetailsCorrectionSRRequest();
        request.setRequestType("BR_REQ");
//        request.setChangeReason(srDetails.getReasonOfChange()+ "$" + srDetails.getAttachmentDetails().get(0).getDocId() + "$" +srDetails.getAttachmentDetails().get(0).getDocIdentificationType());
        request.setChangeReason(srDetails.getReasonOfChange());
        request.setBillId(srDetails.getDisputedBillNo());
        request.setUName(srDetails.getDemand());
        request.setUFathName(srDetails.getMeterReadKWh());
        request.setUMothName(srDetails.getMeterReadKVAh());
        
        RestServices service=new RestServices();
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        AccDetailsCorrectionSRResponse response = service.accDetailsCorrectionSR(request,discomVal);
        
        if(response != null && response.getResCode() != null){
            if("1".equals(response.getResCode())){
                //success case
                successMsg= utils.getLabelValueForKey("SUCCESS_MSG_TEXT_1") + " " + response.getCaseId() + " " + utils.getLabelValueForKey("SUCCESS_MSG_TEXT_2") ;
                pageFlowParam.put("successMsg", successMsg);
                pageFlowParam.put("caseId", response.getCaseId());
                pageFlowParam.put("headerName", utils.getLabelValueForKey("BILL_COR_HEADER_TEXT"));
                
                //insert attachment in db for tracking purpose, as services do not have provision to send attachment details in request
//                if(srDetails.getAttachmentDetails().size()> 0){
//                    DBServices dbservice = new DBServices();
//                    dbservice.addAttachments(srDetails.getAttachmentDetails().get(0).getDocId(),(String)paramSession.get("loggedInAccountNo") , actDetails.getAccountNo(), 
//                                             srDetails.getAttachmentDetails().get(0).getDocName(), srDetails.getAttachmentDetails().get(0).getDocExtension(),
//                                             srDetails.getAttachmentDetails().get(0).getDocIdentificationType());
//                }
                
                //send email on success
                try {
                    sendEmailConfirmation(utils.getLabelValueForKey("BILL_CORRECTION_EMAIL_SUBJECT"), actDetails, srDetails, utils.getLabelValueForKey("BILL_CORRECTION_EMAIL_BODY_HEAD"));
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }

                retVal = "toConfirmation";
            }else if("2".equals(response.getResCode()) || "4".equals(response.getResCode())){
                //business validation case
                billCorrectionValidErrMsg = response.getResMsg();
            }else if("0".equals(response.getResCode())){
                //error / invalid case
                billCorrectionValidErrMsg = response.getResMsg();
            }else{
                retVal = "toError";
            }
            if(billCorrectionValidErrMsg != null){
                RichPopup.PopupHints hints=new RichPopup.PopupHints();
                messagePopupBind.show(hints);
//                utils.showInfoMessage(billCorrectionValidErrMsg);
            }
            pageFlowParam.put("billCorrectionValidErrMsg", billCorrectionValidErrMsg);
        }else{
            retVal = "toError";
        }
        
        return retVal;
    }
    
    public boolean validateBillCorrecFields(ServiceRequestPojo srDetails, String actNumber){
        String disputeBillNoErrMsg = null;
        String meterReadKWhErrMsg = null;
        String meterReadKVAhErrMsg = null;
        String demandErrMsg = null;
        String attachmentErrMsg = null;
        String reasonErrMsg = null;
        
        boolean validFlag = true;
        String actNumErrMsg = null;
        if(actNumber == null || (actNumber != null && actNumber.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
        if(srDetails.getDisputedBillNo() == null || (srDetails.getDisputedBillNo() != null && srDetails.getDisputedBillNo().isEmpty())){
            validFlag = false;
            disputeBillNoErrMsg = utils.getLabelValueForKey("DISP_BILL_NO_ERR_MSG");
            pageFlowParam.put("disputeBillNoErrMsg", disputeBillNoErrMsg);
        }
        if(srDetails.getMeterReadKWh() == null || (srDetails.getMeterReadKWh() != null && srDetails.getMeterReadKWh().isEmpty())){
            validFlag = false;
            meterReadKWhErrMsg = utils.getLabelValueForKey("METER_READ_KWH_ERR_MSG");
            pageFlowParam.put("meterReadKWhErrMsg", meterReadKWhErrMsg);
        }else if(!utils.checkNumeric(srDetails.getMeterReadKWh())){
            validFlag = false;
            meterReadKWhErrMsg = utils.getLabelValueForKey("METER_NUMERIC_ERR_MSG");
            pageFlowParam.put("meterReadKWhErrMsg", meterReadKWhErrMsg);
        }
        
        
        if(srDetails.getMeterReadKVAh() == null || (srDetails.getMeterReadKVAh() != null && srDetails.getMeterReadKVAh().isEmpty())){
            validFlag = false;
            meterReadKVAhErrMsg = utils.getLabelValueForKey("METER_READ_KVAH_ERR_MSG");
            pageFlowParam.put("meterReadKVAhErrMsg", meterReadKVAhErrMsg);
        }else if(!utils.checkNumeric(srDetails.getMeterReadKVAh())){
            validFlag = false;
            meterReadKVAhErrMsg = utils.getLabelValueForKey("METER_NUMERIC_ERR_MSG");
            pageFlowParam.put("meterReadKVAhErrMsg", meterReadKVAhErrMsg);
        }
        
        
        if(srDetails.getDemand() == null || (srDetails.getDemand() != null && srDetails.getDemand().isEmpty())){
            validFlag = false;
            demandErrMsg = utils.getLabelValueForKey("BILL_DEMAND_ERR_MSG");
            pageFlowParam.put("demandErrMsg", demandErrMsg);
        }else if(!utils.checkNumeric(srDetails.getDemand())){
            validFlag = false;
            demandErrMsg = utils.getLabelValueForKey("METER_NUMERIC_ERR_MSG");
            pageFlowParam.put("demandErrMsg", demandErrMsg);
        }
        
        
//        if(srDetails.getAttachmentDetails() == null || (srDetails.getAttachmentDetails() != null && srDetails.getAttachmentDetails().size()== 0)){
//            validFlag = false;
//            attachmentErrMsg = utils.getLabelValueForKey("BILL_ATTACHMENT_ERR_MSG");
//            if(uploadFileBind !=null){
//                uploadFileBind.resetValue();
//            }
//            pageFlowParam.put("attachmentErrMsg", attachmentErrMsg);
//        }
        if(srDetails.getReasonOfChange() == null || (srDetails.getReasonOfChange() != null && srDetails.getReasonOfChange().isEmpty())){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("BILL_REASON_ERR_MSG");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }else if(srDetails.getReasonOfChange() != null && srDetails.getReasonOfChange().length() < 80){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("MIN_80_CHAR_ERR_MSG_");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }
        
        return validFlag;
    }

    public void resetBillCorrectionFields(){
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        pageFlowParam.put("srDetails", srDetails);
        
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("billCorrectionValidErrMsg", null);
        
        if(uploadFileBind !=null){
            uploadFileBind.resetValue();
        }
        
        pageFlowParam.put("documentId", null);
        pageFlowParam.put("documentContentId", null);
        pageFlowParam.put("docContType", null);
        pageFlowParam.put("docFileName", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("disputeBillNoErrMsg", null);
        pageFlowParam.put("meterReadKWhErrMsg", null);
        pageFlowParam.put("meterReadKVAhErrMsg", null);
        pageFlowParam.put("demandErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
    }
    
    public String submitMeterRelComplaint() {
        pageFlowParam.put("headerName", null);
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("meterComplaintValidErrMsg", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("typeOfComplaintErrMsg", null);
        pageFlowParam.put("meterSerialNoErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        
        String meterComplaintValidErrMsg = null;
        String successMsg = null;
        String retVal = null;
               
        // get all the entered values
        Account actDetails = new Account();
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        String discomVal = null;
        
        if(pageFlowParam.get("accountsDetails") != null){
            actDetails = (Account) pageFlowParam.get("accountsDetails");
        }
        
        if(pageFlowParam.get("srDetails") != null){
            srDetails = (ServiceRequestPojo) pageFlowParam.get("srDetails");
        }
        
        //validation check
        if(!validateBillCompFields(srDetails, actDetails.getAccountNo())){
            return null;
        }
        
        //call service
        //set request
        AccDetailsCorrectionSRRequest request=new AccDetailsCorrectionSRRequest();
        request.setRequestType("METER_COMP_REQ");
        request.setActId(actDetails.getAccountNo());
        request.setSerialNbr(srDetails.getMeterSerialNumber());
        request.setMeterComplaintType(srDetails.getMeterComplaintType());
        
        RestServices service=new RestServices();
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        AccDetailsCorrectionSRResponse response = service.accDetailsCorrectionSR(request,discomVal);
        
        if(response != null && response.getResCode() != null){
            if("1".equals(response.getResCode())){
                //success case
                successMsg= utils.getLabelValueForKey("SUCCESS_MSG_TEXT_1") + " " + response.getCaseId() + " " + utils.getLabelValueForKey("SUCCESS_MSG_TEXT_2") ;
                pageFlowParam.put("successMsg", successMsg);
                pageFlowParam.put("caseId", response.getCaseId());
                pageFlowParam.put("headerName", utils.getLabelValueForKey("BILL_COMP_HEADER_TEXT"));
                
                //insert attachment in db for tracking purpose, as services do not have provision to send attachment details in request
                if(srDetails.getAttachmentDetails().size()> 0){
                    DBServices dbservice = new DBServices();
                    dbservice.addAttachments(srDetails.getAttachmentDetails().get(0).getDocId(),(String)paramSession.get("loggedInAccountNo") , actDetails.getAccountNo(), 
                                             srDetails.getAttachmentDetails().get(0).getDocName(), srDetails.getAttachmentDetails().get(0).getDocExtension(),
                                             srDetails.getAttachmentDetails().get(0).getDocIdentificationType(), discomVal);
                }
                
                //send email on success
                try {
                    sendEmailConfirmation(utils.getLabelValueForKey("BILL_COMP_EMAIL_SUBJECT"), actDetails, srDetails, utils.getLabelValueForKey("BILL_COMP_EMAIL_BODY_HEAD"));
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }

                retVal = "toConfirmation";
            }else if("2".equals(response.getResCode()) || "4".equals(response.getResCode())){
                //business validation case
                meterComplaintValidErrMsg = response.getResMsg();
            }else if("0".equals(response.getResCode())){
                //error / invalid case
                meterComplaintValidErrMsg = response.getResMsg();
            }else{
                retVal = "toError";
            }
            if(meterComplaintValidErrMsg != null){
                RichPopup.PopupHints hints=new RichPopup.PopupHints();
                messagePopupBind.show(hints);
//                utils.showInfoMessage(meterComplaintValidErrMsg);
            }
            pageFlowParam.put("meterComplaintValidErrMsg", meterComplaintValidErrMsg);
        }else{
            retVal = "toError";
        }
        
        return retVal;
    }
    
    public boolean validateBillCompFields(ServiceRequestPojo srDetails, String actNumber){
        String typeOfComplaintErrMsg = null;
        String meterSerialNoErrMsg = null;
        String attachmentErrMsg = null;
        
        boolean validFlag = true;
        String actNumErrMsg = null;
        if(actNumber == null || (actNumber != null && actNumber.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
        if(srDetails.getMeterComplaintType() == null || (srDetails.getMeterComplaintType() != null && srDetails.getMeterComplaintType().isEmpty())){
            validFlag = false;
            typeOfComplaintErrMsg = utils.getLabelValueForKey("MRC_TYPE_OF_COMP_ERR_MSG");
            pageFlowParam.put("typeOfComplaintErrMsg", typeOfComplaintErrMsg);
        }
        if(srDetails.getMeterSerialNumber() == null || (srDetails.getMeterSerialNumber() != null && srDetails.getMeterSerialNumber().isEmpty())){
            validFlag = false;
            meterSerialNoErrMsg = utils.getLabelValueForKey("MRC_METER_SERIAL_NO_ERR_MSG");
            pageFlowParam.put("meterSerialNoErrMsg", meterSerialNoErrMsg);
        }
        if(srDetails.getAttachmentDetails() == null || (srDetails.getAttachmentDetails() != null && srDetails.getAttachmentDetails().size()== 0)){
            validFlag = false;
            attachmentErrMsg = utils.getLabelValueForKey("MRC_ATTACHMENT_ERR_MSG");
            if(uploadFileBind !=null){
                uploadFileBind.resetValue();
            }
            pageFlowParam.put("attachmentErrMsg", attachmentErrMsg);
        }
        
        return validFlag;
    }
    
    public void resetMeterComplaintFields(){
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        pageFlowParam.put("srDetails", srDetails);
        
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("meterComplaintValidErrMsg", null);
        
        if(uploadFileBind !=null){
            uploadFileBind.resetValue();
        }
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("typeOfComplaintErrMsg", null);
        pageFlowParam.put("meterSerialNoErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        
    }
    
    public String submitConnectionTransReq() {
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("connTransferValidErrMsg", null);
        pageFlowParam.put("businessErrMsg", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("applicntNameErrMsg", null);
        pageFlowParam.put("fathAppcntNameErrMsg", null);
        pageFlowParam.put("docTypeErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
        String retVal = null;
        String successMsg = null;
        String connTransferValidErrMsg = null;
               
        // get all the entered values
        Account actDetails = new Account();
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        String discomVal = null;
        
        if(pageFlowParam.get("accountsDetails") != null){
            actDetails = (Account) pageFlowParam.get("accountsDetails");
        }
        
        if(pageFlowParam.get("srDetails") != null){
            srDetails = (ServiceRequestPojo) pageFlowParam.get("srDetails");
        }
        
        //validation check
        if(!validateConnTransferFields(srDetails, actDetails.getAccountNo())){
            return null;
        }
        
        
        //call service
        //set request
        AccDetailsCorrectionSRRequest request=new AccDetailsCorrectionSRRequest();
        request.setRequestType("CASE_INIT_COT");
        request.setActId(actDetails.getAccountNo());
        request.setUName(srDetails.getApplicantName());
        request.setUFathName(srDetails.getApplicantFathName());
        request.setCotReason(srDetails.getReasonOfChange());
        
        RestServices service=new RestServices();
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        AccDetailsCorrectionSRResponse response = service.accDetailsCorrectionSR(request,discomVal);
        
        if(response != null && response.getResCode() != null){
            if("1".equals(response.getResCode())){
                //success case
                successMsg= utils.getLabelValueForKey("SUCCESS_MSG_TEXT_1") + " " + response.getCaseId() + " " + utils.getLabelValueForKey("SUCCESS_MSG_TEXT_2") ;
                pageFlowParam.put("successMsg", successMsg);
                pageFlowParam.put("caseId", response.getCaseId());
                pageFlowParam.put("headerName", utils.getLabelValueForKey("CT_HEADER_TEXT"));
                
                //insert attachment in db for tracking purpose, as services do not have provision to send attachment details in request
                if(srDetails.getAttachmentDetails().size()> 0){
                    DBServices dbservice = new DBServices();
                    dbservice.addAttachments(srDetails.getAttachmentDetails().get(0).getDocId(),(String)paramSession.get("loggedInAccountNo") , actDetails.getAccountNo(), 
                                             srDetails.getAttachmentDetails().get(0).getDocName(), srDetails.getAttachmentDetails().get(0).getDocExtension(),
                                             srDetails.getAttachmentDetails().get(0).getDocIdentificationType(), discomVal);
                }
                
                //send email on success
                try {
                    sendEmailConfirmation(utils.getLabelValueForKey("CT_EMAIL_SUBJECT"), actDetails, srDetails, utils.getLabelValueForKey("CT_EMAIL_BODY_HEAD"));
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }

                retVal = "toConfirmation";
            }else if("2".equals(response.getResCode()) || "4".equals(response.getResCode())){
                //business validation case
                connTransferValidErrMsg = response.getResMsg();
            }else if("0".equals(response.getResCode())){
                //error / invalid case
                connTransferValidErrMsg = response.getResMsg();
            }else{
                retVal = "toError";
            }
            if(connTransferValidErrMsg != null){
                RichPopup.PopupHints hints=new RichPopup.PopupHints();
                messagePopupBind.show(hints);
        //                utils.showInfoMessage(meterComplaintValidErrMsg);
            }
            pageFlowParam.put("connTransferValidErrMsg", connTransferValidErrMsg);
        }else{
            retVal = "toError";
        }
        
        return retVal;
    }
    
    public boolean validateConnTransferFields(ServiceRequestPojo srDetails, String actNumber){
        String applicntNameErrMsg = null;
        String fathAppcntNameErrMsg = null;
        String docTypeErrMsg = null;
        String attachmentErrMsg = null;
        String reasonErrMsg = null;
        
        boolean validFlag = true;
        String actNumErrMsg = null;
        if(actNumber == null || (actNumber != null && actNumber.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
        if(srDetails.getApplicantName() == null || (srDetails.getApplicantName() != null && srDetails.getApplicantName().isEmpty())){
            validFlag = false;
            applicntNameErrMsg = utils.getLabelValueForKey("CT_APPLICANT_NAME_ERR_MSG");
            pageFlowParam.put("applicntNameErrMsg", applicntNameErrMsg);
        }
        if(srDetails.getApplicantFathName() == null || (srDetails.getApplicantFathName() != null && srDetails.getApplicantFathName().isEmpty())){
            validFlag = false;
            fathAppcntNameErrMsg = utils.getLabelValueForKey("CT_APPLICANT_FATH_NAME_ERR_MSG");
            pageFlowParam.put("fathAppcntNameErrMsg", fathAppcntNameErrMsg);
        }
        if(pageFlowParam.get("docType") == null || (pageFlowParam.get("docType") != null && ((String)pageFlowParam.get("docType")).isEmpty())){
            validFlag = false;
            docTypeErrMsg = utils.getLabelValueForKey("CT_ADDR_PROOF_TYPE_ERR_MSG");
            pageFlowParam.put("docTypeErrMsg", docTypeErrMsg);
        }
        if(srDetails.getAttachmentDetails() == null || (srDetails.getAttachmentDetails() != null && srDetails.getAttachmentDetails().size()== 0)){
            validFlag = false;
            attachmentErrMsg = utils.getLabelValueForKey("CT_ATTACHMENT_ERR_MSG");
            if(uploadFileBind !=null){
                uploadFileBind.resetValue();
            }
            pageFlowParam.put("attachmentErrMsg", attachmentErrMsg);
        }
        if(srDetails.getReasonOfChange() == null || (srDetails.getReasonOfChange() != null && srDetails.getReasonOfChange().isEmpty())){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("CT_REASON_FOR_CHANGE_ERR_MSG");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }else if(srDetails.getReasonOfChange() != null && srDetails.getReasonOfChange().length() < 80){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("MIN_80_CHAR_ERR_MSG_");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }
        
        return validFlag;
    }
    
    public void resetConnTransferFields(){
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        pageFlowParam.put("srDetails", srDetails);
        
        pageFlowParam.put("docType", null);
        
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("connTransferValidErrMsg", null);
        pageFlowParam.put("businessErrMsg", null);
        
        if(uploadFileBind !=null){
            uploadFileBind.resetValue();
        }
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("applicntNameErrMsg", null);
        pageFlowParam.put("fathAppcntNameErrMsg", null);
        pageFlowParam.put("docTypeErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
    }
    
    public String submitCategoryChangeReq() {
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("catChangeValidErrMsg", null);
        pageFlowParam.put("businessErrMsg", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("catTypeErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
        String retVal = null;
               
        // get all the entered values
        Account actDetails = new Account();
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        
        if(pageFlowParam.get("accountsDetails") != null){
            actDetails = (Account) pageFlowParam.get("accountsDetails");
        }
        
        if(pageFlowParam.get("srDetails") != null){
            srDetails = (ServiceRequestPojo) pageFlowParam.get("srDetails");
        }
        
        //validation check
        if(!validateCatChangeFields(srDetails, actDetails.getAccountNo())){
            return null;
        }else{
            retVal = "toPaymentProcess";
            pageFlowParam.put("paymentType", "CategoryChangeRequest");
        }
        
        return retVal;
    }
    
    public boolean validateCatChangeFields(ServiceRequestPojo srDetails, String actNumber){
        String catTypeErrMsg = null;
        String reasonErrMsg = null;
        
        boolean validFlag = true;
        String actNumErrMsg = null;
        if(actNumber == null || (actNumber != null && actNumber.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
        if(srDetails.getCategoryType() == null || (srDetails.getCategoryType() != null && srDetails.getCategoryType().isEmpty())){
            validFlag = false;
            catTypeErrMsg = utils.getLabelValueForKey("CC_NEW_CAT_ERR_MSG");
            pageFlowParam.put("catTypeErrMsg", catTypeErrMsg);
        }
        if(srDetails.getReasonOfChange() == null || (srDetails.getReasonOfChange() != null && srDetails.getReasonOfChange().isEmpty())){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("CC_REASON_FOR_CHANGE_ERR_MSG");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }else if(srDetails.getReasonOfChange() != null && srDetails.getReasonOfChange().length() < 80){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("MIN_80_CHAR_ERR_MSG_");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }
        
        return validFlag;
    }

    public void resetCatChangeFields(){
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        pageFlowParam.put("srDetails", srDetails);
        
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("catChangeValidErrMsg", null);
        pageFlowParam.put("businessErrMsg", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("catTypeErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
        pageFlowParam.put("paymentType", null);
        
    }
    
    public String submitConnDisconnctionReq() {
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("connDissValidErrMsg", null);
        pageFlowParam.put("businessErrMsg", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("docTypeErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
        String retVal = null;
               
        // get all the entered values
        Account actDetails = new Account();
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        
        if(pageFlowParam.get("accountsDetails") != null){
            actDetails = (Account) pageFlowParam.get("accountsDetails");
        }
        
        if(pageFlowParam.get("srDetails") != null){
            srDetails = (ServiceRequestPojo) pageFlowParam.get("srDetails");
//            if(srDetails.getAttachmentDetails() != null && srDetails.getAttachmentDetails().size()> 0){
//                srDetails.setReasonOfChange(srDetails.getReasonOfChange()+ "$" + srDetails.getAttachmentDetails().get(0).getDocId() + "$" +srDetails.getAttachmentDetails().get(0).getDocIdentificationType());
//           }
//            //update sr details
//            pageFlowParam.put("srDetails", srDetails);
        }
        
        //validation check
        if(!validateConnDiscconFields(srDetails, actDetails.getAccountNo())){
            return null;
        }else{
            retVal = "toPaymentProcess";
            pageFlowParam.put("paymentType", "ConnectionDisconnection");
        }
        
        return retVal;
    }
    
    public boolean validateConnDiscconFields(ServiceRequestPojo srDetails, String actNumber){
       String attachmentErrMsg = null;
       String reasonErrMsg = null;
       String docTypeErrMsg = null;
        
        boolean validFlag = true;
        String actNumErrMsg = null;
        if(actNumber == null || (actNumber != null && actNumber.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
//        if(pageFlowParam.get("docType") == null || (pageFlowParam.get("docType") != null && ((String)pageFlowParam.get("docType")).isEmpty())){
//            validFlag = false;
//            docTypeErrMsg = utils.getLabelValueForKey("CD_ADDR_PROOF_TYPE_ERR_MSG");
//            pageFlowParam.put("docTypeErrMsg", docTypeErrMsg);
//        }
//        if(srDetails.getAttachmentDetails() == null || (srDetails.getAttachmentDetails() != null && srDetails.getAttachmentDetails().size()== 0)){
//            validFlag = false;
//            attachmentErrMsg = utils.getLabelValueForKey("CD_ATTACHMENT_ERR_MSG");
//            if(uploadFileBind !=null){
//                uploadFileBind.resetValue();
//            }
//            pageFlowParam.put("attachmentErrMsg", attachmentErrMsg);
//        }
        if(srDetails.getReasonOfChange() == null || (srDetails.getReasonOfChange() != null && srDetails.getReasonOfChange().isEmpty())){
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("CD_REASON_FOR_CHANGE_ERR_MSG");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }
        
        return validFlag;
    }

    public void resetConnDissconFields(){
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        pageFlowParam.put("srDetails", srDetails);
        
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("connDissValidErrMsg", null);
        pageFlowParam.put("businessErrMsg", null);
        
        if(uploadFileBind !=null){
            uploadFileBind.resetValue();
        }
        
        pageFlowParam.put("documentId", null);
        pageFlowParam.put("documentContentId", null);
        pageFlowParam.put("docContType", null);
        pageFlowParam.put("docFileName", null);
        
        pageFlowParam.put("actNumErrMsg", null);
        pageFlowParam.put("docTypeErrMsg", null);
        pageFlowParam.put("attachmentErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);
        
        pageFlowParam.put("paymentType", null);
        
    }
    
    public String cancelRequest() {
        String serviceType = null;
        if(pageFlowParam.get("selectedCorrectionType") != null){
            serviceType = (String) pageFlowParam.get("selectedCorrectionType");
            
            if("NameCorrection".equals(serviceType)){
                resetNameFields();
            }else if("AddressCorrection".equals(serviceType)){
                resetAddressFields();
            }else if("BillCorrection".equals(serviceType)){
                resetBillCorrectionFields();
            }else if("MeterRelatedComplaint".equals(serviceType)){
                resetMeterComplaintFields();
            }else if("ConnTransferRequest".equals(serviceType)){
                resetConnTransferFields();
            }else if("CatChangeRequest".equals(serviceType)){
                resetCatChangeFields();
            }else if ("ConnDisconnRequest".equals(serviceType)){
                resetConnDissconFields();
            }
        }
        return null;
    }
    
    public String sendEmailConfirmation(String mailSubject, Account actDetails, ServiceRequestPojo srDetails, String serviceReqType) throws SQLException, IOException { 
        String discomVal = null;
        final String subject = mailSubject;
        String mailContent = this.getMailBody(actDetails, srDetails, serviceReqType); 
        RestServices rs = new RestServices();
        SendEmailRequest emailRequest = new SendEmailRequest();
        emailRequest.setFrom("self.service@uppclonline.com");
        //to comment hardcoded one
//        emailRequest.setTo("palashdabkara@gmail.com");
        
        //to uncomment
        emailRequest.setTo(actDetails.getEmail());
        emailRequest.setSubject(subject);
        emailRequest.setContentType("text/html");
        emailRequest.setContent(mailContent);
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        SendEmailResponse emailResponse = rs.sendEmail(emailRequest,discomVal); 
        String result = emailResponse.getResult();
        return result;
    }
    
    private String getMailBody(Account actDetails, ServiceRequestPojo srDetails, String serviceReqType) {
        String body = "";

        
        body = "<html>\n" + 
        "  <head></head>\n" + 
        "  <body>\n" + 
        "    <div align=center>\n" + 
        "      <table border=0 cellspacing=0 cellpadding=0 style='border-collapse:collapse;mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt'>\n" + 
        "        <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>\n" + 
        "          <td width=616 valign=top style='width:462.1pt;padding:0in 5.4pt 0in 5.4pt'>\n" + 
        "            <p style='margin-bottom:0in;margin-bottom:.0001pt;line-height:  normal'>\n" + 
        "              <span style='mso-no-proof:yes'>\n" + 
        "                <img width=454 height=74 src=https://www.uppclonline.com/en_GB/images/logo.png alt=logo.png>\n" + 
        "              </span>\n" + 
        "            </p>\n" + 
        "          </td>\n" + 
        "        </tr>\n" + 
        "        <tr style='mso-yfti-irow:1;height:23.85pt'>\n" + 
        "          <td width=616 valign=top style='width:462.1pt;padding:0in 5.4pt 0in 0in;  height:23.85pt'>\n" + 
        "            <p style='margin-bottom:0in;margin-bottom:.0001pt;line-height:  normal'>\n" + 
        "              <span style='mso-no-proof:yes'>\n" + 
        "                <img width=616 height=33 src=https://www.uppclonline.com/en_GB/images/nav_bg_email.png alt=logo.png>\n" + 
        "              </span>\n" + 
        "            </p>\n" + 
        "          </td>\n" + 
        "        </tr>\n" + 
        "        <tr style='mso-yfti-irow:2;height:225.0pt'>\n" + 
        "          <td width=616 valign=top style='width:462.1pt;padding:0in 5.4pt 0in 5.4pt; height:225.0pt'>\n" + 
        "            <p style='margin-bottom:0in;margin-bottom:.0001pt;line-height: normal'>\n" + 
        "              <b style='mso-bidi-font-weight:normal'>\n" + 
        "                <span style='font-size:12.0pt'>\n" + 
        "                  <span style='mso-spacerun:yes'></span>\n" + 
        "                </span>\n" + 
        "              </b>\n" + 
        "            </p>\n" + 
        "            <p style=margin-bottom:0in;margin-bottom:.0001pt;line-height: normal'>\n" + 
        "              <b style='mso-bidi-font-weight:normal'>\n" + 
        "                <span style='font-size:12.0pt'>\n" + 
        "                  <span style='mso-spacerun:yes'></span>Dear "+actDetails.getName()+", <o:p></o:p>\n" + 
        "                </span>\n" + 
        "              </b>\n" + 
        "            </p>\n" + 
        "            <div>\n" + 
        "              <p style='text-align:justify'>\n" + 
        "                <span style='font-size:11pt; line-height:115%'>Account No : "+actDetails.getAccountNo()+" </span>\n" + 
        "              </p>\n" + 
        "              <p align=center style='text-align:center'>\n" + 
        "                <span style='font-size:11.0pt;line-height:115%;'> "+serviceReqType+" <o:p></o:p>\n" + 
        "                </span>\n" + 
        "              </p>\n" + 
        "              <p style='text-align:justify'>\n" + 
        "                <span style='font-size:10.0pt; line-height:115%;'>Thank you for successfully making your "+serviceReqType+" Online. Your Request Number is <b style='mso-bidi-font-weight:normal'> "+pageFlowParam.get("caseId")+" </b><b style='mso-bidi-font-weight:normal'></b>\n" + 
        "                </span>\n" + 
        "              </p>\n" + 
        "              <p style='text-align:justify'>\n" + 
        "                <span style='font-size:11pt;line-height:115%;font-family:'>If you have any queries about your account, please do not hesitate to <span style='color:black; mso-themecolor:text1'>\n" + 
        "                    <a href='https://www.uppclonline.com/dispatch/Portal/appmanager/uppcl/wss?_nfpb=true&_pageLabel=uppcl_static_contactus&amp;pageID=ST_07'>\n" + 
        "                      <span style='mso-ansi-font-size:11.0pt;mso-bidi-font-size:11.0pt;line-height:115%'>contact us</span>\n" + 
        "                    </a>\n" + 
        "                  </span>\n" + 
        "                </span>\n" + 
        "              </p>\n" + 
        "              <p style='text-align:justify'>\n" + 
        "                <span style='font-size:10pt;line-height:115%;'>We value your relationship with us and assure you of our best services always.</span>\n" + 
        "              </p>\n" + 
        "              <p style='text-align:justify'>\n" + 
        "                <span style='font-size:10.0pt; line-height:115%;'>Yours sincerely,</span>\n" + 
        "              </p>\n" + 
        "              <p style='text-align:justify'>\n" + 
        "                <span style='font-size:10.0pt;line-height:115%;'>Customer Services</span>\n" + 
        "              </p>\n" + 
        "              <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:normal'>\n" + 
        "                <span style='font-size:12.0pt mso-fareast-language:EN-IN'></span>\n" + 
        "              </p>\n" + 
        "              <p style='margin-bottom:0in;margin-bottom:.0001pt;line-height:normal'>\n" + 
        "                <span style='font-size:9.0pt'></span>\n" + 
        "              </p>\n" + 
        "              <p style='margin-bottom:0in;margin-bottom:.0001pt;line-height:normal'></p>\n" + 
        "          </td>\n" + 
        "        </tr>\n" + 
        "        <tr style='mso-yfti-irow:3;height:5.0pt'>\n" + 
        "          <td width=616 valign=top style='width:462.1pt;background:#FF9933;padding:0in 5.4pt 0in 5.4pt;height:5.0pt'>\n" + 
        "            <p style='margin-bottom:0in;margin-bottom:.0001pt;line-height:normal;tab-stops:69.75pt'>\n" + 
        "              <span style='font-size:1.0pt;mso-bidi-theme-font:minor-bidi;color:black;mso-themecolor:text1'></span>\n" + 
        "            </p>\n" + 
        "          </td>\n" + 
        "        </tr>\n" + 
        "        <tr style='mso-yfti-irow:4;mso-yfti-lastrow:yes;height:34.25pt'>\n" + 
        "          <td width=616 valign=top style='width:462.1pt;background:#0E92BD;padding:0in 5.4pt 0in 5.4pt;height:34.25pt'>\n" + 
        "            <p align=center style='margin-bottom:0in;margin-bottom:.0002pt;text-align:center;line-height:normal;tab-stops:69.75pt'>\n" + 
        "              <span style='font-size:9.5pt;color:black;mso-themecolor:text1'>UP Power Corporation Limited | Shakti Bhavan, 14, Ashok Marg, Lucknow, UP, India.</span>\n" + 
        "              <span style='font-size:10.0pt;mso-bidi-font-size:11.0pt;mso-bidi-theme-font:minor-bidi;color:black;mso-themecolor:text1'></span>\n" + 
        "            </p>\n" + 
        "            <p align=center style='margin-bottom:0in;margin-bottom:.0001pt;text-align:center;line-height:normal;tab-stops:69.75pt'>\n" + 
        "              <span style='font-size:10.0pt;mso-bidi-font-size:11.0pt;mso-bidi-theme-font:minor-bidi;color:black;mso-themecolor:text1'></span>\n" + 
        "            </p>\n" + 
        "          </td>\n" + 
        "        </tr>\n" + 
        "      </table>\n" + 
        "    </div>\n" + 
        "    <p align=center style='text-align:center'>\n" + 
        "      <span lang=EN-INstyle='mso-ansi-language:EN-IN'></span>\n" + 
        "    </p>\n" + 
        "    </div>\n" + 
        "    </div>\n" + 
        "    </div>\n" + 
        "  </body>\n" + 
        "</html>";
        
        body = body.replaceAll("null", " ");
        return body;
    }
    
    

    
    
    public void onUploadFileValueChange(ValueChangeEvent valueChangeEvent) {
        System.out.println("onUploadFileValueChange :: starts");
        pageFlowParam.put("attachmentErrMsg", null);
        pageFlowParam.put("documentId", null);
        pageFlowParam.put("documentContentId", null);
        pageFlowParam.put("docContType", null);
        pageFlowParam.put("docFileName", null);
        DBServices dbServices = new DBServices();
        if(valueChangeEvent.getNewValue() != null){
            UploadedFile newValue =(UploadedFile) valueChangeEvent.getNewValue();
            System.out.println("File Name --> "+newValue.getFilename());
            Utils adfUtil = new Utils();

            try {
                adfUtil.createConnection();
                String folderId = null;
//                Long folderId = getFolderIdFromContent("/PORTAL");
//                System.out.println("FolderId  --> "+folderId);
                String serviceType = null;
                if (pageFlowParam.get("selectedCorrectionType") != null) {
                    serviceType = (String) pageFlowParam.get("selectedCorrectionType");
                }

                if ("NameCorrection".equals(serviceType)) {
                    folderId = dbServices.getProperty("NAME_COR_FOLDER_ID");
                } else if ("AddressCorrection".equals(serviceType)) {
                    folderId = dbServices.getProperty("ADDR_COR_FOLDER_ID");
                } else if ("BillCorrection".equals(serviceType)) {
                    folderId = dbServices.getProperty("BILL_COR_FOLDER_ID");
                } else if ("MeterRelatedComplaint".equals(serviceType)) {
                    folderId = dbServices.getProperty("METER_COMP_FOLDER_ID");
                } else if ("ConnTransferRequest".equals(serviceType)) {
                    folderId = dbServices.getProperty("CON_TRANS_FOLDER_ID");
                } else if ("CatChangeRequest".equals(serviceType)) {
                    folderId = "";
                } else if ("ConnDisconnRequest".equals(serviceType)) {
                    folderId = dbServices.getProperty("CON_DIS_FOLDER_ID");
                }


                DataBinder response = adfUtil.uploadFile((String)pageFlowParam.get("docType"), (String)paramSession.get("loggedInUserName"), "Public", newValue, folderId);
                System.out.println("File Content Id 123--> "+response.getLocal("dDocName"));
                System.out.println("DOC ID--> "+response.getLocal("dDocName"));
                if(response.getLocal("dDocName") != null){
                    System.out.println("Inside condition");
                    //set attachment pojo
                    ServiceRequestPojo srDetails = new ServiceRequestPojo();
                    System.out.println("SRDETAILS --> "+pageFlowParam.get("srDetails"));
                    if(pageFlowParam.get("srDetails") != null){
                        System.out.println("sr detials not null");
                        srDetails = (ServiceRequestPojo) pageFlowParam.get("srDetails");
                        List<AttachmentPojo> attachmentList = new ArrayList<AttachmentPojo>();
                        AttachmentPojo attachment = new AttachmentPojo();
                        attachment.setDocId(response.getLocal("dID"));
                        attachment.setDocContentId(response.getLocal("dDocName"));
                        attachment.setDocExtension(response.getLocal("dExtension"));
                        attachment.setDocContentType(response.getLocal("dFormat"));
                        attachment.setDocName(response.getLocal("dOriginalName"));
                        attachment.setDocIdentificationType((String)pageFlowParam.get("docType"));
                        attachmentList.add(attachment);
                        System.out.println("attachmentList size --> "+attachmentList.size());
                        srDetails.setAttachmentDetails(attachmentList);
                        //update list
                        pageFlowParam.put("documentId", attachment.getDocId());
                        pageFlowParam.put("documentContentId", attachment.getDocContentId());
                        pageFlowParam.put("docContType", attachment.getDocContentType());
                        pageFlowParam.put("docFileName", attachment.getDocName());
                        pageFlowParam.put("srDetails", srDetails);
                    }
                }
            } catch (Exception e) {
                pageFlowParam.put("attachmentErrMsg", utils.getLabelValueForKey("SOMETHING_WENT_WRONG"));
                e.printStackTrace();
            }
        }
        System.out.println("onUploadFileValueChange Ends");
    }
    
    public String findLabel(String key){
        String value = utils.getLabelValueForKey(key);
        System.out.println("Key --> "+key+ " " + "Value --> "+value); 
        return value;
    }


    public void setMessagePopupBind(RichPopup messagePopupBind) {
        this.messagePopupBind = messagePopupBind;
    }

    public RichPopup getMessagePopupBind() {
        return messagePopupBind;
    }

    public void closeMessagePopup(ActionEvent actionEvent) {
        // Add event code here...
        getMessagePopupBind().hide();
    }

    public void setDocDownloadDestUrl(String docDownloadDestUrl) {
        this.docDownloadDestUrl = docDownloadDestUrl;
    }

    public String getDocDownloadDestUrl() {
        if(pageFlowParam.get("documentId") != null){
            docDownloadDestUrl = utils.generateDownloadDocumentLink((String) pageFlowParam.get("documentId"));
        }
        return docDownloadDestUrl;
    }

    public void setDocViewDestUrl(String docViewDestUrl) {
        this.docViewDestUrl = docViewDestUrl;
    }

    public String getDocViewDestUrl() {
        if(pageFlowParam.get("documentId") != null){
            docViewDestUrl = utils.viewDocumentInWeb((String) pageFlowParam.get("documentId"));
        }
        return docViewDestUrl;
    }

    public void downloadFile(FacesContext facesContext, OutputStream outputStream) {
        try {
            utils.createConnection();
            utils.getFileFromContent(outputStream, (String) pageFlowParam.get("documentId"),
                                     (String) pageFlowParam.get("documentContentId"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IdcClientException e) {
            e.printStackTrace();
        }

    }


    public String deleteDocument() {
        try {
            utils.createConnection();
            utils.deleteFileFromContent((String) pageFlowParam.get("documentContentId"));
            resetDoc();
            getDeletePopupBind().hide();
        } catch (IdcClientException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void resetDoc(){
        pageFlowParam.put("documentId",null);
        pageFlowParam.put("documentContentId", null);
        pageFlowParam.put("docContType", null);
        pageFlowParam.put("docFileName", null);
        if(uploadFileBind !=null){
            uploadFileBind.resetValue();
        }
    }

    public void setDeletePopupBind(RichPopup deletePopupBind) {
        this.deletePopupBind = deletePopupBind;
    }

    public RichPopup getDeletePopupBind() {
        return deletePopupBind;
    }
    
    public void closeDeletePopup(ActionEvent actionEvent) {
        // Add event code here...
        getDeletePopupBind().hide();
    }

    public String openDeletePopup() {
        RichPopup.PopupHints hints=new RichPopup.PopupHints();
        deletePopupBind.show(hints);
        return null;
    }
}
