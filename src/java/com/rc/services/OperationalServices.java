/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rc.services;

import com.google.gson.Gson;
import com.rc.model.Consent;
import com.rc.model.ConsentRequest;
import com.rc.model.GenericResponse;
import com.rc.model.Location;
import com.rc.model.UserDetail;
import com.rc.model.UserMSISDN;
import com.rc.utill.ScriptExecuter;
import java.io.FileNotFoundException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.rc.dao.GameServiceDao;

/**
 *
 * @author Admin
 */
@Service
public class OperationalServices {

    public void schedulePayment() {

        System.out.println("qwertyuio");
    }
    @Autowired
    public GameServiceDao celtickService;
    @Autowired
    public CsvOperationServices csvOperationServices;

    public GenericResponse registerUser(UserDetail userDetail) throws Exception {
        GenericResponse genericResponse = new GenericResponse();
        // int update = celtickService.registerUser(userDetail);
        try {
            String[] checkFlag = new String[10];
            checkFlag = csvOperationServices.readDataFromFile(userDetail.getMsisdn());

            if (checkFlag[1] != null) {
                csvOperationServices.updateCSV(userDetail.getMsisdn(), "R");

//  String[] data={number,firstName,lastName,status,registrationDate,deActiveDate};
                userDetail.setMsisdn(checkFlag[0]);
                userDetail.setFirstName(checkFlag[1]);
                userDetail.setLastName(checkFlag[2]);
                System.out.println("inside driver already registered");
                genericResponse.setUserInfo(userDetail);
                    genericResponse.setMessage("driver added succesfully");
                    genericResponse.setStatus("success");
                    genericResponse.setStatusCode(0);
                  String consentStatus = null;
                String cmd = null;
                cmd = "/home/prankul/consent_script.sh";
// script calling
//7 pending 
//8 not allowed \
// 9 allowed
                ScriptExecuter.ExecResult execCmd = ScriptExecuter.execCmd(cmd, String.valueOf(userDetail.getMsisdn()), 0);
                System.out.println(execCmd.getLines().get(0));

                Gson gson = new Gson();
                Consent fromJson = gson.fromJson(execCmd.getLines().get(0), Consent.class);
                System.out.println(fromJson.getMsisdn() + "<===Status==>" + fromJson.getConsent().getStatus());;
                consentStatus = fromJson.getConsent().getStatus();
                boolean consentFlag = csvOperationServices.updateConsent(userDetail.getMsisdn(), consentStatus,new Timestamp(System.currentTimeMillis()).toString() );
            } else {
                System.out.println("inside new registration");
                boolean succesFlag = csvOperationServices.saveDataToFile(userDetail.getMsisdn(), "R", new Timestamp(System.currentTimeMillis()).toString(), null, userDetail.getFirstName(), userDetail.getLastName());
                System.out.println("success flag value " + succesFlag);
                String consentStatus = null;
                String cmd = null;
                cmd = "/home/prankul/consent_script.sh";
// script calling
//7 pending 
//8 not allowed \
// 9 allowed
                ScriptExecuter.ExecResult execCmd = ScriptExecuter.execCmd(cmd, String.valueOf(userDetail.getMsisdn()), 0);
                System.out.println(execCmd.getLines().get(0));

                Gson gson = new Gson();
                Consent fromJson = gson.fromJson(execCmd.getLines().get(0), Consent.class);
                System.out.println(fromJson.getMsisdn() + "<===Status==>" + fromJson.getConsent().getStatus());;
                consentStatus = fromJson.getConsent().getStatus();
                boolean consentFlag = csvOperationServices.saveConsentRequest(userDetail.getMsisdn(), consentStatus, new Timestamp(System.currentTimeMillis()).toString());
                System.out.println("conseng flag value " + consentFlag);
                if (!succesFlag && !consentFlag) {
                    // userDetail.setConsentStatus(consentStatus);
                    genericResponse.setUserInfo(userDetail);
                    genericResponse.setMessage("driver added succesfully");
                    genericResponse.setStatus("success");
                    genericResponse.setStatusCode(0);
                } else {
                    genericResponse.setMessage("Couldn't add driver.");
                    genericResponse.setStatus("fail");
                    genericResponse.setStatusCode(1);

                }
            }
        } catch (FileNotFoundException fr) {
            fr.printStackTrace();
            genericResponse.setMessage("Couldn't add file Not Found.");
            genericResponse.setStatus("fail");
            genericResponse.setStatusCode(-1);

        } catch (Exception e) {
            e.printStackTrace();
            genericResponse.setMessage("Couldn't add application error.");
            genericResponse.setStatus("fail");
            genericResponse.setStatusCode(-1);

        }
        return genericResponse;

    }

