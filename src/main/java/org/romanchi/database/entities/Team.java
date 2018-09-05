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

public class Team implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long teamId;
    private String teamName;
    private Integer teamAllGamesNumber;
    private Integer teamWinsNumber;
    private Collection<Game> gameCollection;
    private Collection<User> userCollection;
    private Collection<Question> questionCollection;
    private TeamType teamType;

    public Team() {
    }

    public Team(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamAllGamesNumber() {
        return teamAllGamesNumber;
    }

    public void setTeamAllGamesNumber(Integer teamAllGamesNumber) {
        this.teamAllGamesNumber = teamAllGamesNumber;
    }

    public Integer getTeamWinsNumber() {
        return teamWinsNumber;
    }

    public void setTeamWinsNumber(Integer teamWinsNumber) {
        this.teamWinsNumber = teamWinsNumber;
    }

    public Collection<Game> getGameCollection() {
        return gameCollection;
    }

    public void setGameCollection(Collection<Game> gameCollection) {
        this.gameCollection = gameCollection;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public void setTeamType(TeamType teamType) {
        this.teamType = teamType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamId != null ? teamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.teamId == null && other.teamId != null) || (this.teamId != null && !this.teamId.equals(other.teamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Team{" + "teamId=" + teamId + ", teamName=" + teamName + ", teamAllGamesNumber=" + teamAllGamesNumber + ", teamWinsNumber=" + teamWinsNumber + ", gameCollection=" + gameCollection + ", userCollection=" + userCollection + ", questionCollection=" + questionCollection + ", teamType=" + teamType + '}';
    }

    
    
}
