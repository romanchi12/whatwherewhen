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

public class HintType implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long hintTypeId;
    private String hintTypeName;
    private Collection<Hint> hintCollection;

    public HintType() {
    }

    public HintType(Long hintTypeId) {
        this.hintTypeId = hintTypeId;
    }

    public Long getHintTypeId() {
        return hintTypeId;
    }

    public void setHintTypeId(Long hintTypeId) {
        this.hintTypeId = hintTypeId;
    }

    public String getHintTypeName() {
        return hintTypeName;
    }

    public void setHintTypeName(String hintTypeName) {
        this.hintTypeName = hintTypeName;
    }

    public Collection<Hint> getHintCollection() {
        return hintCollection;
    }

    public void setHintCollection(Collection<Hint> hintCollection) {
        this.hintCollection = hintCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hintTypeId != null ? hintTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HintType)) {
            return false;
        }
        HintType other = (HintType) object;
        if ((this.hintTypeId == null && other.hintTypeId != null) || (this.hintTypeId != null && !this.hintTypeId.equals(other.hintTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.romanchi.database.entities.HintType[ hintTypeId=" + hintTypeId + " ]";
    }
    
}
