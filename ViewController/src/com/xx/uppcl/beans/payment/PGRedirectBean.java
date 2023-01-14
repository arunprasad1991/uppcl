package com.xx.uppcl.beans.payment;

import com.xx.uppcl.pojo.Account;
import com.xx.uppcl.pojo.PaymentRequestPojo;
import com.xx.uppcl.pojo.ServiceRequestPojo;
import com.xx.uppcl.rest.request.SendEmailRequest;
import com.xx.uppcl.rest.response.SendEmailResponse;
import com.xx.uppcl.rest.services.RestServices;

import java.io.IOException;

import java.sql.SQLException;

import java.util.Map;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;

public class PGRedirectBean {
    public PGRedirectBean() {
    }
    
    Map paramSession= ADFContext.getCurrent().getSessionScope();
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();

    public String getParamFromPaymentGateway() {
        String successMsg = null;
        String retValue = null;
        retValue = "toConfirmation";
        
               
        // get all the entered values
        Account actDetails = new Account();
        PaymentRequestPojo payDetails = new PaymentRequestPojo();
        
        if(pageFlowParam.get("accountsDetails") != null){
            actDetails = (Account) pageFlowParam.get("accountsDetails");
        }
        
        if(pageFlowParam.get("payDetails") != null){
            payDetails = (PaymentRequestPojo) pageFlowParam.get("payDetails");
        }
        // read param from payment gateway
        
        if(true){
            //success case
            successMsg = "Thank You, Your request has been submitted successfully along with the processing fees. Your request will be processed soon.";
            
            //send email on success
            try {
                sendEmailConfirmation("Payment Succesfully Received",actDetails, payDetails, "Payment Confirmation");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //error case
            
        }
        pageFlowParam.put("headerName", "Payment");
        pageFlowParam.put("successMsg", "successMsg");
        return retValue;
    }
    
    public String sendEmailConfirmation(String mailSubject,Account actDetails, PaymentRequestPojo payDetails, String serviceReqType) throws SQLException, IOException { 
        String discomVal = null;
        final String subject = mailSubject;
        String mailContent = this.getMailBody(actDetails, payDetails, serviceReqType); 
        RestServices rs = new RestServices();
        SendEmailRequest emailRequest = new SendEmailRequest();
        emailRequest.setFrom("self.service@uppclonline.com");
        //to comment hardcoded one
        emailRequest.setTo("palashdabkara@gmail.com");
        
        //to uncomment
    //        emailRequest.setTo(actDetails.getEmail());
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
    
    private String getMailBody(Account actDetails, PaymentRequestPojo payDetails, String serviceReqType) {
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
        "                <span style='font-size:10.0pt;line-height:115%;'> "+serviceReqType+" <o:p></o:p>\n" + 
        "                </span>\n" + 
        "              </p>\n" + 
        "              <p style='text-align:justify'>\n" + 
        "                <span style='font-size:11.0pt; line-height:115%;'>Thank you for successfully making your "+serviceReqType+" Online. Your Request Number is <b style='mso-bidi-font-weight:normal'> "+pageFlowParam.get("caseId")+" </b><b style='mso-bidi-font-weight:normal'></b>\n" + 
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
        "                <span style='font-size:11pt;line-height:115%;'>We value your relationshipwith us and assure you of our best services always.</span>\n" + 
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
    
}
