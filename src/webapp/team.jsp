<%-- 
    Document   : team
    Created on : 21 серп. 2018, 9:43:24
    Author     : Roman
--%>
<% 
    if(request.getSession().getAttribute("user")==null){
        response.sendRedirect("login.jsp");   
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="WEB-INF/jspf/includes.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="team"/> - ${team.teamName}</title>


    </head>
    <body>
        <%@include file="WEB-INF/jspf/navbar.jspf" %>
        <div class="page">
            <div class="row">
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
                <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                        <div class="panel panel-default">
                        <div class="panel-heading"><fmt:message key="team"/> - ${team.teamName}</div>
                        <div class="panel-body">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">id</th>
                                        <th scope="col"><fmt:message key="username"/></th>
                                        <th scope="col"><fmt:message key="isCaptain"/></th>
                                        <th scope="col"><fmt:message key="role"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${users}" var="user">
                                    <tr>
                                        <th scope="row">${user.userId}</th>
                                        <td>${user.userLogin}</td>
                                        <td>${user.isCaptain}</td>
                                        <td>${user.userRole.userRoleName}</td>
                                    </tr>
                                    </c:forEach> 
                                </tbody>
                            </table>
                            <br>
                            <form action="Controller">
                                <input type="hidden" name="command" value="deleteteam">
                                <fmt:message key="deleteTeam" var="deleteTeamMessage"/>
                                <button type="submit">${deleteTeamMessage}</button>
                            </form>
                        </div>
                  </div>
                </div>
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
            </div>
        </div>
    </body>
</html>
