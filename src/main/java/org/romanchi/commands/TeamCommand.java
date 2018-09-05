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
import org.romanchi.database.dao.TeamDao;
import org.romanchi.database.dao.TeamDaoFactory;
import org.romanchi.database.dao.UserDao;
import org.romanchi.database.dao.UserDaoFactory;
import org.romanchi.database.entities.Team;
import org.romanchi.database.entities.User;
import org.romanchi.database.DatabaseUtils;

/**
 *
 * @author Roman
 */
public class TeamCommand implements Command{
    private final static Logger logger = Logger.getLogger(TeamCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Long teamId = Long.valueOf(request.getParameter("teamId"));
        UserDao userDao = UserDaoFactory.create();
        TeamDao teamDao = TeamDaoFactory.create();
        try {
            Team team = teamDao.findWhereTeamIdEquals(teamId);
            logger.info(team.toString());
            User[] users = userDao.findWhereTeamIdEquals(teamId);
            logger.info(users[0].toString());
            request.setAttribute("users", users);
            request.setAttribute("team", team);
            return "/team.jsp";
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            request.setAttribute("messageError", "Database error");
            return "/error.jsp";
        }
    }
    
}
