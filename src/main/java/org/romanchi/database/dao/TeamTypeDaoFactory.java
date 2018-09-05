/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;
import org.romanchi.database.DatabaseUtils;
import org.romanchi.database.dao.mysql.TeamTypeDaoImpl;

import javax.sql.DataSource;
/**
 *
 * @author Роман
 */
public class TeamTypeDaoFactory {
    public static TeamTypeDao create(){
        return new TeamTypeDaoImpl(DatabaseUtils.getDataSource());
    }
    public static TeamTypeDao create(DataSource dataSource){
        return new TeamTypeDaoImpl(dataSource);
    }
}
