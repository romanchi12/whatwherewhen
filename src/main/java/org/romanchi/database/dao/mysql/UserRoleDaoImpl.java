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

import org.romanchi.database.dao.UserRoleDao;
import org.romanchi.database.dao.mysql.Columns;
import org.romanchi.database.entities.UserRole;

/**
 *
 * @author Роман
 */
public class UserRoleDaoImpl implements UserRoleDao {
    
    private final DataSource dataSource;
    private final String SQL_SELECT = "SELECT " + Columns.USERROLE_USER_ROLE_ID + ", " + Columns.USERROLE_USER_ROLE_NAME + " FROM " + getTable();
    private final String SQL_INSERT = "INSERT INTO " + getTable() + "(" + Columns.USERROLE_USER_ROLE_NAME +") VALUES(?)";
    private final String SQL_UPDATE = "UPDATE " + getTable() + " SET " + Columns.USERROLE_USER_ROLE_NAME + "=? WHERE " + Columns.USERROLE_USER_ROLE_ID + "=?";
    private final String SQL_DELETE = "DELETE FROM " + getTable() + " WHERE " + Columns.USERROLE_USER_ROLE_ID + "=?";
   
    UserRoleDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UserRole[] findAll() throws Exception {
        return findByDynamicSelect(SQL_SELECT + " ORDER BY " + Columns.USERROLE_USER_ROLE_ID,null);
    }

    @Override
    public UserRole findWhereUserRoleIdEquals(int userRoleId) throws Exception {
        return findSingleByDynamicSelect(SQL_SELECT + " WHERE " + Columns.USERROLE_USER_ROLE_ID + "=? ORDER BY " + Columns.USERROLE_USER_ROLE_ID, new Object[]{userRoleId});
    }

    @Override
    public UserRole[] findWhereUserRoleNameEquals(String userRoleName) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.USERROLE_USER_ROLE_NAME + "=? ORDER BY " + Columns.USERROLE_USER_ROLE_ID, new Object[]{userRoleName});
    }

    @Override
    public int update(UserRole newUserRole) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, newUserRole.getUserRoleName());
            preparedStatement.setLong(2, newUserRole.getUserRoleId());
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
    public long insert(UserRole newUserRole) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        long insertedId = -1;
        try{
            preparedStatement = connection.prepareStatement(SQL_INSERT,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newUserRole.getUserRoleName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                insertedId = resultSet.getLong(1);
            }
            newUserRole.setUserRoleId(insertedId);
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
    public int delete(UserRole userRoleToDelete) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, userRoleToDelete.getUserRoleId());
            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();
            return affectedRows;
        }catch(Exception exception){
            exception.printStackTrace();
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
    
    
    public UserRole findSingleByDynamicSelect(String SQL,
            Object[] params) throws Exception{
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
    public UserRole[] findByDynamicSelect(String SQL,
            Object[] params)throws Exception{
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
            return fetchMultiResults(resultSet);
        }catch(Exception exception){
            //logger.error(exception,exception);
            throw new Exception(exception);
        }
        
    }
    
    private UserRole fetchSingleResult(ResultSet resultSet) throws SQLException{
        if(resultSet.next()){
            UserRole dto = new UserRole();
            populateDto(dto, resultSet);
            return dto;
        }
        return null;
    }
    private UserRole[] fetchMultiResults(ResultSet resultSet) throws SQLException {
        Collection<UserRole> userRoles = new ArrayList<>();
        while(resultSet.next()){
            UserRole dto = new UserRole();
            populateDto(dto, resultSet);
            userRoles.add(dto);
        }
        UserRole[] userRolesArray = new UserRole[userRoles.size()];
        userRoles.toArray(userRolesArray);
        return userRolesArray;
    }

    private void populateDto(UserRole dto, ResultSet resultSet) throws SQLException {
        dto.setUserRoleId(resultSet.getLong(Columns.USERROLE_USER_ROLE_ID));
        dto.setUserRoleName(resultSet.getString(Columns.USERROLE_USER_ROLE_NAME));
        dto.setGameCollection(null);
        dto.setUserCollection(null);
    }

    private String getTable() {
        return "UserRole";
    }
    
}
