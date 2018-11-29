/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rc.dao.impl.resultsetextracter;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Admin
 */
public class OneValueResultExtracter implements ResultSetExtractor<String>{

    @Override
    public String extractData(ResultSet rs) throws SQLException, DataAccessException {
    String Result = null;
    while (rs.next()){
    Result = rs.getString(1);
            }
    
    return Result;
    }
    
    
    
    
    
}
