/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.DatabaseUtils;
import org.romanchi.database.dao.mysql.QuestionDaoImpl;

import javax.sql.DataSource;

/**
 *
 * @author Роман
 */
public class QuestionDaoFactory {

    public static QuestionDao create(){
        return new QuestionDaoImpl(DatabaseUtils.getDataSource());
    }

    public static QuestionDao create(DataSource dataSource){
        return new QuestionDaoImpl(dataSource);
    }
}