    public GenericResponse DeRegisteredDriver(UserDetail userDetail) {
        GenericResponse genericResponse = new GenericResponse();
        try {
            //boolean result = csvOperationServices.updateCSV(userDetail.getMsisdn(), "D");
         //   boolean deleteResult = csvOperationServices.deleteLine(userDetail.getMsisdn());
          
            
            boolean checkFlag =  csvOperationServices.removeLineFromFile(userDetail.getMsisdn());
if (checkFlag){
                UserMSISDN userMSISDN = new UserMSISDN();
                userMSISDN.setMsisdn(userDetail.getMsisdn());
                genericResponse.setUserInfo(userMSISDN);
                genericResponse.setMessage("driver De Registred  succesfully");
                genericResponse.setStatus("success");
                genericResponse.setStatusCode(0);
}
else {
                genericResponse.setMessage("msisdn Not Found");
                genericResponse.setStatus("Fail");
                genericResponse.setStatusCode(1);
    
    
    
}


        } catch (Exception e) {
            e.printStackTrace();
            genericResponse.setMessage("Couldn't De registered application error.");
            genericResponse.setStatus("fail");
            genericResponse.setStatusCode(-1);

        }

        return genericResponse;
    }

    public GenericResponse consentRequest(UserDetail userDetail) throws Exception {

        GenericResponse genericResponse = new GenericResponse();
        ConsentRequest consentRequest = new ConsentRequest();
String[] userStatus = new String[10];
        try {
            userStatus = csvOperationServices.readDataFromFile(userDetail.getMsisdn());

            String[] Status = csvOperationServices.readConsentStatus(userDetail.getMsisdn());
            String consentStatus = null;
// String status = celtickService.getDriverStatus(userDetail);
            if (userStatus[3]==null) {
                genericResponse.setMessage("Driver is Not Registered ");
                genericResponse.setStatus("fail");
                genericResponse.setStatusCode(0);
            } else {
                if (Status[1] != null) {
                    consentStatus = Status[1];
                    consentRequest.setMsisdn(userDetail.getMsisdn());
              consentRequest.setConsentStatus(consentStatus);
              genericResponse.setUserInfo(consentRequest);
               genericResponse.setMessage("Data Retrive Succefully");
                genericResponse.setStatus("success");
                genericResponse.setStatusCode(0);
                }
              
            }
        } catch (FileNotFoundException fr) {
            fr.printStackTrace();
            genericResponse.setMessage("Couldn't Retrive file Not Found.");
            genericResponse.setStatus("fail");
            genericResponse.setStatusCode(-1);

        } catch (Exception e) {
            e.printStackTrace();
            genericResponse.setMessage("Couldn't Retrive  due to application error ");
            genericResponse.setStatus("fail");
            genericResponse.setStatusCode(-1);

        }
        return genericResponse;
    }

    public GenericResponse getLocation(UserDetail userDetail) {
        GenericResponse genericResponse = new GenericResponse();
        Location location = new Location();
        try {
            String[] locationData = new String[15];
            String[] userStatus = new String[10];

            userStatus = csvOperationServices.readDataFromFile(userDetail.getMsisdn());
if (userStatus[3]==null) {
                genericResponse.setMessage("Driver is Not Registered ");
                genericResponse.setStatus("fail");
                genericResponse.setStatusCode(1);
            } else {
            
            
            
            locationData = csvOperationServices.getLocationFromFile(userDetail.getMsisdn());
          
            if (locationData!=null){
            location.setMsisdn(locationData[0]);
            location.setPositionTime(locationData[1]);
            location.setResourceName(locationData[2]);
            location.setLatitude(locationData[3]);
            location.setLongitude(locationData[4]);
            location.setAddress(locationData[5]);
            }
            if (location.getMsisdn() != null) {
                // int consentConfigValue = celtickService.getConsetntIntervalConfig();
                // int consentUpdate = celtickService.setConsentCounter(consentConfigValue, userDetail.getMsisdnNo());
                genericResponse.setUserInfo(location);
                genericResponse.setMessage("Data Retrive Succefully ");
                genericResponse.setStatus("success");
                genericResponse.setStatusCode(0);
            } else {
                genericResponse.setMessage(" No Data  to Retrive  ");
                genericResponse.setStatus("fail");
                genericResponse.setStatusCode(1);
            }
}
        } catch (Exception e) {
            e.printStackTrace();
            genericResponse.setMessage("file not found");

            genericResponse.setStatus("fail");
            genericResponse.setStatusCode(-1);

        }

        return genericResponse;

    }
}
