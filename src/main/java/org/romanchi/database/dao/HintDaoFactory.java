/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.DatabaseUtils;
import org.romanchi.database.dao.mysql.HintDaoImpl;

import javax.sql.DataSource;

/**
 *
 * @author Roman
 */
public class HintDaoFactory {

    public static HintDao create(){
        return new HintDaoImpl(DatabaseUtils.getDataSource());
    }

    public static HintDao create(DataSource dataSource){
        return new HintDaoImpl(dataSource);
    }
}
