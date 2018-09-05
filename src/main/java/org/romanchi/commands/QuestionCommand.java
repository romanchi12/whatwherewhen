/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.commands;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.romanchi.database.dao.QuestionDao;
import org.romanchi.database.dao.QuestionDaoFactory;
import org.romanchi.database.entities.Question;
import org.romanchi.database.DatabaseUtils;

/**
 *
 * @author Роман
 */
public class QuestionCommand implements Command{
    
    private final static Logger logger = Logger.getLogger(QuestionCommand.class.getName());
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        QuestionDao questionDao = QuestionDaoFactory.create();
        try {
            Random random =  new Random();  
            int questionsCount = questionDao.getCount();
            int questionIndex = random.nextInt(questionsCount) + 1;
            Question question;
            while((question = questionDao.findWhereQuestionIdEquals(questionIndex)) == null){}
            request.getSession().setAttribute("question", question);
            return "/question.jsp";
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            request.setAttribute("messageError", "Database error");
            return "/error.jsp";
        }
    }
    
}
