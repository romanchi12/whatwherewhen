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
import org.romanchi.database.dao.TeamTypeDao;
import org.romanchi.database.dao.TeamTypeDaoFactory;
import org.romanchi.database.dao.UserDao;
import org.romanchi.database.dao.UserDaoFactory;
import org.romanchi.database.entities.Team;
import org.romanchi.database.entities.TeamType;
import org.romanchi.database.entities.User;
import org.romanchi.database.DatabaseUtils;

/**
 *
 * @author Roman
 */
public class CreateTeamCommand implements Command{
    private final static Logger logger = Logger.getLogger(CreateTeamCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if(currentUser == null){
            request.setAttribute("messageError", "Unauthorized exception");
            return "/error.jsp";
        }
        String[] usernames = request.getParameterValues("users[]");
        String teamName = request.getParameter("teamname");
        
        UserDao userDao = UserDaoFactory.create();
        TeamDao teamDao = TeamDaoFactory.create();
        TeamTypeDao teamTypeDao = TeamTypeDaoFactory.create();
        
        try {
            TeamType teamType = teamTypeDao.findWhereTeamTypeIdEquals(1);
            Team newTeam = new Team();
            newTeam.setTeamName(teamName);
            newTeam.setTeamType(teamType);
            newTeam.setTeamAllGamesNumber(0);
            newTeam.setTeamWinsNumber(0);
            long teamId = teamDao.insert(newTeam);
            currentUser.setIsCaptain((short)1);
            currentUser.setTeam(newTeam);
            userDao.update(currentUser);
            for(String username:usernames){
                User user = userDao.findWhereUserLoginEquals(username);
                if(user != null){
                    if(user.getTeam().getTeamId() == 0){
                        user.setTeam(newTeam);
                        userDao.update(user);
                    }
                }
            }
            return "/Controller?teamId=" + teamId + "&command=team";
        } catch (Exception ex) {
            Logger.getLogger(CreateTeamCommand.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("messageError", ex.getMessage());
            return "/error.jsp";
        }
    }
    
}
