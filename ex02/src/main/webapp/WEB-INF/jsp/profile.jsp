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
    <h2>
        <c:out value="${sessionScope.authUser.name}"/>
        <c:out value="${sessionScope.authUser.surname}"/>
        <br>
        <c:out value="${sessionScope.authUser.email}"/>
    </h2>

    <h2>
    <table>
        <thead>
        <tr>
            <th>Date</th>
            <th>Time</th>
            <th>IP</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sessionScope.authArr}" var="item">
            <tr>
                <td>${item.date}</td>
                <td>${item.time}</td>
                <td>${item.ip}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </h2>


    <a href="/logout">Logout</a>


</body>
</html>
