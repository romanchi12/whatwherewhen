<%-- 
    Document   : teams
    Created on : 21 серп. 2018, 12:02:35
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
        <title><fmt:message key="teams"/></title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navbar.jspf" %>
        <div class="page">
            <div class="row">
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
                <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                        <div class="panel panel-default">
                        <div class="panel-heading"><fmt:message key="teams"/></div>
                        <div class="panel-body">
                            <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col"><fmt:message key="teamName"/></th>
                                    <th scope="col"><fmt:message key="allGames"/></th>
                                    <th scope="col"><fmt:message key="wins"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${teams}" var="team">
                                <tr>
                                    <th scope="row">${team.teamId}</th>
                                    <td>${team.teamName}</td>
                                    <td>${team.teamAllGamesNumber}</td>
                                    <td>${team.teamWinsNumber}</td>
                                </tr>
                                </c:forEach>
                            </tbody>
                          </table>
                        </div>
                  </div>
                </div>
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
            </div>
        </div>
    </body>
</html>
