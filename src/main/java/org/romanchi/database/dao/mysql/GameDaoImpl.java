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

import org.romanchi.database.dao.GameDao;
import org.romanchi.database.dao.mysql.Columns;
import org.romanchi.database.entities.Game;
import org.romanchi.database.entities.Team;
import org.romanchi.database.entities.UserRole;

/**
 *
 * @author Роман
 */
public class GameDaoImpl implements GameDao {

    
    
    
    private final String SQL_SELECT = "SELECT " + Columns.GAME_GAME_ID + ", " + Columns.GAME_TEAM_ID + ", " + Columns.GAME_USER_ROLE_ID + ", " + Columns.GAME_POINTS_EXPERTS + ", " + Columns.GAME_POINTS_QUESTIONERS + ", " + Columns.TEAM_TEAM_TYPE_ID + ", " + Columns.TEAM_TEAM_NAME + ", " + Columns.TEAM_TEAM_ALL_GAMES_NUMBER + ", " + Columns.TEAM_TEAM_WINS_NUMBER + ", " + Columns.USERROLE_USER_ROLE_NAME +" FROM " + getTable() + " INNER JOIN Team ON " + Columns.GAME_TEAM_ID + "=" + Columns.TEAM_TEAM_ID + " INNER JOIN UserRole ON " + Columns.GAME_USER_ROLE_ID + "=" + Columns.USERROLE_USER_ROLE_ID + "";
    private final String SQL_INSERT = "INSERT INTO " + getTable() + "(" + Columns.GAME_TEAM_ID + ", " + Columns.GAME_USER_ROLE_ID + ", " + Columns.GAME_POINTS_EXPERTS + ", " + Columns.GAME_POINTS_QUESTIONERS + ") VALUES(?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE " + getTable() +" SET " + Columns.GAME_TEAM_ID + "=?, " + Columns.GAME_USER_ROLE_ID + "=?, " + Columns.GAME_POINTS_EXPERTS + "=?, " + Columns.GAME_POINTS_QUESTIONERS + "=? WHERE " + Columns.GAME_GAME_ID + "=?";
    private final String SQL_DELETE = "DELETE FROM " + getTable() + " WHERE " + Columns.GAME_GAME_ID + "=?";
    private DataSource dataSource;

    public GameDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public Game[] findAll() throws Exception {
        return findByDynamicSelect(SQL_SELECT, null);
    }

    @Override
    public Game findWhereGameIdEquals(long gameId) throws Exception {
        return findSingleByDynamicSelect(SQL_SELECT + " WHERE " + Columns.GAME_GAME_ID + "=? ORDER BY " + Columns.GAME_GAME_ID, new Object[]{gameId});
    }

    @Override
    public Game[] findWhereUserRoleIdEquals(long userRoleId) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.GAME_USER_ROLE_ID + "=? ORDER BY " + Columns.GAME_GAME_ID, new Object[]{userRoleId});
    }

    @Override
    public Game[] findWhereTeamIdEquals(long teamId) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.GAME_TEAM_ID + "=? ORDER BY " + Columns.GAME_GAME_ID, new Object[]{teamId});
    }

    @Override
    public int update(Game newGame) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setLong(1, newGame.getTeam().getTeamId());
            Object userRoleId = (newGame.getUserRole() == null) ? null : newGame.getUserRole().getUserRoleId();
            preparedStatement.setObject(2, userRoleId);
            preparedStatement.setInt(3, newGame.getPointsExperts());
            preparedStatement.setInt(4, newGame.getPointsQuestioners());
            preparedStatement.setLong(5, newGame.getGameId());
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
    public long insert(Game newGame) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        long insertedId = -1;
        try{
            preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, newGame.getTeam().getTeamId());
            preparedStatement.setObject(2, null);
            preparedStatement.setInt(3, newGame.getPointsExperts());
            preparedStatement.setInt(4, newGame.getPointsQuestioners());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                insertedId = resultSet.getLong(1);
            }
            newGame.setGameId(insertedId);
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
    public int delete(Game gameToDelete) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, gameToDelete.getGameId());
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
    public int deleteAllWhereTeamIdEquals(long teamId) throws Exception {
        Game[] gamesToDelete = findWhereTeamIdEquals(teamId);
        int affectedRows = 0;
        for(Game gameToDelete:gamesToDelete){
            affectedRows += delete(gameToDelete);
        }
        return affectedRows;
    }
    
    public Game findSingleByDynamicSelect(String SQL, Object[] params) throws Exception{
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
        }
    }
    public Game[] findByDynamicSelect(String sql, Object[] params) throws Exception{
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
    
    private Game[] fetchMultiResults(ResultSet resultSet) throws Exception {
        Collection<Game> games = new ArrayList<>();
        while(resultSet.next()){
            Game dto = new Game();
            populateDto(dto, resultSet);
            games.add(dto);
        }
        
        Game[] gamesArray = new Game[games.size()];
        games.toArray(gamesArray);
        return gamesArray;
    }
    private Game fetchSingleResult(ResultSet resultSet) throws Exception{
        if(resultSet.next()){
            Game dto = new Game();
            populateDto(dto, resultSet);
            return dto;
        }
        return null;
    }

    private void populateDto(Game dto, ResultSet resultSet) throws Exception{
        dto.setGameId(resultSet.getLong(Columns.GAME_GAME_ID));
        dto.setPointsExperts(resultSet.getInt(Columns.GAME_POINTS_EXPERTS));
        dto.setPointsQuestioners(resultSet.getInt(Columns.GAME_POINTS_QUESTIONERS));
            Team team = new Team();
            team.setTeamId(resultSet.getLong(Columns.GAME_TEAM_ID));
        dto.setTeam(team);
            UserRole userRole = new UserRole();
            userRole.setUserRoleId(resultSet.getLong(Columns.GAME_USER_ROLE_ID));
        dto.setUserRole(userRole);
    }
    
    public String getTable(){
        return "Game";
    }

}
