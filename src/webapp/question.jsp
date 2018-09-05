<%-- 
    Document   : question
    Created on : Aug 26, 2018, 6:05:23 PM
    Author     : Роман
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="WEB-INF/jspf/includes.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="question"/></title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navbarGame.jspf" %>
        <div class="page">
            <div class="row">
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"><div class="row"><fmt:message key="experts"/></div><div class="row">${sessionScope.game.pointsExperts}</div></div>
                <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">${sessionScope.question.questionName}</div>
                            <div class="panel-body">
                                <p>${sessionScope.question.questionDescription}</p>
                            </div>
                            
                            <!-- check question type -->
                            <form method="POST" action="Controller">
                                <input type="hidden" name="command" value="checkanswer"/>
                                <fmt:message key="yourAnswer" var="yourAnswerMessage"/>
                                <input type="text" class="form-control" style="margin-bottom: 5px;" placeholder="${yourAnswerMessage}"  name="answer" />
                                <button type="submit" class="btn btn-primary" style="margin-bottom: 5px;"><fmt:message key="answer"/></button>
                            </form>
                            <div class="hint">
                                ${sessionScope.hint}
                            </div>
                            <ul class="list-group">
                                <li class="list-group-item">
                                    <form action="Controller" method="post">
                                        <input type="hidden" name="command" value="hint" />
                                        <input type="hidden" name="hintTypeId" value="1" />
                                        <button type="submit" class="btn btn-alert" style="margin-top: 5px;"><fmt:message key="hint"/></button>
                                    </form>
                                </li>
                                <c:if test="${requestScope.hint != null}">
                                    <li class="list-group-item"><fmt:message key="hint"/>: ${hint}</li>
                                </c:if>


                            </ul>
                      </div>
                </div>
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"><div class="row"><fmt:message key="questioners" /></div><div class="row">${sessionScope.game.pointsQuestioners}</div></div>
            </div>
        </div>
    </body>
</html>
