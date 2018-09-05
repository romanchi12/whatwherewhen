/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.DatabaseUtils;
import org.romanchi.database.dao.mysql.UserDaoImpl;

import javax.sql.DataSource;

/**
 *
 * @author Роман
 */
public class UserDaoFactory {
    public static UserDao create(){
        return new UserDaoImpl(DatabaseUtils.getDataSource());
    }
    public static UserDao create(DataSource dataSource){
        return new UserDaoImpl(dataSource);
    }
}
