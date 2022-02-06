<%@ page import="edu.school21.cinema.models.User" %>
<%@ page import="java.util.Optional" %><%--
  Created by IntelliJ IDEA.
  User: heigan
  Date: 02.02.2022
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<%--        <% User user = ((Optional<User>) request.getSession().getAttribute("user")).get();%>--%>
<%--        <h2><%=user.getName()%> <%=user.getSurname()%></h2>--%>
<%--        <h2><%=user.getEmail()%></h2>--%>

    <h2>
        <c:out value="${sessionScope.authUser.name}"/>
        <c:out value="${sessionScope.authUser.surname}"/>
        <br>
        <c:out value="${sessionScope.authUser.email}"/>
        <br>
        <c:forEach items="${sessionScope.authArr}" var="item">
            ${item}<br>
        </c:forEach>

    </h2>

    <a href="/logout">Logout</a>


</body>
</html>
