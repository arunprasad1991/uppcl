package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.Account;
import com.xx.uppcl.pojo.BillDetail;
import com.xx.uppcl.pojo.ConsumpDetailsPOJO;
import com.xx.uppcl.pojo.ServiceRequestPojo;
import com.xx.uppcl.rest.request.DateRangePojo;
import com.xx.uppcl.rest.request.GetBillDownloadRequest;
import com.xx.uppcl.rest.request.GetBillingDetailsRequest;
import com.xx.uppcl.rest.request.GetBillingDetailsRequest.DateRange;
import com.xx.uppcl.rest.request.GetBillingDetailsRequest.SearchParameters;
import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.request.GetConsumptionDetailsRequest;
import com.xx.uppcl.rest.request.GetOTPRequest;
import com.xx.uppcl.rest.request.SearchParamPojo;
import com.xx.uppcl.rest.request.SendEmailRequest;
import com.xx.uppcl.rest.response.Billing.BillDetails;
import com.xx.uppcl.rest.response.Billing.BillInfo;
import com.xx.uppcl.rest.response.GetBillDownloadResponse;
import com.xx.uppcl.rest.response.GetBillingDetailsResponse;
import com.xx.uppcl.rest.response.GetConsumerDetailsResponse;
import com.xx.uppcl.rest.response.GetConsumptionDetailsResponse;
import com.xx.uppcl.rest.response.GetOTPResponse;
import com.xx.uppcl.rest.response.SendEmailResponse;
import com.xx.uppcl.rest.response.billconsumption.ConsumptionDetails;
import com.xx.uppcl.rest.services.RestServices;
import com.xx.uppcl.services.DBServices;

import com.xx.uppcl.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import java.util.TreeMap;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.view.faces.bi.component.chart.UIDataItem;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.PopupFetchEvent;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.UploadedFile;

import org.springframework.util.Base64Utils;

import sun.misc.IOUtils;

public class MyAccountBean implements Serializable{
        private static final long serialVersionUID=1843725674L;
    private transient RichTable billDetailsTable;
    private transient RichPopup addAccountPopUp;
    private transient RichPopup removeAccountPopUp;
    private RichPopup editPhotoPopupBind;
    private RichInputFile uploadFileBind;
    private RichPopup emailBillPopupBind;

    public MyAccountBean() {
        super();
    }
    
    private String accountNumber;
    private String lastLogin;
    private String name;
    private String billingAddress ;
    private String premisesAddress;
    private String sanctionedLoad;
    private String discomName;
    private String division;
    private String subDivision;
    private String supplyType;
    private String securityAmount;
    private String mobileNumber;
    private String email;
    private List<String> myAccountList;
    private List<BillDetail> billDetailsList=new ArrayList<BillDetail>();
    private String discomValue;
    private String addAccountNo;
    private String addAccValidationMsg;
    private boolean addAccValidationFlag=false;
    private String selectedAccNo;
    private List<Account> manageAccountList;
    private boolean addSuccessFlag=false;
    private String removeAccNo;
    
    private String addMobileNo;
    private String addMobileValidationMsg;
    private boolean addMobileValidationFlag=false;
    private String addMobileOTP;
    private String addMobileOTPValidationMsg;
    private String enteredMobileOTP;
    
    private String addEmail;
    private String addEmailValidationMsg;
    private  boolean addEmailValidationFlag;
    private String addEmailOTP;
    private String addEmailOTPValidationMsg;
    private String enteredEmailOTP;
    
    
    Map paramSession= ADFContext.getCurrent().getSessionScope();
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();
    Utils utils = new Utils();


    public void setRemoveAccNo(String removeAccNo) {
        this.removeAccNo = removeAccNo;
    }

    public String getRemoveAccNo() {
        return removeAccNo;
    }

    public void setAddSuccessFlag(boolean addSuccessFlag) {
        this.addSuccessFlag = addSuccessFlag;
    }

    public boolean isAddSuccessFlag() {
        return addSuccessFlag;
    }

    public void setManageAccountList(List<Account> manageAccountList) {
        this.manageAccountList = manageAccountList;
    }

    public List<Account> getManageAccountList() {
        return manageAccountList;
    }

    public void setSelectedAccNo(String selectedAccNo) {
        this.selectedAccNo = selectedAccNo;
    }

    public String getSelectedAccNo() {
        return selectedAccNo;
    }

    public void setAddAccValidationFlag(boolean addAccValidationFlag) {
        this.addAccValidationFlag = addAccValidationFlag;
    }

    public boolean isAddAccValidationFlag() {
        return addAccValidationFlag;
    }

    public void setAddAccValidationMsg(String addAccValidationMsg) {
        this.addAccValidationMsg = addAccValidationMsg;
    }

    public String getAddAccValidationMsg() {
        return addAccValidationMsg;
    }


    public void setAddAccountNo(String addAccountNo) {
        this.addAccountNo = addAccountNo;
    }

    public String getAddAccountNo() {
        return addAccountNo;
    }

    public void setDiscomValue(String discomValue) {
        this.discomValue = discomValue;
    }

    public String getDiscomValue() {
        return discomValue;
    }

    public void setBillDetailsList(List<BillDetail> billDetailsList) {
        this.billDetailsList = billDetailsList;
    }

