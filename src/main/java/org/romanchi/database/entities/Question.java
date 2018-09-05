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

public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long questionId;
    private String questionName;
    private String questionDescription;
    private String rightAnswer;
    private Collection<Hint> hintCollection;
    private QuestionType questionType;
    private User user;

    public Question() {
    }

    public Question(Long questionId) {
        this.questionId = questionId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Collection<Hint> getHintCollection() {
        return hintCollection;
    }

    public void setHintCollection(Collection<Hint> hintCollection) {
        this.hintCollection = hintCollection;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Question{" + "questionId=" + questionId + ", questionName=" + questionName + ", questionDescription=" + questionDescription + ", rightAnswer=" + rightAnswer + ", hintCollection=" + hintCollection + ", questionType=" + questionType + ", user=" + user + '}';
    }

    
    
}
