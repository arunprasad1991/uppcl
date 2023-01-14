package com.xx.uppcl.services;

import com.xx.uppcl.pojo.LoginDetails;

import java.io.IOException;
import java.io.Reader;

import java.sql.Blob;
import java.sql.Clob;

import oracle.jdbc.internal.OracleClob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import javax.sql.rowset.serial.SerialClob;

import oracle.adf.share.ADFContext;

import oracle.jbo.domain.ClobDomain;

public class DBServices {
    public DBServices() {
        super();
    }
    
    public LoginDetails authenticUser(String accountNumber, String pwd, String discom){
        LoginDetails loginDetails=new LoginDetails();
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{        
            loginDetails.setAuthenticationSuccessful(false);
            String query="select ACCOUNT_NUMBER,FIRST_NAME,EMAIL,MOBILE_NUMBER,LAST_REGISTER_TIME,LAST_PASS_CHANGE_TIME,LAST_LOGIN_TIME,PROFILE_PIC_NAME,PROFILE_PIC_CONTENT from XX_USER_ACCOUNTS where account_number=? and user_pwd=? and discom=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");          
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, pwd);
            pstmt.setString(3, discom);
            rs=pstmt.executeQuery();
            if(rs.next()){
                String accNo=rs.getString(1);
                String firstName=rs.getString(2);
                String email=rs.getString(3);
                String mobileNo=rs.getString(4);
                Date lastRegister=rs.getTimestamp(5);
                Date lastPassChange=rs.getTimestamp(6);
                Date lastLogin=rs.getTimestamp(7);
                String profilePicName=rs.getString(8);
                Blob profilePicContent=rs.getBlob(9);
                if(accNo!=null && accNo.length()>0){
                    loginDetails.setLoggedInUserName(firstName);
                    loginDetails.setAuthenticationSuccessful(true);
                    loginDetails.setMobileNo(mobileNo);
                    loginDetails.setValidAccount(true);
                    loginDetails.setEmail(email);
                    loginDetails.setLoggedInUserDiscom(discom);
                    loginDetails.setRegisteredLastDate(lastRegister);
                    loginDetails.setChangePasswordLastDate(lastPassChange);
                    loginDetails.setLastLoggedInDate(lastLogin);
                    loginDetails.setProfilePicName(profilePicName);
                    loginDetails.setProfilePicContent(convertBlobToStr(profilePicContent));
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return loginDetails;
    }
    
    public LoginDetails validateUserAccount(String accountNumber,String discom){
        
        boolean flag=false;
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        LoginDetails loginDetails=new LoginDetails();
        loginDetails.setValidAccount(false); 
        try{                   
            String query="select ACCOUNT_NUMBER,FIRST_NAME,EMAIL,MOBILE_NUMBER,SECURITY_QUESTION,SECURITY_ANSWER from XX_USER_ACCOUNTS where account_number=? and discom=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, discom);
            rs=pstmt.executeQuery();
            if(rs.next()){
                String userAccountNo=rs.getString(1);
                String firstName=rs.getString(2);
                String email=rs.getString(3);
                String mobileNo=rs.getString(4);
                String securityQuestion=rs.getString(5);
                String securityAnswer=rs.getString(6);
                if(userAccountNo!=null && userAccountNo.length()>0){
                    loginDetails.setLoggedInUserName(firstName);
                    loginDetails.setMobileNo(mobileNo);
                    loginDetails.setValidAccount(true);
                    loginDetails.setSecurityQuestion(securityQuestion);
                    loginDetails.setSecurityAnswer(securityAnswer);
                    loginDetails.setEmail(email);
                    loginDetails.setLoggedInUserDiscom(discom);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return loginDetails;
    }
    
    public boolean registerUser(String accountNumber,String password,String firstName,String lastName,String mobileNo,String email,String securityQuestion,String securityAnswer){
        
        boolean flag=false;
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="INSERT INTO XX_USER_ACCOUNTS (DISCOM,ACCOUNT_NUMBER,USER_PWD,FIRST_NAME,LAST_NAME,EMAIL,MOBILE_NUMBER,SECURITY_QUESTION,SECURITY_ANSWER,LAST_LOGIN_TIME, WHATSAPP_SUBSCRIBED, LAST_REGISTER_TIME, LAST_PASS_CHANGE_TIME,PROFILE_PIC_NAME, PROFILE_PIC_CONTENT) values(?,?,?,?,?,?,?,?,?,sysdate,?,sysdate,sysdate,?,?)";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, "DVVNL");
            pstmt.setString(2, accountNumber);
            pstmt.setString(3, password);
            pstmt.setString(4, firstName);
            pstmt.setString(5, lastName);
            pstmt.setString(6, email);
            pstmt.setString(7, mobileNo);
            pstmt.setString(8, securityQuestion);
            pstmt.setString(9, securityAnswer);
            pstmt.setString(10, "");
            pstmt.setString(11, "");
            pstmt.setString(12, "");
            int i=pstmt.executeUpdate();           
            if(i==1){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return false;
    }
    
    public boolean resetPassword(String accountNumber,String password, String discom){
        
        boolean flag=false;
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="update  XX_USER_ACCOUNTS set user_pwd=? , LAST_PASS_CHANGE_TIME=sysdate where account_number=? and discom=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, password);
            pstmt.setString(2, accountNumber);
            pstmt.setString(3, discom);
            int i=pstmt.executeUpdate();            
            if(i==1){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return false;
    }
    
    private static void closeDBResources(Connection conn, PreparedStatement pstmt, ResultSet rs){
        
        if(rs!=null){
            try{
                rs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
        if(pstmt!=null){
            try{
                pstmt.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
        if(conn!=null){
            try{
                conn.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }
    
    public List<String> getLinkedAccounts(String accountNo){
       
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List<String> accountList=new ArrayList<String>();
        try{        
            String query="select distinct LINKED_ACC_NO from XX_MANAGE_ACCOUNTS where PARENT_ACC_NO=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");          
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, accountNo);//loggedinAccountNo
            rs=pstmt.executeQuery();
            while(rs.next()){
                String linkedAccount=rs.getString(1);
                accountList.add(linkedAccount);
            }          
        }

        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }

        return accountList;
    }
    
    public String getProperty(String key){

        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String value=null;
        try{                   
            String query="select PROP_VALUE from XX_UPPCL_PROPERTIES where PROP_KEY=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, key);
            rs=pstmt.executeQuery();
            if(rs.next()){
                value=rs.getString(1);               
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return value;
    }
    
    public List<String> getDiscomList(){
        List<String> discomList=new ArrayList<String>();
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="select distinct DISCOM from XX_UPPCL_DISCOM order by DISCOM asc";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            rs=pstmt.executeQuery();
            while(rs.next()){
               String discom=rs.getString(1);   
               discomList.add(discom);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return discomList;
    }
    
    public List<String> getEquipmentList(){
        List<String> list=new ArrayList<String>();
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="SELECT DISTINCT EQUIPMENT_NAME FROM XX_APPLIANCE_WATTAGES ORDER BY EQUIPMENT_NAME";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            rs=pstmt.executeQuery();
            while(rs.next()){
               String value=rs.getString(1);   
               list.add(value);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return list;
    }
    
    public List<String> getWattageByEquipment(String equipmentName){
        List<String> list=new ArrayList<String>();
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="SELECT DISTINCT EQUIPMENT_POWER_WATTAGE FROM XX_APPLIANCE_WATTAGES WHERE EQUIPMENT_NAME = '" + equipmentName + "' ORDER BY EQUIPMENT_POWER_WATTAGE";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            rs=pstmt.executeQuery();
            while(rs.next()){
               String value=rs.getString(1);   
               list.add(value);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return list;
    }
    
    public boolean addAccounts(String parentAccountNo,String linkedAccountNo){
        
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="INSERT INTO XX_MANAGE_ACCOUNTS (PARENT_ACC_NO,LINKED_ACC_NO) values(?,?)";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1,parentAccountNo);
            pstmt.setString(2, linkedAccountNo);
            int i=pstmt.executeUpdate();           
            if(i==1){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return false;
    }
    
    public boolean removeAccount(String parentAccountNo,String linkedAccountNo){
        
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="DELETE FROM XX_MANAGE_ACCOUNTS where PARENT_ACC_NO=? and LINKED_ACC_NO=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1,parentAccountNo);
            pstmt.setString(2, linkedAccountNo);
            int i=pstmt.executeUpdate();           
            if(i==1){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return false;
    }
    
    public boolean addAttachments(String attachmentId, String parentAccountNo, String linkedAccountNo, String fileName, String fileExt, String docType, String discom){
        
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="INSERT INTO XX_UPPCL_ATTACHMENT_MANAGEMENT (ATTACHMENT_ID, PRIMARY_ACC_ID, SEC_ACC_ID, FILE_NAME, FILE_EXT, DOC_TYPE, DISCOM) values(?,?,?,?,?,?,?)";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1,attachmentId);
            pstmt.setString(2, parentAccountNo);
            pstmt.setString(3, linkedAccountNo);
            pstmt.setString(4, fileName);
            pstmt.setString(5, fileExt);
            pstmt.setString(6, docType);
            pstmt.setString(7, discom);
            int i=pstmt.executeUpdate();           
            if(i==1){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return false;
    }
    
    public String getSubscriptionStatus(String accountNo, String discom){
       
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String subscriptionStatus = null;
        try{      
            //todo for table update
            String query="select WHATSAPP_SUBSCRIBED from XX_USER_ACCOUNTS where account_number=? and discom=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, accountNo);
            pstmt.setString(2, discom);
            rs=pstmt.executeQuery();
            while(rs.next()){
                subscriptionStatus =rs.getString(1);
            }          
        }

        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }

        return subscriptionStatus;
    }
    
    public boolean updateSubscriptionStatus(String accountNo, String trackId, String servConNum, String subsFlag, String createdDate, String discom){
        
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="update  XX_USER_ACCOUNTS set WHATSAPP_SUBSCRIBED=? where account_number=? and discom=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");      
            conn=ds.getConnection();
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, subsFlag);
            pstmt.setString(2, accountNo);
            pstmt.setString(3, discom);
            int i=pstmt.executeUpdate();           
            if(i==1){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return false;
    }
    
    public boolean updateProfileDetails(String accountNo, String mobileNum, String email, String discom){
        
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="update  XX_USER_ACCOUNTS set MOBILE_NUMBER=? , EMAIL=? where account_number=? and discom=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");      
            conn=ds.getConnection();
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, mobileNum);
            pstmt.setString(2, email);
            pstmt.setString(3, accountNo);
            pstmt.setString(4, discom);
            int i=pstmt.executeUpdate();           
            if(i==1){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return false;
    }
    
    public Date getLastLogin(String actNumber, String discom){

        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Date value=null;
        try{                   
            String query="select LAST_LOGIN_TIME from XX_USER_ACCOUNTS where ACCOUNT_NUMBER=? and discom=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, actNumber);
            pstmt.setString(2, discom);
            System.out.println("Final Query --> "+pstmt);
            rs=pstmt.executeQuery();
            System.out.println("resultset --> "+rs);
            if(rs.next()){
                System.out.println("last login date --> "+rs.getTimestamp(1));
                value= rs.getTimestamp(1);         
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return value;
    } 
    
    public boolean updateLastLogin(String accountNo, String discom){
        System.out.println("Inside updateLastLogin :: starts");
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="update XX_USER_ACCOUNTS set LAST_LOGIN_TIME= sysdate where ACCOUNT_NUMBER= ? and discom=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");      
            conn=ds.getConnection();
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, accountNo);
            pstmt.setString(2, discom);
            System.out.println("Final Query --> "+pstmt.toString());
            int i=pstmt.executeUpdate();           
            if(i==1){
                System.out.println("If update done");
                return true;
            }
            else{
                return false;
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        System.out.println("Inside updateLastLogin :: ends");
        return false;
    }
    
    public boolean updateProfilePhoto(String accountNo, String fileName, String fileContent, String discom){
        System.out.println("Inside updateProfilePhoto :: starts");
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{ 
            String query="update XX_USER_ACCOUNTS set PROFILE_PIC_NAME= ? , PROFILE_PIC_CONTENT= ? where account_number= ? and discom=?";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");      
            conn=ds.getConnection();
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, fileName);
            
            System.out.println("FileContent --> "+fileContent);
            oracle.sql.BLOB myBlob = oracle.sql.BLOB.createTemporary(conn, false,oracle.sql.BLOB.DURATION_SESSION);
             byte[] buff = fileContent.getBytes();
             myBlob.putBytes(1,buff);
            pstmt.setBlob(2, myBlob);
            
            System.out.println("Blob --> "+myBlob);
            pstmt.setString(3, accountNo);
            pstmt.setString(4, discom);
            System.out.println("Final Query --> "+pstmt.toString());
            int i=pstmt.executeUpdate();           
            if(i==1){
                System.out.println("If update done");
                return true;
            }
            else{
                return false;
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        System.out.println("Inside updateProfilePhoto :: ends");
        return false;
    }
    
    public boolean captureFeedback(String accountNumber,String name,String serConNum,String discom,String email,String phone,String address,String city, String state, String pin, String comments){
        
        boolean flag=false;
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{                   
            String query="INSERT INTO XX_USER_FEEDBACK (ACCOUNT_NUM,NAME,SERV_CONN_NUM,DISCOM,EMAIL_ID,PHONE_NUM,ADDRESS,CITY,STATE,PIN_CODE, COMMENTS) values(?,?,?,?,?,?,?,?,?,?,?)";
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/UPPCLDS");            
            conn=ds.getConnection();  
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, name);
            pstmt.setString(3, serConNum);
            pstmt.setString(4, discom);
            pstmt.setString(5, email);
            pstmt.setString(6, phone);
            pstmt.setString(7, address);
            pstmt.setString(8, city);
            pstmt.setString(9, state);
            pstmt.setString(10, pin);
            pstmt.setString(11, comments);
            int i=pstmt.executeUpdate();           
            if(i==1){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeDBResources(conn,pstmt,rs);
        }
        return false;
    }
    
    public String convertBlobToStr(Blob blobStr) throws SQLException, IOException {
        byte[] bdata = blobStr.getBytes(1, (int) blobStr.length());
        String text = new String(bdata);
        
        return text;
    }
    
}
