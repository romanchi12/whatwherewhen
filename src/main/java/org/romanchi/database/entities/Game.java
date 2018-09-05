/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.entities;

import java.io.Serializable;


/**
 *
 * @author Roman
 */

public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
  
    private Long gameId;
    private Integer pointsExperts;
    private Integer pointsQuestioners;
    private Team team;
    private UserRole userRole;

    public Game() {
    }

    public Game(Long gameId) {
        this.gameId = gameId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Integer getPointsExperts() {
        return pointsExperts;
    }

    public void setPointsExperts(Integer pointsExperts) {
        this.pointsExperts = pointsExperts;
    }

    public Integer getPointsQuestioners() {
        return pointsQuestioners;
    }

    public void setPointsQuestioners(Integer pointsQuestioners) {
        this.pointsQuestioners = pointsQuestioners;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameId != null ? gameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.romanchi.database.entities.Game[ gameId=" + gameId + " ]";
    }
    
}
