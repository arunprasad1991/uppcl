package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.Account;
import com.xx.uppcl.pojo.MeterReadingsPOJO;
import com.xx.uppcl.pojo.SelfBillMeterGenPOJO;
import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.request.GetMeterDetailsRequest;
import com.xx.uppcl.rest.request.GetNetMeterDetailsRequest;
import com.xx.uppcl.rest.request.SendEmailRequest;
import com.xx.uppcl.rest.request.SubmitBillMeterGenRequest;
import com.xx.uppcl.rest.request.SubmitBillNetMeterGenRequest;
import com.xx.uppcl.rest.response.GetConsumerDetailsResponse;
import com.xx.uppcl.rest.response.GetMeterDetailsResponse;
import com.xx.uppcl.rest.response.GetNetMeterDetailsResponse;
import com.xx.uppcl.rest.response.PreviousReadingsPOJO;
import com.xx.uppcl.rest.response.SendEmailResponse;
import com.xx.uppcl.rest.response.SubmitBillMeterGenResponse;
import com.xx.uppcl.rest.response.SubmitBillNetMeterGenResponse;
import com.xx.uppcl.rest.services.RestServices;
import com.xx.uppcl.services.DBServices;

import com.xx.uppcl.utils.Utils;

import java.io.IOException;

import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;

public class SelfBillGenerationBean {
    public SelfBillGenerationBean() {
    }

    Map paramSession = ADFContext.getCurrent().getSessionScope();
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();
    Utils utils = new Utils();

    public void resetFields() {
        Account accountDetails = new Account();
        pageFlowParam.put("accountsDetails", accountDetails);
        pageFlowParam.put("userEntryBillDetails", null);

    }

    public void resetLOV() {
        pageFlowParam.put("accountsList", null);


    }

    public String getAccountListAndDetails() {
        System.out.println("getAccountListAndDetails :: Starts");
        resetLOV();
        resetFields();
        String primaryAccNo = null;
        
        paramSession.put("isCurrentTab", "consumerservices");


        List<SelectItem> accountsList = new ArrayList<SelectItem>();
        Account accountDetails = new Account();


        //get linked accounts with primary acc no
        if(paramSession.get("accountsListFromSession") != null){
            accountsList =(List<SelectItem>) paramSession.get("accountsListFromSession");
        }
        System.out.println("account list size--> " + accountsList.size());

        pageFlowParam.put("accountsList", accountsList);

        //get consumer details for primary account no
        if(paramSession.get("accountsDetailsFromSession") != null){
            accountDetails =(Account) paramSession.get("accountsDetailsFromSession");
        }
        pageFlowParam.put("accountsDetails", accountDetails);

        System.out.println("getAccountListAndDetails :: Ends");
        return "continue";
    }

    public List<SelectItem> getLinkedAccount(String accNo) {
        System.out.println("getLinkedAccount  :: Starts");
        DBServices services = new DBServices();
        List<SelectItem> accountList = new ArrayList<SelectItem>();

        List<String> linkedAccounts = services.getLinkedAccounts(accNo);

        accountList.add(new SelectItem(accNo, accNo));

        if (linkedAccounts.size() > 0) {
            for (String acc : linkedAccounts) {
                accountList.add(new SelectItem(acc, acc));
            }
        }

        System.out.println("getLinkedAccount  :: Ends");
        return accountList;
    }

    public Account getConsumerAccDetails(String accNo) {
        System.out.println("getConsumerAccDetails  :: Starts");
        Account accDetails = new Account();
        String discomVal = null;
        //set request
        GetConsumerDetailsRequest request = new GetConsumerDetailsRequest();
        request.setKno(accNo);

        RestServices service = new RestServices();

        if (paramSession.get("loggedInUserDiscom") != null) {
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }

        GetConsumerDetailsResponse response = service.getConsumerDetails(request, discomVal);

        if (response != null && response.getKno() != null && accNo.equalsIgnoreCase(response.getKno())) {
            accDetails = mapToAccountResponse(response);
        }

        System.out.println("getConsumerAccDetails  :: Ends");
        return accDetails;
    }

    public Account mapToAccountResponse(GetConsumerDetailsResponse response) {
        Account accDetails = new Account();

        accDetails.setAccountNo(response.getKno());
        accDetails.setName(response.getConsumerName());

        String addr = "";
        if (response.getBillingAddress().getAddressLine1() != null) {
            addr = addr + response.getBillingAddress().getAddressLine1() + "\n";
        }
        if (response.getBillingAddress().getAddressLine2() != null) {
            addr = addr + response.getBillingAddress().getAddressLine2() + "\n";
        }
        if (response.getBillingAddress().getAddressLine3() != null) {
            addr = addr + response.getBillingAddress().getAddressLine3() + "\n";
        }
        if (response.getBillingAddress().getAddressLine4() != null) {
            addr = addr + response.getBillingAddress().getAddressLine4() + "\n";
        }
        if (response.getBillingAddress().getCity() != null) {
            addr = addr + response.getBillingAddress().getCity() + "\n";
        }
        if (response.getBillingAddress().getPinCode() != null) {
            addr = addr + response.getBillingAddress().getPinCode() + "\n";
        }
        if (response.getBillingAddress().getState() != null) {
            addr = addr + response.getBillingAddress().getState();
        }
        accDetails.setAddress(addr);

        accDetails.setDiscom(response.getDiscom());
        accDetails.setEmail(response.getEmailAddress());

        String currentLoad = null;
        float sancLoadInKW = 0f;
        float sancLoadInKVA = 0f;
        float sancLoadInBHP = 0f;
        float finalSancLoad = 0f;

        if (response.getSanctionedLoadInKW() != null) {
            //convert to number
            sancLoadInKW = Float.parseFloat(response.getSanctionedLoadInKW());
        }
        if (response.getSanctionedLoadInKVA() != null) {
            //convert to number
            sancLoadInKVA = Float.parseFloat(response.getSanctionedLoadInKVA());
        }
        if (response.getSanctionedLoadInBHP() != null) {
            //convert to number
            sancLoadInBHP = Float.parseFloat(response.getSanctionedLoadInBHP());
        }
        if (sancLoadInKW > 0) {
            currentLoad = String.valueOf(sancLoadInKW) + " " + "KW";
            finalSancLoad = sancLoadInKW;
        } else if (sancLoadInKVA > 0) {
            currentLoad = String.valueOf(sancLoadInKVA) + " " + "KVA";
            finalSancLoad = sancLoadInKVA;
        } else if (sancLoadInBHP > 0) {
            currentLoad = String.valueOf(sancLoadInBHP) + " " + "BHP";
            finalSancLoad = sancLoadInBHP;
        }
        accDetails.setCurrentLoad(currentLoad);
        accDetails.setSancLoad(finalSancLoad);

        //todo mapping for suply type

        accDetails.setMobileNo(response.getMobileNumber());
        accDetails.setDivision(response.getDivision());

        if (response.getSecurityDeposit() != null) {
            accDetails.setSecurityAmount(Float.parseFloat(response.getSecurityDeposit()));
        }

        //todo mapping for meter number

        System.out.println("Acc No - " + accDetails.getAccountNo());
        System.out.println("currentLoad - " + accDetails.getCurrentLoad());
        System.out.println("Mobile Number - " + accDetails.getMobileNo());
        System.out.println("Address - " + accDetails.getAddress());


        return accDetails;
    }

