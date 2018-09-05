/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.DatabaseUtils;
import org.romanchi.database.dao.mysql.TeamDaoImpl;

import javax.sql.DataSource;

/**
 *
 * @author Роман
 */
public class TeamDaoFactory {
    public static TeamDao create(){
        return new TeamDaoImpl(DatabaseUtils.getDataSource());
    }
    public static TeamDao create(DataSource dataSource){
        return new TeamDaoImpl(dataSource);
    }
}
