package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.Account;
import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.request.SendEmailRequest;
import com.xx.uppcl.rest.request.WhatsAppSubsRequest;
import com.xx.uppcl.rest.response.GetConsumerDetailsResponse;
import com.xx.uppcl.rest.response.SendEmailResponse;
import com.xx.uppcl.rest.response.WhatsAppSubsResponse;
import com.xx.uppcl.rest.services.RestServices;

import com.xx.uppcl.services.DBServices;
import com.xx.uppcl.utils.Utils;

import java.io.IOException;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.context.AdfFacesContext;

public class WhatsAppSubscriptionBean {
    
    Map paramSession= ADFContext.getCurrent().getSessionScope();
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();
    Utils utils = new Utils();
    DBServices dbService = new DBServices();
    private RichPopup messagePopupBind;


    public WhatsAppSubscriptionBean() {
    }
    
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
    
    public String submitWhatsAppSubs(){
        System.out.println("submitWhatsAppSubs  :: Starts");
        Account accDetails = new Account();
        String discomVal = null;
        String retValue = null;
        String whatsappValidErrMsg = null;
        String successMsg = null;
        
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("whatsappValidErrMsg", null);
        
        //get account details
        if(pageFlowParam.get("accountsDetails") != null){
            accDetails = (Account) pageFlowParam.get("accountsDetails");
        }
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        //validation check
        if(!validateWhatsappFields(accDetails.getAccountNo())){
            return null;
        }
        
        //set request
        WhatsAppSubsRequest request=new WhatsAppSubsRequest();
        request.setSrc(utils.getLabelValueForKey("UP_SRC_VALUE"));
        request.setAcctId(accDetails.getAccountNo());
        request.setMobNo(accDetails.getMobileNo());
        request.setDiscom(discomVal);
        
        RestServices service=new RestServices();
        
        WhatsAppSubsResponse response = service.subscribeWhatsApp(request,discomVal);
        
        if(response != null){
            if ("0".equals(response.getResCode())) {
                //todo insert records into table
                dbService.updateSubscriptionStatus(accDetails.getAccountNo(), "", "", "Y", "", discomVal);
                successMsg = utils.getLabelValueForKey("WS_BUS_VALID_SUCCESS_MSG");
                pageFlowParam.put("successMsg", successMsg);
                try {
                    sendEmailConfirmation(utils.getLabelValueForKey("WHATSAPP_EMAIL_SUBJECT"), accDetails,
                                          utils.getLabelValueForKey("WHATSAPP_EMAIL_MSG_1"));
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }

                retValue = "toMessage";
            }else if("1".equals(response.getResCode())){
                whatsappValidErrMsg =  utils.getLabelValueForKey("WS_BUS_VALID_ERR_MSG");
                pageFlowParam.put("whatsappValidErrMsg",whatsappValidErrMsg);
            }else{
                retValue = "toError";
            }
        } else {
            retValue = "toError";
        }
        
        if(whatsappValidErrMsg != null){
             RichPopup.PopupHints hints=new RichPopup.PopupHints();
             messagePopupBind.show(hints);
        }
        
        System.out.println("submitWhatsAppSubs  :: Ends");
        return retValue;
    }

    public String cancelRequest() {
        pageFlowParam.put("successMsg", null);
        pageFlowParam.put("whatsappValidErrMsg", null);
        pageFlowParam.put("actNumErrMsg", null);
        
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

    public String iniitSubscription() {
        String retValue = "toWhataAppSubs";
        String status = null;
        String discomVal = null;
        pageFlowParam.put("isSubscribe", false);
        
        //set current tab
        paramSession.put("isCurrentTab", "accounts");
        
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
        
        if(paramSession.get("loggedInUserDiscom")!= null){
            discomVal = (String) paramSession.get("loggedInUserDiscom");
        }
        
        //get subscription status
        if(accountDetails.getAccountNo() != null){
            status = dbService.getSubscriptionStatus(accountDetails.getAccountNo(), discomVal);
            System.out.println("Status ---> "+status);
        }
        
        if(status != null && "Y".equals(status)){
            pageFlowParam.put("isSubscribe", true);
        }
        
        return retValue;
    }
    public boolean validateWhatsappFields(String actNumber){
        
        boolean validFlag = true;
        String actNumErrMsg = null;
        if(actNumber == null || (actNumber != null && actNumber.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("ACT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
        
        return validFlag;
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
