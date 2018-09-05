/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Roman
 */
public class NoCommand implements Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("messageError", "Bad command");
        return "/error.jsp";
    }
    
}