    public void onAccountSelValueChange(ValueChangeEvent valueChangeEvent) {
        // get selected value
        String selectedValue = null;

        if (valueChangeEvent.getNewValue() != null) {
            selectedValue = (String) valueChangeEvent.getNewValue();
        }

        if (selectedValue != null) {
            //reset fields if already exist
            resetFields();
            // call customer service
            pageFlowParam.put("accountsDetails", getConsumerAccDetails(selectedValue));
        }
    }


    public String checkEligibility() {
        String retValue = null;
        pageFlowParam.put("elibErrMsg", null);
        pageFlowParam.put("headerName", null);
        pageFlowParam.put("businessValidErrMsg", null);

        //get bill type flow
        String billType = null;
        SelfBillMeterGenPOJO billMeterDetails = new SelfBillMeterGenPOJO();
        pageFlowParam.put("userEntryBillDetails", billMeterDetails);

        Account accDetails = new Account();
        //get account details
        if (pageFlowParam.get("accountsDetails") != null) {
            accDetails = (Account) pageFlowParam.get("accountsDetails");
        }


        if (pageFlowParam.get("billType") != null) {
            billType = (String) pageFlowParam.get("billType");
        }
        
        //validation check
        if(!validateAccNoFields(accDetails.getAccountNo())){
            return null;
        }
        
        try{

            if (billType != null) {
                if ("SelfBillMeter".equals(billType)) {
                    pageFlowParam.put("headerName", utils.getLabelValueForKey("SBG_METER_HEAD"));
                    //get meter details
                    billMeterDetails = getMeterDetails(accDetails);
                    if (validateMeterDetails(billMeterDetails, accDetails)) {
                        pageFlowParam.put("userEntryBillDetails", billMeterDetails);
                        retValue = "toMeterGeneration";
                    } else {
                        retValue = "toMessage";
                    }

                } else if ("SelfBillNetMeter".equals(billType)) {
                    pageFlowParam.put("headerName", utils.getLabelValueForKey("SBG_NET_METER_HEAD"));
                    //get net meter details
                    billMeterDetails = getNetMeterDetails(accDetails);
                    if (validateNetMeterDetails(billMeterDetails, accDetails)) {
                        pageFlowParam.put("userEntryBillDetails", billMeterDetails);
                        retValue = "toNetMeterGeneration";
                    } else {
                        retValue = "toMessage";
                    }
                }
            }
            
            if(pageFlowParam.get("elibErrMsg") != null){
                retValue = "toMessage";
            }
        }catch(Exception e){
            retValue = "toError";
        }

        return retValue;
    }

