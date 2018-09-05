/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.entities.TeamType;

/**
 *
 * @author Роман
 */
public interface TeamTypeDao {
    public TeamType[] findAll() throws Exception;
    public TeamType findWhereTeamTypeIdEquals(long teamTypeId) throws Exception;
    public TeamType[] findWhereTeamTypeNameEquals(String teamTypeName) throws Exception;
    public int update(TeamType newTeamType) throws Exception;
    public long insert(TeamType newTeamType) throws Exception;
    public int delete(TeamType teamTypeToDelete) throws Exception;
}
