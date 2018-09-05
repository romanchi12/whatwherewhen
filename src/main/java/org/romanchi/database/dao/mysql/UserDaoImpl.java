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

import org.romanchi.database.dao.UserDao;
import org.romanchi.database.entities.Team;
import org.romanchi.database.entities.TeamType;
import org.romanchi.database.entities.User;
import org.romanchi.database.entities.UserRole;

/**
 *
 * @author Роман
 */
public class UserDaoImpl implements UserDao {
    private final static Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    private final String SQL_SELECT = "SELECT " + Columns.USER_USER_ID + ", " + Columns.USER_USER_ROLE_ID + ", " + Columns.USER_TEAM_ID + ", " + Columns.USER_USER_LOGIN + ", " + Columns.USER_USER_PASSWORD + ", " + Columns.USER_IS_CAPTAIN + ", " + Columns.TEAM_TEAM_TYPE_ID + ", " + Columns.TEAM_TEAM_NAME + ", " + Columns.TEAM_TEAM_ALL_GAMES_NUMBER + ", " + Columns.TEAM_TEAM_WINS_NUMBER + ", " + Columns.USERROLE_USER_ROLE_ID + ", " + Columns.USERROLE_USER_ROLE_NAME + " FROM " + getTable() + " LEFT JOIN Team ON " + Columns.USER_TEAM_ID + "=" + Columns.TEAM_TEAM_ID + " LEFT JOIN UserRole ON " + Columns.USER_USER_ROLE_ID + "=" + Columns.USERROLE_USER_ROLE_ID + "";
    private final String SQL_INSERT = "INSERT INTO " + getTable() + "(" + Columns.USER_USER_ROLE_ID + ", " + Columns.USER_TEAM_ID + ", " + Columns.USER_USER_LOGIN + ", " + Columns.USER_USER_PASSWORD + ", " + Columns.USER_IS_CAPTAIN + ") VALUES(?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE " + getTable() +" SET " + Columns.USER_USER_ROLE_ID + "=?, " + Columns.USER_TEAM_ID + "=?, " + Columns.USER_USER_LOGIN + "=?, " + Columns.USER_USER_PASSWORD + "=?, " + Columns.USER_IS_CAPTAIN + "=? WHERE " + Columns.USER_USER_ID + "=?";
    private final String SQL_DELETE = "DELETE FROM " + getTable() + " WHERE " + Columns.USER_USER_ID + "=?";
    private final DataSource dataSource;


    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public User[] findAll() throws Exception {
        return findByDynamicSelect(SQL_SELECT + " ORDER BY UserId", null);
    }
    
    @Override
    public User findWhereUserIdEquals(long userId) throws Exception {
        return findSingleByDynamicSelect(SQL_SELECT + " WHERE " + Columns.USER_USER_ID + "=? ORDER BY " + Columns.USER_USER_ID, new Object[]{userId});
    }

