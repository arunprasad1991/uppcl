package com.xx.uppcl.rest.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.xx.uppcl.rest.request.AccDetailsCorrectionSRRequest;
import com.xx.uppcl.rest.request.GetBillDownloadRequest;
import com.xx.uppcl.rest.request.GetBillingDetailsRequest;
import com.xx.uppcl.rest.request.GetConsumerDetailsRequest;
import com.xx.uppcl.rest.request.GetConsumptionDetailsRequest;
import com.xx.uppcl.rest.request.GetMeterDetailsRequest;
import com.xx.uppcl.rest.request.GetNetMeterDetailsRequest;
import com.xx.uppcl.rest.request.GetOTPRequest;
import com.xx.uppcl.rest.request.GetPanDetailsRequest;
import com.xx.uppcl.rest.request.GetProcessingFeeRequest;
import com.xx.uppcl.rest.request.GetSRStatusRequest;
import com.xx.uppcl.rest.request.InitiatePaymentRequest;
import com.xx.uppcl.rest.request.OnlineLoadExtChargesRequest;
import com.xx.uppcl.rest.request.OnlineLoadExtEligibltyRequest;
import com.xx.uppcl.rest.request.SendEmailRequest;
import com.xx.uppcl.rest.request.SubmitBillMeterGenRequest;
import com.xx.uppcl.rest.request.SubmitBillNetMeterGenRequest;
import com.xx.uppcl.rest.request.UpdateConsumerDetailsRequest;
import com.xx.uppcl.rest.request.UpdatePanDetailsRequest;
import com.xx.uppcl.rest.request.ValidateBillRequest;
import com.xx.uppcl.rest.request.VerifyOTPRequest;
import com.xx.uppcl.rest.request.WhatsAppSubsRequest;
import com.xx.uppcl.rest.response.AccDetailsCorrectionSRResponse;
import com.xx.uppcl.rest.response.GetBillDownloadResponse;
import com.xx.uppcl.rest.response.GetBillingDetailsResponse;
import com.xx.uppcl.rest.response.GetConsumerDetailsResponse;
import com.xx.uppcl.rest.response.GetConsumptionDetailsResponse;
import com.xx.uppcl.rest.response.GetMeterDetailsResponse;
import com.xx.uppcl.rest.response.GetNetMeterDetailsResponse;
import com.xx.uppcl.rest.response.GetOTPResponse;
import com.xx.uppcl.rest.response.GetPanDetailsResponse;
import com.xx.uppcl.rest.response.GetProcessingFeeResponse;
import com.xx.uppcl.rest.response.GetSRStatusResponse;
import com.xx.uppcl.rest.response.InitiatePaymentResponse;
import com.xx.uppcl.rest.response.OnlineLoadExtChargesResponse;
import com.xx.uppcl.rest.response.OnlineLoadExtEligibltyResponse;
import com.xx.uppcl.rest.response.SendEmailResponse;
import com.xx.uppcl.rest.response.SubmitBillMeterGenResponse;
import com.xx.uppcl.rest.response.SubmitBillNetMeterGenResponse;
import com.xx.uppcl.rest.response.UpdateConsumerDetailsResponse;
import com.xx.uppcl.rest.response.UpdatePanDetailsResponse;
import com.xx.uppcl.rest.response.ValidateBillResponse;
import com.xx.uppcl.rest.response.VerifyOTPResponse;

import com.xx.uppcl.rest.response.WhatsAppSubsResponse;
import com.xx.uppcl.services.DBServices;

import com.xx.uppcl.utils.Utils;

