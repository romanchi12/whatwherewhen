<%-- 
    Document   : signup
    Created on : 20 серп. 2018, 23:18:27
    Author     : Roman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="WEB-INF/jspf/includes.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="signUp"/></title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navbar.jspf" %>
        <div class="page">
            <div class="row">
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
                <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                        <div class="panel panel-default">
                        <div class="panel-heading"><fmt:message key="signUp"/></div>
                        <div class="panel-body">
                            <form method="post" action="Controller">
                                <div class="input-group">
                                    <input type="hidden" name="command" value="signup"/>
                                    <fmt:message key="username" var="usernameMessage"/>
                                    <fmt:message key="password" var="passwordMessage"/>
                                    <fmt:message key="passwordAgain" var="passwordAgainMessage"/>
                                    <input type="text" name="username" class="form-control" placeholder="${usernameMessage}">
                                    <input type="password" name="password" class="form-control" placeholder="${passwordMessage}">
                                    <input type="password" name="passwordagain" class="form-control" placeholder="${passwordAgainMessage}">
                                    <button type="submit" class="btn btn-success"><fmt:message key="signUp"/></button>
                                    <div role="alert">${messageError}</div>
                                </div>
                            </form>
                        </div>
                  </div>
                </div>
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
            </div>
        </div>
    </body>
</html>
