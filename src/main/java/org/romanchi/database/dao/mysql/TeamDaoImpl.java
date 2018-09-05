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

import org.romanchi.database.dao.TeamDao;
import org.romanchi.database.entities.Team;
import org.romanchi.database.entities.TeamType;

/**
 *
 * @author Роман
 */
public class TeamDaoImpl implements TeamDao {
    
    private final static Logger logger = Logger.getLogger(TeamDaoImpl.class.getName());
    
    private final String SQL_SELECT = "SELECT " + Columns.TEAM_TEAM_ID + ", " + Columns.TEAM_TEAM_TYPE_ID + ", " + Columns.TEAM_TEAM_NAME + ", " + Columns.TEAM_TEAM_ALL_GAMES_NUMBER + ", " + Columns.TEAM_TEAM_WINS_NUMBER + ", " + Columns.TEAMTYPE_TEAM_TYPE_NAME + " FROM " + getTable() + " INNER JOIN TeamType ON " + Columns.TEAM_TEAM_TYPE_ID + "=" + Columns.TEAMTYPE_TEAM_TYPE_ID + "";
    private final String SQL_INSERT = "INSERT INTO " + getTable() + "(" + Columns.TEAM_TEAM_TYPE_ID + ", " + Columns.TEAM_TEAM_NAME + ", " + Columns.TEAM_TEAM_ALL_GAMES_NUMBER + ", " + Columns.TEAM_TEAM_WINS_NUMBER + ") VALUES(?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE " + getTable() +" SET " + Columns.TEAM_TEAM_TYPE_ID + "=?, " + Columns.TEAM_TEAM_NAME + "=?, " + Columns.TEAM_TEAM_ALL_GAMES_NUMBER + "=?, " + Columns.TEAM_TEAM_WINS_NUMBER + "=? WHERE " + Columns.TEAM_TEAM_ID + "=?";
    private final String SQL_DELETE = "DELETE FROM " + getTable() + " WHERE " + Columns.TEAM_TEAM_ID + "=?";
    private final DataSource dataSource;

    public TeamDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public Team[] findAll() throws Exception {
        return findByDynamicSelect(SQL_SELECT, null);
    }

    @Override
    public Team findWhereTeamIdEquals(long teamId) throws Exception {
        return findSingleByDynamicSelect(SQL_SELECT + " WHERE " + Columns.TEAM_TEAM_ID + "=? ORDER BY " + Columns.TEAM_TEAM_ID, new Object[]{teamId});
    }

    @Override
    public Team[] findWhereTeamTypeIdEquals(long teamTypeId) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.TEAM_TEAM_TYPE_ID + "=? ORDER BY " + Columns.TEAM_TEAM_ID, new Object[]{teamTypeId});
    }

    @Override
    public Team[] findWhereTeamNameEquals(String teamName) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.TEAM_TEAM_NAME + "=? ORDER BY " + Columns.TEAM_TEAM_ID, new Object[]{teamName});
    }
    //"UPDATE " + getTable() +" SET " + COLUMN_TEAM_TYPE_ID + "=?, " + COLUMN_TEAM_NAME + "=?, " + COLUMN_TEAM_ALL_GAMES_NUMBER + "=?, " + COLUMN_TEAM_WINS_NUMBER + "=? WHERE " + COLUMN_TEAM_ID + "=?";

    @Override
    public int update(Team newTeam) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setLong(1, newTeam.getTeamType().getTeamTypeId());
            preparedStatement.setString(2, newTeam.getTeamName());
            preparedStatement.setInt(3, newTeam.getTeamAllGamesNumber());
            preparedStatement.setInt(4, newTeam.getTeamWinsNumber());
            preparedStatement.setLong(5, newTeam.getTeamId());
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
    public long insert(Team newTeam) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        long insertedId = -1;
        try{
            preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, newTeam.getTeamType().getTeamTypeId());
            preparedStatement.setString(2, newTeam.getTeamName());
            preparedStatement.setInt(3, newTeam.getTeamAllGamesNumber());
            preparedStatement.setInt(4, newTeam.getTeamWinsNumber());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                insertedId = resultSet.getLong(1);
            }
            newTeam.setTeamId(insertedId);
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
    public int delete(Team teamToDelete) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, teamToDelete.getTeamId());
            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();
            return affectedRows;
        }catch(Exception exception){
            logger.info(exception.getMessage());
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
    public int deleteAllWhereTeamTypeIdEquals(long teamTypeId) throws Exception {
        Team[] teamsToDelete = findWhereTeamTypeIdEquals(teamTypeId);
        int affectedRows = 0;
        for(Team teamToDelete:teamsToDelete){
            affectedRows += delete(teamToDelete);
        }
        return affectedRows;
    }
    
    public Team findSingleByDynamicSelect(String SQL, Object[] params) throws Exception{
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
    public Team[] findByDynamicSelect(String sql, Object[] params) throws Exception{
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
    
    private Team[] fetchMultiResults(ResultSet resultSet) throws Exception {
        Collection<Team> teams = new ArrayList<>();
        while(resultSet.next()){
            Team dto = new Team();
            populateDto(dto, resultSet);
            teams.add(dto);
        }
        
        Team[] teamsArray = new Team[teams.size()];
        teams.toArray(teamsArray);
        return teamsArray;
    }
    private Team fetchSingleResult(ResultSet resultSet) throws Exception{
        if(resultSet.next()){
            Team dto = new Team();
            populateDto(dto, resultSet);
            return dto;
        }
        return null;
    }

    private void populateDto(Team dto, ResultSet resultSet) throws Exception{
        dto.setTeamId(resultSet.getLong(Columns.TEAM_TEAM_ID));
        TeamType teamType = new TeamType();
            teamType.setTeamTypeId(resultSet.getLong(Columns.TEAM_TEAM_TYPE_ID));
            teamType.setTeamTypeName(resultSet.getString(Columns.TEAMTYPE_TEAM_TYPE_NAME));
        dto.setTeamType(teamType);
        dto.setTeamName(resultSet.getString(Columns.TEAM_TEAM_NAME));
        dto.setTeamAllGamesNumber(resultSet.getInt(Columns.TEAM_TEAM_ALL_GAMES_NUMBER));
        dto.setTeamWinsNumber(resultSet.getInt(Columns.TEAM_TEAM_WINS_NUMBER));
    }
    
    public String getTable(){
        return "Team";
    }
    
}
