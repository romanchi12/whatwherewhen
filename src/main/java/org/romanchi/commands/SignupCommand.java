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
import org.romanchi.database.dao.UserRoleDao;
import org.romanchi.database.dao.UserRoleDaoFactory;
import org.romanchi.database.entities.User;
import org.romanchi.database.DatabaseUtils;

/**
 *
 * @author Roman
 */
public class SignupCommand implements Command{
    private final static Logger logger = Logger.getLogger(SignupCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordAgain = request.getParameter("passwordagain");
        if(password.length( )>= 4 && password.equals(passwordAgain)){
            try {
                UserDao userDao = UserDaoFactory.create();
                UserRoleDao userRoleDao = UserRoleDaoFactory.create();
                TeamDao teamDao = TeamDaoFactory.create();
                User user = new User();
                user.setIsCaptain((short)0);
                user.setUserLogin(username);
                user.setUserPassword(password);
                user.setUserRole(userRoleDao.findWhereUserRoleIdEquals(1));
                userDao.insert(user);
                request.getSession().setAttribute("user", user);
                return "/profile.jsp";
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                request.setAttribute("messageError", "Database problem");
                return "/error.jsp";
            }
        }else{
            request.setAttribute("messageError", "Passwords are not equals");
            return "/signup.jsp";
        }
    }
    
}
