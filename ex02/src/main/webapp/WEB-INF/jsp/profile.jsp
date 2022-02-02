<%--
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
    <c:out value="${sessionScope.user.name}"/>
    <c:out value="${sessionScope.user.surname}"/>
    <c:out value="${sessionScope.user.email}"/>
</body>
</html>
