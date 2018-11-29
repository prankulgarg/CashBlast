/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rc.dao.impl;

import com.rc.model.UserDetail;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import com.rc.dao.impl.resultsetextracter.OneValueResultExtracter;
import com.rc.dao.impl.rowmapper.IntegerValueRowMapper;
import com.rc.model.GenericResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.rc.dao.GameServiceDao;

/**
 *
 * @author Thakur Ravi Chauhan
 */
//@Repository
public class GameServiceDaoImpl implements GameServiceDao {

    private final JdbcTemplate jdbcTemplate;

    public GameServiceDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int registerUser(UserDetail userDetail) {
        GenericResponse genericResponse = new GenericResponse();
        int update = 0;
        try {
            String member_sql = "INSERT INTO bal_user_profile(firstName,lastName,msisdn,registerDate,status) "
                    + " VALUES(?,?,?,?,?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            update = jdbcTemplate.update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement pst = con.prepareStatement(member_sql, new String[]{"id"});
                    // pst.setInt(1,memberDetail.getEmployeeId());
                    pst.setString(1, userDetail.getFirstName());
                    pst.setString(2, userDetail.getLastName());
                    pst.setString(3, userDetail.getMsisdn());
                    pst.setString(4, new Timestamp(System.currentTimeMillis()).toString());
                    pst.setString(5, "R");

                    return pst;
                }
            },
                    keyHolder);
            Number key = keyHolder.getKey();

            System.out.println("Key ====>" + key);

        } catch (Exception e) {

         //   genericResponse.setErrormsg(e.getMessage());

            genericResponse.setMessage("Couldn't add Member application server error");
            genericResponse.setStatus("fail");
            genericResponse.setStatusCode(-1);
            e.printStackTrace();

        }

        return update;

    }

    @Override
    public GenericResponse DeRegisteredDriver(UserDetail userDetail) {
        GenericResponse genericResponse = new GenericResponse();
        try {
            int update = jdbcTemplate.update("UPDATE `celltickappdb`.`bal_user_profile` SET   `status` = 'D' WHERE `msisdn` = " + userDetail.getMsisdn() + "");
            if (update > 0) {

                genericResponse.setMessage("driver DeRegistred Succefully  succesfully");
                genericResponse.setStatus("success");
                genericResponse.setStatusCode(0);
            } else {
                genericResponse.setMessage("Couldn't Deregistred.");
                genericResponse.setStatus("fail");
                genericResponse.setStatusCode(1);

            }

        } catch (Exception e) {

//            genericResponse.setErrormsg(e.getMessage());

            genericResponse.setMessage("Couldn't Deregistred application server error");
            genericResponse.setStatus("fail");
            genericResponse.setStatusCode(-1);
            e.printStackTrace();

        }

        return genericResponse;

    }

    @Override
    public String getDriverStatus(UserDetail userDetail) {

        String consent = jdbcTemplate.query("SELECT `status` FROM `bal_user_profile` WHERE `msisdn` = " + userDetail.getMsisdn() + "", new OneValueResultExtracter());

        return consent;

    }

    @Override
    public int setConsentStatus(String consentStatus, int msisdn) {
      int update = 0;
        try{
            System.out.println(new Timestamp(System.currentTimeMillis()).toString());
                    
         update = jdbcTemplate.update("UPDATE `bal_user_profile` SET `consentStatus` = '" + consentStatus + "' , lastConsentCheck = '" + new Timestamp(System.currentTimeMillis()).toString() + "' WHERE msisdn = '" + msisdn + "'");
       }
       catch (Exception e)
       {
           e.printStackTrace();
        
    }
        return update;
    }

    @Override
    public int getConsetntIntervalConfig() {
        String counter = jdbcTemplate.query("SELECT FLOOR(`maxAllowedValue`/`intervalValue`) AS counter FROM `bal_config` WHERE `paramName` = 'CONSENT_TIMER'", new OneValueResultExtracter());
        return counter != null ? Integer.parseInt(counter) : 0;
    }

    @Override
    public int setConsentCounter(int consentConfigValue, int msisdnNo) {
        int update = jdbcTemplate.update("UPDATE `bal_user_profile` SET `noOfConsentCheck` = " + consentConfigValue + " WHERE `msisdn` = '" + msisdnNo + "'");
        return update;
    }

    @Override
    public List<Integer> getPendeingConsent() {
        List<Integer> pendingConsentList = jdbcTemplate.query("SELECT `msisdn` FROM `bal_user_profile` WHERE `consentStatus`= 'P'", new IntegerValueRowMapper());

        return pendingConsentList;
    }

    @Override
    public String geturlForConsent() {
    String consentURl = jdbcTemplate.query("SELECT `celltickUrl` FROM `bal_url_config` WHERE `parameterName` = 'CONSENT_SCRIPT'", new OneValueResultExtracter());
   return consentURl;
    }

}
