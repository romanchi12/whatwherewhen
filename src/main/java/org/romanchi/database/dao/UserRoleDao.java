/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.entities.UserRole;

/**
 *
 * @author Роман
 */
public interface UserRoleDao {
    public UserRole[] findAll() throws Exception;
    public UserRole findWhereUserRoleIdEquals(int userRoleId) throws Exception;
    public UserRole[] findWhereUserRoleNameEquals(String userRoleName) throws Exception;
    public int update(UserRole newUser) throws Exception;
    public long insert(UserRole newUser) throws Exception;
    public int delete(UserRole userToDelete) throws Exception;
}
