/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.entities.User;
import org.romanchi.database.entities.UserRole;

/**
 *
 * @author Роман
 */
public interface UserDao {
    public User[] findAll() throws Exception;
    public User findWhereUserIdEquals(long userId) throws Exception;
    public User[] findWhereUserRoleIdEquals(long userRoleId) throws Exception;
    public User[] findWhereTeamIdEquals(long teamId) throws Exception;
    public User findWhereUserLoginEquals(String userLogin) throws Exception;
    public User findWhereUserLoginAndPasswordEquals(String userLogin, String userPassword) throws Exception;
    public int update(User newUser) throws Exception;
    public long insert(User newUser) throws Exception;
    public int delete(User userToDelete) throws Exception;
    public int deleteAllWhereUserRoleIdEquals(long userRoleId)throws Exception;
}
