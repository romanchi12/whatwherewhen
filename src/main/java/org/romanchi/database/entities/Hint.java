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

public class Hint implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long hintId;
    private String hintDescription;
    private HintType hintType;

    private Question question;

    public Hint() {
    }

    public Hint(Long hintId) {
        this.hintId = hintId;
    }

    public Long getHintId() {
        return hintId;
    }

    public void setHintId(Long hintId) {
        this.hintId = hintId;
    }

    public String getHintDescription() {
        return hintDescription;
    }

    public void setHintDescription(String hintDescription) {
        this.hintDescription = hintDescription;
    }

    public HintType getHintType() {
        return hintType;
    }

    public void setHintType(HintType hintType) {
        this.hintType = hintType;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hintId != null ? hintId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hint)) {
            return false;
        }
        Hint other = (Hint) object;
        if ((this.hintId == null && other.hintId != null) || (this.hintId != null && !this.hintId.equals(other.hintId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.romanchi.database.entities.Hint[ hintId=" + hintId + " ]";
    }

    
    
}