    public List<BillDetail> getBillDetailsList() {
        return billDetailsList;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setPremisesAddress(String premisesAddress) {
        this.premisesAddress = premisesAddress;
    }

    public String getPremisesAddress() {
        return premisesAddress;
    }

    public void setSanctionedLoad(String sanctionedLoad) {
        this.sanctionedLoad = sanctionedLoad;
    }

    public String getSanctionedLoad() {
        return sanctionedLoad;
    }

    public void setDiscomName(String discomName) {
        this.discomName = discomName;
    }

    public String getDiscomName() {
        return discomName;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDivision() {
        return division;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setSecurityAmount(String securityAmount) {
        this.securityAmount = securityAmount;
    }

    public String getSecurityAmount() {
        return securityAmount;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setMyAccountList(List<String> myAccountList) {
        this.myAccountList = myAccountList;
    }

    public List<String> getMyAccountList() {
        return myAccountList;
    }
    
    public void setAddMobileNo(String addMobileNo) {
        this.addMobileNo = addMobileNo;
    }

    public String getAddMobileNo() {
        return addMobileNo;
    }

    public void setAddMobileValidationMsg(String addMobileValidationMsg) {
        this.addMobileValidationMsg = addMobileValidationMsg;
    }

    public String getAddMobileValidationMsg() {
        return addMobileValidationMsg;
    }

    public void setAddMobileValidationFlag(boolean addMobileValidationFlag) {
        this.addMobileValidationFlag = addMobileValidationFlag;
    }

    public boolean isAddMobileValidationFlag() {
        return addMobileValidationFlag;
    }
    
    public void setAddMobileOTP(String addMobileOTP) {
        this.addMobileOTP = addMobileOTP;
    }

    public String getAddMobileOTP() {
        return addMobileOTP;
    }

    public void setAddMobileOTPValidationMsg(String addMobileOTPValidationMsg) {
        this.addMobileOTPValidationMsg = addMobileOTPValidationMsg;
    }

    public String getAddMobileOTPValidationMsg() {
        return addMobileOTPValidationMsg;
    }

    public void setEnteredMobileOTP(String enteredMobileOTP) {
        this.enteredMobileOTP = enteredMobileOTP;
    }

    public String getEnteredMobileOTP() {
        return enteredMobileOTP;
    }

    public void setAddEmail(String addEmail) {
        this.addEmail = addEmail;
    }

    public String getAddEmail() {
        return addEmail;
    }

    public void setAddEmailValidationFlag(boolean addEmailValidationFlag) {
        this.addEmailValidationFlag = addEmailValidationFlag;
    }

    public boolean isAddEmailValidationFlag() {
        return addEmailValidationFlag;
    }
    
    public void setAddEmailValidationMsg(String addEmailValidationMsg) {
        this.addEmailValidationMsg = addEmailValidationMsg;
    }

    public String getAddEmailValidationMsg() {
        return addEmailValidationMsg;
    }

    public void setAddEmailOTP(String addEmailOTP) {
        this.addEmailOTP = addEmailOTP;
    }

    public String getAddEmailOTP() {
        return addEmailOTP;
    }

    public void setAddEmailOTPValidationMsg(String addEmailOTPValidationMsg) {
        this.addEmailOTPValidationMsg = addEmailOTPValidationMsg;
    }

    public String getAddEmailOTPValidationMsg() {
        return addEmailOTPValidationMsg;
    }

    public void setEnteredEmailOTP(String enteredEmailOTP) {
        this.enteredEmailOTP = enteredEmailOTP;
    }

    public String getEnteredEmailOTP() {
        return enteredEmailOTP;
    }
    
    public void getMyAccountPannelData(String accNo){
        DBServices services=new DBServices();
        List<String> linkedAccounts=services.getLinkedAccounts(accNo);
        if(myAccountList!=null && myAccountList.size()>0){
            myAccountList.clear();
        }
        myAccountList=new ArrayList<String>();
        myAccountList.add(accNo);
        myAccountList.addAll(linkedAccounts);        
    }
    
    public void getProfileDetails(String accountNumber){
            String accountValidationMsg=null;
            boolean accountValidationFlag=false;
            GetConsumerDetailsRequest request=new GetConsumerDetailsRequest(); 
            request.setKno(accountNumber);
            RestServices service=new RestServices();
            GetConsumerDetailsResponse response=service.getConsumerDetails(request,discomValue);
            if(response!=null && response.getKno()!=null && accountNumber.equalsIgnoreCase(response.getKno())){
                this.setName(response.getConsumerName());           
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
                this.setBillingAddress(addr);
                this.setSanctionedLoad(response.getSanctionedLoadInKW());
                this.setDiscomName(response.getDiscom());
                this.setDivision(response.getDivision());
                this.setSubDivision(response.getSubDivision());
                this.setSecurityAmount(response.getSecurityDeposit());
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
                    this.setPremisesAddress(addr2);
                this.setMobileNumber(response.getMobileNumber());
                this.setEmail(response.getEmailAddress());
                accountValidationFlag=true;
                accountValidationMsg="Account Number validated successfully.";
                }else if(response!=null && response.getErrorCode()!=null){
                    accountValidationMsg="Account Number validation failed. Please enter a valid account number.";
                }
            }
        
    public void getBillingData(String accountNo) {
            List<BillDetail> list=new ArrayList<BillDetail>();
            RestServices service=new RestServices();
            if(billDetailsList!=null && billDetailsList.size()>0){
                billDetailsList.clear();
            }
            GetBillingDetailsRequest request=new GetBillingDetailsRequest(); 
            request.setKnumber(accountNo); //"0010960869"
            DateRange dr = request.new DateRange();
//            dr.setFromDate("18-07-2021"); //"18-07-2021"
//            dr.setToDate("18-07-2022"); //"18-07-2022"
            Date currentDate = new Date();
            dr.setFromDate(utils.getPrevious12MonthDate(currentDate));
            dr.setToDate(utils.convertDateToStr(currentDate, "dd-MM-yyyy"));
            SearchParameters sp =  request.new SearchParameters();
            sp.setDateRange(dr);
            request.setSearchParameters(sp);
            
            GetBillingDetailsResponse response = service.getBillingDetails(request,discomValue);
            if(response!=null && response.getBillDetails()!=null){
                ArrayList<BillDetails> billDetails = response.getBillDetails();
                for(BillDetails bd:billDetails){
                    BillDetail bill = new BillDetail();
                    BillInfo billInfo = bd.getBillInfo();
                    if(billInfo!=null){
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
            }
            billDetailsList=list;  
            if(billDetailsTable!=null){
               AdfFacesContext.getCurrentInstance().addPartialTarget(billDetailsTable);
            }
        }

    
    
    public String getUserName(){
        Map paramSession=new HashMap();
        paramSession=ADFContext.getCurrent().getSessionScope(); 
        String accountNo=null;
        String username=null;
        if(paramSession.get("loggedInAccountNo")!=null){
            accountNo=paramSession.get("loggedInAccountNo").toString();
            accountNumber=accountNo;
        }
        if(paramSession.get("loggedInUserName")!=null){
            username=paramSession.get("loggedInUserName").toString();
        }   
        return username;
        
    }
    
    public void loadAccount(){
        System.out.println("loadAccount :: starts");
        //set current tab
        paramSession.put("isCurrentTab", "myaccount");
        try{
        Map paramSession=new HashMap();
        paramSession=ADFContext.getCurrent().getSessionScope(); 
        String accountNo=null;
        String username=null;
        if(paramSession.get("loggedInAccountNo")!=null){
            accountNo=paramSession.get("loggedInAccountNo").toString();
            accountNumber=accountNo;
            selectedAccNo=accountNumber;
            lastLogin="31-Aug-2022 10:52PM";
        }
        if(paramSession.get("loggedInUserName")!=null){
            username=paramSession.get("loggedInUserName").toString();
        }
        if(paramSession.get("loggedInUserDiscom")!=null){
            discomValue=paramSession.get("loggedInUserDiscom").toString();
        }
        getMyAccountPannelDataOnLoad(accountNumber);
//        getBillingDataOnLoad(selectedAccNo);
            getBillingData(selectedAccNo);
        getProfileDetailsOnLoad(selectedAccNo);
        getConsumptionDetail(selectedAccNo);
        pageFlowParam.put("tabType", "billView");
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("loadAccount :: ends");
    }


    public void setBillDetailsTable(RichTable billDetailsTable) {
        this.billDetailsTable = billDetailsTable;
    }

    public RichTable getBillDetailsTable() {
        return billDetailsTable;
    }

    public void addAccount(ActionEvent actionEvent) {
        addMobileValidationMsg=null;
        addEmailValidationMsg=null;
        addSuccessFlag=false;
        addAccValidationMsg=null;
        
        
        if(addAccountNo==null || addAccountNo.isEmpty()){
            addAccValidationMsg="Please enter the account number.";
        }
        else if(!addAccValidationFlag){
            addAccValidationMsg="Please validate the account number to proceed.";
        }else if(!addMobileValidationFlag){
            addMobileValidationMsg="Please verify the mobile no to proceed.";
        }
        else if(!addEmailValidationFlag){
            addEmailValidationMsg="Please validate the email to proceed.";
        }
        else{
            DBServices db=new DBServices();
            boolean flag=db.addAccounts(accountNumber, addAccountNo);
            if(flag){
                addSuccessFlag=true;
                if(manageAccountList!=null){
                    GetConsumerDetailsRequest request=new GetConsumerDetailsRequest(); 
                    request.setKno(addAccountNo);
                    RestServices service=new RestServices();
                    GetConsumerDetailsResponse response=service.getConsumerDetails(request,discomValue);
                    if(response!=null && response.getKno()!=null && addAccountNo.equalsIgnoreCase(response.getKno())){
                        Account account=new Account();
                        account.setAccountNo(addAccountNo);
                        account.setName(response.getConsumerName());
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
                        account.setAddress(addr);
                        account.setDiscom(response.getDiscom());
                        account.setDivision(response.getDivision());
                        account.setSubDivision(response.getSubDivision());
                        account.setConnectionStatus(response.getConnectionStatus());
                        account.setEmail(response.getEmailAddress());
                        account.setMobileNo(response.getMobileNumber());
                        account.setPersonID(response.getPersonID());
                        manageAccountList.add(account);
                    }
                }
                    
                
            }
        }
        utils.executeClientJavascript("dataTableInvoke();");
    }

    public void addAccountActionListener(ActionEvent actionEvent) {
        // Add event code here...
        addAccountNo=null;
        addSuccessFlag=false;
        addAccValidationMsg=null;
        addAccValidationFlag=false;
        
        addMobileNo = null;
        addMobileValidationMsg = null;
        addMobileValidationFlag=false;
        addMobileOTP = null;
        addMobileOTPValidationMsg = null;
        enteredMobileOTP = null;
        
        addEmail = null;
        addEmailValidationMsg = null;
        addEmailValidationFlag = false;
        addEmailOTP = null;
        addEmailOTPValidationMsg = null;
        enteredEmailOTP = null;
        
        utils.executeClientJavascript("dataTableInvoke();");
        if(addAccountPopUp!=null){
            RichPopup.PopupHints hints=new RichPopup.PopupHints();
            addAccountPopUp.show(hints);
        }
    }
    
    public void validateAccountNo(ActionEvent actionEvent) {
        addAccValidationMsg=null;
        addAccValidationFlag=false;
        if(addAccountNo==null || addAccountNo.isEmpty()){
            addAccValidationMsg="Please enter the account number.";
        }
        else if(myAccountList.contains(addAccountNo)){
            addAccValidationMsg="Account Number already linked to this account."; 
        }
        else{
            GetConsumerDetailsRequest request=new GetConsumerDetailsRequest(); 
            request.setKno(addAccountNo);
            RestServices service=new RestServices();
            GetConsumerDetailsResponse response=service.getConsumerDetails(request,discomValue);
            if(response!=null && response.getKno()!=null && addAccountNo.equalsIgnoreCase(response.getKno())){
                addAccValidationMsg=null;
                addAccValidationFlag=true;
                
                //account got validated
                //capture the primary account mobile and email id
                Account actDetails = new Account();
                //get consumer details for primary account no
                if(paramSession.get("accountsDetailsFromSession") != null){
                    actDetails =(Account) paramSession.get("accountsDetailsFromSession");
                    System.out.println("Mobile --> "+actDetails.getMobileNo());
                    System.out.println("Mobile --> "+actDetails.getEmail());
                    addMobileNo = actDetails.getMobileNo();
                    addEmail = actDetails.getEmail();
                }
            }else if(response!=null && response.getErrorCode()!=null){
                addAccValidationMsg="Account Number validation failed. Please enter a valid account number.";
            }
        }
    }
    
    public void addAccountNoValueChange(ValueChangeEvent valueChangeEvent) {
        String newValue=null;
        String oldValue=null;
        if(valueChangeEvent.getNewValue()!=null){
             newValue=valueChangeEvent.getNewValue().toString();            
        }
        if(valueChangeEvent.getOldValue()!=null){
             oldValue=valueChangeEvent.getOldValue().toString();            
        }
        if(newValue!=null && oldValue!=null && !newValue.equalsIgnoreCase(oldValue)){
            addAccValidationFlag=false;
            addAccValidationMsg=null;
        }
    }

    public void setAddAccountPopUp(RichPopup addAccountPopUp) {
        this.addAccountPopUp = addAccountPopUp;
    }

    public RichPopup getAddAccountPopUp() {
        return addAccountPopUp;
    }

    public void closeAddPopUp(ActionEvent actionEvent) {
        // Add event code here...
        if(addAccountPopUp!=null){
            addAccountPopUp.hide();
        }
        getMyAccountPannelData(accountNumber);
        refreshPage();
    }

    public String manageAccountsAction() {
        // Add event code here...
        if(manageAccountList!=null){
            manageAccountList.clear();
        }
        manageAccountList=new ArrayList<Account>();
        if(myAccountList!=null){
            for(String accountNo:myAccountList){
                GetConsumerDetailsRequest request=new GetConsumerDetailsRequest(); 
                request.setKno(accountNo);
                RestServices service=new RestServices();
                GetConsumerDetailsResponse response=service.getConsumerDetails(request,discomValue);
                if(response!=null && response.getKno()!=null && accountNo.equalsIgnoreCase(response.getKno())){
                    Account account=new Account();
                    account.setAccountNo(accountNo);
                    account.setName(response.getConsumerName());
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
                    account.setAddress(addr);
                    account.setDiscom(response.getDiscom());
                    account.setDivision(response.getDivision());
                    account.setSubDivision(response.getSubDivision());
                    account.setConnectionStatus(response.getConnectionStatus());
                    account.setEmail(response.getEmailAddress());
                    account.setMobileNo(response.getMobileNumber());
                    account.setPersonID(response.getPersonID());
                    manageAccountList.add(account);
                }
            }
        }
        return "manage";
    }

    public void selectAccountListener(ActionEvent actionEvent) {
        // Add event code here...
        try{
        if(selectedAccNo!=null){
            getBillingData(selectedAccNo); 
            getProfileDetails(selectedAccNo);
            getConsumptionDetail(selectedAccNo);
            pageFlowParam.put("tabType", "billView");
            refreshPage();
        }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void refreshPage(){
        FacesContext fctx = FacesContext.getCurrentInstance();
        String refreshpage = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
        UIV.setViewId(refreshpage);
        fctx.setViewRoot(UIV);
    }

    public void removeAccountActionListener(ActionEvent actionEvent) {
        // Add event code here...
        if(removeAccNo!=null){
            DBServices db=new DBServices();
            boolean flag=db.removeAccount(accountNumber, removeAccNo);
            if(flag){
                if(manageAccountList!=null && manageAccountList.size()>0){
                    for(Account acc:manageAccountList){
                        if(acc!=null && acc.getAccountNo()!=null && removeAccNo.equalsIgnoreCase(acc.getAccountNo())){
                            manageAccountList.remove(acc);
                            break;
                        }
                    }
                }
                getMyAccountPannelData(accountNumber);
                closeRemovePopUp(null);
                refreshPage();
            } 
            
        }
    }

    public void setRemoveAccountPopUp(RichPopup removeAccountPopUp) {
        this.removeAccountPopUp = removeAccountPopUp;
    }

    public RichPopup getRemoveAccountPopUp() {
        return removeAccountPopUp;
    }

    public void closeRemovePopUp(ActionEvent actionEvent) {
        if(removeAccountPopUp!=null){
            removeAccountPopUp.hide();
        }
    }

    public void openRemovePopUp(ActionEvent actionEvent) {
        if(removeAccountPopUp!=null){
            RichPopup.PopupHints hints=new RichPopup.PopupHints();
            removeAccountPopUp.show(hints);
        }
    }
    
    public void getMyAccountPannelDataOnLoad(String accNo){
        List<SelectItem> linkedAccounts = new ArrayList<SelectItem>();
        
        //get linked accounts with primary acc no
        if(paramSession.get("accountsListFromSession") != null){
            linkedAccounts =(List<SelectItem>) paramSession.get("accountsListFromSession");
        }
        System.out.println("account list size--> "+linkedAccounts.size());
        
        
        if(myAccountList!=null && myAccountList.size()>0){
            myAccountList.clear();
        }
        myAccountList=new ArrayList<String>();
        
        if(linkedAccounts.size() > 0){
            for(SelectItem item : linkedAccounts){
                myAccountList.add(item.getValue().toString());
            }
        }
                
    }
    
    public void getProfileDetailsOnLoad(String accountNumber){
        String accountValidationMsg=null;
        boolean accountValidationFlag=false;
            Account response = new Account();
        
            //get consumer details for primary account no
            if(paramSession.get("accountsDetailsFromSession") != null){
                response =(Account) paramSession.get("accountsDetailsFromSession");
            }
        
        if(response!=null && response.getAccountNo()!=null && accountNumber.equalsIgnoreCase(response.getAccountNo())){
            this.setName(response.getName());
            this.setBillingAddress(response.getAddress());
            this.setSanctionedLoad(response.getCurrentLoad());
            this.setDiscomName(response.getDiscom());
            this.setDivision(response.getDivision());
            this.setSubDivision(response.getSubDivision());
            this.setSecurityAmount(String.valueOf(response.getSecurityAmount()));
            this.setPremisesAddress(response.getPremiseAddr());
            this.setMobileNumber(response.getMobileNo());
            this.setEmail(response.getEmail());
            accountValidationFlag=true;
            accountValidationMsg="Account Number validated successfully.";
            }
        }
    
    public void getBillingDataOnLoad(String accountNo) {
        Account actDetails = new Account();
        List<BillDetail> billDetails = new ArrayList<BillDetail>();
        //get consumer details for primary account no
        if(paramSession.get("accountsDetailsFromSession") != null){
            actDetails =(Account) paramSession.get("accountsDetailsFromSession");
            System.out.println("Bill Details size --> "+actDetails.getBillDetails().size());
        }
        
        if(billDetailsList!=null && billDetailsList.size()>0){
            billDetailsList.clear();
        }
        System.out.println("Account Details --> "+actDetails.getAccountNo());
        if(actDetails != null && actDetails.getBillDetails() != null){
            System.out.println("Inside Bill Details");
            System.out.println("billDetailsList size from acct session  --> "+actDetails.getBillDetails().size());
            if(actDetails.getBillDetails().size() > 0){
                billDetailsList=actDetails.getBillDetails(); 
            }else if(paramSession.get("billDetailsFromSession") != null){
                billDetails =(List<BillDetail>) paramSession.get("billDetailsFromSession");
                System.out.println("billDetailsList size from bill session  --> "+billDetails.size());
            }
        }
        System.out.println("billDetailsList size --> "+billDetailsList.size());
         
        if(billDetailsTable!=null){
           AdfFacesContext.getCurrentInstance().addPartialTarget(billDetailsTable);
        }
    }
    
    // Bar Chart data
    public List<UIDataItem> getConsumptionDetailsBarChart(Map<String, List<ConsumpDetailsPOJO>> consumptionMap) {
        List<UIDataItem> dataItems = new ArrayList<UIDataItem>();
        int janUnits = 0;
        int febUnits = 0;
        int marUnits = 0;
        int aprUnits = 0;
        int mayUnits = 0;
        int junUnits = 0;
        int julUnits = 0;
        int augUnits = 0;
        int sepUnits = 0;
        int octUnits = 0;
        int novUnits = 0;
        int decUnits = 0;
        
        Map<String, Integer> barChartMap = new HashMap<String, Integer>();
        
        if(consumptionMap.size() > 0){
            for(Map.Entry<String,List<ConsumpDetailsPOJO>> entry : consumptionMap.entrySet()){
                
                if("January".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO janDetails : entry.getValue()){
                        janUnits = janUnits + Integer.parseInt(janDetails.getMeterReadUnits());
                    }
                }else if("February".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO febDetails : entry.getValue()){
                        febUnits = febUnits + Integer.parseInt(febDetails.getMeterReadUnits());
                    }
                }else if("March".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO marDetails : entry.getValue()){
                        marUnits = marUnits + Integer.parseInt(marDetails.getMeterReadUnits());
                    }
                }else if("April".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO aprDetails : entry.getValue()){
                        aprUnits = aprUnits + Integer.parseInt(aprDetails.getMeterReadUnits());
                    }
                }else if("May".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO mayDetails : entry.getValue()){
                        mayUnits = mayUnits + Integer.parseInt(mayDetails.getMeterReadUnits());
                    }
                }else if("June".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO junDetails : entry.getValue()){
                        junUnits = junUnits + Integer.parseInt(junDetails.getMeterReadUnits());
                    }
                }else if("July".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO julyDetails : entry.getValue()){
                        julUnits = julUnits + Integer.parseInt(julyDetails.getMeterReadUnits());
                    }
                }else if("August".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO augDetails : entry.getValue()){
                        augUnits = augUnits + Integer.parseInt(augDetails.getMeterReadUnits());
                    }
                }else if("September".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO sepDetails : entry.getValue()){
                        sepUnits = sepUnits + Integer.parseInt(sepDetails.getMeterReadUnits());
                    }
                }else if("October".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO octDetails : entry.getValue()){
                        octUnits = octUnits + Integer.parseInt(octDetails.getMeterReadUnits());
                    }
                }else if("November".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO novDetails : entry.getValue()){
                        novUnits = novUnits + Integer.parseInt(novDetails.getMeterReadUnits());
                    }
                }else if("December".equals(entry.getKey())){
                    for(ConsumpDetailsPOJO decDetails : entry.getValue()){
                        decUnits = decUnits + Integer.parseInt(decDetails.getMeterReadUnits());
                    }
                }
            }
            
