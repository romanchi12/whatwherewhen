/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.entities.Game;
import org.romanchi.database.entities.User;

/**
 *
 * @author Роман
 */
public interface GameDao {
    public Game[] findAll() throws Exception;
    public Game findWhereGameIdEquals(long gameId) throws Exception;
    public Game[] findWhereUserRoleIdEquals(long userRoleId) throws Exception;
    public Game[] findWhereTeamIdEquals(long teamId) throws Exception;
    public int update(Game newGame) throws Exception;
    public long insert(Game newGame) throws Exception;
    public int delete(Game gameToDelete) throws Exception;
    public int deleteAllWhereTeamIdEquals(long userRoleId)throws Exception;
}
