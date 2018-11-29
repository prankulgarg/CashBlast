/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rc.dao;

import com.rc.model.GenericResponse;
import com.rc.model.UserDetail;
import java.util.List;

/**
 *
 * @author Thakur Ravi Chauhan
 */
public interface GameServiceDao {
    public int registerUser(UserDetail userDetail);

    public GenericResponse DeRegisteredDriver(UserDetail userDetail);

    public String getDriverStatus(UserDetail userDetail);

    public int setConsentStatus(String consentStaus, int msisdn);

    public int getConsetntIntervalConfig();

    public int setConsentCounter(int consentConfigValue, int msisdnNo);

    public List<Integer> getPendeingConsent();

    public String geturlForConsent();
    
}