            System.out.println("Total Jan Units --> "+janUnits);
            System.out.println("Total Feb Units --> "+febUnits);
            System.out.println("Total March Units --> "+marUnits);
            System.out.println("Total Apr Units --> "+aprUnits);
            System.out.println("Total May Units --> "+mayUnits);
            System.out.println("Total June Units --> "+junUnits);
            System.out.println("Total July Units --> "+julUnits);
            System.out.println("Total August Units --> "+augUnits);
            System.out.println("Total Sep Units --> "+sepUnits);
            System.out.println("Total Oct Units --> "+octUnits);
            System.out.println("Total Nov Units --> "+novUnits);
            System.out.println("Total Dec Units --> "+decUnits);
            
            barChartMap.put("01_Jannuary", janUnits);
            barChartMap.put("02_February", febUnits);
            barChartMap.put("03_March", marUnits);
            barChartMap.put("04_April", aprUnits);
            barChartMap.put("05_May", mayUnits);
            barChartMap.put("06_June", junUnits);
            barChartMap.put("07_July", julUnits);
            barChartMap.put("08_August", augUnits);
            barChartMap.put("09_September", sepUnits);
            barChartMap.put("10_October", octUnits);
            barChartMap.put("11_November", novUnits);
            barChartMap.put("12_December", decUnits);
        }
        
        if(barChartMap.size() > 0){
            Map<String, Integer> sortedBarChartMap = new TreeMap<String, Integer>(barChartMap);
            for(Map.Entry<String,Integer> entry : sortedBarChartMap.entrySet()){
                UIDataItem dataItem = new UIDataItem();
                System.out.println("Month --> "+entry.getKey());
                dataItem.setValue(entry.getValue());
                dataItem.setGroup(entry.getKey().substring(entry.getKey().indexOf("_")+1, entry.getKey().length()));
                dataItem.setSeries(entry.getKey().substring(entry.getKey().indexOf("_")+1, entry.getKey().length()));
                
                dataItems.add(dataItem);
            }
        }
        
        
        return dataItems;
    }

   
    public void billdetailsActionEvent(ActionEvent actionEvent) {
        utils.executeClientJavascript("dataTableInvoke();");
    }
    
    public String getConsumptionDetail(String actNumber){
        String retValue = null;
        String discomVal = null;
        List<UIDataItem> dataItems = new ArrayList<UIDataItem>();
        
        Map<String, List<ConsumpDetailsPOJO>> monthWiseMap = new HashMap<String, List<ConsumpDetailsPOJO>>();
        
        if (paramSession.get("loggedInUserDiscom") != null) {
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        //prepare request
        GetConsumptionDetailsRequest request = new GetConsumptionDetailsRequest();
        request.setKnumber(actNumber);
        
        DateRangePojo dr = new DateRangePojo();
        Date currentDate = new Date();
        dr.setFromDate(utils.getPrevious12MonthDate(currentDate));
        dr.setToDate(utils.convertDateToStr(currentDate, "dd-MM-yyyy"));
        SearchParamPojo sp =  new SearchParamPojo();
        sp.setDateRange(dr);
        
        request.setSearchParameters(sp);
        
        RestServices service=new RestServices();
        GetConsumptionDetailsResponse response=service.getConsumptionDetails(request,discomValue);
        if(response!=null){
            if(response.getConsumptionDetails() != null && response.getConsumptionDetails().size() > 0){
               monthWiseMap =  mapToConsumpDetails(response);
                dataItems = getConsumptionDetailsBarChart(monthWiseMap);
                
            }
        }else{
            retValue = "toError";
        }
        pageFlowParam.put("consumpBarChartDetails", ModelUtils.toCollectionModel(dataItems));
        //
        return retValue;
    }
    
    public Map<String, List<ConsumpDetailsPOJO>> mapToConsumpDetails(GetConsumptionDetailsResponse response){
        List<ConsumpDetailsPOJO> consumptionList = new ArrayList<ConsumpDetailsPOJO>();
        
        List<ConsumpDetailsPOJO> janConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        List<ConsumpDetailsPOJO> febConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        List<ConsumpDetailsPOJO> marConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        List<ConsumpDetailsPOJO> aprConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        List<ConsumpDetailsPOJO> mayConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        List<ConsumpDetailsPOJO> junConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        List<ConsumpDetailsPOJO> julyConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        List<ConsumpDetailsPOJO> augConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        List<ConsumpDetailsPOJO> sepConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        List<ConsumpDetailsPOJO> octConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        List<ConsumpDetailsPOJO> novConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        List<ConsumpDetailsPOJO> decConsumptionList = new ArrayList<ConsumpDetailsPOJO>();
        
        Map<String, List<ConsumpDetailsPOJO>> monthWiseMap = new HashMap<String, List<ConsumpDetailsPOJO>>();
        List<String> monthList = new ArrayList<String>();

        ArrayList<ConsumptionDetails> resConsumpList = response.getConsumptionDetails();
        for(ConsumptionDetails consump : resConsumpList){
            ConsumpDetailsPOJO consumpPojo = new ConsumpDetailsPOJO();
            consumpPojo.setBillNum(consump.getBillInfo().getBillNo());
            consumpPojo.setBillDueDate(consump.getBillInfo().getBillDueDate());
            
            consumpPojo.setFromDate(consump.getBillDateInfo().getFromDate());
            consumpPojo.setToDate(consump.getBillDateInfo().getToDate());
            consumpPojo.setBillNum(consump.getBillInfo().getBillNo());
            consumpPojo.setBillMonth(consump.getBillDateInfo().getBillMonth());
            
            consumpPojo.setMeterReadsSartReading(consump.getMeterReading().getStartReading());
            consumpPojo.setMeterReadEndReading(consump.getMeterReading().getEndReading());
            consumpPojo.setMeterReadMeasurementUnit(consump.getMeterReading().getMeasurementUnit());
            consumpPojo.setMeterReadUnits(consump.getMeterReading().getUnits());
            consumpPojo.setMeterReadTimeofUse(consump.getMeterReading().getTimeofUse());
            
            consumpPojo.setUnitsBilled(consump.getUnitBilled().getUnitsBilled());
            consumpPojo.setUnitBillMeasurementUnit(consump.getUnitBilled().getMeasurementUnit());
            consumpPojo.setUnitBillTimeofUse(consump.getUnitBilled().getTimeofUse());
            
            consumpPojo.setSpId(consump.getSpId());
            consumpPojo.setBillAmount(consump.getBillAmount());
            consumpPojo.setMeterReadingDate(consump.getMeterReadingDate());
            
            consumptionList.add(consumpPojo);
            
            if("Jan".equals(consumpPojo.getBillMonth())){
                janConsumptionList.add(consumpPojo);   
            }else if("Feb".equals(consumpPojo.getBillMonth())){
                febConsumptionList.add(consumpPojo);   
            }else if("Mar".equals(consumpPojo.getBillMonth())){
                marConsumptionList.add(consumpPojo);   
            }else if("Apr".equals(consumpPojo.getBillMonth())){
                aprConsumptionList.add(consumpPojo);   
            }else if("May".equals(consumpPojo.getBillMonth())){
                mayConsumptionList.add(consumpPojo);   
            }else if("Jun".equals(consumpPojo.getBillMonth())){
                junConsumptionList.add(consumpPojo);   
            }else if("Jul".equals(consumpPojo.getBillMonth())){
                julyConsumptionList.add(consumpPojo);   
            }else if("Aug".equals(consumpPojo.getBillMonth())){
                augConsumptionList.add(consumpPojo);   
            }else if("Sep".equals(consumpPojo.getBillMonth())){
                sepConsumptionList.add(consumpPojo);   
            }else if("Oct".equals(consumpPojo.getBillMonth())){
                octConsumptionList.add(consumpPojo);   
            }else if("Nov".equals(consumpPojo.getBillMonth())){
                novConsumptionList.add(consumpPojo);   
            }else if("Dec".equals(consumpPojo.getBillMonth())){
                decConsumptionList.add(consumpPojo);   
            }
        }
        
        
        monthWiseMap.put("January", janConsumptionList);
        monthWiseMap.put("February", febConsumptionList);
        monthWiseMap.put("March", marConsumptionList);
        monthWiseMap.put("April", aprConsumptionList);
        monthWiseMap.put("May", mayConsumptionList);
        monthWiseMap.put("June", junConsumptionList);
        monthWiseMap.put("July", julyConsumptionList);
        monthWiseMap.put("August", augConsumptionList);
        monthWiseMap.put("September", sepConsumptionList);
        monthWiseMap.put("October", octConsumptionList);
        monthWiseMap.put("November", novConsumptionList);
        monthWiseMap.put("December", decConsumptionList);
        
        
        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");
        
        pageFlowParam.put("consumpMonthWiseMap", monthWiseMap);
        pageFlowParam.put("monthList", monthList);

        System.out.println("Final Map size ---> "+monthWiseMap.size());
        return monthWiseMap;
    }

    
    public void validateMobileNo(ActionEvent actionEvent) {
        addMobileValidationMsg=null;
        addMobileOTPValidationMsg=null;
        addMobileOTP=null;
        if(addMobileNo==null || addMobileNo.isEmpty()){
            addMobileValidationMsg="Please enter the mobile number."; 
        }else if(addMobileNo != null && !utils.isValidMobileNo(addMobileNo)){
            addMobileValidationMsg="Please enter valid mobile number."; 
        }
        else if(addMobileNo!=null){
            GetOTPRequest request=new GetOTPRequest(); 
            request.setMobileNo(addMobileNo);
            request.setMode("MOBILE");
            request.setSource("WSS");
            RestServices service=new RestServices();
            GetOTPResponse response=service.getOTP(request,discomValue);           
            if(response!=null && "0".equalsIgnoreCase(response.getResCode())){
                addMobileOTP=response.getOtp();
                addMobileValidationMsg=null;
            }
            else if(response!=null && "1".equalsIgnoreCase(response.getResCode())){
                addMobileOTP=null;
                addMobileValidationMsg="Invalid mobile number. OTP not sent successfully.";
            }
            else{
                addMobileOTP=null;
                addMobileValidationMsg="Some error occured in sending OTP. Please try again later.";
            }
        }
    }
    
    public void verifyMobileOTP(ActionEvent actionEvent) {
        addMobileOTPValidationMsg=null;
        addMobileValidationMsg=null;
        if(enteredMobileOTP==null){
            addMobileOTPValidationMsg="Please enter the OTP to verify mobile number.";
        }
        else{
            if(enteredMobileOTP!=null && addMobileOTP!=null && enteredMobileOTP.equalsIgnoreCase(addMobileOTP)){
                addMobileValidationFlag=true;
            }
            else{
                addMobileOTPValidationMsg="OTP entered is incorrect";
            }
        }
    }

    
    
    public void validateEmail(ActionEvent actionEvent) {
        addEmailValidationMsg=null;
        addEmailOTPValidationMsg=null;
        addEmailOTP=null;
        if(addEmail==null|| addEmail.isEmpty()){
            addEmailValidationMsg="Please enter the email id.";
        }else if(addEmail != null && !utils.validateEmail(addEmail)){
            addEmailValidationMsg="Please enter valid email id.";
        }
        else if(addEmail!=null){
            GetOTPRequest request=new GetOTPRequest(); 
            request.setEmail(addEmail);
            request.setMode("EMAIL");
            request.setSource("WSS");
            RestServices service=new RestServices();
            GetOTPResponse response=service.getOTP(request,discomValue);
                if(response!=null && "0".equalsIgnoreCase(response.getResCode())){
                    addEmailOTP=response.getOtp();
                    addEmailValidationMsg=null;
                }
                else if(response!=null && "1".equalsIgnoreCase(response.getResCode())){
                    addEmailOTP=null;
                    addEmailValidationMsg="Invalid email address. OTP not sent successfully.";
                }
                else{
                    addEmailOTP=null;
                    addEmailValidationMsg="Some error occured in sending OTP. Please try again later.";
                }
        }
    }
    
    public void verifyEmailOTP(ActionEvent actionEvent) {
        addEmailValidationMsg=null;
        addEmailOTPValidationMsg=null;
        if(enteredEmailOTP==null){
            addEmailOTPValidationMsg="Please enter the OTP to verify Email";
        }
        else{
            if(enteredEmailOTP!=null && addEmailOTP!=null && enteredEmailOTP.equalsIgnoreCase(addEmailOTP)){
                addEmailValidationFlag=true;
            }
            else{
                addEmailOTPValidationMsg="OTP entered is incorrect";
            }
        }
    }


    public String downloadBill(OutputStream outputStream) {
        String billId = null;
        String discomVal = null;
        String downloadErrMsg = null;
        pageFlowParam.put("downloadBussValidErrMsg", null);
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
                    pageFlowParam.put("downloadBussValidErrMsg", downloadErrMsg);
                } else {
                    downloadErrMsg = response.getResMsg();
                    pageFlowParam.put("downloadBussValidErrMsg", downloadErrMsg);
                }
            } else {
                downloadErrMsg = "Something went wrong.Please try again after some time";
                pageFlowParam.put("downloadBussValidErrMsg", downloadErrMsg);
            }

        } catch (IOException ioe) {
            System.out.println("IO Exception occurred. Unable to retrieve file. Message: " + ioe.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception message: " + ex.getMessage());
        }
        return null;
    }

    public void downloadBill(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        downloadBill(outputStream);

    }

    public void setEditPhotoPopupBind(RichPopup editPhotoPopupBind) {
        this.editPhotoPopupBind = editPhotoPopupBind;
    }

    public RichPopup getEditPhotoPopupBind() {
        return editPhotoPopupBind;
    }
   
    public void onUploadFileValueChange(ValueChangeEvent valueChangeEvent) {
        System.out.println("onUploadFileValueChange :: starts");
        pageFlowParam.put("attachmentErrMsg", null);
        
        try {
            if (valueChangeEvent.getNewValue() != null) {
                UploadedFile fileuploaded = (UploadedFile) valueChangeEvent.getNewValue();
                System.out.println("File Name --> " + fileuploaded.getFilename());

                String fileName = fileuploaded.getFilename();
                InputStream fileStream = fileuploaded.getInputStream();
                long fileLength = fileuploaded.getLength();
                String contentType = fileuploaded.getContentType();
                String discomVal = null;
                
                if(paramSession.get("loggedInUserDiscom")!= null){
                    discomVal = (String) paramSession.get("loggedInUserDiscom");
                }
                
                System.out.println("File Name "+fileName);
                String dataUrl = utils.encodeDataUrl(contentType, (int)fileLength, fileStream); // utils comes from the below code 
                System.out.println("data Url base 64 --> "+dataUrl);
                pageFlowParam.put("profilePicContent", dataUrl);
                pageFlowParam.put("profilePicName", discomVal+"_"+fileName);
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("onUploadFileValueChange :: Ends");
    }

    public void setUploadFileBind(RichInputFile uploadFileBind) {
        this.uploadFileBind = uploadFileBind;
    }

    public RichInputFile getUploadFileBind() {
        return uploadFileBind;
    }

    public void closeProfilePicPopup(ActionEvent actionEvent) {
        String discomVal = null;
        DBServices services=new DBServices();
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        if(pageFlowParam.get("profilePicName") != null && pageFlowParam.get("profilePicContent") != null){
            services.updateProfilePhoto((String)paramSession.get("loggedInAccountNo"), (String)pageFlowParam.get("profilePicName"), (String)pageFlowParam.get("profilePicContent"), discomVal);
            paramSession.put("profilePicContent", (String)pageFlowParam.get("profilePicContent"));
            paramSession.put("profilePicName", (String)pageFlowParam.get("profilePicName"));
        }
        uploadFileBind.resetValue();
        pageFlowParam.put("profilePicContent", null);
        pageFlowParam.put("profilePicName", null);
        editPhotoPopupBind.hide();
    }
    
    public String findLabel(String key) {
        String value = utils.getLabelValueForKey(key);
        System.out.println("Key --> " + key + " " + "Value --> " + value);
        return value;
    }

    public void popupFetchListn(PopupFetchEvent popupFetchEvent) {
        uploadFileBind.resetValue();
        pageFlowParam.put("profilePicContent", null);
        pageFlowParam.put("profilePicName", null);
    }

    public String toBillPayment() {
        //get charges
        System.out.println(pageFlowParam.get("chargesToPay"));
        String chargesToPay = (String) pageFlowParam.get("chargesToPay");
        
        pageFlowParam.put("payAmount", chargesToPay);
        System.out.println(pageFlowParam.get("payAmount"));
        return "toPayment";
    }
    
    public String emailBill() {
        String billId = null;
        String discomVal = null;
        String emailBillErrMsg = null;
        pageFlowParam.put("emailBillBussValidErrMsg", null);
        
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
                        
                        sendEmailConfirmation(utils.getLabelValueForKey("BILL_EMAIL_SUBJECT"), response.getReportContent(), response.getReportContentType());

                        if (emailBillPopupBind != null) {
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            emailBillPopupBind.show(hints);
                        }


                    }
                } else if ("0".equals(response.getResCode())) {
                    emailBillErrMsg = response.getResMsg();
                    pageFlowParam.put("emailBillBussValidErrMsg", emailBillErrMsg);
                } else {
                    emailBillErrMsg = response.getResMsg();
                    pageFlowParam.put("emailBillBussValidErrMsg", emailBillErrMsg);
                }
            } else {
                emailBillErrMsg = "Something went wrong.Please try again after some time";
                pageFlowParam.put("emailBillBussValidErrMsg", emailBillErrMsg);
            }

        } catch (Exception ex) {
            System.out.println("Exception message: " + ex.getMessage());
        }
        return null;
    }
    
    public String sendEmailConfirmation(String mailSubject, String content, String contentType) throws SQLException, IOException { 
        String discomVal = null;
        final String subject = mailSubject;
        String mailContent = this.getMailBody(content, contentType); 
        RestServices rs = new RestServices();
        SendEmailRequest emailRequest = new SendEmailRequest();
        emailRequest.setFrom("self.service@uppclonline.com");
        
        //to uncomment
        emailRequest.setTo(email);
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
    
    private String getMailBody(String content, String contentType) {
        String body = "<html>\n" + 
        "   <head></head>\n" + 
        "   <body>\n" + 
        "      <div>\n" + 
        "	  <iframe height=\"100%\" width=\"100%\" src=\"data:" + contentType + ";base64," + content +"/>\n" + 
        "</div>\n" + 
        "   </body>\n" + 
        "</html>";

        body = body.replaceAll("null", " ");
        return body;
    }

    public void setEmailBillPopupBind(RichPopup emailBillPopupBind) {
        this.emailBillPopupBind = emailBillPopupBind;
    }

    public RichPopup getEmailBillPopupBind() {
        return emailBillPopupBind;
    }

    public void closeEmailBillPoup(ActionEvent actionEvent) {
        getEmailBillPopupBind().hide();
    }
}
