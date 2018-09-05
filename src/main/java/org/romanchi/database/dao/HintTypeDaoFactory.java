/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.DatabaseUtils;
import org.romanchi.database.dao.mysql.HintTypeDaoImpl;

import javax.sql.DataSource;

/**
 *
 * @author Roman
 */
public class HintTypeDaoFactory {

    public static HintTypeDao create(){
        return new HintTypeDaoImpl(DatabaseUtils.getDataSource());
    }

    public static HintTypeDao create(DataSource dataSource){
        return new HintTypeDaoImpl(dataSource);
    }
}
