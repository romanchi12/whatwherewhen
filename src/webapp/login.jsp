<%-- 
    Document   : login
    Created on : 20 серп. 2018, 19:29:46
    Author     : Roman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<% 
    if(request.getSession().getAttribute("user")!=null){
        response.sendRedirect("profile.jsp");   
    }
%>
<!DOCTYPE html>

<html>
    <head>
        <%@include file="WEB-INF/jspf/includes.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="logIn"/></title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navbar.jspf" %>
        <div class="page">
            <div class="row">
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
                <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                        <div class="panel panel-default">
                        <fmt:message key="logIn" var="loginMessage"/>
                        <div class="panel-heading">${loginMessage}</div>
                        <div class="panel-body">
                            <form method="post" action="Controller">
                                <input type="hidden" name="command" value="login"/>
                                <input name="userlogin" type="text"/>
                                <input name="userpassword" type="password"/>
                                <input type="submit" value="${loginMessage}" />
                                <div>${messageError}</div>
                            </form>
                        </div>
                  </div>
                </div>
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
            </div>
        </div>
    </body>
</html>