    @Override
    public User[] findWhereUserRoleIdEquals(long userRoleId) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.USER_USER_ROLE_ID + " = ? ORDER BY " + Columns.USER_USER_ID, new Object[]{userRoleId});
    }

    @Override
    public User[] findWhereTeamIdEquals(long teamId) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.USER_TEAM_ID + " = ? ORDER BY " + Columns.USER_USER_ID, new Object[]{teamId});
    }

    @Override
    public User findWhereUserLoginEquals(String userLogin) throws Exception {
        return findSingleByDynamicSelect(SQL_SELECT + " WHERE " + Columns.USER_USER_LOGIN + " = ? ORDER BY " + Columns.USER_USER_ID, new Object[]{userLogin});
    }
    
    @Override
    public User findWhereUserLoginAndPasswordEquals(String userLogin, String userPassword) throws Exception {
        return findSingleByDynamicSelect(SQL_SELECT + " WHERE " + Columns.USER_USER_LOGIN + "=? AND " + Columns.USER_USER_PASSWORD + "=? ORDER BY " + Columns.USER_USER_ID, new Object[]{userLogin, userPassword});
    }
   
    
    //"INSERT INTO User(UserRoleId, TeamId, UserLogin, UserPassword, IsCaptain) VALUES(?,?,?,?,?)";
    @Override
    public long insert(User newUser) throws Exception{
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        long insertedId = -1;
        try{
            preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, newUser.getUserRole().getUserRoleId());
            preparedStatement.setObject(2, null);
            preparedStatement.setString(3, newUser.getUserLogin());
            preparedStatement.setString(4, newUser.getUserPassword());
            preparedStatement.setShort(5, newUser.getIsCaptain());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                insertedId = resultSet.getLong(1);
            }
            newUser.setUserId(insertedId);
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
    
    //"UPDATE User SET UserRoleId=?, TeamId=?, UserLogin=?, UserPassword=?, IsCaptain=? WHERE UserId = ?";
    @Override
    public int update(User newUser) throws Exception{
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setLong(1, newUser.getUserRole().getUserRoleId());
            Object teamId = (newUser.getTeam() == null)?null:newUser.getTeam().getTeamId();
            preparedStatement.setObject(2, teamId);
            preparedStatement.setString(3, newUser.getUserLogin());
            preparedStatement.setString(4, newUser.getUserPassword());
            preparedStatement.setShort(5, newUser.getIsCaptain());
            preparedStatement.setLong(6, newUser.getUserId());
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
    
    //"DELETE FROM User WHERE User.UserId=?";
    @Override
    public int delete(User userToDelete) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, userToDelete.getUserId());
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
    public int deleteAllWhereUserRoleIdEquals(long userRoleId) throws Exception{
        User[] usersToDelete = findWhereUserRoleIdEquals(userRoleId);
        int affectedRows = 0;
        for(User userToDelete:usersToDelete){
            affectedRows += delete(userToDelete);
        }
        return affectedRows;
    }
    
    public User findSingleByDynamicSelect(String SQL, Object[] params) throws Exception{
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
    public User[] findByDynamicSelect(String sql, Object[] params) throws Exception{
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
    
    private User[] fetchMultiResults(ResultSet resultSet) throws Exception {
        Collection<User> users = new ArrayList<>();
        while(resultSet.next()){
            User dto = new User();
            populateDto(dto, resultSet);
            users.add(dto);
        }
        
        User[] usersArray = new User[users.size()];
        users.toArray(usersArray);
        return usersArray;
    }
    private User fetchSingleResult(ResultSet resultSet) throws Exception{
        if(resultSet.next()){
            User dto = new User();
            populateDto(dto, resultSet);
            return dto;
        }
        return null;
    }

    private void populateDto(User dto, ResultSet resultSet) throws Exception{
        dto.setUserId(resultSet.getLong(Columns.USER_USER_ID));
        dto.setUserLogin(resultSet.getString(Columns.USER_USER_LOGIN));
        dto.setUserPassword(resultSet.getString(Columns.USER_USER_PASSWORD));
        dto.setIsCaptain(resultSet.getShort(Columns.USER_IS_CAPTAIN));
        if(resultSet.getLong(Columns.USER_TEAM_ID)>0){
            Team team = new Team();
                team.setTeamId(resultSet.getLong(Columns.USER_TEAM_ID));
                team.setTeamName(resultSet.getString(Columns.TEAM_TEAM_NAME));
                team.setTeamAllGamesNumber(resultSet.getInt(Columns.TEAM_TEAM_ALL_GAMES_NUMBER));
                team.setTeamWinsNumber(resultSet.getInt(Columns.TEAM_TEAM_WINS_NUMBER));
                TeamType teamType = new TeamType();
                    teamType.setTeamTypeId(resultSet.getLong(Columns.TEAM_TEAM_TYPE_ID));
                team.setTeamType(teamType);
            dto.setTeam(team);
        }else{
            dto.setTeam(null);
        }
        if(resultSet.getLong(Columns.USER_USER_ROLE_ID) > 0){
            UserRole userRole = new UserRole();
                userRole.setUserRoleId(resultSet.getLong(Columns.USER_USER_ROLE_ID));
                userRole.setUserRoleName(resultSet.getString(Columns.USERROLE_USER_ROLE_NAME));
            dto.setUserRole(userRole);
        }else{
            dto.setUserRole(null);
        }
    }
    
    public String getTable(){
        return "User";
    }

    

}
