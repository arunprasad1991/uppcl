package com.xx.uppcl.rest.client;

import com.xx.uppcl.rest.request.GetBillingDetailsRequest;
import com.xx.uppcl.rest.request.GetBillingDetailsRequest.DateRange;
import com.xx.uppcl.rest.request.GetBillingDetailsRequest.SearchParameters;
import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.request.GetOTPRequest;
import com.xx.uppcl.rest.request.ValidateBillRequest;
import com.xx.uppcl.rest.request.VerifyOTPRequest;
import com.xx.uppcl.rest.response.GetOTPResponse;

import com.xx.uppcl.rest.services.RestServices;

import java.io.Serializable;

public class RestServicesClient implements Serializable{
    public RestServicesClient() {
        super();
    }
    private static final long serialVersionUID=132861L; 
    
    public static void main(String [] args){
        RestServices services=new RestServices();
//        GetOTPRequest request=new GetOTPRequest();
 //       request.setAcctID("9800000000");
/*        request.setMode("BOTH");
        request.setSource("WSS");
        request.setMobileNo("6377246209");
       request.setEmail("sachin.bhardwaj2891@gmail.com");
        services.getOTP(request);*/
        
/*        VerifyOTPRequest request=new VerifyOTPRequest();
        request.setMobileNo("9729755654");
        request.setOtp("3764");
        request.setVerifymode("MOBILE");
        services.verifyOTP(request);*/
 /*       GetConsumerDetailsRequest request=new GetConsumerDetailsRequest();
        request.setKno("4624954000");
        GetBillD request=new ValidateBillRequest();
        request.setKno("0010960869");
        request.setBillNo("001094939784");
        request.setDuration("100");
        request.setSbmBillFlag("FALSE");*/
 GetBillingDetailsRequest request=new GetBillingDetailsRequest(); 
 request.setKnumber("0010960869"); //"0010960869"
 DateRange dr = request.new DateRange();
 dr.setFromDate("18-07-2021"); //"18-07-2021"
 dr.setToDate("18-07-2022"); //"18-07-2022"
 SearchParameters sp =  request.new SearchParameters();
 sp.setDateRange(dr);
 request.setSearchParameters(sp);
        services.getBillingDetails(request,"DVVNL");
    }
}
