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

import org.romanchi.database.dao.HintDao;
import org.romanchi.database.dao.mysql.Columns;
import org.romanchi.database.entities.Hint;
import org.romanchi.database.entities.HintType;
import org.romanchi.database.entities.Question;
import org.romanchi.database.entities.QuestionType;
import org.romanchi.database.entities.User;


/**
 *
 * @author Roman
 */
public class HintDaoImpl implements HintDao {
    
    private final String SQL_SELECT = "SELECT " + Columns.HINT_HINT_ID + ", " + Columns.HINT_HINT_TYPE_ID + ", " + Columns.HINT_QUESTION_ID + ", " + Columns.HINT_HINT_DESCRIPTION + ", " + Columns.HINTTYPE_HINT_TYPE_NAME + ", " + Columns.QUESTION_QUESTION_TYPE_ID + ", " + Columns.QUESTION_USER_ID + ", " + Columns.QUESTION_QUESTION_NAME + ", " + Columns.QUESTION_QUESTION_DESCRIPTION + ", " + Columns.QUESTION_RIGHT_ANSWER + " FROM " + getTable() + " INNER JOIN Question ON " + Columns.HINT_QUESTION_ID +"=" + Columns.QUESTION_QUESTION_ID + " INNER JOIN HintType ON " + Columns.HINT_HINT_TYPE_ID + "=" + Columns.HINTTYPE_HINT_TYPE_ID +"";
    private final String SQL_INSERT = "INSERT INTO " + getTable() + "(" + Columns.HINT_HINT_TYPE_ID + ", " + Columns.HINT_QUESTION_ID + ", " + Columns.HINT_HINT_DESCRIPTION + ") VALUES(?,?,?)";
    private final String SQL_UPDATE = "UPDATE " + getTable() +" SET " + Columns.HINT_HINT_TYPE_ID + "=?, " + Columns.HINT_QUESTION_ID + "=?, " + Columns.HINT_HINT_DESCRIPTION + "=? WHERE " + Columns.HINT_HINT_ID + "=?";
    private final String SQL_DELETE = "DELETE FROM " + getTable() + " WHERE " + Columns.HINT_HINT_ID + "=?";
    private DataSource dataSource;

    public HintDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public Hint[] findAll() throws Exception {
        return findByDynamicSelect(SQL_SELECT, null);
    }

    @Override
    public Hint findWhereHintIdEquals(long hintId) throws Exception {
        return findSingleByDynamicSelect(SQL_SELECT + " WHERE " + Columns.HINT_HINT_ID + "=? ORDER BY " + Columns.HINT_HINT_ID, new Object[]{hintId});
    }

    @Override
    public Hint[] findWhereHintTypeIdEquals(long hintTypeId) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.HINT_HINT_TYPE_ID + "=? ORDER BY " + Columns.HINT_HINT_ID, new Object[]{hintTypeId});
    }

    @Override
    public Hint[] findWhereQuestionIdEquals(long questionId) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.HINT_QUESTION_ID + "=? ORDER BY " + Columns.HINT_HINT_ID, new Object[]{questionId});
    }

    @Override
    public int update(Hint newHint) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setLong(1, newHint.getHintType().getHintTypeId());
            preparedStatement.setLong(2, newHint.getQuestion().getQuestionId());
            preparedStatement.setString(3, newHint.getHintDescription());
            preparedStatement.setLong(4, newHint.getHintId());
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
    public long insert(Hint newHint) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        long insertedId = -1;
        try{
            preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, newHint.getHintType().getHintTypeId());
            preparedStatement.setLong(2, newHint.getQuestion().getQuestionId());
            preparedStatement.setString(3, newHint.getHintDescription());
            preparedStatement.setLong(4, newHint.getHintId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                insertedId = resultSet.getLong(1);
            }
            newHint.setHintId(insertedId);
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
    public int delete(Hint hintToDelete) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, hintToDelete.getHintId());
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

    @Override
    public int deleteAllWhereHintTypeIdEquals(long hintTypeId) throws Exception {
        Hint[] hintsToDelete = findWhereHintTypeIdEquals(hintTypeId);
        int affectedRows = 0;
        for(Hint hintToDelete:hintsToDelete){
            affectedRows += delete(hintToDelete);
        }
        return affectedRows;
    }
    
    public Hint findSingleByDynamicSelect(String SQL, Object[] params) throws Exception{
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
    public Hint[] findByDynamicSelect(String sql, Object[] params) throws Exception{
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
    
    private Hint[] fetchMultiResults(ResultSet resultSet) throws Exception {
        Collection<Hint> hints = new ArrayList<>();
        while(resultSet.next()){
            Hint dto = new Hint();
            populateDto(dto, resultSet);
            hints.add(dto);
        }
        
        Hint[] hintsArray = new Hint[hints.size()];
        hints.toArray(hintsArray);
        return hintsArray;
    }
    private Hint fetchSingleResult(ResultSet resultSet) throws Exception{
        if(resultSet.next()){
            Hint dto = new Hint();
            populateDto(dto, resultSet);
            return dto;
        }
        return null;
    }

    private void populateDto(Hint dto, ResultSet resultSet) throws Exception{
        dto.setHintId(resultSet.getLong(Columns.HINT_HINT_ID));
        dto.setHintDescription(resultSet.getString(Columns.HINT_HINT_DESCRIPTION));
            HintType hintType = new HintType();
            hintType.setHintTypeId(resultSet.getLong(Columns.HINT_HINT_TYPE_ID));
            hintType.setHintTypeName(resultSet.getString(Columns.HINTTYPE_HINT_TYPE_NAME));
        dto.setHintType(hintType);
            Question question = new Question();
            question.setQuestionId(resultSet.getLong(Columns.HINT_QUESTION_ID));
            question.setQuestionName(resultSet.getString(Columns.QUESTION_QUESTION_NAME));
            question.setRightAnswer(resultSet.getString(Columns.QUESTION_RIGHT_ANSWER));
                QuestionType questionType = new QuestionType();
                questionType.setQuestionTypeId(resultSet.getLong(Columns.QUESTION_QUESTION_TYPE_ID));
            question.setQuestionType(questionType);
            question.setQuestionDescription(resultSet.getString(Columns.QUESTION_QUESTION_DESCRIPTION));
                User user = new User();
                user.setUserId(resultSet.getLong(Columns.QUESTION_USER_ID));
            question.setUser(user);
        dto.setQuestion(question);
    }
    
    public String getTable(){
        return "Hint";
    }
    
    
}
