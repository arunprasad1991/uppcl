package com.xx.uppcl.beans;

import com.xx.uppcl.rest.request.SendEmailRequest;
import com.xx.uppcl.rest.response.SendEmailResponse;
import com.xx.uppcl.rest.services.RestServices;

import java.io.IOException;

import java.io.Serializable;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public class ComplaintRegister implements Serializable{
        private static final long serialVersionUID=16534243L;
    public ComplaintRegister() {
        super();
    }
    
    private String accountNo;
    private String caseType;
    private String comments;
    private static String fromEmail = "self.service@uppclonline.com";
//    private static String toAddress = "dharm24kamheda@gmail.com";
    private static String toAddress = "ibrahim.dev.code@gmail.com";
    private String discom;


    public void setDiscom(String discom) {
        this.discom = discom;
    }

    public String getDiscom() {
        return discom;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    } 

    public String onClickSubmit() {
        try {
            this.sendComplaintEmail(toAddress);
            this.showInfoMessage("Your Complaint Request has been submitted!");
            this.reset();
        } catch (IOException | SQLException e) {
        }
        return null;
    }

    private String sendComplaintEmail(String toEmail)  throws SQLException, IOException { 

        final String subject = "New Complaint Request Submitted";
        String mailContent = this.getMailBody();
        
        RestServices rs = new RestServices();
        SendEmailRequest emailRequest = new SendEmailRequest();
        emailRequest.setFrom(fromEmail);
        emailRequest.setTo(toAddress);
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
            "<tr class=\"tableHeader\"><td colspan=\"2\" align=\"center\"> <h2 style=\"color:white;\">Complaint Request - Details</h2></td></tr>" +
            "    <tr><th>&nbsp;Account No </th> <td width=\"50%\">" + accountNo + "</td></tr>\n" +
            "    <tr><th>&nbsp;Case Type</th>\n" + "      <td width=\"50%\">" + caseType + "</td>\n" +
            "    </tr><tr>\n" + "      <th>\n" + "        &nbsp;Comments\n" + "      </th>\n" +
            "      <td width=\"50%\">" + comments + "</td>\n" + "    </tr>\n" + "  </table>";

        body_3 = body_3.replaceAll("null", " ");
        body = body_1 + body_2 + body_3 + "</div>" + "</body>" + "</html>";
        String footer = this.getEmailFooterDetails();
        return body + footer;
    }
    
    private String getHeaderImage() {
        String imgTag = "<img src=\"https://www.uppclonline.com/en_GB/images/logo.png\">";
        return imgTag;
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
        "                 <div class=\"row\"> \n" + 
        "      <div class=\"col-md-6 col-sm-12\">\n" +        
        "                 </div>\n" + 
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

    private void showInfoMessage(String msg) {
        FacesMessage message = new FacesMessage(msg); message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
    }
    
    private void reset(){
        accountNo = "";
        caseType = "";
        comments = "";
    }
}
