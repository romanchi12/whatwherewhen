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

import org.romanchi.database.dao.TeamTypeDao;
import org.romanchi.database.dao.mysql.Columns;
import org.romanchi.database.entities.TeamType;

/**
 *
 * @author Роман
 */
public class TeamTypeDaoImpl implements TeamTypeDao {
    
    private final String SQL_SELECT = "SELECT " + Columns.TEAMTYPE_TEAM_TYPE_ID + ", " + Columns.TEAMTYPE_TEAM_TYPE_NAME + " FROM " + getTable();
    private final String SQL_INSERT = "INSERT INTO " + getTable() + "(" + Columns.TEAMTYPE_TEAM_TYPE_NAME + ") VALUES(?)";
    private final String SQL_UPDATE = "UPDATE " + getTable() +" SET " + Columns.TEAMTYPE_TEAM_TYPE_NAME + "=? WHERE " + Columns.TEAMTYPE_TEAM_TYPE_ID + "=?";
    private final String SQL_DELETE = "DELETE FROM " + getTable() + " WHERE " + Columns.TEAMTYPE_TEAM_TYPE_ID + "=?";
    private DataSource dataSource;

    public TeamTypeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    
    
    @Override
    public TeamType[] findAll() throws Exception {
        return findByDynamicSelect(SQL_SELECT, null);
    }

    @Override
    public TeamType findWhereTeamTypeIdEquals(long teamTypeId) throws Exception {
        return findSingleByDynamicSelect(SQL_SELECT + " WHERE " + Columns.TEAMTYPE_TEAM_TYPE_ID + "=? ORDER BY " + Columns.TEAMTYPE_TEAM_TYPE_ID, new Object[]{teamTypeId});
    }

    @Override
    public TeamType[] findWhereTeamTypeNameEquals(String teamTypeName) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.TEAMTYPE_TEAM_TYPE_NAME + "=? ORDER BY " + Columns.TEAMTYPE_TEAM_TYPE_ID, new Object[]{teamTypeName});
    }

    @Override
    public int update(TeamType newTeamType) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, newTeamType.getTeamTypeName());
            preparedStatement.setLong(2, newTeamType.getTeamTypeId());
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
    public long insert(TeamType newTeamType) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        long insertedId = -1;
        try{
            preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newTeamType.getTeamTypeName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                insertedId = resultSet.getLong(1);
            }
            newTeamType.setTeamTypeId(insertedId);
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
    public int delete(TeamType teamTypeToDelete) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, teamTypeToDelete.getTeamTypeId());
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
    
    public TeamType findSingleByDynamicSelect(String SQL, Object[] params) throws Exception{
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
    public TeamType[] findByDynamicSelect(String sql, Object[] params) throws Exception{
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
    
    private TeamType[] fetchMultiResults(ResultSet resultSet) throws Exception {
        Collection<TeamType> teamTypes = new ArrayList<>();
        while(resultSet.next()){
            TeamType dto = new TeamType();
            populateDto(dto, resultSet);
            teamTypes.add(dto);
        }
        
        TeamType[] teamTypesArray = new TeamType[teamTypes.size()];
        teamTypes.toArray(teamTypesArray);
        return teamTypesArray;
    }
    private TeamType fetchSingleResult(ResultSet resultSet) throws Exception{
        if(resultSet.next()){
            TeamType dto = new TeamType();
            populateDto(dto, resultSet);
            return dto;
        }
        return null;
    }

    private void populateDto(TeamType dto, ResultSet resultSet) throws Exception{
        dto.setTeamTypeId(resultSet.getLong(Columns.TEAMTYPE_TEAM_TYPE_ID));
        dto.setTeamTypeName(resultSet.getString(Columns.TEAMTYPE_TEAM_TYPE_NAME));
    }
    
    public String getTable(){
        return "TeamType";
    }
    
}
