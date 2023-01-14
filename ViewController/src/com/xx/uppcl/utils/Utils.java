package com.xx.uppcl.utils;

import com.xx.uppcl.services.DBServices;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.io.OutputStreamWriter;

import java.nio.charset.Charset;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.DataObject;
import oracle.stellent.ridc.model.DataResultSet;
import oracle.stellent.ridc.model.TransferFile;
import oracle.stellent.ridc.protocol.ServiceResponse;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class Utils {

    public Utils() {
        super();
    }

    Map paramSession = ADFContext.getCurrent().getSessionScope();
    Map<String, Object> pageFlowParam = AdfFacesContext.getCurrentInstance().getPageFlowScope();
    private DBServices dbServices=new DBServices();

    //----------------  Document Utils Starts------------
    private IdcClient client;
    private IdcContext connectionContext;

//    private String BASE_PROPERTY_PATH = "/com/xx/uppcl/bundles/";
    private String BASE_PROPERTY_PATH = dbServices.getProperty("SERVER_PROP_FILE_PATH");
    private String LABEL_PROPERTY_PATH = "uppcl_Label.properties";
    private String ENVIRONMENT_PROPERTY_PATH = "uppcl_Env.properties";
    private String HARDCODED_RESP_PROPERTY_PATH = "uppcl_Hardcoded_Response.properties";
    
    private String CONTENT_URL = "CONTENT_URL";
    private String CONTENT_USER_NAME = "CONTENT_USER_NAME";
    private String CONTENT_PASSWORD = "CONTENT_PWD";
    
    

    //document utilities
    //get connection
    public void createConnection() throws IdcClientException {
        String connectURL = dbServices.getProperty(CONTENT_URL);
        String username = dbServices.getProperty(CONTENT_USER_NAME);
        String password = dbServices.getProperty(CONTENT_PASSWORD);
        System.out.println("Connecting to content server at " + connectURL + " using username " + username +
                           " and password");
        try {
            IdcClientManager idcClientManager = new IdcClientManager();
            this.client = idcClientManager.createClient(connectURL);
        } catch (IdcClientException e) {
            System.out.println("Error occurred while establishing client");
            pageFlowParam.put("attachmentErrMsg", "Something went worng .Please try again.");
            throw e;
        }
        this.connectionContext = new IdcContext(username , password);
        System.out.println("Succesfully connected RIDC client to " + connectURL);
    }

    //get folder id
    public Long getFolderIdFromContent(String folderPath) {
        ServiceResponse myServiceResponse = null;
        Long myFolderId = null;

        try {
            DataBinder myRequestDataBinder = client.createBinder();
            myRequestDataBinder.putLocal("IdcService", "COLLECTION_INFO");
            myRequestDataBinder.putLocal("hasCollectionPath", "true");
            // Folder path for which ID is needed.
            myRequestDataBinder.putLocal("dCollectionPath", folderPath);
            myServiceResponse = client.sendRequest(connectionContext, myRequestDataBinder);
            DataBinder myResponseDataBinder = myServiceResponse.getResponseAsBinder();
            DataResultSet myDataResultSet = myResponseDataBinder.getResultSet("PATH");
            DataObject myDataObject = myDataResultSet.getRows().get(myDataResultSet.getRows().size() - 1);
            myFolderId = new Long(myDataObject.get("dCollectionID"));
            System.out.println("Folder id: " + myFolderId);
        } catch (IdcClientException idcce) {
            System.out.println("IDC Client Exception occurred. Unable to retrieve folder id from path. Message: " +
                               idcce.getMessage() + ", Stack trace: ");
            pageFlowParam.put("attachmentErrMsg", "Something went worng .Please try again.");
            idcce.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception occurred. Unable to retrieve folder id from path. Message: " +
                               e.getMessage() + ", Stack trace: ");
            pageFlowParam.put("attachmentErrMsg", "Something went worng .Please try again.");
            e.printStackTrace();
        } finally {
            if (myServiceResponse != null) {
                myServiceResponse.close();
            }
        }

        return myFolderId;
    }

    //upload file
    public DataBinder uploadFile(String docType, String userName, String securityGroup,
                                 UploadedFile fileuploaded, String folderId) throws IdcClientException, IOException {

        ServiceResponse response = null;
        InputStream fileStream = null;
        try {

            String fileName = fileuploaded.getFilename();
            fileStream = fileuploaded.getInputStream();
            long fileLength = fileuploaded.getLength();
            String contentType = fileuploaded.getContentType();

            DataBinder requestData = client.createBinder();

            System.out.println("Before ::  Request  --> " + dataBinderToString(requestData));

            requestData.putLocal("IdcService", "CHECKIN_UNIVERSAL");
            requestData.putLocal("xCollectionID", folderId);
            requestData.putLocal("dDocType", "Document");
            requestData.putLocal("dDocTitle", fileuploaded.getFilename());
            requestData.putLocal("dDocAuthor", userName);
            requestData.putLocal("dSecurityGroup", securityGroup);
            requestData.putLocal("dDocAccount", "");
            requestData.addFile("primaryFile", new TransferFile(fileStream, fileName, fileLength, contentType));

            System.out.println("After :: Request  --> " + dataBinderToString(requestData));

            response = client.sendRequest(connectionContext, requestData);
            DataBinder responseData = response.getResponseAsBinder();

            System.out.println("After :: Response  --> " + dataBinderToString(responseData));

        } catch (IdcClientException idcce) {
            System.out.println("IDC Client Exception occurred. Unable to upload file. Message: " + idcce.getMessage() +
                               ", Stack trace: ");
            pageFlowParam.put("attachmentErrMsg", getLabelValueForKey("SOMETHING_WENT_WRONG"));
            idcce.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("IO Exception occurred. Unable to upload file. Message: " + ioe.getMessage() +
                               ", Stack trace: ");
            pageFlowParam.put("attachmentErrMsg", getLabelValueForKey("SOMETHING_WENT_WRONG"));
            ioe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception occurred. Unable to upload file. Message: " + e.getMessage() +
                               ", Stack trace: ");
            pageFlowParam.put("attachmentErrMsg", getLabelValueForKey("SOMETHING_WENT_WRONG"));
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return response.getResponseAsBinder();
        }
    }

    public String dataBinderToString(DataBinder binder) {
        StringBuilder sb = new StringBuilder();
        sb.append("DataBinder {\n");

        // Files
        sb.append("\tFiles {\n");
        if (binder.containsFiles()) {
            for (String fileName : binder.getFileNames()) {
                sb.append("\t\tFile name = " + fileName + "\n");
                sb.append("\t\tContent length = " + binder.getFile(fileName));
            }
        } else {
            sb.append("\t\tnone\n");
        }
        sb.append("\t}\n");

        // Local Data
        sb.append("\tLocalData {\n");
        if (!binder.getLocalData().isEmpty()) {
            Map<String, String> sortedMap = new TreeMap<String, String>(binder.getLocalData());
            for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
                sb.append("\t\t")
                  .append(entry.getKey())
                  .append(" = ")
                  .append(entry.getValue())
                  .append("\n");
            }
        } else {
            sb.append("\t\tnone\n");
        }
        sb.append("\t}\n");

        // Options
        sb.append("\tOptions {\n");
        if (!binder.getOptionListNames().isEmpty()) {
            List<String> options = new ArrayList<String>(binder.getOptionListNames());
            Collections.sort(options);
            for (String option : options) {
                Iterator<String> iterator = binder.getOptionList(option).iterator();
                while (iterator.hasNext()) {
                    sb.append("\t\t")
                      .append(iterator.next())
                      .append("\n");
                }
            }
        } else {
            sb.append("\t\tnone\n");
        }
        sb.append("\t}\n");

        // Result sets
        sb.append("\tResultSets {\n");
        if (!binder.getResultSetNames().isEmpty()) {
            Iterator<String> names = binder.getResultSetNames().iterator();
            while (names.hasNext()) {
                String name = names.next();
                sb.append("\t\t")
                  .append(name)
                  .append(" {\n");
                for (DataObject obj : binder.getResultSet(name).getRows()) {
                    Map<String, String> sortedMap = new TreeMap<String, String>(obj);
                    for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
                        sb.append("\t\t\t")
                          .append(entry.getKey())
                          .append(" = ")
                          .append(entry.getValue())
                          .append("\n");
                    }
                }
                sb.append("\t\t}\n");
            }
        } else {
            sb.append("\t\tnone\n");
        }
        sb.append("\t}\n");
        sb.append("}");
        return sb.toString();
    }
    
    //get file
    public void getFileFromContent(OutputStream outputStream, String docId, String contentId) throws FileNotFoundException {
       try {
           System.out.println("getFileFromContent :: Doc Id  --> "+docId);
                System.out.println("getFileFromContent :: content Id  --> "+contentId);
            DataBinder dataBinder = client.createBinder();   
            dataBinder.putLocal("IdcService", "GET_FILE");        
            dataBinder.putLocal("dID", docId);
            dataBinder.putLocal("dDocName", contentId);
            dataBinder.putLocal("RevisionSelectedMethod","LatestReleased");
                System.out.println("After :: Request  --> " + dataBinderToString(dataBinder));
            ServiceResponse responseString =client.sendRequest(connectionContext, dataBinder);             
            InputStream  inputStream = responseString.getResponseStream();  
            System.out.println("Header  --> "+responseString.getHeader("Content-Type"));
            byte[] buf = new byte[1024 * 256];
            long i = 0;
            int len;

            System.out.println("Downloading File from UCM Server"); 

            while (true) {
                    i++;
                    len = inputStream.read(buf);
                System.out.println("len  --> "+len);
                    outputStream.write(buf, 0, len);
                    if (len == -1) {
                        break;
                   }
                 
                }
                 outputStream.flush(); 
                 inputStream.close();
                 outputStream.close();
            } catch (IdcClientException ice) {
                     System.out.print("IDC Client Exception occured. Exception message: " + ice.getMessage());
            } catch (IOException ioe) {
                     System.out.println("IO Exception occurred. Unable to retrieve file. Message: " + ioe.getMessage()); 
            } catch (Exception ex) {
                     System.out.println("Exception message: " + ex.getMessage() );
            }               
        
    }
    
    public void deleteFileFromContent(String contentId) throws IdcClientException { 
        try{
            System.out.println("contentId  --> "+contentId);
            DataBinder requestData = client.createBinder();   
            requestData.putLocal("IdcService", "DELETE_DOC");
            requestData.putLocal("dDocName", contentId);
            System.out.println("Before ::  Request  --> "+dataBinderToString(requestData));
           
            ServiceResponse response = client.sendRequest(connectionContext, requestData);
            DataBinder responseData = response.getResponseAsBinder();
            System.out.println(dataBinderToString(responseData));
        } catch (IdcClientException ice) {
                     System.out.print("IDC Client Exception occured. Exception message: " + ice.getMessage());
        }catch (Exception ex) {
                     System.out.println("Exception message: " + ex.getMessage() );
        }    
    }
    

    //----------------  Document Utils Ends------------


    //----------------  Properties Load Utils Starts------------

    public Properties loadLabelProperties() throws Exception {
        FileInputStream in = new FileInputStream(BASE_PROPERTY_PATH + LABEL_PROPERTY_PATH);
        Properties p = new Properties();
        p.load(in);

        return p;
    }
    

    public Properties loadEnvironmentProperties() throws Exception {
        FileInputStream in = new FileInputStream(BASE_PROPERTY_PATH + ENVIRONMENT_PROPERTY_PATH);
        Properties p = new Properties();
        p.load(in);

        return p;
    }

    public Properties loadHardCodedRespProperties() throws Exception {
        FileInputStream in = new FileInputStream(BASE_PROPERTY_PATH + HARDCODED_RESP_PROPERTY_PATH);
        Properties p = new Properties();
        p.load(in);

        return p;
    }

    public String getLabelValueForKey(String key) {
        String keyValue = null;
        try {
            Properties p = new Properties();
            p = loadLabelProperties();

            keyValue = p.getProperty(key);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyValue;
    }

    public String getEnvValueForKey(String key) {
        String keyValue = null;
        try {
            Properties p = new Properties();
            p = loadEnvironmentProperties();
            keyValue = p.getProperty(key);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyValue;
    }

    public String getHardCodeRespValueForKey(String key) {
        String keyValue = null;
        try {
            Properties p = new Properties();
            p = loadHardCodedRespProperties();
            keyValue = p.getProperty(key);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyValue;
    }

    //----------------  Properties Load Utils Ends------------


    public void redirect(String url) {
        try {
            FacesContext fctx = FacesContext.getCurrentInstance();
            ExternalContext ectx = fctx.getExternalContext();
            
            String contextPath = fctx.getExternalContext().getRequestContextPath();
            
            System.out.println("Url --> "+url);
            
            ectx.redirect(url);
            fctx.responseComplete();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
    
    public void showInfoMessage(String msg) {
        FacesMessage message = new FacesMessage(msg); message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
    }
    
    public boolean checkNumeric(String str) {
        boolean numeric = false;
        boolean validFlag = false;
        numeric = str.matches("-?\\d+(\\.\\d+)?");
        if (numeric) {
            validFlag = true;
        }
        return validFlag;
    }
    
    public boolean checkNumericWithoutDecimal(String str) {
        boolean numeric = false;
        boolean validFlag = false;
        numeric = str.matches("-?\\d+(\\.\\d+)?");
        if (numeric) {
            //convert to decimal
            double d = Double.parseDouble(str);
            System.out.println("double value --> " + d);
            if (d % 1 != 0) {
                //number is decimal
                validFlag = false;
            } else {
                ////number is not decimal
                validFlag = true;
            }
        }
        return validFlag;
    }

    public boolean checkNumericWithNthDecimalVal(String str, int num) {
        boolean numeric = false;
        boolean validFlag = false;

        int digitBeforeDecimal = 0;
        int digitAfterDecimal = 0;

        numeric = str.matches("-?\\d+(\\.\\d+)?");
        if (numeric) {
            digitBeforeDecimal = noOfDigitBeforeDecimal(str);
            digitAfterDecimal = noOfDigitAfterDecimal(str);
            if (digitBeforeDecimal > num && digitAfterDecimal > num) {
                validFlag = false;
            } else {
                validFlag = true;
            }
        }
        return validFlag;
    }

    public int noOfDigitBeforeDecimal(String str) {
        int digitBeforeDecimal = 0;
        String beforedecimalValue = null;
        if (str != null && str.contains(".")) {
            beforedecimalValue = str.substring(0, str.indexOf("."));
        }
        System.out.println("beforedecimalValue  --> " + beforedecimalValue);
        if (beforedecimalValue != null) {
            digitBeforeDecimal = beforedecimalValue.length();
        }
        System.out.println("digitBeforeDecimal  --> " + digitBeforeDecimal);
        return digitBeforeDecimal;
    }

    public int noOfDigitAfterDecimal(String str) {
        int digitAfterDecimal = 0;
        String afterdecimalValue = null;
        if (str != null && str.contains(".")) {
            afterdecimalValue = str.substring(str.indexOf(".")+1, str.length());
        }
        System.out.println("afterdecimalValue  --> " + afterdecimalValue);
        if (afterdecimalValue != null) {
            digitAfterDecimal = afterdecimalValue.length();
        }
        System.out.println("digitAfterDecimal  --> " + digitAfterDecimal);
        return digitAfterDecimal;
    }

    public String addZeroAfterDecimal(String str, int num) {
        String finalStr = null;
        if (str != null) {
            finalStr = str;
        } else {
            finalStr = "0";
        }

        if (finalStr != null && !finalStr.contains(".")) {
            finalStr = finalStr + ".";
            for (int i = 0; i < num; i++) {
                finalStr = finalStr + "0";
            }
        }
        System.out.println("Final Str  --> " + finalStr);
        return finalStr;
    }
    
    public void executeClientJavascript(String script) {
     FacesContext facesContext = FacesContext.getCurrentInstance();
     ExtendedRenderKitService service = Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);
     service.addScript(facesContext, script);
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
    
    public String convertDateToStr(Date date, String format){
        DateFormat dateFormat = new SimpleDateFormat(format);  
        String strDate = dateFormat.format(date);
        
        return strDate;
    }
    
    public String getPrevious12MonthDate(Date date){
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        cal.set(Calendar.MONTH, -12);  

        Date pre12MonthDate = cal.getTime();  
        return dateFormat.format(pre12MonthDate);
    }
    
    public String getWccURL() {
     String contentUrl= dbServices.getProperty("CONTENT_DOC_VIEW_URL");
     StringBuilder urlBuilder = new StringBuilder(contentUrl);
     return urlBuilder.toString();
    }
     
    public String generateDownloadDocumentLink(String dDocName) {
     StringBuilder docURL = new StringBuilder(getWccURL());
     docURL.append("/wccproxy/d?dDocName=");
     docURL.append(dDocName);
     System.out.println("Download Url --> "+docURL);
     return docURL.toString();
    }
     
    public String viewDocumentInWeb(String dDocName) {
     StringBuilder docURL = new StringBuilder(getWccURL());
     docURL.append("/wccproxy/d?dDocName=");
     docURL.append(dDocName);
     docURL.append("&Rendition=web");
     System.out.println("View Url --> "+docURL);
     return docURL.toString();
    }
    
    private void refreshPage(){
        FacesContext fctx = FacesContext.getCurrentInstance();
        String refreshpage = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
        UIV.setViewId(refreshpage);
        fctx.setViewRoot(UIV);
    }
    
    public boolean validateEmail(String email){
        String regex = "^(.+)@(.+)$";
//        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
         
        Pattern pattern = Pattern.compile(regex);
          Matcher matcher = pattern.matcher(email);
          System.out.println(email +" : "+ matcher.matches());
          
        return matcher.matches();
    }
    
    //function to check if the mobile number is valid or not
    public boolean isValidMobileNo(String str) {
        //(0/91): number starts with (0/91)
        //[7-9]: starting of the number may contain a digit between 0 to 9
        //[0-9]: then contains digits 0 to 9
        Pattern ptrn = Pattern.compile("\\d{10}");
        Matcher match = ptrn.matcher(str);
        return (match.find() && match.group().equals(str));
    }
    
    //function to check alpha numeric character
    public boolean isValidPanNumber(String str) {
        Pattern ptrn = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher match = ptrn.matcher(str);
        return (match.find() && match.group().equals(str));
    }
    
    // Base 64 utility starts
    
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final void writeMetadata(String mediaType, OutputStreamWriter outputWriter) throws IOException {
        outputWriter.write("data:");
        outputWriter.write(mediaType);
        outputWriter.write(";base64,");
        outputWriter.flush();
    }

    private static final void writeContent(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int count = inputStream.read(buffer, 0, buffer.length);
            if (count == -1) {
                break;
            }
            outputStream.write(buffer, 0, count);
        }
        outputStream.flush();
    }

    public static final String encodeDataUrl(String mediaType, int inputSize,
                                             InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream rawOutputStream = new ByteArrayOutputStream(inputSize);
             OutputStreamWriter utf8OutputWriter = new OutputStreamWriter(rawOutputStream, Charset.forName("UTF-8").newEncoder());
             OutputStream base64OutputStream = BASE64_ENCODER.wrap(rawOutputStream)) {
            writeMetadata(mediaType, utf8OutputWriter);
            writeContent(inputStream, base64OutputStream);
            return rawOutputStream.toString();
        }
    }

    public static final String encodeDataUrl(String mediaType, InputStream inputStream) throws IOException {
        return encodeDataUrl(mediaType, 1024, inputStream);
    }

    // Base 64 utility ends
   
    //encrypt and decrypt utility starts
    
    public static final String AES = "AES";
    public String encryptPass(String key, String password) throws NoSuchAlgorithmException, NoSuchPaddingException,
                                                                  InvalidKeyException,
                                                                  InvalidAlgorithmParameterException,
                                                                  IllegalBlockSizeException, BadPaddingException {
        byte[] bytekey = hexStringToByteArray(key);
        SecretKeySpec sks = new SecretKeySpec(bytekey, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
        byte[] encrypted = cipher.doFinal(password.getBytes());
        String encryptedpwd = byteArrayToHexString(encrypted);
        System.out.println("****************  Encrypted Password  ****************");
        System.out.println(encryptedpwd);
        System.out.println("****************  Encrypted Password  ****************");
        return encryptedpwd;
    }

    public String decryptPass(String key, String password) throws NoSuchAlgorithmException, NoSuchPaddingException,
                                                                  InvalidKeyException, IllegalBlockSizeException,
                                                                  BadPaddingException {

        byte[] bytekey = hexStringToByteArray(key);
        SecretKeySpec sks = new SecretKeySpec(bytekey, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, sks);
        byte[] decrypted = cipher.doFinal(hexStringToByteArray(password));
        String originalPassword = new String(decrypted);
        System.out.println("****************  Original Password  ****************");
        System.out.println(originalPassword);
        System.out.println("****************  Original Password  ****************");
        return originalPassword;
    }

    private String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    private byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
    
    
    //encrypt and decrypt utiliyy ends
}
