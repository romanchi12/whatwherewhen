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
 * @author Роман
 */
public class DeleteTeamCommand implements Command{
    private final static Logger logger = Logger.getLogger(DeleteTeamCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User)request.getSession().getAttribute("user");
        TeamDao teamDao = TeamDaoFactory.create();
        UserDao userDao = UserDaoFactory.create();
        if(user != null){
            Team team = user.getTeam();
            logger.log(Level.INFO, "Team to delete: {0}", team.toString());
            try {
                User[] users = userDao.findWhereTeamIdEquals(team.getTeamId());
                for(User userFromTeam:users){
                    userFromTeam.setTeam(null);
                    userDao.update(userFromTeam);
                }
                if(teamDao.delete(team)>0){
                    user.setTeam(null);
                    request.getSession().setAttribute("user", user);
                }
                return "/index.jsp";
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                request.setAttribute("messageError", "Database exception");
                return "/error.jsp";
            }
        }
        request.setAttribute("messageError", "Unauthtorized exception");
        return "/error.jsp";
    }
}
