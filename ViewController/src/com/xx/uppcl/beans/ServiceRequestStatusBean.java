package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.Account;
import com.xx.uppcl.pojo.BillDetail;
import com.xx.uppcl.pojo.ServiceRequestPojo;
import com.xx.uppcl.rest.request.GetBillDownloadRequest;
import com.xx.uppcl.rest.request.GetSRStatusRequest;
import com.xx.uppcl.rest.response.GetBillDownloadResponse;
import com.xx.uppcl.rest.response.GetSRStatusResponse;
import com.xx.uppcl.rest.response.ServiceReqStatusPOJO;
import com.xx.uppcl.rest.services.RestServices;

import com.xx.uppcl.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.context.AdfFacesContext;


public class ServiceRequestStatusBean {
    public ServiceRequestStatusBean() {
    }
    
    Map paramSession= ADFContext.getCurrent().getSessionScope();
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();
    private RichPopup messagePopupBind;
    
    Utils utils = new Utils();

    public String initializeLOV() {
        String primaryAccNo = null;
        List<SelectItem> accountsList = new ArrayList<SelectItem>();
        
        paramSession.put("isCurrentTab", "consumerservices");
        
        List<ServiceRequestPojo> srList = new ArrayList<ServiceRequestPojo>();
        pageFlowParam.put("srList", srList);
        
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
        return "toshowSRStatus";
    }

    
    public String getServiceReqStatus() {
        System.out.println("getServiceReqStatus :: Starts");
        
        pageFlowParam.put("fromDateErrMsg", null);
        pageFlowParam.put("toDateErrMsg", null);
        pageFlowParam.put("actErrMsg", null);
        
        pageFlowParam.put("srReqBussValidErrMsg", null);
        
        String retVal = null;
        List<ServiceRequestPojo> srList = new ArrayList<ServiceRequestPojo>();
        String discomVal = null;
        
        System.out.println("From Date --> "+pageFlowParam.get("fromDate"));
        System.out.println("To Date --> "+pageFlowParam.get("toDate"));
        try {
            //validation check
            if (!validateSRFields()) {
                retVal = null;
            } else {
                //call service to fetch the status
                //set request
                GetSRStatusRequest request = new GetSRStatusRequest();
                request.setAccountId((String) pageFlowParam.get("accountNo"));
                request.setFromDate(utils.convertDateToStr((Date) pageFlowParam.get("fromDate"), "dd-MM-yyyy"));
                request.setToDate(utils.convertDateToStr((Date) pageFlowParam.get("toDate"), "dd-MM-yyyy"));
                request.setRequestType(utils.getLabelValueForKey("SRS_SERVICE_TYPE_VALUE"));

                RestServices service = new RestServices();

                if (paramSession.get("loggedInUserDiscom") != null) {
                    discomVal = (String) paramSession.get("loggedInUserDiscom");
                }

                GetSRStatusResponse response = service.getSRStatus(request, discomVal);

                if (response != null) {
                    if (response.getCaseStatus() != null && response.getCaseStatus().size() > 0) {
                        if ("1".equals(response.getCaseStatus()
                                               .get(0)
                                               .getErrCode())) {
                            srList = mapToSRResponse(response);
                        } else if ("0".equals(response.getCaseStatus()
                                                      .get(0)
                                                      .getErrCode())) {
                            pageFlowParam.put("srReqBussValidErrMsg", response.getCaseStatus()
                                                                              .get(0)
                                                                              .getErrDesc());
                        } else {
                            retVal = "toError";
                        }
                    }
                }

                if (pageFlowParam.get("srReqBussValidErrMsg") != null) {
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    messagePopupBind.show(hints);
                }

                pageFlowParam.put("srList", srList);
            }


        } catch (Exception e) {
            e.printStackTrace();
            retVal = "toError";
        }
        utils.executeClientJavascript("dataTableInvoke();");
        System.out.println("getServiceReqStatus :: Ends");
        return retVal;
    }
    
    public boolean validateSRFields(){
        String fromDateErrMsg = null;
        String toDateErrMsg = null;
        String actErrMsg = null;
        
        boolean validFlag = true;
        Date toDate = new Date();
        Date fromDate = new Date();
        
        if(pageFlowParam.get("accountNo") == null){
            validFlag = false;
            actErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actErrMsg", actErrMsg);
        }
        
        System.out.println("From Date --> "+pageFlowParam.get("fromDate"));
        if(pageFlowParam.get("fromDate") == null){
            validFlag = false;
            fromDateErrMsg = utils.getLabelValueForKey("SRS_FROM_DATE_ERR_MSG");
            pageFlowParam.put("fromDateErrMsg", fromDateErrMsg);
        }else{
            fromDate = (Date)pageFlowParam.get("fromDate");
        }
        
        
        //validate to date
        System.out.println("To Date --> "+pageFlowParam.get("toDate"));
        if(pageFlowParam.get("toDate") == null){
            validFlag = false;
            toDateErrMsg = utils.getLabelValueForKey("SRS_TO_DATE_ERR_MSG");
            pageFlowParam.put("toDateErrMsg", toDateErrMsg);
        }else{
            toDate = (Date)pageFlowParam.get("toDate");
        }
        
        
        if(fromDate != null && toDate != null){
            if(toDate.compareTo(fromDate) < 0){
                validFlag = false;
                fromDateErrMsg = utils.getLabelValueForKey("SRS_FROM_DATE_ERR_MSG_2");
                pageFlowParam.put("fromDateErrMsg", fromDateErrMsg);
                
                toDateErrMsg = utils.getLabelValueForKey("SRS_TO_DATE_ERR_MSG_2");
                pageFlowParam.put("toDateErrMsg", toDateErrMsg);
            }else{
                //validate date range should be between 12 months
                long difference_In_Time
                                = fromDate.getTime() - toDate.getTime();
                long difference_In_Years
                                = (difference_In_Time
                                   / (1000l * 60 * 60 * 24 * 365));
                
                if(difference_In_Years > 1){
                    validFlag = false;
                    pageFlowParam.put("srReqBussValidErrMsg", utils.getLabelValueForKey("SRS_DATE_RANGE_ERR_MSG"));
                }
            }
        }
        
        return validFlag;
    }
    
