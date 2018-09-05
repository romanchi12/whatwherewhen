/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.entities.Hint;

/**
 *
 * @author Roman
 */
public interface HintDao {
    public Hint[] findAll() throws Exception;
    public Hint findWhereHintIdEquals(long hintId) throws Exception;
    public Hint[] findWhereHintTypeIdEquals(long hintTypeId) throws Exception;
    public Hint[] findWhereQuestionIdEquals(long questionId) throws Exception;
    public int update(Hint newHint) throws Exception;
    public long insert(Hint newHint) throws Exception;
    public int delete(Hint hintToDelete) throws Exception;
    public int deleteAllWhereHintTypeIdEquals(long hintTypeId)throws Exception;
}
