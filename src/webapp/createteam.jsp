<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
    Document   : createteam
    Created on : 21 серп. 2018, 12:15:28
    Author     : Roman
--%>
<%
    if(request.getSession().getAttribute("user")==null){
        response.sendRedirect("/login.jsp");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="WEB-INF/jspf/includes.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create team</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navbar.jspf" %>
        <div class="page">
            <div class="row">
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
                <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                        <div class="panel panel-default">
                        <div class="panel-heading">Create team</div>
                        <div class="panel-body">
                            <form method="post" action="Controller">
                                <input type="hidden" name="command" value="createteam"/>
                                <fmt:message key="teamName" var="teamNameMessage"/>
                                <input name="teamname" type="text" class="form-control" placeholder="${teamNameMessage}"/>
                                <div id="users">
                                    <fmt:message key="username" var="usernameMessage"/>
                                    <input name="users[]" type="text" class="form-control" placeholder="${usernameMessage}"/>
                                </div>
                                <div style="margin-top: 5px;">
                                    <button id="addUser" type="button" class="btn btn-primary"><fmt:message key="addUser"/></button>
                                    <button type="submit" class="btn btn-success"><fmt:message key="createTeam"/></button>
                                </div>
                            </form>
                        </div>
                  </div>
                </div>
                <div class="col-12 col-sm-12 col-md-3 col-lg-3 col-xl-4"></div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#addUser").click(function(){
                $("#users").append('<input name="users[]" type="text" class="form-control" placeholder="Username"/>');
            });
        });
    </script>
</html>
