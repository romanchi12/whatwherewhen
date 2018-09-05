/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.entities.Question;

/**
 *
 * @author Роман
 */
public interface QuestionDao {
    public Question[] findAll() throws Exception;
    public Question findWhereQuestionIdEquals(long questionId) throws Exception;
    public Question[] findWhereQuestionTypeIdEquals(long questionTypeId) throws Exception;
    public Question[] findWhereUserIdEquals(long userId) throws Exception;
    public int update(Question newQuestion) throws Exception;
    public long insert(Question newQuestion) throws Exception;
    public int delete(Question questionToDelete) throws Exception;
    public int deleteAllWhereQuestionTypeIdEquals(long questionTypeId)throws Exception;
    public int getCount() throws Exception;
}
