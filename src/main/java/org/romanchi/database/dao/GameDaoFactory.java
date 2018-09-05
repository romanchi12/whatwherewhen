/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.DatabaseUtils;
import org.romanchi.database.dao.mysql.GameDaoImpl;

import javax.sql.DataSource;

/**
 *
 * @author Роман
 */
public class GameDaoFactory {

    public static GameDao create(){
        return new GameDaoImpl(DatabaseUtils.getDataSource());
    }

    public static GameDao create(DataSource dataSource){
        return new GameDaoImpl(dataSource);
    }
}
