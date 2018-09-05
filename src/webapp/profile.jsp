<%-- 
    Document   : profile
    Created on : 20 серп. 2018, 14:19:08
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
        <title><fmt:message key="profile" var="profileMessage"/></title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navbar.jspf" %>
        <div class="page">
            <div class="row">
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
                <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                        <div class="panel panel-default">
                        <div class="panel-heading">${profileMessage}</div>
                        <div class="panel-body">
                            <h1><fmt:message key="hello"/>, ${sessionScope.user.userLogin}</h1>
                            <h2><fmt:message key="yourPassword"/>: ${sessionScope.user.userPassword}</h2>
                            <h2><fmt:message key="yourTeam"/>: ${sessionScope.user.team.teamName}</h2>
                            <form action="Controller">
                                <input type="hidden" name="command" value="delete"/>
                                <fmt:message key="deleteProfile" var="deleteProfileMessage"/>
                                <input type="submit" value="${deleteProfileMessage}" name="delete profile" />
                            </form>
                        </div>
                  </div>
                </div>
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
            </div>
        </div>    
    </body>
</html>