    public SelfBillMeterGenPOJO getMeterDetails(Account accDetails) {
        System.out.println("getMeterDetails  :: Starts");
        
        SelfBillMeterGenPOJO billMeterDetails = new SelfBillMeterGenPOJO();

        try {
            String discomVal = null;

            if (paramSession.get("loggedInUserDiscom") != null) {
                discomVal = (String) paramSession.get("loggedInUserDiscom");
            }

            if (accDetails.getAccountNo() != null) {
                //set request
                GetMeterDetailsRequest request = new GetMeterDetailsRequest();
                request.setAccountNumber(accDetails.getAccountNo());

                RestServices service = new RestServices();

                GetMeterDetailsResponse response = service.getMeterDetails(request, discomVal);

                if (response != null && response.getErrCode() != null  && "1".equals(response.getErrCode())) {
                    billMeterDetails = mapToBillMeterResponse(response, accDetails.getAccountNo());
                } else if(response.getErrDesc() != null){
                    pageFlowParam.put("elibErrMsg", response.getErrDesc());
                }else{
                    pageFlowParam.put("elibErrMsg", utils.getLabelValueForKey("SOMETHING_WENT_WRONG"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        System.out.println("getMeterDetails  :: Ends");
        return billMeterDetails;
    }

    public SelfBillMeterGenPOJO mapToBillMeterResponse(GetMeterDetailsResponse response, String accNo) {
        SelfBillMeterGenPOJO billMeterDetails = new SelfBillMeterGenPOJO();

        billMeterDetails.setAccountNo(accNo);
        billMeterDetails.setMeterStatus(response.getMeterStatus());
        billMeterDetails.setSupplyType(response.getSupplyType());
        billMeterDetails.setPurposeOfSupply(response.getPurposeOfSupply());
        billMeterDetails.setLeftDigit(response.getLeftDigit());
        billMeterDetails.setRightDigit(response.getRightDigit());
        billMeterDetails.setLeftDigitMD(response.getLeftDigitMD());
        billMeterDetails.setRightDigitMD(response.getRightDigitMD());
        billMeterDetails.setMeterSerialNumber(response.getMeterSerialNumber());
        billMeterDetails.setMeterConfigType(response.getMeterConfigType());
        billMeterDetails.setManufacturerCode(response.getManufacturerCode());
        billMeterDetails.setBadgeNumber(response.getBadgeNumber());
        billMeterDetails.setPreviousReadKWH(response.getPreviousReadKWH());
        billMeterDetails.setWSSMeterReadDateTime(response.getPreviousReadDateTime());

        System.out.println("Acc No - " + billMeterDetails.getAccountNo());
        System.out.println("Meter Config Type - " + billMeterDetails.getMeterConfigType());

        return billMeterDetails;
    }

    public boolean validateAccNoFields(String actNumber){
        boolean validFlag = true;
        String actNumErrMsg = null;
        if(actNumber == null || (actNumber != null && actNumber.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
        return validFlag;
    }
    
    public boolean validateMeterDetails(SelfBillMeterGenPOJO billMeterDetails, Account accDetails) {

        boolean validFlag = true;

        if (billMeterDetails.getMeterConfigType() != null &&
            !("SIMKW".equals(billMeterDetails.getMeterConfigType()))) {
            validFlag = false;
            pageFlowParam.put("businessValidErrMsg",
                              utils.getLabelValueForKey("SBG_METER_VALIDATION_1_MSG"));
        } else if (billMeterDetails.getPurposeOfSupply() != null &&
                   !("LMV1".equals(billMeterDetails.getPurposeOfSupply()) ||
                     "LMV2".equals(billMeterDetails.getPurposeOfSupply()) ||
                     "LMV4".equals(billMeterDetails.getPurposeOfSupply()))) {
            validFlag = false;
            pageFlowParam.put("businessValidErrMsg",
                              utils.getLabelValueForKey("SBG_METER_VALIDATION_2_MSG"));
        } else if (billMeterDetails.getMeterStatus() != null && !("ACTIVE".equals(billMeterDetails.getMeterStatus()))) {
            validFlag = false;
            pageFlowParam.put("businessValidErrMsg",
                              utils.getLabelValueForKey("SBG_METER_VALIDATION_3_MSG"));
        } else if (accDetails.getSancLoad() != null && accDetails.getSancLoad() > 9) {
            validFlag = false;
            pageFlowParam.put("businessValidErrMsg",
                              utils.getLabelValueForKey("SBG_METER_VALIDATION_4_MSG"));
        }

        return validFlag;
    }


    public SelfBillMeterGenPOJO getNetMeterDetails(Account accDetails) {
        System.out.println("getNetMeterDetails  :: Starts");

        SelfBillMeterGenPOJO billMeterDetails = new SelfBillMeterGenPOJO();
        try{
        String discomVal = null;


        if (paramSession.get("loggedInUserDiscom") != null) {
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }

        if (accDetails.getAccountNo() != null) {
            //set request
            GetNetMeterDetailsRequest request = new GetNetMeterDetailsRequest();
            request.setAcctId(accDetails.getAccountNo());

            RestServices service = new RestServices();

            GetNetMeterDetailsResponse response = service.getNetMeterDetails(request, discomVal);

            if (response != null && response.getSerialNbr() != null) {
                billMeterDetails = mapToBillNetMeterResponse(response, accDetails.getAccountNo());
            } else {
                pageFlowParam.put("elibErrMsg", utils.getLabelValueForKey("SOMETHING_WENT_WRONG"));
            }

        }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        System.out.println("getNetMeterDetails  :: Ends");
        return billMeterDetails;
    }

    public SelfBillMeterGenPOJO mapToBillNetMeterResponse(GetNetMeterDetailsResponse response, String accNo) {
        SelfBillMeterGenPOJO billMeterDetails = new SelfBillMeterGenPOJO();

        billMeterDetails.setAccountNo(accNo);
        billMeterDetails.setBadgeNumber(response.getBadgeNbr());
        billMeterDetails.setMeterSerialNumber(response.getSerialNbr());
        billMeterDetails.setMeterConfigType(response.getMtrConfigType());
        billMeterDetails.setMeterConfigId(response.getMeterConfigId());
        billMeterDetails.setPurposeOfSupply(response.getPurposeOfSupply());
        billMeterDetails.setCategory(response.getCategory());
        billMeterDetails.setMeterStatus(response.getMeterStatusInfo());
        billMeterDetails.setMaufacturingType(response.getMaufacturingType());
        billMeterDetails.setResCd(response.getResCd());

        List<MeterReadingsPOJO> meterReadingList = new ArrayList<MeterReadingsPOJO>();

        List<PreviousReadingsPOJO> prevReadListResponse = response.getPreviousReads();
        for (PreviousReadingsPOJO prevReadResponse : prevReadListResponse) {
            MeterReadingsPOJO meterReadings = new MeterReadingsPOJO();
            meterReadings.setSeq(prevReadResponse.getSeq());
            meterReadings.setUomCD(prevReadResponse.getUomCD());
            meterReadings.setFullScale(prevReadResponse.getFullScale());
            meterReadings.setDigitsLeft(prevReadResponse.getDigitsLeft());
            meterReadings.setDigitsRight(prevReadResponse.getDigitsRight());
            meterReadings.setPrevReading(prevReadResponse.getPrevReading());
            meterReadings.setPrevReadDate(prevReadResponse.getPrevReadDate());
            System.out.println("Previous Read - " + meterReadings.getPrevReading());
            System.out.println("UOM - " + meterReadings.getUomCD());
            System.out.println("Digit Left - " + meterReadings.getDigitsLeft());
            meterReadingList.add(meterReadings);
        }
        billMeterDetails.setPreviousReads(meterReadingList);


        System.out.println("Acc No - " + billMeterDetails.getAccountNo());
        System.out.println("Meter Config Type - " + billMeterDetails.getMeterConfigType());
        

        return billMeterDetails;
    }

    public boolean validateNetMeterDetails(SelfBillMeterGenPOJO billMeterDetails, Account accDetails) {
        boolean validFlag = true;

        if (billMeterDetails.getMeterConfigType() != null &&
            !("NET_METER".equals(billMeterDetails.getMeterConfigType()) ||
              "NET_MET_KVAH".equals(billMeterDetails.getMeterConfigType()))) {
            validFlag = false;
            pageFlowParam.put("businessValidErrMsg", utils.getLabelValueForKey("SBG_NET_METER_VALIDATION_1_MSG"));
        } else if (billMeterDetails.getMeterStatus() != null && !("ACTIVE".equals(billMeterDetails.getMeterStatus()))) {
            validFlag = false;
            pageFlowParam.put("businessValidErrMsg", utils.getLabelValueForKey("SBG_NET_METER_VALIDATION_2_MSG"));
        }

        return validFlag;
    }


    public String submitMeterDetails() {
        System.out.println("submitMeterDetails  :: Starts");
        String retValue = null;
        pageFlowParam.put("meterBusErrMsg", null);
        pageFlowParam.put("successMsg", null);

        pageFlowParam.put("meterReadErrMsg", null);
        pageFlowParam.put("demandErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);

        SelfBillMeterGenPOJO userEnteredDetails = new SelfBillMeterGenPOJO();
        String discomVal = null;

        if (paramSession.get("loggedInUserDiscom") != null) {
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }

        //get entered values
        userEnteredDetails = (SelfBillMeterGenPOJO) pageFlowParam.get("userEntryBillDetails");

        Account accDetails = new Account();

        try {
            //get account details
            if (pageFlowParam.get("accountsDetails") != null) {
                accDetails = (Account) pageFlowParam.get("accountsDetails");
            }

            if (!validateUserEntMeterDetails(userEnteredDetails, accDetails)) {
                return null;
            } else {
                //set request
                SubmitBillMeterGenRequest request = new SubmitBillMeterGenRequest();
                request.setAccountId(userEnteredDetails.getAccountNo());
                request.setBadgeNumber(userEnteredDetails.getBadgeNumber());
                request.setMeterSerialNumber(userEnteredDetails.getMeterSerialNumber());
                request.setManufacturerCode(userEnteredDetails.getManufacturerCode());
                request.setCumulativeEnergyKWH(userEnteredDetails.getCumulativeEnergyKWH());
                request.setMaximumDemandKW(userEnteredDetails.getUserEntDemand());
                request.setWSSMeterReadDateTime(userEnteredDetails.getWSSMeterReadDateTime());
                request.setEmailId(accDetails.getEmail());
                request.setMobileNo(accDetails.getMobileNo());
                request.setRemarks(userEnteredDetails.getUserEntComments());

                RestServices service = new RestServices();

                SubmitBillMeterGenResponse response = service.submitBillMeterDetails(request, discomVal);

                if (response != null && response.getResult() != null) {
                    if ("Success"
                        .equals(response.getResult())) {
                        //send email
                        sendEmailConfirmation(utils.getLabelValueForKey("SELF_BILL_GEN_EMAIL_SUBJECT"), accDetails, utils.getLabelValueForKey("SELF_BILL_GEN_EMAIL_MSG_1"));
                        pageFlowParam.put("successMsg",
                                          utils.getLabelValueForKey("SBG_SUCCESS_MSG"));
                        retValue = "toConfirm";
                    } else {
                        //error
                        pageFlowParam.put("meterBusErrMsg", response.getResultMessage());
                    }
                }


            }
        } catch (Exception e) {
            retValue = "toError";
            e.printStackTrace();
        }
        System.out.println("submitMeterDetails  :: Ends");
        return retValue;
    }

    public boolean validateUserEntMeterDetails(SelfBillMeterGenPOJO billMeterDetails, Account accDetails) {

        String meterReadErrMsg = null;
        String demandErrMsg = null;
        String reasonErrMsg = null;

        Integer leftDigit = 0;
        Integer currReadLength = 0;
        boolean updateCurrRead = false;
        Integer rightDigit = 0;

        Integer rightDigitMD = 0;
        String meterBusErrMsg = null;

        boolean validFlag = true;
        Date previousDate;
        Date newDate = new Date();

        try {
            previousDate = convertStrToDate(billMeterDetails.getWSSMeterReadDateTime(), "dd-MMM-yyyy hh:mm:ss");
            System.out.println("Previous Date --> " + previousDate);
            System.out.println("New Date --> " + newDate);
            // check - if bill is already generated or not
            if (newDate.compareTo(previousDate) == 0 || newDate.compareTo(previousDate) < 0) {
                validFlag = false;
                meterBusErrMsg = utils.getLabelValueForKey("SBG_METER_DETAILS_ERR_MSG_8");
                pageFlowParam.put("meterBusErrMsg", meterBusErrMsg);
            } else {
                //check for remarks field
                if (billMeterDetails.getRemarks() == null ||
                    (billMeterDetails.getRemarks() != null && billMeterDetails.getRemarks().isEmpty())) {
                    validFlag = false;
                    reasonErrMsg = utils.getLabelValueForKey("SBG_METER_DETAILS_ERR_MSG_7");
                    pageFlowParam.put("reasonErrMsg", reasonErrMsg);
                }

                //check for Meter Reading field
                if (billMeterDetails.getUserEntMeterReading() == null ||
                    (billMeterDetails.getUserEntMeterReading() != null &&
                     billMeterDetails.getUserEntMeterReading().isEmpty())) {
                    validFlag = false;
                    meterReadErrMsg = utils.getLabelValueForKey("SBG_METER_DETAILS_ERR_MSG_1");
                    pageFlowParam.put("meterReadErrMsg", meterReadErrMsg);
                } else if (!utils.checkNumericWithoutDecimal(billMeterDetails.getUserEntMeterReading())) {
                    validFlag = false;
                    meterBusErrMsg = utils.getLabelValueForKey("SBG_METER_DETAILS_ERR_MSG_2");
                    pageFlowParam.put("meterBusErrMsg", meterBusErrMsg);
                } else if (billMeterDetails.getLeftDigit() != null) {
                    //convert to numeric
                    leftDigit = Integer.parseInt(billMeterDetails.getLeftDigit());
                    System.out.println("Left Digit --> " + leftDigit);
                    if (billMeterDetails.getUserEntMeterReading().contains(".")) {
                        currReadLength = billMeterDetails.getUserEntMeterReading()
                                                         .substring(0, billMeterDetails.getUserEntMeterReading().indexOf("."))
                                                         .length();
                    } else {
                        currReadLength = billMeterDetails.getUserEntMeterReading().length();
                    }
                    System.out.println("currReadLength --> " + currReadLength);
                    if (currReadLength > leftDigit) {
                        validFlag = false;
                        meterBusErrMsg = utils.getLabelValueForKey("SBG_METER_DETAILS_ERR_MSG_3");
                        pageFlowParam.put("meterBusErrMsg", meterBusErrMsg);
                    } else {
                        //add zero with repect to right digit
                        if (billMeterDetails.getRightDigit() != null) {
                            //convert to float
                            rightDigit = Integer.parseInt(billMeterDetails.getRightDigit());
                            System.out.println("Right Digit  --> " + rightDigit);
                            billMeterDetails.setUserEntMeterReading(utils.addZeroAfterDecimal(billMeterDetails.getUserEntMeterReading(),
                                                                                        rightDigit));
                            updateCurrRead = true;
                        }
                    }

                }


                if (updateCurrRead &&
                    billMeterDetails.getPreviousReadKWH() != null) {
                    //convert to numeric
                    System.out.println("billMeterDetails.getPreviousReadKWH()  --> " +
                                       billMeterDetails.getPreviousReadKWH());
                    System.out.println("billMeterDetails.getUserEntMeterReading()  --> " +
                                       billMeterDetails.getUserEntMeterReading());
                    Float prevRead = Float.parseFloat(billMeterDetails.getPreviousReadKWH());
                    Float currRead = Float.parseFloat(billMeterDetails.getUserEntMeterReading());

                    if (prevRead >= currRead) {
                        validFlag = false;
                        meterBusErrMsg = utils.getLabelValueForKey("SBG_METER_DETAILS_ERR_MSG_4");
                        pageFlowParam.put("meterBusErrMsg", meterBusErrMsg);
                    }

                }


                if (billMeterDetails.getUserEntDemand() == null ||
                    (billMeterDetails.getUserEntDemand() != null && billMeterDetails.getUserEntDemand().isEmpty())) {
                    //add zero with repect to right digit
                    if (billMeterDetails.getRightDigitMD() != null) {
                        //convert to float
                        rightDigitMD = Integer.parseInt(billMeterDetails.getRightDigitMD());
                        billMeterDetails.setUserEntDemand(utils.addZeroAfterDecimal(billMeterDetails.getUserEntDemand(),
                                                                              rightDigitMD));
                    }

                } else if (!utils.checkNumericWithNthDecimalVal(billMeterDetails.getUserEntDemand(), 2)) {
                    validFlag = false;
                    demandErrMsg = utils.getLabelValueForKey("SBG_METER_DETAILS_ERR_MSG_5");
                    pageFlowParam.put("demandErrMsg", demandErrMsg);
                } else {
                    //check if left and right greater than leftdigitmd
                    Integer leftDigitMD = Integer.parseInt(billMeterDetails.getLeftDigitMD());
                    String rightDigitValue =
                        billMeterDetails.getUserEntDemand()
                        .substring(billMeterDetails.getUserEntDemand().indexOf(".")+1,
                                   billMeterDetails.getUserEntDemand().length());
                    String leftDigitValue =
                        billMeterDetails.getUserEntDemand()
                        .substring(0, billMeterDetails.getUserEntDemand().indexOf("."));

                    if (Integer.parseInt(leftDigitValue) > leftDigitMD &&
                        Integer.parseInt(rightDigitValue) > leftDigitMD) {
                        validFlag = false;
                        meterBusErrMsg = utils.getLabelValueForKey("SBG_METER_DETAILS_ERR_MSG_6");
                        pageFlowParam.put("meterBusErrMsg", meterBusErrMsg);
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return validFlag;
    }

    public Date convertStrToDate(String str, String dateFormat) {
        Date convertedDate = new Date();
        if (str != null) {
            SimpleDateFormat sdformat = new SimpleDateFormat(dateFormat);
            try {
                convertedDate = sdformat.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return convertedDate;
    }

    public String submitNetMeterDetails() {
        System.out.println("submitNetMeterDetails  :: Starts");
        String retValue = null;
        pageFlowParam.put("netmeterBusErrMsg", null);
        pageFlowParam.put("successMsg", null);

        pageFlowParam.put("meterReadKwhErrMsg", null);
        pageFlowParam.put("meterReadKwheErrMsg", null);
        pageFlowParam.put("meterReadKVhErrMsg", null);
        pageFlowParam.put("meterReadKVheErrMsg", null);
        pageFlowParam.put("kvademandErrMsg", null);
        pageFlowParam.put("reasonErrMsg", null);


        SelfBillMeterGenPOJO userEnteredDetails = new SelfBillMeterGenPOJO();
        String discomVal = null;

        if (paramSession.get("loggedInUserDiscom") != null) {
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }

        //get entered values
        userEnteredDetails = (SelfBillMeterGenPOJO) pageFlowParam.get("userEntryBillDetails");

        Account accDetails = new Account();
        
        try {
            //get account details
            if (pageFlowParam.get("accountsDetails") != null) {
                accDetails = (Account) pageFlowParam.get("accountsDetails");
            }
            if (!validateUserEntNetMeterDetails(userEnteredDetails, accDetails)) {
                return null;
            } else {
                //set request
                SubmitBillNetMeterGenRequest request = new SubmitBillNetMeterGenRequest();
                request.setAccountId(userEnteredDetails.getAccountNo());
                request.setBadgeNumber(userEnteredDetails.getBadgeNumber());
                request.setMeterSerialNumber(userEnteredDetails.getMeterSerialNumber());
                request.setManufacturerCode(userEnteredDetails.getManufacturerCode());
                request.setWSSMeterReadDateTime(userEnteredDetails.getWSSMeterReadDateTime());
                request.setEmailId(accDetails.getEmail());
                request.setMobileNo(accDetails.getMobileNo());
                request.setRemarks(userEnteredDetails.getUserEntComments());

                if (userEnteredDetails.getMeterConfigType() != null &&
                    "NET_METER ".equals(userEnteredDetails.getMeterConfigType())) {
                    request.setCumulativeEnergyKWH(userEnteredDetails.getUserEntReadingKwh());
                    request.setCumulativeEnergyKWHE(userEnteredDetails.getUserEntReadingKwhe());
                    request.setMaximumDemandKW(userEnteredDetails.getUserEntMaxKvaDemand());
                } else if (userEnteredDetails.getMeterConfigType() != null &&
                           "NET_MET_KVAH ".equals(userEnteredDetails.getMeterConfigType())) {
                    request.setCumulativeEnergyKWH(userEnteredDetails.getUserEntReadingKwh());
                    request.setCumulativeEnergyKVAH(userEnteredDetails.getUserEntReadingKvh());
                    request.setCumulativeEnergyKWHE(userEnteredDetails.getUserEntReadingKwhe());
                    request.setCumulativeEnergyKVAHE(userEnteredDetails.getUserEntReadingKvhe());

                    request.setMaximumDemandKVA(userEnteredDetails.getUserEntMaxKvaDemand());
                }


                //            request.setMaximumDemandKVE(userEnteredDetails.getUserEntDemand());

                RestServices service = new RestServices();

                SubmitBillNetMeterGenResponse response = service.submitBillNetMeterDetails(request, discomVal);

                if (response != null && response.getResult() != null) {
                    if ("Success"
                        .equals(response.getResult())) {
                        //send email
                        sendEmailConfirmation(utils.getLabelValueForKey("SELF_BILL_GEN_EMAIL_SUBJECT"), accDetails, utils.getLabelValueForKey("SELF_BILL_GEN_EMAIL_MSG_1"));
                        pageFlowParam.put("successMsg", utils.getLabelValueForKey("SBG_SUCCESS_MSG"));
                        retValue = "toConfirm";
                    } else {
                        //error
                        pageFlowParam.put("netmeterBusErrMsg", response.getResultMessage());
                    }
                }


            }

        } catch (Exception e) {
            retValue = "toError";
            e.printStackTrace();
        }
        
        System.out.println("submitNetMeterDetails  :: Ends");
        return retValue;
    }

    public boolean validateUserEntNetMeterDetails(SelfBillMeterGenPOJO billMeterDetails, Account accDetails) {

        String meterReadKwhErrMsg = null;
        String meterReadKwheErrMsg = null;
        String meterReadKVhErrMsg = null;
        String meterReadKVheErrMsg = null;
        String kvademandErrMsg = null;
        String kWdemandErrMsg = null;
        String reasonErrMsg = null;

        boolean validFlag = true;
        MeterReadingsPOJO prevRead = new MeterReadingsPOJO();
        Date previousDate;
        Date currentDate = new Date();

        int currentMonth;
        int prevMonth;

        Float prevReading;
        Float currentReading;

        String netmeterBusErrMsg = null;

        boolean updateCurrRead = false;

        //get previous readings
        if (billMeterDetails.getPreviousReads() != null) {
            List<MeterReadingsPOJO> prevReadList = billMeterDetails.getPreviousReads();
            for (MeterReadingsPOJO prevRd : prevReadList) {
                if (prevRd.getSeq() != null && "1".equals(prevRd.getSeq())) {
                    prevRead.setDigitsLeft(prevRd.getDigitsLeft());
                    prevRead.setDigitsRight(prevRd.getDigitsRight());
                    prevRead.setFullScale(prevRd.getFullScale());
                    prevRead.setPrevReadDate(prevRd.getPrevReadDate());
                    prevRead.setPrevReading(prevRd.getPrevReading());
                    prevRead.setSeq(prevRd.getSeq());
                    prevRead.setUomCD(prevRd.getUomCD());
                    System.out.println("---------------------");
                    System.out.println("Previous Read - " + prevRead.getPrevReading());
                    System.out.println("UOM - " + prevRead.getUomCD());
                    System.out.println("Digit Left - " + prevRead.getDigitsLeft());
                    System.out.println("Previous Read - " + prevRead.getPrevReading());
                    System.out.println("---------------------");
                }
            }
        }
        
        //validate reading KWH
        if (billMeterDetails.getUserEntReadingKwh() == null || (billMeterDetails.getUserEntReadingKwh() != null && billMeterDetails.getUserEntReadingKwh().isEmpty())) {
            validFlag = false;
            meterReadKwhErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_2");
            pageFlowParam.put("meterReadKwhErrMsg", meterReadKwhErrMsg);
        } else if (!utils.checkNumericWithoutDecimal(billMeterDetails.getUserEntReadingKwh())) {
            validFlag = false;
            netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_3");
            pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
        } else if (prevRead.getDigitsLeft() != null) {
            //convert to numeric
            int leftDigit = Integer.parseInt(prevRead.getDigitsLeft());
            int currReadLength = billMeterDetails.getUserEntReadingKwh().length();
            if (currReadLength > leftDigit) {
                validFlag = false;
                netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_4");
                pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
            } else {
                //add zero with repect to right digit
                if (prevRead.getDigitsRight() != null) {
                    //convert to float
                    int rightDigit = Integer.parseInt(prevRead.getDigitsRight());
                    billMeterDetails.setUserEntReadingKwh(utils.addZeroAfterDecimal(billMeterDetails.getUserEntReadingKwh(),
                                                                              rightDigit));
                }
            }

        }
        
        
        //validate reading KWHE
        if (billMeterDetails.getUserEntReadingKwhe() == null || (billMeterDetails.getUserEntReadingKwhe() != null && billMeterDetails.getUserEntReadingKwhe().isEmpty())) {
            validFlag = false;
            meterReadKwheErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_5");
            pageFlowParam.put("meterReadKwheErrMsg", meterReadKwheErrMsg);
        } else if (!utils.checkNumericWithoutDecimal(billMeterDetails.getUserEntReadingKwhe())) {
            validFlag = false;
            netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_6");
            pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
        } else if (prevRead.getDigitsLeft() != null) {
            //convert to numeric
            int leftDigit = Integer.parseInt(prevRead.getDigitsLeft());
            int currReadLength = billMeterDetails.getUserEntReadingKwhe().length();
            if (currReadLength > leftDigit) {
                validFlag = false;
                netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_7");
                pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
            } else {
                //add zero with repect to right digit
                if (prevRead.getDigitsRight() != null) {
                    //convert to float
                    int rightDigit = Integer.parseInt(prevRead.getDigitsRight());
                    billMeterDetails.setUserEntReadingKwhe(utils.addZeroAfterDecimal(billMeterDetails.getUserEntReadingKwhe(),
                                                                               rightDigit));
                }
            }

        }

        //validate Reading for NET_MET_KVAH meter config type
        if(billMeterDetails.getMeterConfigType() != null && "NET_MET_KVAH".equals(billMeterDetails.getMeterConfigType())){
            //validate Reading KVH
            if (billMeterDetails.getUserEntReadingKvh() == null || (billMeterDetails.getUserEntReadingKvh() != null && billMeterDetails.getUserEntReadingKvh().isEmpty())) {
                validFlag = false;
                meterReadKVhErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_19");
                pageFlowParam.put("meterReadKVhErrMsg", meterReadKVhErrMsg);
            } else if (!utils.checkNumericWithoutDecimal(billMeterDetails.getUserEntReadingKvh())) {
                validFlag = false;
                netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_20");
                pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
            } else if (prevRead.getDigitsLeft() != null) {
                //convert to numeric
                int leftDigit = Integer.parseInt(prevRead.getDigitsLeft());
                int currReadLength = billMeterDetails.getUserEntReadingKvh().length();
                if (currReadLength > leftDigit) {
                    validFlag = false;
                    netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_21");
                    pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
                } else {
                    //add zero with repect to right digit
                    if (prevRead.getDigitsRight() != null) {
                        //convert to float
                        int rightDigit = Integer.parseInt(prevRead.getDigitsRight());
                        billMeterDetails.setUserEntReadingKvh(utils.addZeroAfterDecimal(billMeterDetails.getUserEntReadingKvh(),
                                                                                  rightDigit));
                    }
                }

            }

            
            //validate Reading KVHE
            if (billMeterDetails.getUserEntReadingKvhe() == null || (billMeterDetails.getUserEntReadingKvhe() != null && billMeterDetails.getUserEntReadingKvhe().isEmpty())) {
                validFlag = false;
                meterReadKVheErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_22");
                pageFlowParam.put("meterReadKVheErrMsg", meterReadKVheErrMsg);
            } else if (!utils.checkNumericWithoutDecimal(billMeterDetails.getUserEntReadingKvhe())) {
                validFlag = false;
                netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_23");
                pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
            } else if (prevRead.getDigitsLeft() != null) {
                //convert to numeric
                int leftDigit = Integer.parseInt(prevRead.getDigitsLeft());
                int currReadLength = billMeterDetails.getUserEntReadingKvhe().length();
                if (currReadLength > leftDigit) {
                    validFlag = false;
                    netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_24");
                    pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
                } else {
                    //add zero with repect to right digit
                    if (prevRead.getDigitsRight() != null) {
                        //convert to float
                        int rightDigit = Integer.parseInt(prevRead.getDigitsRight());
                        billMeterDetails.setUserEntReadingKvhe(utils.addZeroAfterDecimal(billMeterDetails.getUserEntReadingKvhe(),
                                                                                   rightDigit));
                    }
                }

            }
 
        }
        


        //validate demand in KWH
        if(billMeterDetails.getMeterConfigType() != null && "NET_METER".equals(billMeterDetails.getMeterConfigType())){
            if (billMeterDetails.getUserEntMaxKwDemand() == null ||
                (billMeterDetails.getUserEntMaxKwDemand() != null &&
                 billMeterDetails.getUserEntMaxKwDemand().isEmpty())) {
                validFlag = false;
                kWdemandErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_8_1");
                pageFlowParam.put("kWdemandErrMsg", kWdemandErrMsg);
            } else if (!utils.checkNumericWithNthDecimalVal(billMeterDetails.getUserEntMaxKwDemand(), 2)) {
                validFlag = false;
                netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_9");
                pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
            }    
        }
        
        
        //validate demand in KV
        if(billMeterDetails.getMeterConfigType() != null && "NET_MET_KVAH".equals(billMeterDetails.getMeterConfigType())){
            if (billMeterDetails.getUserEntMaxKvaDemand() == null ||
                (billMeterDetails.getUserEntMaxKvaDemand() != null &&
                 billMeterDetails.getUserEntMaxKvaDemand().isEmpty())) {
                validFlag = false;
                kvademandErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_25");
                pageFlowParam.put("kvademandErrMsg", kvademandErrMsg);
            } else if (!utils.checkNumericWithNthDecimalVal(billMeterDetails.getUserEntMaxKvaDemand(), 2)) {
                validFlag = false;
                netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_26");
                pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
            }
        }
        
        
        
        //Validate Comments 
        if (billMeterDetails.getRemarks() == null ||
            (billMeterDetails.getRemarks() != null && billMeterDetails.getRemarks().isEmpty())) {
            validFlag = false;
            reasonErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_1");
            pageFlowParam.put("reasonErrMsg", reasonErrMsg);
        }
        
        
        //validate current date with previous date
        if ("1".equals(prevRead.getSeq())) {
            previousDate = convertStrToDate(prevRead.getPrevReadDate(), "dd-MMM-yyyy hh:mm:ss");

            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            currentMonth = cal.get(Calendar.MONTH);

            cal.setTime(previousDate);
            prevMonth = cal.get(Calendar.MONTH);

            if (currentMonth <= prevMonth) {
                validFlag = false;
                netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_10");
                pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
            }

        }

    
    
        //validate current reading with previous reading for KWH and KWHE
        if ("1".equals(prevRead.getSeq())) {
                if (prevRead.getUomCD() != null && "KWH".equals(prevRead.getUomCD())) {
                    if (prevRead.getPrevReading() != null && billMeterDetails.getUserEntReadingKwh() != null) {
                        prevReading = Float.parseFloat(prevRead.getPrevReading());
                        if(utils.checkNumeric(billMeterDetails.getUserEntReadingKwh())){
                            currentReading = Float.parseFloat(billMeterDetails.getUserEntReadingKwh());
                            
                            if (prevReading >= currentReading) {
                                validFlag = false;
                                netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_11");
                                pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
                            }
                        }else{
                            validFlag = false;
                            netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_3");
                            pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
                        }
                        
                        
                    }

                }else if (prevRead.getUomCD() != null && "KWHE".equals(prevRead.getUomCD())) {
                    if (prevRead.getPrevReading() != null && billMeterDetails.getUserEntReadingKwhe() != null) {
                        prevReading = Float.parseFloat(prevRead.getPrevReading());
                        if(utils.checkNumeric(billMeterDetails.getUserEntReadingKwhe())){
                            currentReading = Float.parseFloat(billMeterDetails.getUserEntReadingKwhe());
                            
                            if (prevReading >= currentReading) {
                                validFlag = false;
                                meterReadKwheErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_12");
                                pageFlowParam.put("meterReadKwheErrMsg", meterReadKwheErrMsg);
                            }
                        }else{
                            validFlag = false;
                            netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_6");
                            pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
                        }
                    }
                }
        }
        
        

        //validate current reading with previous reading for KVH and KVHE
        if(billMeterDetails.getMeterConfigType() != null && "NET_MET_KVAH".equals(billMeterDetails.getMeterConfigType())) {
            if ("1".equals(prevRead.getSeq())) {
               if (prevRead.getUomCD() != null && "KVAH".equals(prevRead.getUomCD())) {
                    if (prevRead.getPrevReading() != null && billMeterDetails.getUserEntReadingKvh() != null) {
                        prevReading = Float.parseFloat(prevRead.getPrevReading());
                        if(utils.checkNumeric(billMeterDetails.getUserEntReadingKvh())){
                            currentReading = Float.parseFloat(billMeterDetails.getUserEntReadingKvh());
                            
                            if (prevReading >= currentReading) {
                                validFlag = false;
                                netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_30");
                                pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
                            }
                        }else{
                            validFlag = false;
                            netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_20");
                            pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
                        }
                    }
                } else if (prevRead.getUomCD() != null && "KV-E".equals(prevRead.getUomCD())) {
                    if (prevRead.getPrevReading() != null && billMeterDetails.getUserEntReadingKvhe() != null) {
                        prevReading = Float.parseFloat(prevRead.getPrevReading());
                        if(utils.checkNumeric(billMeterDetails.getUserEntReadingKvhe())){
                            currentReading = Float.parseFloat(billMeterDetails.getUserEntReadingKvhe());
                            
                            if (prevReading >= currentReading) {
                                validFlag = false;
                                netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_31");
                                pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
                            }
                        }else{
                            validFlag = false;
                            netmeterBusErrMsg = utils.getLabelValueForKey("SBG_NET_METER_DETAILS_ERR_MSG_23");
                            pageFlowParam.put("netmeterBusErrMsg", netmeterBusErrMsg);
                        }
                    }
                }
            }

        }


        return validFlag;
    }
    
    public String findLabel(String key){
        String value = utils.getLabelValueForKey(key);
        System.out.println("Key --> "+key+ " " + "Value --> "+value); 
        return value;
    }
    
    
    public String sendEmailConfirmation(String mailSubject, Account actDetails, String message) throws SQLException, IOException { 
        String discomVal = null;
        final String subject = mailSubject;
        String mailContent = this.getMailBody(actDetails, message); 
        RestServices rs = new RestServices();
        SendEmailRequest emailRequest = new SendEmailRequest();
        emailRequest.setFrom("self.service@uppclonline.com");
        //to comment hardcoded one
//            emailRequest.setTo("palashdabkara@gmail.com");
        
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
    
    private String getMailHeader() {
        String topHtml = "<html>\n" + 
        "   <head></head>\n" + 
        "   <body>";
        
        String header = "<table  border=0 cellspacing=0 cellpadding=0 style='border-collapse:collapse;mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt'>\n" + 
        "            <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>\n" + 
        "               <td width=616 valign=top style='width:462.1pt;padding:0in 5.4pt 0in 5.4pt'>\n" + 
        "                  <p  style='margin-bottom:0in;margin-bottom:.0001pt;line-height:  normal'><span style='mso-no-proof:yes'><img width=454 height=74 src=https://www.uppclonline.com/en_GB/images/logo.png alt=logo.png></span></p>\n" + 
        "               </td>\n" + 
        "            </tr>\n" + 
        "            <tr style='mso-yfti-irow:1;height:23.85pt'>\n" + 
        "               <td width=616 valign=top style='width:462.1pt;padding:0in 5.4pt 0in 0in;  height:23.85pt'>\n" + 
        "                  <p  style='margin-bottom:0in;margin-bottom:.0001pt;line-height:  normal'><span style='mso-no-proof:yes'><img width=616 height=33  src=https://www.uppclonline.com/en_GB/images/nav_bg_email.png alt=logo.png></span></p>\n" + 
        "               </td>\n" + 
        "            </tr>";
        
        
        return topHtml + header;
    }
    
    private String getMailFooter() {
        String footer = "<tr style='mso-yfti-irow:3;height:5.0pt'><td width=616 valign=top style='width:462.1pt;background:#FF9933;padding:0in 5.4pt 0in 5.4pt;height:5.0pt'><p  style='margin-bottom:0in;margin-bottom:.0001pt;line-height:normal;tab-stops:69.75pt'><span style='font-size:1.0pt;mso-bidi-theme-font:minor-bidi;color:black;mso-themecolor:text1'></span></p></td></tr><tr style='mso-yfti-irow:4;mso-yfti-lastrow:yes;height:34.25pt'><td width=616 valign=top style='width:462.1pt;background:#0E92BD;padding:0in 5.4pt 0in 5.4pt;height:34.25pt'><p  align=center style='margin-bottom:0in;margin-bottom:.0002pt;text-align:center;line-height:normal;tab-stops:69.75pt'><span  style='font-size:9.5pt;color:black;mso-themecolor:text1'>UP Power Corporation Limited | Shakti Bhavan, 14, Ashok  Marg, Lucknow, UP, India.</span><span style='font-size:10.0pt;mso-bidi-font-size:11.0pt;mso-bidi-theme-font:minor-bidi;color:black;mso-themecolor:text1'></span></p><p  align=center style='margin-bottom:0in;margin-bottom:.0001pt;text-align:center;line-height:normal;tab-stops:69.75pt'><span  style='font-size:10.0pt;mso-bidi-font-size:11.0pt;mso-bidi-theme-font:minor-bidi;color:black;mso-themecolor:text1'></span></p></td></tr>\n" + 
        "         </table>\n" + 
        "         </div>\n" + 
        "         <p  align=center style='text-align:center'><span lang=EN-INstyle='mso-ansi-language:EN-IN'></span></p>\n" + 
        "      </div>\n" + 
        "      </div></div>";
        
        String bottomHtml = "</body>\n" + 
        "</html>"; 
        
        return footer + bottomHtml;
    }
    
    private String getMailBody(Account actDetails, String msg) {
        String emailHeader = getMailHeader();
        String emaiFooter = getMailFooter();
        
        String body = "<tr style='mso-yfti-irow:2;height:225.0pt'>\n" + 
        "               <td width=616 valign=top style='width:462.1pt;padding:0in 5.4pt 0in 5.4pt; height:225.0pt'>\n" + 
        "                  <p  style='margin-bottom:0in;margin-bottom:.0001pt;line-height: normal'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><span style='mso-spacerun:yes'></span></span></b></p>\n" + 
        "                  <p  style=margin-bottom:0in;margin-bottom:.0001pt;line-height: normal'>\n" + 
        "                     <b style='mso-bidi-font-weight:normal'>\n" + 
        "                        <span style='font-size:12.0pt'>\n" + 
        "                           <span style='mso-spacerun:yes'></span>Dear " + actDetails.getName() + " ,\n" + 
        "                           <o:p></o:p>\n" + 
        "                        </span>\n" + 
        "                     </b>\n" + 
        "                  </p>\n" + 
        "                  <div>\n" + 
        "                     <p  style='text-align:justify'><span style='font-size:11pt; line-height:115%'>Account No : " +actDetails.getAccountNo()+ "</span></p>\n" + 
        "                     <p  style='text-align:justify'><span style='font-size:11.0pt; line-height:115%;'> " +msg+ ".</span></p>\n" + 
        "                     <p style='margin-bottom:0in;margin-bottom:.0001pt;line-height:normal'></p>\n" + 
        "               </td>\n" + 
        "            </tr>";

        
        body = body.replaceAll("null", " ");
        return emailHeader + body + emaiFooter;
    }
    
    
    
    

}
