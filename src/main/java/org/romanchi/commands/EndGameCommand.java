/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.commands;

import org.romanchi.database.dao.TeamDao;
import org.romanchi.database.dao.TeamDaoFactory;
import org.romanchi.database.entities.Team;
import org.romanchi.database.entities.User;
import org.romanchi.database.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Роман
 */
public class EndGameCommand implements Command{
    private final static Logger logger = Logger.getLogger(EndGameCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        TeamDao teamDao = TeamDaoFactory.create();
        User user = (User) request.getSession().getAttribute("user");
        try{
            Team team = user.getTeam();
            team.setTeamAllGamesNumber(team.getTeamAllGamesNumber() + 1);
            teamDao.update(team);
        } catch (NullPointerException exception){
            logger.log(Level.SEVERE,null, exception);
            request.setAttribute("messageError", "Clear cache and try again");
            return "/error.jsp";
        } catch (Exception exception) {
            logger.log(Level.SEVERE,null, exception);
            request.setAttribute("messageError", "Database exception");
            return "/error.jsp";
        }
        request.getSession().removeAttribute("game");
        return "/index.jsp";
    }
    
}
