/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.commands;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.romanchi.database.dao.GameDao;
import org.romanchi.database.dao.GameDaoFactory;
import org.romanchi.database.entities.Game;
import org.romanchi.database.entities.Question;
import org.romanchi.database.DatabaseUtils;
import org.romanchi.servlet.CommandManager;

/**
 *
 * @author Роман
 */
public class CheckAnswerCommand implements Command{
    private final static Logger logger = Logger.getLogger(CheckAnswerCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if(request.getSession().getAttribute("user")!=null){
            Question question = (Question) request.getSession().getAttribute("question");
            if(question != null){
                GameDao gameDao = GameDaoFactory.create();
                String teamAnswer = request.getParameter("answer");
                Game game = (Game) request.getSession().getAttribute("game");
                if(teamAnswer.equals(question.getRightAnswer())){
                    game.setPointsExperts(game.getPointsExperts()+ 1);
                }else{
                    game.setPointsQuestioners(game.getPointsQuestioners() + 1);
                }
                try {
                    gameDao.update(game);
                    request.getSession().setAttribute("game", game);
                    if((game.getPointsQuestioners() >= 6) || (game.getPointsExperts() >= 6)){
                        return CommandManager.getInstance().getCommand("winner").execute(request,response);
                    }
                    return CommandManager.getInstance().getCommand("question").execute(request, response);
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, null, ex);
                    request.setAttribute("messageError", "Database error");
                    return "/error.jsp";
                }
            }else{
                logger.log(Level.SEVERE, "question == null");
                request.setAttribute("messageError", "Clear cache and start again");
                return "/error.jsp";
            }
        }else{
            request.setAttribute("messageError", "Unauthorized exception");
            return "/error.jsp";
        }
    }
    
}
