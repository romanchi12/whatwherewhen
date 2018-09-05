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

import org.romanchi.database.dao.HintTypeDao;
import org.romanchi.database.dao.mysql.Columns;
import org.romanchi.database.entities.HintType;

/**
 *
 * @author Roman
 */
public class HintTypeDaoImpl implements HintTypeDao {

    
    private final String SQL_SELECT = "SELECT " + Columns.HINTTYPE_HINT_TYPE_ID + ", " + Columns.HINTTYPE_HINT_TYPE_NAME + " FROM " + getTable();
    private final String SQL_INSERT = "INSERT INTO " + getTable() + "(" + Columns.HINTTYPE_HINT_TYPE_NAME + ") VALUES(?)";
    private final String SQL_UPDATE = "UPDATE " + getTable() +" SET " + Columns.HINTTYPE_HINT_TYPE_NAME + "=? WHERE " + Columns.HINTTYPE_HINT_TYPE_ID + "=?";
    private final String SQL_DELETE = "DELETE FROM " + getTable() + " WHERE " + Columns.HINTTYPE_HINT_TYPE_ID + "=?";
    private DataSource dataSource;

    public HintTypeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    
    @Override
    public HintType[] findAll() throws Exception {
        return findByDynamicSelect(SQL_SELECT, null);
    }

    @Override
    public HintType findWhereHintTypeIdEquals(long hintTypeId) throws Exception {
        return findSingleByDynamicSelect(SQL_SELECT + " WHERE " + Columns.HINTTYPE_HINT_TYPE_ID + "=? ORDER BY " + Columns.HINTTYPE_HINT_TYPE_ID, new Object[]{hintTypeId});
    } 

    @Override
    public HintType[] findWhereHintTypeNameEquals(String hintTypeName) throws Exception {
        return findByDynamicSelect(SQL_SELECT + " WHERE " + Columns.HINTTYPE_HINT_TYPE_NAME + "=? ORDER BY " + Columns.HINTTYPE_HINT_TYPE_ID, new Object[]{hintTypeName});
    }

    @Override
    public int update(HintType newHintType) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, newHintType.getHintTypeName());
            preparedStatement.setLong(2, newHintType.getHintTypeId());
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
    public long insert(HintType newHintType) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        long insertedId = -1;
        try{
            preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newHintType.getHintTypeName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                insertedId = resultSet.getLong(1);
            }
            newHintType.setHintTypeId(insertedId);
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
    public int delete(HintType hintTypeToDelete) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, hintTypeToDelete.getHintTypeId());
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
    
    public HintType findSingleByDynamicSelect(String SQL, Object[] params) throws Exception{
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
    public HintType[] findByDynamicSelect(String sql, Object[] params) throws Exception{
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
    
    private HintType[] fetchMultiResults(ResultSet resultSet) throws Exception {
        Collection<HintType> hintTypes = new ArrayList<>();
        while(resultSet.next()){
            HintType dto = new HintType();
            populateDto(dto, resultSet);
            hintTypes.add(dto);
        }
        
        HintType[] hintTypesArray = new HintType[hintTypes.size()];
        hintTypes.toArray(hintTypesArray);
        return hintTypesArray;
    }
    private HintType fetchSingleResult(ResultSet resultSet) throws Exception{
        if(resultSet.next()){
            HintType dto = new HintType();
            populateDto(dto, resultSet);
            return dto;
        }
        return null;
    }

    private void populateDto(HintType dto, ResultSet resultSet) throws Exception{
        dto.setHintTypeId(resultSet.getLong(Columns.HINTTYPE_HINT_TYPE_ID));
        dto.setHintTypeName(resultSet.getString(Columns.HINTTYPE_HINT_TYPE_NAME));
    }
    
    public String getTable(){
        return "HintType";
    }
    
}
