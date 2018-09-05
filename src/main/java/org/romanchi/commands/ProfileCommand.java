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
import org.romanchi.database.dao.UserDao;
import org.romanchi.database.dao.UserDaoFactory;
import org.romanchi.database.entities.User;
import org.romanchi.database.DatabaseUtils;

/**
 *
 * @author Roman
 */
public class ProfileCommand implements Command{
    private final static Logger logger = Logger.getLogger(ProfileCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        UserDao userDao = UserDaoFactory.create();
        try {
            User user = userDao.findWhereUserIdEquals(Long.valueOf(request.getParameter("userId")));
            request.setAttribute("user", user);
            return "/profile.jsp";
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return "/profile.jsp";
    }
    
}
