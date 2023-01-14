package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.Account;
import com.xx.uppcl.pojo.BillDetail;
import com.xx.uppcl.rest.request.GetBillingDetailsRequest;
import com.xx.uppcl.rest.request.GetBillingDetailsRequest.DateRange;
import com.xx.uppcl.rest.request.GetBillingDetailsRequest.SearchParameters;
import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.response.Billing.BillDetails;
import com.xx.uppcl.rest.response.Billing.BillInfo;
import com.xx.uppcl.rest.response.GetBillingDetailsResponse;
import com.xx.uppcl.rest.response.GetConsumerDetailsResponse;
import com.xx.uppcl.rest.services.RestServices;
import com.xx.uppcl.services.DBServices;

import com.xx.uppcl.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Map;

import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;

public class AccountDetailsOnLoadBean {
    public AccountDetailsOnLoadBean() {
    }

    Map paramSession = ADFContext.getCurrent().getSessionScope();
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();
    
    private DBServices dbServices=new DBServices();
    String error = null;
    Utils utils = new Utils();
    
    
    public String getAccountListandDetails(){
        String retValue = null;
        Boolean loggedInFlag = false;
        String loggedIn = null;
        String entryFlag = null;
        try{
        System.out.println("Logged in succes  --> "+paramSession.get("isLoggedInSuccess"));
        if(paramSession.get("isLoggedInSuccess") != null){
            loggedIn = (String) paramSession.get("isLoggedInSuccess");
            if("Y".equals(loggedIn)){
                loggedInFlag = true;
            }else{
                loggedInFlag = false;
            }
            
        }
        System.out.println("initialEntryFlag  --> "+paramSession.get("initialEntryFlag"));
        if(paramSession.get("initialEntryFlag") != null){
            entryFlag = (String) paramSession.get("initialEntryFlag");
        }
        System.out.println("Entry Flag --> "+entryFlag);
        if(loggedInFlag){
            System.out.println("Logged In");
            if(!("Y".equals(entryFlag))){
                System.out.println("First Time Entry");
               //continue to call account details
               retValue = getAccListandDetails();
            }else{
                System.out.println("Not the First Time Entry");
                retValue= "continue";
            }
        }else{
            System.out.println("Not Logged In");
            //redirect to login as user is not authenticated
            try {
                Utils util = new Utils();
                util.redirect(dbServices.getProperty("LOGIN_URL"));
            } catch (Exception e) {
                e.printStackTrace();
                retValue = "toError";
            }
        }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error Message --> "+e.getMessage());
        }
        return retValue;
    }
    
    public String getAccListandDetails() {
        System.out.println("getAccountListAndDetails :: Starts");
        //set current tab
        String retValue = null;
        try {
            String primaryAccNo = null;
            String primaryUserName = null;
            Boolean loggedIn = false;


            List<SelectItem> accountsList = new ArrayList<SelectItem>();
            Account accountDetails = new Account();
            List<BillDetail> billDetails = new ArrayList<BillDetail>();

            // get primary account no
            if (paramSession.get("loggedInAccountNo") != null) {
                primaryAccNo = (String) paramSession.get("loggedInAccountNo");
            }

            //get primary user name
            if (paramSession.get("loggedInUserName") != null) {
                primaryUserName = (String) paramSession.get("loggedInUserName");
            }


            //get linked accounts with primary acc no
            if (primaryAccNo != null) {
                accountsList = getLinkedAccount(primaryAccNo);
            }
            System.out.println("account list size--> " + accountsList.size());

            paramSession.put("accountsListFromSession", accountsList);

            //get consumer details for primary account no
            if (primaryAccNo != null) {
                accountDetails = getConsumerAccDetails(primaryAccNo);
            }
            
            //get bill details for primary account no
            if (primaryAccNo != null) {
                billDetails = getBillingData(primaryAccNo);
                accountDetails.setBillDetails(billDetails);
            }
            paramSession.put("billDetailsFromSession", billDetails);
            paramSession.put("accountsDetailsFromSession", accountDetails);
            
            if(accountDetails.getAccountNo() != null){
                paramSession.put("initialEntryFlag", "Y");
            }
            
            if(error != null){
                throw new Exception();
            }else{
               retValue = "continue";
            }
        } catch (Exception e) {
            System.out.println("Inside catch block ");
            retValue = "toError";
            e.printStackTrace();
        }
        System.out.println("getAccountListAndDetails :: Ends");
        return retValue;
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
        try {
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
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Inside catch block of getConsumerAccDetails");
            error = "serviceError";
        }
        System.out.println("getConsumerAccDetails  :: Ends");
        return accDetails;
    }

