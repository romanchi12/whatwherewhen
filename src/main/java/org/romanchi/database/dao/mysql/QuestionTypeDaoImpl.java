/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.sql.DataSource;

import org.romanchi.database.dao.QuestionTypeDao;
import org.romanchi.database.dao.mysql.Columns;
import org.romanchi.database.entities.QuestionType;

/**
 *
 * @author Роман
 */
public class QuestionTypeDaoImpl implements QuestionTypeDao {

    
   
    
    private final String SQL_SELECT = "SELECT " + Columns.QUESTIONTYPE_QUESTION_TYPE_ID + ", " + Columns.QUESTIONTYPE_QUESTION_TYPE_NAME + " FROM " + getTable();
    private final String SQL_INSERT = "INSERT INTO " + getTable() + "(" + Columns.QUESTIONTYPE_QUESTION_TYPE_NAME + ") VALUES(?)";
    private final String SQL_UPDATE = "UPDATE " + getTable() +" SET " + Columns.QUESTIONTYPE_QUESTION_TYPE_NAME + "=? WHERE " + Columns.QUESTIONTYPE_QUESTION_TYPE_ID + "=?";
    private final String SQL_DELETE = "DELETE FROM " + getTable() + " WHERE " + Columns.QUESTIONTYPE_QUESTION_TYPE_ID + "=?";
    private DataSource dataSource;

    public QuestionTypeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public QuestionType[] findAll() throws Exception {
        return findByDynamicSelect(SQL_SELECT, null);
    }

    @Override
    public QuestionType findWhereQuestionTypeIdEquals(long questionTypeId) throws Exception {
        return findSingleByDynamicSelect(SQL_SELECT + " WHERE " + Columns.QUESTIONTYPE_QUESTION_TYPE_ID + "=? ORDER BY " + Columns.QUESTIONTYPE_QUESTION_TYPE_ID, new Object[]{questionTypeId});
    }

    @Override
    public QuestionType[] findWhereQuestionTypeNameEquals(String questionTypeName) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.QUESTIONTYPE_QUESTION_TYPE_NAME + "=? ORDER BY " + Columns.QUESTIONTYPE_QUESTION_TYPE_ID, new Object[]{questionTypeName});
    }

    @Override
    public int update(QuestionType newQuestionType) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, newQuestionType.getQuestionTypeName());
            preparedStatement.setLong(2, newQuestionType.getQuestionTypeId());
            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();
            return affectedRows;
        }catch(SQLException exception){
            exception.printStackTrace();
            if(connection != null){
                try{
                    connection.rollback();
                }catch(SQLException sqlException){
                    sqlException.printStackTrace();
                }
            }
            return -1;
        }finally{
            if(connection != null){
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public long insert(QuestionType newQuestionType) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        long insertedId = -1;
        try{
            preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newQuestionType.getQuestionTypeName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                insertedId = resultSet.getLong(1);
            }
            newQuestionType.setQuestionTypeId(insertedId);
            connection.commit();
            return insertedId;
        }catch(SQLException exception){
            exception.printStackTrace();
            if(connection != null){
                try{
                    connection.rollback();
                }catch(SQLException exception2){
                   exception2.printStackTrace();
                }
            }
            return insertedId;
        }finally{
            if(connection != null){
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public int delete(QuestionType questionTypeToDelete) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, questionTypeToDelete.getQuestionTypeId());
            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();
            return affectedRows;
        }catch(Exception exception){
            try{
                if(connection != null){
                    connection.rollback();
                }
            }catch(Exception exception2){
                exception2.printStackTrace();
            }
            return -1;
        }finally{
            if(connection != null){
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
    
    public QuestionType findSingleByDynamicSelect(String SQL, Object[] params) throws Exception{
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(SQL);
            if(params != null){
                for(int i = 1; i <= params.length; i++){
                    preparedStatement.setObject(i,params[i-1]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            return fetchSingleResult(resultSet);
        }catch(Exception exception){
            throw new Exception(exception);
        }finally{
            if(connection != null){
                connection.close();
            }
        }
    }
    public QuestionType[] findByDynamicSelect(String sql, Object[] params) throws Exception{
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(sql);
            if(params != null){
                for(int i = 1; i <= params.length; i++){
                    preparedStatement.setObject(i, params[i-1]);
                }
            }
            resultSet = preparedStatement.executeQuery();
           
            return fetchMultiResults(resultSet);
        }catch(Exception exception){
            //logger.error(ex, ex);
            exception.printStackTrace();
            throw new Exception(exception);
        }finally{
            if(connection != null){
                connection.close();
            }
        }   
    }
    
    private QuestionType[] fetchMultiResults(ResultSet resultSet) throws Exception {
        Collection<QuestionType> questionTypes = new ArrayList<>();
        while(resultSet.next()){
            QuestionType dto = new QuestionType();
            populateDto(dto, resultSet);
            questionTypes.add(dto);
        }
        
        QuestionType[] questionTypesArray = new QuestionType[questionTypes.size()];
        questionTypes.toArray(questionTypesArray);
        return questionTypesArray;
    }
    private QuestionType fetchSingleResult(ResultSet resultSet) throws Exception{
        if(resultSet.next()){
            QuestionType dto = new QuestionType();
            populateDto(dto, resultSet);
            return dto;
        }
        return null;
    }

    private void populateDto(QuestionType dto, ResultSet resultSet) throws Exception{
        dto.setQuestionTypeId(resultSet.getLong(Columns.QUESTIONTYPE_QUESTION_TYPE_ID));
        dto.setQuestionTypeName(resultSet.getString(Columns.QUESTIONTYPE_QUESTION_TYPE_NAME));
    }
    
    public String getTable(){
        return "QuestionType";
    }
}
