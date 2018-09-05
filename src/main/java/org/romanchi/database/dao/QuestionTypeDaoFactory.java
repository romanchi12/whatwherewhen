/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.DatabaseUtils;
import org.romanchi.database.dao.mysql.QuestionTypeDaoImpl;

import javax.sql.DataSource;

/**
 *
 * @author Роман
 */
public class QuestionTypeDaoFactory {

    public static QuestionTypeDao create(){
        return new QuestionTypeDaoImpl(DatabaseUtils.getDataSource());
    }

    public static QuestionTypeDao create(DataSource dataSource){
        return new QuestionTypeDaoImpl(dataSource);
    }
}
