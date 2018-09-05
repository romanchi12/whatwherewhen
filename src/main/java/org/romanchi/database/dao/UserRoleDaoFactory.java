/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.DatabaseUtils;
import org.romanchi.database.dao.mysql.UserRoleDaoImpl;

import javax.sql.DataSource;

/**
 *
 * @author Роман
 */
public class UserRoleDaoFactory {

    public static UserRoleDao create(){
        return new UserRoleDaoImpl(DatabaseUtils.getDataSource());
    }

    public static UserRoleDao create(DataSource dataSource){
        return new UserRoleDaoImpl(dataSource);
    }
}
