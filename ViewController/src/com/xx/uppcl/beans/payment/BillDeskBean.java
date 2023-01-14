package com.xx.uppcl.beans.payment;

import com.xx.uppcl.pojo.Account;
import com.xx.uppcl.pojo.PaymentRequestPojo;
import com.xx.uppcl.pojo.ServiceRequestPojo;

import com.xx.uppcl.rest.request.AccDetailsCorrectionSRRequest;
import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.request.GetProcessingFeeRequest;
import com.xx.uppcl.rest.request.InitiatePaymentRequest;
import com.xx.uppcl.rest.response.AccDetailsCorrectionSRResponse;
import com.xx.uppcl.rest.response.GetConsumerDetailsResponse;
import com.xx.uppcl.rest.response.GetProcessingFeeResponse;
import com.xx.uppcl.rest.response.InitiatePaymentResponse;
import com.xx.uppcl.rest.services.RestServices;

import com.xx.uppcl.utils.Utils;

import java.io.IOException;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.time.LocalDate;

import java.util.Date;
import java.util.Map;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;

public class BillDeskBean {

    public BillDeskBean() {
    }

    Map paramSession = ADFContext.getCurrent().getSessionScope();
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();
    
    Utils utils = new Utils();


    public String fetchPrices() {
        Account actDetails = new Account();
        String paymentFor = null;
        PaymentRequestPojo paymentDetails = new PaymentRequestPojo();
        String charges = null;

        String retValue = null;

        //get account details
        if (pageFlowParam.get("accountDetails") != null) {
            actDetails = (Account) pageFlowParam.get("accountDetails");
        }

        //get payment for
        if (pageFlowParam.get("paymentFor") != null) {
            paymentFor = (String) pageFlowParam.get("paymentFor");
        }

        //get charges
        if (pageFlowParam.get("charges") != null) {
            System.out.println("Charges --> "+pageFlowParam.get("charges"));
            charges = (String) pageFlowParam.get("charges");
        }

        try {

            if (paymentFor != null) {
                if ("CategoryChangeRequest".equals(paymentFor)) {
                    //fetch price and mapping to payment pojo
                    paymentDetails = getProcessingFees(actDetails.getAccountNo(), "CC");
                } else if ("ConnectionDisconnection".equals(paymentFor)) {
                    //fetch price and mapping to payment pojo
                    paymentDetails = getProcessingFees(actDetails.getAccountNo(), "DISCON");

                } else {
                    //no fetch price service call req . already have price details, then map to payment obj
                    if (charges != null) {
                        paymentDetails = mapToPaymentResponse(charges, (String) pageFlowParam.get("comments"));
                    }
                }
            }

            pageFlowParam.put("paymentDetails", paymentDetails);

            //handle error
            if (pageFlowParam.get("businessErrMsg") != null) {
                pageFlowParam.put("successMsg", pageFlowParam.get("businessErrMsg"));
                retValue = "toMessage";
            } else {
                retValue = "toBillDesk";
            }
        } catch (Exception e) {
            retValue = "toError";
            e.printStackTrace();
        }
        return retValue;
    }


    public PaymentRequestPojo getProcessingFees(String accNo, String caseFilter) {
        System.out.println("getProcessingFees  :: Starts");

        String discomVal = null;
        String businessErrMsg = null;
        PaymentRequestPojo paymentDetails = new PaymentRequestPojo();
        try {

            //set request
            GetProcessingFeeRequest request = new GetProcessingFeeRequest();
            request.setActId(accNo);
            request.setCaseFilter(caseFilter);

            RestServices service = new RestServices();

            if (paramSession.get("loggedInUserDiscom") != null) {
                discomVal = (String) paramSession.get("loggedInUserDiscom");
            }

            GetProcessingFeeResponse response = service.getPriceDetails(request, discomVal);

            if (response != null) {
                if ("1".equals(response.getResCode())) {
                    paymentDetails = mapToPaymentResponse(response);
                } else {
                    businessErrMsg = response.getResMsg();
                }
            }

            pageFlowParam.put("businessErrMsg", businessErrMsg);
        } catch (Exception e) {
            throw e;
        }
        System.out.println("getProcessingFees  :: Ends");
        return paymentDetails;
    }

    public PaymentRequestPojo mapToPaymentResponse(GetProcessingFeeResponse response) {
        PaymentRequestPojo paymentDetails = new PaymentRequestPojo();

        //get sr details
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        if (pageFlowParam.get("serviceReqDetails") != null) {
            srDetails = (ServiceRequestPojo) pageFlowParam.get("serviceReqDetails");
        }

        paymentDetails.setCharges1(response.getProcessfee());
        paymentDetails.setComments(srDetails.getReasonOfChange());

        System.out.println("Price - " + paymentDetails.getCharges1());

        return paymentDetails;
    }

    public PaymentRequestPojo mapToPaymentResponse(String charges, String comments) {
        PaymentRequestPojo paymentDetails = new PaymentRequestPojo();

        paymentDetails.setCharges1(charges);
        paymentDetails.setComments(comments);

        System.out.println("Price - " + paymentDetails.getCharges1());

        return paymentDetails;
    }