    public Account mapToAccountResponse(GetConsumerDetailsResponse response) {
        Account accDetails = new Account();

        accDetails.setAccountNo(response.getKno());
        accDetails.setName(response.getConsumerName());
        accDetails.setOutStandingAmount(response.getAccountInfo());

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
        
        String addr2="";
        if(response.getPremiseAddress().getAddressLine1()!= null){
            addr2=addr2+response.getPremiseAddress().getAddressLine1()+"\n";
        }
        if(response.getPremiseAddress().getAddressLine2()!= null){
            addr2=addr2+response.getPremiseAddress().getAddressLine2()+"\n";
        }
        if(response.getPremiseAddress().getAddressLine3()!= null){
            addr2=addr2+response.getPremiseAddress().getAddressLine3()+"\n";
        }
        if(response.getPremiseAddress().getAddressLine4()!= null){
            addr2=addr2+response.getPremiseAddress().getAddressLine4()+"\n";
        }
        if(response.getPremiseAddress().getCity()!= null){
            addr2=addr2+response.getPremiseAddress().getCity()+"\n";
        }
        if(response.getPremiseAddress().getPinCode()!= null){
            addr2=addr2+response.getPremiseAddress().getPinCode()+"\n";
        }
        if(response.getPremiseAddress().getState()!= null){
            addr2=addr2+response.getPremiseAddress().getState();
        }
        accDetails.setPremiseAddr(addr2);

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
        accDetails.setSupplyType(response.getCategory());

        accDetails.setMobileNo(response.getMobileNumber());
        accDetails.setPhoneNum(response.getPhoneNumber());
        accDetails.setEmail(response.getEmailAddress());
        accDetails.setDivision(response.getDivision());
        accDetails.setDiscom(response.getDiscom());
        accDetails.setSubDivision(response.getSubDivision());
        
        if (response.getSecurityDeposit() != null) {
            accDetails.setSecurityAmount(Float.parseFloat(response.getSecurityDeposit()));
        }
        
        //todo mapping for meter number
        accDetails.setMeterNum(response.getCin());

        System.out.println("Acc No - " + accDetails.getAccountNo());
        System.out.println("currentLoad - " + accDetails.getCurrentLoad());
        System.out.println("Mobile Number - " + accDetails.getMobileNo());
        System.out.println("Address - " + accDetails.getAddress());


        return accDetails;
    }

    public List<BillDetail> getBillingData(String accountNo) {
        List<BillDetail> list = new ArrayList<BillDetail>();
        RestServices service = new RestServices();
        Date currentDate = new Date();
        String discomVal = null;
        try {
            if (paramSession.get("loggedInUserDiscom") != null) {
                discomVal = (String) paramSession.get("loggedInUserDiscom");
            }

            GetBillingDetailsRequest request = new GetBillingDetailsRequest();
            request.setKnumber(accountNo); //"0010960869"
            DateRange dr = request.new DateRange();
            dr.setFromDate(utils.getPrevious12MonthDate(currentDate));
            dr.setToDate(utils.convertDateToStr(currentDate, "dd-MM-yyyy"));
            SearchParameters sp = request.new SearchParameters();
            sp.setDateRange(dr);
            request.setSearchParameters(sp);

            GetBillingDetailsResponse response = service.getBillingDetails(request, discomVal);
            if (response != null && response.getBillDetails() != null) {
                ArrayList<BillDetails> billDetails = response.getBillDetails();
                for (BillDetails bd : billDetails) {
                    BillDetail bill = new BillDetail();
                    BillInfo billInfo = bd.getBillInfo();
                    if (billInfo != null) {
                        bill.setBillDueDate(billInfo.getBillDueDate());
                        bill.setBillNo(billInfo.getBillNo());
                    }
                    
                    if(bd.getBillAmount() == null){
                        bill.setBillAmount("0.0");
                    }else{
                        bill.setBillAmount(bd.getBillAmount());  
                    }
                    
                    bill.setBillIssuedDate(bd.getBillIssuedDate());
                    bill.setBillMonthYear(bd.getBillMonthYear());
                    
                    if(bd.getPaymentMade() == null){
                        bill.setPaymentMade("0.0");
                    }else{
                        bill.setPaymentMade(bd.getPaymentMade());  
                    }
                    
                    bill.setPaymentDate(bd.getPaymentDate());
                    
                    if(bill.getBillAmount() != null && bill.getPaymentMade() != null){
                        //convert amount str to int
                        Float billAmt = Float.parseFloat(bill.getBillAmount());
                        Float paymntMade = Float.parseFloat(bill.getPaymentMade());
                        
                        Float billDue = billAmt - paymntMade;
                        System.out.println("Payment Due --> "+billDue);
                        
                        String totalAmtDue = String.valueOf(billDue);
                        
                        bill.setTotalPaymentDue(totalAmtDue);
                    }
                    
                    list.add(bill);
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Inside catch block of getConsumerAccDetails");
            error = "serviceError";
        }

        return list;
    }
}
