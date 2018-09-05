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
import java.util.logging.Logger;
import javax.sql.DataSource;

import org.romanchi.database.dao.QuestionDao;
import org.romanchi.database.entities.Question;
import org.romanchi.database.entities.QuestionType;
import org.romanchi.database.entities.Team;
import org.romanchi.database.entities.User;
import org.romanchi.database.entities.UserRole;

/**
 *
 * @author Роман
 */
public class QuestionDaoImpl implements QuestionDao {
    private final static Logger logger = Logger.getLogger(QuestionDaoImpl.class.getName());
    private final String SQL_SELECT = "SELECT " + Columns.QUESTION_QUESTION_ID + ", " + Columns.QUESTION_QUESTION_TYPE_ID + ", " + Columns.QUESTION_USER_ID + ", " + Columns.QUESTION_QUESTION_NAME + ", " + Columns.QUESTION_QUESTION_DESCRIPTION + ", " + Columns.QUESTION_RIGHT_ANSWER +", " + Columns.QUESTIONTYPE_QUESTION_TYPE_NAME + ", " + Columns.USER_USER_ROLE_ID + ", " + Columns.USER_TEAM_ID + ", " + Columns.USER_USER_LOGIN + ", " + Columns.USER_USER_PASSWORD + ", " + Columns.USER_IS_CAPTAIN + " FROM " + getTable() + " INNER JOIN QuestionType ON " + Columns.QUESTION_QUESTION_TYPE_ID + "=" + Columns.QUESTIONTYPE_QUESTION_TYPE_ID + " INNER JOIN User ON " + Columns.QUESTION_USER_ID + "=" + Columns.USER_USER_ID + "";
    private final String SQL_INSERT = "INSERT INTO " + getTable() + "(" + Columns.QUESTION_QUESTION_TYPE_ID + ", " + Columns.QUESTION_USER_ID + ", " + Columns.QUESTION_QUESTION_NAME + ", " + Columns.QUESTION_QUESTION_DESCRIPTION + ", " + Columns.QUESTION_RIGHT_ANSWER +") VALUES(?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE " + getTable() +" SET " + Columns.QUESTION_QUESTION_TYPE_ID + "=?, " + Columns.QUESTION_USER_ID + "=?, " + Columns.QUESTION_QUESTION_NAME + "=?, " + Columns.QUESTION_QUESTION_DESCRIPTION + "=?," + Columns.QUESTION_RIGHT_ANSWER + "=? WHERE " + Columns.QUESTION_QUESTION_ID + "=?";
    private final String SQL_DELETE = "DELETE FROM " + getTable() + " WHERE " + Columns.QUESTION_QUESTION_ID + "=?";
    private final String SQL_COUNT = "SELECT max(Question.QuestionId) FROM " + getTable();
    
    private DataSource dataSource;

    public QuestionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public Question[] findAll() throws Exception {
        return findByDynamicSelect(SQL_SELECT, null);
    }

    @Override
    public Question findWhereQuestionIdEquals(long questionId) throws Exception {
        return findSingleByDynamicSelect(SQL_SELECT + " WHERE " + Columns.QUESTION_QUESTION_ID + "=? ORDER BY " + Columns.QUESTION_QUESTION_ID, new Object[]{questionId});
    }

    @Override
    public Question[] findWhereQuestionTypeIdEquals(long questionTypeId) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.QUESTION_QUESTION_TYPE_ID + "=? ORDER BY " + Columns.QUESTION_QUESTION_ID, new Object[]{questionTypeId});
    }

    @Override
    public Question[] findWhereUserIdEquals(long userId) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.QUESTION_USER_ID + "=? ORDER BY " + Columns.QUESTION_QUESTION_ID, new Object[]{userId});
    }