    public List<ServiceRequestPojo> mapToSRResponse(GetSRStatusResponse response){
        List<ServiceRequestPojo> srList = new ArrayList<ServiceRequestPojo>();

        List<ServiceReqStatusPOJO> caseStatus = response.getCaseStatus();
        
        if(caseStatus.size() > 0){
            for(ServiceReqStatusPOJO status : caseStatus){
                ServiceRequestPojo sr = new ServiceRequestPojo();
                sr.setCaseId(status.getCaseId());
                sr.setCreationDate(status.getCaseCreationDateTime());
                sr.setReqStatus(status.getCaseStatus());
                sr.setDesc(status.getRemarks());
                
                //split comments by ;
                if(status.getComments() !=  null){
                    String[] splitedArray = status.getComments().split(";");
                    String finalStr = null;
                    int count =0 ;
                    if(splitedArray.length >= 2){
                        count = 2;
                    }else{
                        count = splitedArray.length;
                    }
                    
                    for (int i=0 ; i < count ; i++) {
                        finalStr = finalStr + splitedArray[i] + ";";
                    }
                    
                    sr.setSrcomments(finalStr);
                    
                    //mapping for billid
                    Account actDetails = new Account();
                    //get consumer details for primary account no
                    if (paramSession.get("accountsDetailsFromSession") != null) {
                        actDetails = (Account) paramSession.get("accountsDetailsFromSession");
                        System.out.println("Bill Size --> "+actDetails.getBillDetails().size());
                        for (BillDetail bill : actDetails.getBillDetails()) {
                            if (bill.getBillNo() != null) {
                                if (status.getComments().contains(bill.getBillNo())) {
                                    sr.setBillId(bill.getBillNo());
                                    sr.setCheckBillId(true);
                                }
                            }
                        }
                    }
                }
                srList.add(sr);
            }
        }
        
        
        return srList;
    }

    public String downloadBill(OutputStream outputStream) {
        String fileContent = null;
        String billId = null;
        String discomVal = null;
        String downloadErrMsg = null;
        pageFlowParam.put("srReqBussValidErrMsg", null);
        pageFlowParam.put("contentType", null);
        
        if(pageFlowParam.get("billId") != null){
            billId = (String) pageFlowParam.get("billId");
        }
        //call service to fetch the status
        //set request
        GetBillDownloadRequest request=new GetBillDownloadRequest();
        request.setBillId(billId);
        request.setFlag(utils.getLabelValueForKey("SRS_BILL_DOWNLD_FLAG"));
        
        RestServices service=new RestServices();
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        GetBillDownloadResponse response = service.getBillDownloadFileContent(request,discomVal);
        try {

            if (response != null) {
                if ("1".equals(response.getResCode())) {
                    if (response.getReportContent() != null) {
                        //download base64 to file
                        System.out.println("ReportContent --> " + response.getReportContent());
                        System.out.println("ContentType  --> " + response.getReportContentType());
                        pageFlowParam.put("contentType", response.getReportContentType());

                        byte[] decoded = Base64.getDecoder().decode(response.getReportContent());
                        System.out.println("decode");

                        InputStream inputStream = new ByteArrayInputStream(decoded);
                        System.out.println("Inputstrea --> " + inputStream);

                        byte[] buf = new byte[1024 * 256];
                        long i = 0;
                        int len;

                        System.out.println("Downloading File from UCM Server");

                        while (true) {
                            i++;
                            len = inputStream.read(buf);
                            System.out.println("len  --> " + len);
                            outputStream.write(buf, 0, len);
                            if (len == -1) {
                                break;
                            }

                        }
                        outputStream.flush();
                        inputStream.close();
                        outputStream.close();

                    }
                } else if ("0".equals(response.getResCode())) {
                    downloadErrMsg = response.getResMsg();
                    pageFlowParam.put("srReqBussValidErrMsg", downloadErrMsg);
                } else {
                    downloadErrMsg = response.getResMsg();
                    pageFlowParam.put("srReqBussValidErrMsg", downloadErrMsg);
                }
            } else {
                downloadErrMsg = "Something went wrong.Please try again after some time";
                pageFlowParam.put("srReqBussValidErrMsg", downloadErrMsg);
            }

            if (pageFlowParam.get("srReqBussValidErrMsg") != null) {
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                messagePopupBind.show(hints);
            }

            utils.executeClientJavascript("dataTableInvoke();");
        } catch (IOException ioe) {
            System.out.println("IO Exception occurred. Unable to retrieve file. Message: " + ioe.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception message: " + ex.getMessage());
        }
        return null;
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

    public void downloadBillReport(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        downloadBill(outputStream);

    }
}
