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
import org.romanchi.database.entities.Team;
import org.romanchi.database.DatabaseUtils;

/**
 *
 * @author Roman
 */
public class TeamsCommand implements Command{
    private final static Logger logger = Logger.getLogger(TeamsCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        TeamDao teamDao = TeamDaoFactory.create();
        try {
            Team[] teams = teamDao.findAll();
            request.setAttribute("teams", teams);
            return "/teams.jsp";
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            request.setAttribute("messageError", "Database error");
            return "/error.jsp";
        }
    }
    
}