//"UPDATE " + getTable() +" SET " + COLUMN_QUESTION_TYPE_ID + "=?, " + COLUMN_TEAM_ID + "=?, " + COLUMN_QUESTION_NAME + "=?, " + COLUMN_QUESTION_DESCRIPTION + "=? WHERE " + COLUMN_QUESTION_ID + "=?";
    @Override
    public int update(Question newQuestion) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setLong(1, newQuestion.getQuestionType().getQuestionTypeId());
            preparedStatement.setLong(2, newQuestion.getUser().getUserId());
            preparedStatement.setString(3, newQuestion.getQuestionName());
            preparedStatement.setString(4, newQuestion.getQuestionDescription());
            preparedStatement.setLong(5, newQuestion.getQuestionId());
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

    //"INSERT INTO " + getTable() + "(" + COLUMN_QUESTION_TYPE_ID + ", " + COLUMN_TEAM_ID + ", " + COLUMN_QUESTION_NAME + ", " + COLUMN_QUESTION_DESCRIPTION + ") VALUES(?,?,?,?)";
    @Override
    public long insert(Question newQuestion) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        long insertedId = -1;
        try{
            preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, newQuestion.getQuestionType().getQuestionTypeId());
            preparedStatement.setLong(2, newQuestion.getUser().getUserId());
            preparedStatement.setString(3, newQuestion.getQuestionName());
            preparedStatement.setString(4, newQuestion.getQuestionDescription());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                insertedId = resultSet.getLong(1);
            }
            newQuestion.setQuestionId(insertedId);
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
    public int delete(Question questionToDelete) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, questionToDelete.getQuestionId());
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
    public int deleteAllWhereQuestionTypeIdEquals(long questionTypeId) throws Exception {
        Question[] questionsToDelete = findWhereQuestionTypeIdEquals(questionTypeId);
        int affectedRows = 0;
        for(Question teamToDelete:questionsToDelete){
            affectedRows += delete(teamToDelete);
        }
        return affectedRows;
    }
    
    @Override
    public int getCount() throws Exception {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT);
        ResultSet resultSet = preparedStatement.executeQuery();
        try{
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }finally{
            if(connection != null){
                connection.close();
            }
        }
        return 0;
    }   
    
    public Question findSingleByDynamicSelect(String SQL, Object[] params) throws Exception{
        logger.info("findSingleByDynamicSelect");
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
            resultSet = preparedStatement.executeQuery();//
            return fetchSingleResult(resultSet);
        }catch(Exception exception){
            exception.printStackTrace();
            throw new Exception(exception);
        }finally{
            if(connection != null){
                connection.close();
            }
        }
    }
    public Question[] findByDynamicSelect(String sql, Object[] params) throws Exception{
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
    
    private Question[] fetchMultiResults(ResultSet resultSet) throws Exception {
        Collection<Question> questions = new ArrayList<>();
        while(resultSet.next()){
            Question dto = new Question();
            populateDto(dto, resultSet);
            questions.add(dto);
        }
        
        Question[] questionsArray = new Question[questions.size()];
        questions.toArray(questionsArray);
        return questionsArray;
    }
    private Question fetchSingleResult(ResultSet resultSet) throws Exception{
        if(resultSet.next()){
            Question dto = new Question();
            populateDto(dto, resultSet);
            return dto;
        }
        
        return null;
    }

    private void populateDto(Question dto, ResultSet resultSet) throws Exception{
        dto.setQuestionId(resultSet.getLong(Columns.QUESTION_QUESTION_ID));
            QuestionType questionType = new QuestionType();
            questionType.setQuestionTypeId(resultSet.getLong(Columns.QUESTION_QUESTION_TYPE_ID));
            questionType.setQuestionTypeName(resultSet.getString(Columns.QUESTIONTYPE_QUESTION_TYPE_NAME));
        dto.setQuestionType(questionType);
            User user = new User();
            user.setUserId(resultSet.getLong(Columns.QUESTION_USER_ID));
            user.setUserLogin(resultSet.getString(Columns.USER_USER_LOGIN));
            user.setUserPassword(resultSet.getString(Columns.USER_USER_PASSWORD));
            user.setIsCaptain(resultSet.getShort(Columns.USER_IS_CAPTAIN));
                UserRole userRole = new UserRole();
                userRole.setUserRoleId(resultSet.getLong(Columns.USER_USER_ROLE_ID));
            user.setUserRole(userRole);
                Team team = new Team();
                team.setTeamId(resultSet.getLong(Columns.USER_TEAM_ID));
            user.setTeam(team);
        dto.setUser(user);
        dto.setQuestionName(resultSet.getString(Columns.QUESTION_QUESTION_NAME));
        dto.setQuestionDescription(resultSet.getString(Columns.QUESTION_QUESTION_DESCRIPTION));
        dto.setRightAnswer(resultSet.getString(Columns.QUESTION_RIGHT_ANSWER));
    }
    
    public String getTable(){
        return "Question";
    }

        
}
