/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rc.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Admin
 */
public class IntegerValueRowMapper implements RowMapper<Integer>{

    @Override
    public Integer mapRow(ResultSet rs, int i) throws SQLException {
        int msisdn = rs.getInt(1);
return msisdn;
    }
    
}
