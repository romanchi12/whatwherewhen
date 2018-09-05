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
import org.romanchi.database.entities.Team;
import org.romanchi.database.entities.User;
import org.romanchi.database.DatabaseUtils;
import org.romanchi.servlet.CommandManager;

/**
 *
 * @author Роман
 */
public class StartGameCommand implements Command{
    private final static Logger logger = Logger.getLogger(StartGameCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User)request.getSession().getAttribute("user");
        if(user != null){
            if(user.getIsCaptain()!=0){
                Team team = user.getTeam();
                if(team != null){
                    GameDao gameDao = GameDaoFactory.create();
                    Game newGame = new Game();
                    newGame.setTeam(team);
                    newGame.setPointsExperts(0);
                    newGame.setPointsQuestioners(0);
                    newGame.setUserRole(null);
                    try {
                        gameDao.insert(newGame);
                        request.getSession().setAttribute("game", newGame);
                        return CommandManager.getInstance().getCommand("question").execute(request, response);
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, null, ex);
                        request.setAttribute("messageError", "Database error");
                        return "/error.jsp";
                    }
                }else{
                    request.setAttribute("messageError", "You should create team to start game");
                    return "/error.jsp";
                }
            }else{
                request.setAttribute("messageError", "Just captain can start game");
                return "/error.jsp";
            }
        }else{
            request.setAttribute("messageError", "Unauthtorized exception");
            return "/error.jsp";
        }
    }
    
}