    public String initiatePayments() {
        System.out.println("initiatePayments :: Starts");
        pageFlowParam.put("intPaymentValidErrMsg", null);

        pageFlowParam.put("acceptTermsErrMsg", null);

        String retVal = null;
        String discomVal = null;
        String intPaymentValidErrMsg = null;

        // get all the payment values
        Account actDetails = new Account();
        ServiceRequestPojo srDetails = new ServiceRequestPojo();
        PaymentRequestPojo paymentDetails = new PaymentRequestPojo();
        String paymentFor = null;


        if (pageFlowParam.get("accountsDetails") != null) {
            actDetails = (Account) pageFlowParam.get("accountsDetails");
        }

        if (pageFlowParam.get("serviceReqDetails") != null) {
            srDetails = (ServiceRequestPojo) pageFlowParam.get("srDetails");
        }

        if (pageFlowParam.get("paymentDetails") != null) {
            paymentDetails = (PaymentRequestPojo) pageFlowParam.get("paymentDetails");
        }

        //get payment for
        if (pageFlowParam.get("paymentFor") != null) {
            paymentFor = (String) pageFlowParam.get("paymentFor");
        }

        //validation check
        if (!validateAcceptTermsFields()) {
            return null;
        }

        if (paymentFor != null) {
            if ("CategoryChangeRequest".equals(paymentFor)) {
                paymentDetails.setServiceRequestType("CR_CatChange");
                paymentDetails.setParam1(srDetails.getCategoryType());
            } else if ("ConnectionDisconnection".equals(paymentFor)) {
                paymentDetails.setServiceRequestType("CR_ConDisc");
            } else if ("OnlineLoadExtension".equals(paymentFor)) {
                paymentDetails.setServiceRequestType("load_extension");
            }
        }

        paymentDetails.setAccountNo(actDetails.getAccountNo());
        paymentDetails.setRequestDate(dateToString(new Date()));

        //call service
        //set request
        InitiatePaymentRequest request = new InitiatePaymentRequest();
        request = mapToInitPayRequest(paymentDetails);

        RestServices service = new RestServices();

        if (paramSession.get("loggedInUserDiscom") != null) {
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }

        InitiatePaymentResponse response = service.initiatePayment(request, discomVal);

        if (response != null && response.getResCode() != null) {
            if ("0".equals(response.getResCode())) {
                //on success
                //navigate to payment gateway
                preparePaymentGatewayParam();

            } else if ("2".equals(response.getResCode()) || "4".equals(response.getResCode())) {
                //business validation case
                intPaymentValidErrMsg = response.getResMsg();
            } else if("0".equals(response.getResCode())){
                //error / invalid case
                intPaymentValidErrMsg = response.getResMsg();
            }else{
                retVal = "toError";
            }
            pageFlowParam.put("intPaymentValidErrMsg", intPaymentValidErrMsg);
        }


        System.out.println("submitNameCorrection :: Ends");
        return retVal;
    }

    public boolean validateAcceptTermsFields() {
        Boolean checkedBVal = false;
        boolean validFlag = true;
        String acceptTermsErrMsg = null;
        System.out.println("Checked value --> " + pageFlowParam.get("checked"));
        if (pageFlowParam.get("checked") == null ||
            (pageFlowParam.get("checked") != null && !((Boolean) pageFlowParam.get("checked")))) {
            validFlag = false;
            acceptTermsErrMsg = utils.getLabelValueForKey("TERMS_COND_ERR_MSG");
            pageFlowParam.put("acceptTermsErrMsg", acceptTermsErrMsg);
        }

        return validFlag;
    }

    public InitiatePaymentRequest mapToInitPayRequest(PaymentRequestPojo paymentDetails) {
        InitiatePaymentRequest request = new InitiatePaymentRequest();
        request.setAccountNo(paymentDetails.getAccountNo());
        request.setServiceRequestType(paymentDetails.getServiceRequestType());
        request.setTrackId(paymentDetails.getTrackId());
        request.setComments(paymentDetails.getComments());
        request.setParam1(paymentDetails.getParam1());
        request.setParam2(paymentDetails.getParam2());
        request.setParam3(paymentDetails.getParam3());
        request.setParam4(paymentDetails.getParam4());
        request.setParam5(paymentDetails.getParam5());
        request.setCharges1(paymentDetails.getCharges1());
        request.setCharges2(paymentDetails.getCharges2());
        request.setRequest_Date(paymentDetails.getRequestDate());

        return request;
    }

    public void resetNameFields() {

        pageFlowParam.put("acceptTermsErrMsg", null);
        pageFlowParam.put("intPaymentValidErrMsg", null);

    }

    public String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        //Formatting the obtained date
        String formattedDate = formatter.format(date);

        return formattedDate;
    }

    public void preparePaymentGatewayParam() {
        //todo
    }
    
    public String findLabel(String key){
        String value = utils.getLabelValueForKey(key);
        System.out.println("Key --> "+key+ " " + "Value --> "+value); 
        return value;
    }
}