import java.util.ArrayList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestServices {
    public RestServices() {
        super();
    }
    private static final long serialVersionUID=132862L; 
    private DBServices dbServices=new DBServices();
    
    public GetOTPResponse getOTP(GetOTPRequest request,String discom){        
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_GET_OTP");
        ObjectMapper mapper=new ObjectMapper();
        GetOTPResponse response=new GetOTPResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();            
        response=new ObjectMapper().readValue(respJsonStr, GetOTPResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public VerifyOTPResponse verifyOTP(VerifyOTPRequest request,String discom){        
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_VERIFY_OTP");
        ObjectMapper mapper=new ObjectMapper();
        VerifyOTPResponse response=new VerifyOTPResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();            
        response=new ObjectMapper().readValue(respJsonStr, VerifyOTPResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public GetConsumerDetailsResponse getConsumerDetails(GetConsumerDetailsRequest request,String discom){        
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_GET_CONSUMER_DETAILS");    
        ObjectMapper mapper=new ObjectMapper();
        GetConsumerDetailsResponse response=new GetConsumerDetailsResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> " + reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();   
        System.out.println("Response --> " + respJsonStr);
                        //hardcoded response check
//            Utils util = new Utils();
//            String respJsonStr = util.getHardCodeRespValueForKey("CONSUMER_SERVICE_RESPONSE");
            
        response=new ObjectMapper().readValue(respJsonStr, GetConsumerDetailsResponse.class);
        }
        catch(Exception e){
            System.out.println("Inside Rest Service Catch Block");
            e.printStackTrace();
        }
        return response;
    }
    
    public ValidateBillResponse validateBill(ValidateBillRequest request,String discom){        
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_VALIDATE_BILL");    
        ObjectMapper mapper=new ObjectMapper();
        ValidateBillResponse response=new ValidateBillResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();            
        response=new ObjectMapper().readValue(respJsonStr, ValidateBillResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public GetBillingDetailsResponse getBillingDetails(GetBillingDetailsRequest request,String discom){
            String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_GET_BILL_DETAILS");    
            ObjectMapper mapper=new ObjectMapper();
            GetBillingDetailsResponse response=new GetBillingDetailsResponse();
            try{            
            String reqStr=mapper.writeValueAsString(request);
                System.out.println("Request --> " + reqStr);
            RestTemplate restTemplate=new RestTemplate();
            HttpHeaders headers =new HttpHeaders();
            headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
            String respJsonStr=responseEntity.getBody(); 
                System.out.println("Response --> " + respJsonStr);
            response=new ObjectMapper().readValue(respJsonStr, GetBillingDetailsResponse.class);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return response;
        }
    
    public GetConsumptionDetailsResponse getConsumptionDetails(GetConsumptionDetailsRequest request,String discom){
           String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_GET_BILL_CONSUMPTION_HISTORY");    
           ObjectMapper mapper=new ObjectMapper();
           GetConsumptionDetailsResponse response=new GetConsumptionDetailsResponse();
           try{            
           String reqStr=mapper.writeValueAsString(request);
               System.out.println("Request --> " + reqStr);
           RestTemplate restTemplate=new RestTemplate();
           HttpHeaders headers =new HttpHeaders();
            headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));           headers.setContentType(MediaType.APPLICATION_JSON);
           HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
               System.out.println("Url --> " + url);
           ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
           String respJsonStr=responseEntity.getBody();  
               System.out.println("Response --> " + respJsonStr);
           response=new ObjectMapper().readValue(respJsonStr, GetConsumptionDetailsResponse.class);
           }
           catch(Exception e){
               e.printStackTrace();
           }
           return response;
       }
       
    public UpdateConsumerDetailsResponse updateProfile(UpdateConsumerDetailsRequest request,String discom){                    
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_UPDATE_CONSUMER_DETAILS");    
        ObjectMapper mapper=new ObjectMapper();
        UpdateConsumerDetailsResponse response=new UpdateConsumerDetailsResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request str --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("url --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();            
            System.out.println("Response str --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, UpdateConsumerDetailsResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public SendEmailResponse sendEmail(SendEmailRequest request,String discom){                    
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_SEND_EMAIL");    
        ObjectMapper mapper=new ObjectMapper();
        SendEmailResponse response=new SendEmailResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();            
        response=new ObjectMapper().readValue(respJsonStr, SendEmailResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public AccDetailsCorrectionSRResponse accDetailsCorrectionSR(AccDetailsCorrectionSRRequest request, String discom){ 
        //to be uncomment after db entry 
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_ACCOUNT_CORRECTION");
        ObjectMapper mapper=new ObjectMapper();
        AccDetailsCorrectionSRResponse response=new AccDetailsCorrectionSRResponse();
        try{            
            String reqStr = mapper.writeValueAsString(request);
            System.out.println("Request --> " + reqStr);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"),
                                 dbServices.getProperty("REST_SERVICE_PWD"));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(reqStr, headers);
            System.out.println("Url --> " + url);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
//            String respJsonStr = responseEntity.getBody();
            //hardcoded response check
                        Utils util = new Utils();
                        String respJsonStr = util.getHardCodeRespValueForKey("ACCOUNT_CORRECT_HARDCODE_RESPONSE");
            //
            System.out.println("Res string --> " + respJsonStr);
            if (respJsonStr != null) {
                response = new ObjectMapper().readValue(respJsonStr, AccDetailsCorrectionSRResponse.class);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public GetSRStatusResponse getSRStatus(GetSRStatusRequest request, String discom){ 
        //to be uncomment after db entry 
            String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_SR_STATUS");
        
    //        String url=dbServices.getProperty(discom+"_SERVICE_URL")+ "/Ext/Services/RPS/ServiceReq";
        ObjectMapper mapper=new ObjectMapper();
        GetSRStatusResponse response=new GetSRStatusResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("URL --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();  
            
            //hardcoded response check
//                        Utils util = new Utils();
//                        String respJsonStr = util.getHardCodeRespValueForKey("SERVICE_REQ_STATUS_RESPONSE");
            
            
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, GetSRStatusResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public GetBillDownloadResponse getBillDownloadFileContent(GetBillDownloadRequest request, String discom){ 
        //to be uncomment after db entry 
            String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_BILL_DONWLOAD_FILE_CONTENT");
        
    //        String url=dbServices.getProperty(discom+"_SERVICE_URL")+ "/Ext/Services/RPS/ServiceReq";
        ObjectMapper mapper=new ObjectMapper();
        GetBillDownloadResponse response=new GetBillDownloadResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("URL --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();  
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, GetBillDownloadResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public OnlineLoadExtEligibltyResponse onlineLoadExtEligiblty(OnlineLoadExtEligibltyRequest request, String discom){ 
        //to be uncomment after db entry 
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_LOAD_EXT_ELIGIBILITY");
        
        ObjectMapper mapper=new ObjectMapper();
        OnlineLoadExtEligibltyResponse response=new OnlineLoadExtEligibltyResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("URL --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();
            //hardcoded response check
//            Utils util = new Utils();
//            String respJsonStr = util.getHardCodeRespValueForKey("ONLINE_EXTENSION_ELIGBLE_HARDOCDED_RESPONSE");
            
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, OnlineLoadExtEligibltyResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public OnlineLoadExtChargesResponse onlineLoadExtCharges(OnlineLoadExtChargesRequest request, String discom){ 
        //to be uncomment after db entry 
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_LOAD_EXT_CHARGES");
        ObjectMapper mapper=new ObjectMapper();
        OnlineLoadExtChargesResponse response=new OnlineLoadExtChargesResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("Url --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody(); 
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, OnlineLoadExtChargesResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public GetMeterDetailsResponse getMeterDetails(GetMeterDetailsRequest request, String discom){ 
        //to be uncomment after db entry 
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_GET_METER_DETAILS");
        ObjectMapper mapper=new ObjectMapper();
        GetMeterDetailsResponse response=new GetMeterDetailsResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("Url --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();   
            
            //hardcoded response check
//            Utils util = new Utils();
//            String respJsonStr = util.getHardCodeRespValueForKey("SELF_BILL_GET_METER_RESPONSE");
            
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, GetMeterDetailsResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public GetNetMeterDetailsResponse getNetMeterDetails(GetNetMeterDetailsRequest request, String discom){ 
        //to be uncomment after db entry 
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_GET_NET_METER_DETAILS");
        
        ObjectMapper mapper=new ObjectMapper();
        GetNetMeterDetailsResponse response=new GetNetMeterDetailsResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("Url --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody(); 
                        //hardcoded response check
//            Utils util = new Utils();
//            String respJsonStr = util.getHardCodeRespValueForKey("SELF_BILL_GET_NET_METER_RESPONSE");
            
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, GetNetMeterDetailsResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public SubmitBillMeterGenResponse submitBillMeterDetails(SubmitBillMeterGenRequest request, String discom){ 
        //to be uncomment after db entry 
            String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_SUBMIT_METER_DETAILS");
        
        ObjectMapper mapper=new ObjectMapper();
        SubmitBillMeterGenResponse response=new SubmitBillMeterGenResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("Url --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody(); 
            
        //hardcoded response check
//            Utils util = new Utils();
//            String respJsonStr = util.getHardCodeRespValueForKey("SELF_BILL_SUBMIT_METER_RESPONSE");
 
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, SubmitBillMeterGenResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public SubmitBillNetMeterGenResponse submitBillNetMeterDetails(SubmitBillNetMeterGenRequest request, String discom){ 
        //to be uncomment after db entry 
            String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_SUBMIT_NET_METER_DETAILS");
        
        ObjectMapper mapper=new ObjectMapper();
        SubmitBillNetMeterGenResponse response=new SubmitBillNetMeterGenResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("Url --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody(); 
            
            //hardcoded response check
//            Utils util = new Utils();
//            String respJsonStr = util.getHardCodeRespValueForKey("SELF_BILL_SUBMIT_NET_METER_RESPONSE");
 
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, SubmitBillNetMeterGenResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public GetProcessingFeeResponse getPriceDetails(GetProcessingFeeRequest request, String discom){ 
        //to be uncomment after db entry 
        String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_PAY_FETCH_PRICE");
        
//        String url=dbServices.getProperty(discom+"_SERVICE_URL")+ "/INTERNAL/RPS/CaseValidation/GDATA";
        ObjectMapper mapper=new ObjectMapper();
        GetProcessingFeeResponse response=new GetProcessingFeeResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("Url --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();     
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, GetProcessingFeeResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public InitiatePaymentResponse initiatePayment(InitiatePaymentRequest request, String discom){ 
        //to be uncomment after db entry 
            String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_INITIATE_PAYMENT");
        
//        String url=dbServices.getProperty(discom+"_SERVICE_URL")+ "/Ext/Services/RPS/ServiceReq";
        ObjectMapper mapper=new ObjectMapper();
        InitiatePaymentResponse response=new InitiatePaymentResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();            
        response=new ObjectMapper().readValue(respJsonStr, InitiatePaymentResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public WhatsAppSubsResponse subscribeWhatsApp(WhatsAppSubsRequest request, String discom){ 
        //to be uncomment after db entry 
            String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_WHATSAPP_SUBSCRIPTION");
        
    //        String url=dbServices.getProperty(discom+"_SERVICE_URL")+ "/Ext/Services/RPS/ServiceReq";
        ObjectMapper mapper=new ObjectMapper();
        WhatsAppSubsResponse response=new WhatsAppSubsResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("Url --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();  
            
        //hardcoded response check
        //            Utils util = new Utils();
        //            String respJsonStr = util.getHardCodeRespValueForKey("SELF_BILL_SUBMIT_METER_RESPONSE");
            
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, WhatsAppSubsResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public GetPanDetailsResponse getPanDetails(GetPanDetailsRequest request, String discom){ 
        //to be uncomment after db entry 
            String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_GET_UPDATE_PAN");
        
    //        String url=dbServices.getProperty(discom+"_SERVICE_URL")+ "/Ext/Services/RPS/ServiceReq";
        ObjectMapper mapper=new ObjectMapper();
        GetPanDetailsResponse response=new GetPanDetailsResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("Url --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();  
            
//        hardcoded response check
//                    Utils util = new Utils();
//                    String respJsonStr = util.getHardCodeRespValueForKey("GET_PAN_DETAILS");
            
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, GetPanDetailsResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public UpdatePanDetailsResponse updatePanNumber(UpdatePanDetailsRequest request, String discom){ 
        //to be uncomment after db entry 
            String url=dbServices.getProperty(discom+"_SERVICE_URL")+dbServices.getProperty("REST_GET_UPDATE_PAN");
        
    //        String url=dbServices.getProperty(discom+"_SERVICE_URL")+ "/Ext/Services/RPS/ServiceReq";
        ObjectMapper mapper=new ObjectMapper();
        UpdatePanDetailsResponse response=new UpdatePanDetailsResponse();
        try{            
        String reqStr=mapper.writeValueAsString(request);
            System.out.println("Request --> "+reqStr);
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBasicAuth(dbServices.getProperty("REST_SERVICE_USERNAME"), dbServices.getProperty("REST_SERVICE_PWD"));        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity=new HttpEntity<String>(reqStr,headers);
            System.out.println("Url --> "+url);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, entity, String.class);
        String respJsonStr=responseEntity.getBody();  
            
        //hardcoded response check
        //            Utils util = new Utils();
        //            String respJsonStr = util.getHardCodeRespValueForKey("UPDATE_PAN_DETAILS");
            
            System.out.println("Response --> "+respJsonStr);
        response=new ObjectMapper().readValue(respJsonStr, UpdatePanDetailsResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
