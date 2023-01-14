package com.xx.uppcl.beans;

import com.xx.uppcl.pojo.ServiceRequestPojo;
import com.xx.uppcl.rest.request.SendEmailRequest;
import com.xx.uppcl.rest.response.SendEmailResponse;
import com.xx.uppcl.rest.services.RestServices;
import com.xx.uppcl.services.DBServices;

import com.xx.uppcl.utils.Utils;

import java.io.IOException;

import java.io.Serializable;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.context.AdfFacesContext;


public class Feedback implements Serializable{
        private static final long serialVersionUID=134633L;
    private String name;
    private String account;
    private String connection;
    private String email;
    private String telephone;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String comments;
    private String discom;
    private static String fromEmail = "self.service@uppclonline.com";
//    private String orgEmail = "dharm24kamheda@gmail.com"; 
    private static String orgEmail = "palashdabkara@gmail.com";
    public ArrayList<SelectItem> discomList = new ArrayList<SelectItem>();
    private RichPopup messagePopupBind;
    
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();
    Utils utils = new Utils();

    public Feedback() {
        super();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getConnection() {
        return connection;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setDiscom(String discom) {
        this.discom = discom;
    }

    public String getDiscom() {
        return discom;
    }

    public void setDiscomList(ArrayList<SelectItem> discomList) {
        this.discomList = discomList;
    }

    public ArrayList<SelectItem> getDiscomList() {
        DBServices ds = new DBServices();
        if(discomList.size()==0){
            List<String> list = new ArrayList<String>();
            list = ds.getDiscomList();
            for(String value : list){
                discomList.add(new SelectItem(value));
            }
        }  
        return discomList;
    }

    public String onClickSubmit() {
        try {
            pageFlowParam.put("nameErrMsg", null);
            pageFlowParam.put("actNumErrMsg", null);
            pageFlowParam.put("servConnErrMsg", null);
            pageFlowParam.put("discomErrMsg", null);
            pageFlowParam.put("emailErrMsg", null);
            pageFlowParam.put("phoneErrMsg", null);
            pageFlowParam.put("addrErrMsg", null);
            pageFlowParam.put("cityErrMsg", null);
            pageFlowParam.put("stateErrMsg", null);
            pageFlowParam.put("pinErrMsg", null);
            pageFlowParam.put("commentsErrMsg", null);
            
            //validation check
            if(!validateFeedbackFields()){
                return null;
            }
            
            //ADD Feedback in table
            DBServices dbServices = new DBServices();
            dbServices.captureFeedback(account, name, connection, discom, email, telephone, address, city, state, zip, comments);
            
            this.sendFeedbackToOrg();
            this.sendFeedbackConfirmation();
            
            RichPopup.PopupHints hints=new RichPopup.PopupHints();
            messagePopupBind.show(hints);
            
            pageFlowParam.put("successMsg", utils.getLabelValueForKey("FB_SUCCESS_MSG"));
//            this.showInfoMessage("Your Feedback has been submitted! Thanks for sharing your feeedback !");
            this.reset();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            RichPopup.PopupHints hints=new RichPopup.PopupHints();
            messagePopupBind.show(hints);
            
            pageFlowParam.put("successMsg", utils.getLabelValueForKey("FB_ERROR_MSG"));
        }
        return null;
    }
    
    public boolean validateFeedbackFields(){
        String nameErrMsg = null;
        String actNumErrMsg = null;
        String servConnErrMsg = null;
        String discomErrMsg = null;
        String emailErrMsg = null;
        String phoneErrMsg = null;
        String addrErrMsg = null;
        String cityErrMsg = null;
        String stateErrMsg = null;
        String pinErrMsg = null;
        String commentsErrMsg = null;
        
        
        boolean validFlag = true;
        
        if(name == null || (name != null && name.isEmpty())){
            validFlag = false;
            nameErrMsg = utils.getLabelValueForKey("FB_NAME_ORG_ERR_MSG");
            pageFlowParam.put("nameErrMsg", nameErrMsg);
        }
        if(account == null || (account != null && account.isEmpty())){
            validFlag = false;
            actNumErrMsg = utils.getLabelValueForKey("FB_ACCOUNT_NO_ERR_MSG");
            pageFlowParam.put("actNumErrMsg", actNumErrMsg);
        }
        if(connection == null || (connection != null && connection.isEmpty())){
            validFlag = false;
            servConnErrMsg = utils.getLabelValueForKey("FB_SER_CONN_NO_ERR_MSG");
            pageFlowParam.put("servConnErrMsg", servConnErrMsg);
        }
        if(discom == null || (discom != null && (discom.isEmpty()))){
            validFlag = false;
            discomErrMsg = utils.getLabelValueForKey("FB_DISCOM_ERR_MSG");
            pageFlowParam.put("discomErrMsg", discomErrMsg);
        }
        if(email == null || (email != null && email.isEmpty())){
            validFlag = false;
            emailErrMsg = utils.getLabelValueForKey("FB_EMAIL_ERR_MSG");
            pageFlowParam.put("emailErrMsg", emailErrMsg);
        }else if(!utils.validateEmail(email)){
            validFlag = false;
            emailErrMsg = utils.getLabelValueForKey("FB_VALID_EMAIL_ERR_MSG");
            pageFlowParam.put("emailErrMsg", emailErrMsg);
        }
        if(telephone == null || (telephone != null && (telephone.isEmpty()))){
            validFlag = false;
            phoneErrMsg = utils.getLabelValueForKey("FB_PHONE_ERR_MSG");
            pageFlowParam.put("phoneErrMsg", phoneErrMsg);
        }else if(!utils.isValidMobileNo(telephone)){
            validFlag = false;
            phoneErrMsg = utils.getLabelValueForKey("FB_VALID_PHN_ERR_MSG");
            pageFlowParam.put("phoneErrMsg", phoneErrMsg);
        }
        if(address == null || (address != null && address.isEmpty())){
            validFlag = false;
            addrErrMsg = utils.getLabelValueForKey("FB_ADDR_ERR_MSG");
            pageFlowParam.put("addrErrMsg", addrErrMsg);
        }
        if(city == null || (city != null && city.isEmpty())){
            validFlag = false;
            cityErrMsg = utils.getLabelValueForKey("FB_CITY_ERR_MSG");
            pageFlowParam.put("cityErrMsg", cityErrMsg);
        }
        if(state == null || (state != null && state.isEmpty())){
            validFlag = false;
            stateErrMsg = utils.getLabelValueForKey("FB_STATE_ERR_MSG");
            pageFlowParam.put("stateErrMsg", stateErrMsg);
        }
        if(zip == null || (zip != null && zip.isEmpty())){
            validFlag = false;
            pinErrMsg = utils.getLabelValueForKey("FB_ZIP_ERR_MSG");
            pageFlowParam.put("pinErrMsg", pinErrMsg);
        }
        if(comments == null || (comments != null && comments.isEmpty())){
            validFlag = false;
            commentsErrMsg = utils.getLabelValueForKey("FB_COMMENTS_ERR_MSG");
            pageFlowParam.put("commentsErrMsg", commentsErrMsg);
        }else if(comments != null && comments.length() < 80){
            validFlag = false;
            commentsErrMsg = utils.getLabelValueForKey("MIN_80_CHAR_ERR_MSG_");
            pageFlowParam.put("commentsErrMsg", commentsErrMsg);
        }
        
        return validFlag;
    }
    

    public String sendFeedbackToOrg() throws SQLException, IOException { 
        
        final String subject = "Recieved Feedback";
        String mailContent = this.getMailBody(); 
        RestServices rs = new RestServices();
        SendEmailRequest emailRequest = new SendEmailRequest();
        emailRequest.setFrom(fromEmail);
        System.out.println("Org email id --> "+orgEmail);
        emailRequest.setTo(orgEmail);
        emailRequest.setSubject(subject);
        emailRequest.setContentType("text/html");
        emailRequest.setContent(mailContent);
        SendEmailResponse emailResponse = rs.sendEmail(emailRequest,discom); 
        String result = emailResponse.getResult();
        return result;
    }
    
    public String sendFeedbackConfirmation() throws SQLException, IOException { 
        
        final String subject = "Feedback has been Submitted";
        String mailContent = this.getMailBody();
        RestServices rs = new RestServices();
        SendEmailRequest emailRequest = new SendEmailRequest();
        emailRequest.setFrom(fromEmail);
        System.out.println("email id --> "+email);
        emailRequest.setTo(email);
        emailRequest.setSubject(subject);
        emailRequest.setContentType("text/html");
        emailRequest.setContent(mailContent);
        SendEmailResponse emailResponse = rs.sendEmail(emailRequest,discom); 
        String result = emailResponse.getResult();
        return result; 
    }

    private String getMailBody() {
        String body = "";
        String hdrImage = this.getHeaderImage();
        String body_1 =
            "<html>\n" + "<head>\n" + "<link href='https://fonts.googleapis.com/css?family=Cairo' rel='stylesheet'>\n" +
            "<style>\n" + "   span{\n" + "           color:#600080;\n" +
            "           font-family: 'Cairo' !important;\n" + "           \n" + "   }\n" + "   table {\n" +
            "     border-collapse: collapse;\n" + "   }\n" + "   th{\n" + "           border: 1px solid #EEE;\n" +
            "           color:#600080;\n" + "           font-family: 'Cairo' !important;\n" + "   }\n" + "   td{\n" +
            "           border: 1px solid #EEE;\n" + "           font-family: 'Cairo' !important;\n" +
            "           color:#1E1E1E;\n" + "   }\n" + "   body{\n" + "           font-family: 'Cairo' !important;\n" +
            "           color:#600080;\n" + "   }\n" + "   b{\n" + "           color:#600080;\n" + "   }\n" +
            "   .tableHeader{\n" + "           text-align:center;\n" + "           background-color:#ff8e01;\n" +
            "           border: 1px solid #EEE;\n" + "   }\n" + "   .mailContent{\n" +
            "           margin-top:-80px;\n" + "   }\n" + "</style>\n" + "</head>\n" + "<body>\n" + hdrImage +
            "<div style=\"width: 100%;display:inline-block;\">\n";
        String body_2 = "</div>\n" + "<div class=\"mailContent\">\n";
     
        String body_3 = 
            " <table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" align=\"center\" width=\"100%\">\n" +
            "<tr class=\"tableHeader\"><td colspan=\"2\" align=\"center\"> <h2 style=\"color:white;\">Feedback - Details</h2></td></tr>" +
            "    <tr><th>&nbsp;Name/Organisation </th> <td width=\"50%\">" + this.name + "</td></tr>\n" +
            "    <tr><th>&nbsp;Account No</th>\n" + "      <td width=\"50%\">" + this.account + "</td>\n" +
            "    </tr><tr>\n" + "      <th>\n" + "        &nbsp;Service Connection No\n" + "      </th>\n" +
            "      <td width=\"50%\">" + this.connection + "</td>\n" + "    </tr><tr>\n" + "      <th>\n" +
            "        &nbsp;Discom\n" + "      </th>\n" + "      <td width=\"50%\">" + this.discom +
            "</td>\n" + "    </tr><tr>\n" + "      <th>\n" + "        &nbsp;Email Address\n" + "      </th>\n" +
            "      <td width=\"50%\">" + this.email + "</td>\n" + "    </tr><tr>\n" + "      <th>\n" +
            "        &nbsp;Telephone\n" + "      </th>\n" + "      <td width=\"50%\">" + telephone +
            "</td>\n" + "    </tr><tr>\n" + "      <th>\n" + "        &nbsp;Address\n" + "      </th>\n" +
            "      <td width=\"50%\">" + address + "</td>\n" + "    </tr><tr>\n" + "      <th>\n" +
            "        &nbsp;City\n" + "      </th>\n" + "      <td width=\"50%\">" + city+ "</td>\n" +
            "    </tr><tr>\n" + "      <th>\n" + "        &nbsp;State\n" + "      </th>\n" +
            "      <td width=\"50%\">" + state + "</td>\n" + "    </tr><tr>\n" + "      <th>\n" +
            "        &nbsp;Zip/Pin\n" + "      </th>\n" + "      <td width=\"50%\">" + zip +
            "</td>\n" + "    </tr><tr>\n" + "      <th>\n" + "        &nbsp;Comments\n" + "      </th>\n" +
            "      <td width=\"50%\">" + comments + "</td>\n" + "    </tr>\n" + "  </table>";

        body_3 = body_3.replaceAll("null", " ");
        body = body_1 + body_2 + body_3 + "</div>" + "</body>" + "</html>";
        String footer = this.getEmailFooterDetails();
        return body + footer;
    }
 
    private String getEmailFooterDetails() {
        String footer = " <section class=\"gradient sec-padding\">\n" + 
        "    <div class=\"container\">\n" + 
        "      <div class=\"row\">\n" + 
        "        <div class=\"col-md-3 col-sm-12 colmargin clearfix\">\n" + 
        "        </div>\n" + 
        "\n" + 
        "\n" + 
        "\n" + 
        "        <div class=\"col-md-9 col-sm-12 colmargin tex-left clearfix\">\n" + 
        "          <h4 class=\"uppercase footer-title less-mar3\">UP Power Corporation Limited</h4>\n" + 
        "          <div class=\"clearfix\"></div>\n" + 
        "             <ul class=\"address-info no-border\">\n" + 
        "\n" + 
        "               <li><i class=\"fa fa-home\"></i> Shakti Bhavan, 14, Ashok Marg, Lucknow, UP, India.   <i class=\"fa fa-phone\"></i> Phone: 91+522-2887701-03</li>\n" + 
        "\n" + 
        "\n" + 
        "             </ul>\n" + 
        "\n" + 
        "          <div class=\"clearfix\"></div>\n" + 
        "		  <div class=\"row\"> \n" + 
        "      <div class=\"col-md-6 col-sm-12\">\n" + 
       
        "		  </div>\n" + 
        "\n" + 
        "  </div>\n" + 
        "\n" + 
        "        </div>\n" + 
        "        <!--end item-->\n" + 
        "\n" + 
        "      </div>\n" + 
        "    </div>\n" + 
        "  </section>";
        
        return footer;
    }

    private String getHeaderImage() {
        String imgTag = "<img src=\"https://www.uppclonline.com/en_GB/images/logo.png\">";
        return imgTag;
    }

    private void showInfoMessage(String msg) {
        FacesMessage message = new FacesMessage(msg); message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
    }
    
    
    private void reset(){ 
        name="";
        account="";
        connection="";
        email="";
        telephone="";
        address="";
        city="";
        state="";
        zip="";
        comments="";
        discom="";
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
    
    public String findLabel(String key){
        String value = utils.getLabelValueForKey(key);
        System.out.println("Key --> "+key+ " " + "Value --> "+value); 
        return value;
    }
}
