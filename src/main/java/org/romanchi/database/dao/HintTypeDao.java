/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.entities.HintType;

/**
 *
 * @author Roman
 */
public interface HintTypeDao {
    public HintType[] findAll() throws Exception;
    public HintType findWhereHintTypeIdEquals(long hintTypeId) throws Exception;
    public HintType[] findWhereHintTypeNameEquals(String hintTypeName) throws Exception;
    public int update(HintType newHintType) throws Exception;
    public long insert(HintType newHintType) throws Exception;
    public int delete(HintType hintTypeToDelete) throws Exception;  
}
