/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.entities;

import java.io.Serializable;
import java.util.Collection;


/**
 *
 * @author Roman
 */

public class TeamType implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long teamTypeId;
    private String teamTypeName;
    private Collection<Team> teamCollection;

    public TeamType() {
    }

    public TeamType(Long teamTypeId) {
        this.teamTypeId = teamTypeId;
    }

    public Long getTeamTypeId() {
        return teamTypeId;
    }

    public void setTeamTypeId(Long teamTypeId) {
        this.teamTypeId = teamTypeId;
    }

    public String getTeamTypeName() {
        return teamTypeName;
    }

    public void setTeamTypeName(String teamTypeName) {
        this.teamTypeName = teamTypeName;
    }

    public Collection<Team> getTeamCollection() {
        return teamCollection;
    }

    public void setTeamCollection(Collection<Team> teamCollection) {
        this.teamCollection = teamCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamTypeId != null ? teamTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeamType)) {
            return false;
        }
        TeamType other = (TeamType) object;
        if ((this.teamTypeId == null && other.teamTypeId != null) || (this.teamTypeId != null && !this.teamTypeId.equals(other.teamTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.romanchi.database.entities.TeamType[ teamTypeId=" + teamTypeId + " ]";
    }
    
}
