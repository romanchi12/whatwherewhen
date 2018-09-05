/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database.dao;

import org.romanchi.database.entities.QuestionType;

/**
 *
 * @author Роман
 */
public interface QuestionTypeDao {
    public QuestionType[] findAll() throws Exception;
    public QuestionType findWhereQuestionTypeIdEquals(long questionTypeId) throws Exception;
    public QuestionType[] findWhereQuestionTypeNameEquals(String questionTypeName) throws Exception;
    public int update(QuestionType newQuestionType) throws Exception;
    public long insert(QuestionType newQuestionType) throws Exception;
    public int delete(QuestionType questionTypeToDelete) throws Exception;
}
