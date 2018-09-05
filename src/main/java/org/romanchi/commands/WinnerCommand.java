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
import org.romanchi.database.dao.TeamDao;
import org.romanchi.database.dao.TeamDaoFactory;
import org.romanchi.database.dao.UserRoleDao;
import org.romanchi.database.dao.UserRoleDaoFactory;
import org.romanchi.database.entities.Game;
import org.romanchi.database.entities.Team;
import org.romanchi.database.entities.User;
import org.romanchi.database.entities.UserRole;
import org.romanchi.database.DatabaseUtils;

/**
 *
 * @author Роман
 */
public class WinnerCommand implements Command{
    private final static Logger logger = Logger.getLogger(WinnerCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        GameDao gameDao = GameDaoFactory.create();
        TeamDao teamDao = TeamDaoFactory.create();
        UserRoleDao userRoleDao = UserRoleDaoFactory.create();
        Object gameRaw = request.getSession().getAttribute("game");
        Object userRaw = request.getSession().getAttribute("user");
        if(gameRaw == null || userRaw == null){
            request.setAttribute("messageError", "Clear cache and try again");
            return "/error.jsp";
        }
        Game game = (Game) gameRaw;
        User user = (User) userRaw;
        Team team = user.getTeam();
        UserRole winnerUserRole;
        
        if(game.getPointsExperts() >= 6){
            // team win
            try {
                winnerUserRole = userRoleDao.findWhereUserRoleNameEquals("expert")[0];
                game.setUserRole(winnerUserRole);
                gameDao.update(game);
                team.setTeamAllGamesNumber(team.getTeamAllGamesNumber() + 1);
                team.setTeamWinsNumber(team.getTeamWinsNumber() + 1);
                teamDao.update(team);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                request.setAttribute("messageError", "Database exception");
                return "/error.jsp";
            }
        }else if(game.getPointsQuestioners() >= 6){
            // team lose
            try {
                winnerUserRole = userRoleDao.findWhereUserRoleNameEquals("questioner")[0];
                game.setUserRole(winnerUserRole);
                gameDao.update(game);
                team.setTeamAllGamesNumber(team.getTeamAllGamesNumber() + 1);
                teamDao.update(team);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                request.setAttribute("messageError", "Database exception");
                return "/error.jsp";
            }
        }else{
            request.setAttribute("messageError", "Game hasn`t finished yet");
            return "/error.jsp";
        }
        request.getSession().removeAttribute("game");
        request.getSession().removeAttribute("question");
        request.setAttribute("winner", winnerUserRole.getUserRoleName());
        return "/winner.jsp";
    }
    
}
