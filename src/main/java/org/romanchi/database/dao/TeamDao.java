/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.entities.Team;

public interface TeamDao {
    public Team[] findAll() throws Exception;
    public Team findWhereTeamIdEquals(long teamId) throws Exception;
    public Team[] findWhereTeamTypeIdEquals(long teamTypeId) throws Exception;
    public Team[] findWhereTeamNameEquals(String teamName) throws Exception;
    public int update(Team newTeam) throws Exception;
    public long insert(Team newTeam) throws Exception;
    public int delete(Team teamToDelete) throws Exception;
    public int deleteAllWhereTeamTypeIdEquals(long teamTypeId)throws Exception;
}
