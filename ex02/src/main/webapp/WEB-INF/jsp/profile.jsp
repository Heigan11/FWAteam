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

    <form method="post" action="/images" enctype="multipart/form-data">
        <input type="file" name="file-name">
        <button type="submit">Upload</button>
    </form>

<%--    <img src="${pageContext.request.contextPath}/images/no-img.jpg" width="150" height="180">--%>
    <img src="data:<%=request.getAttribute("mimeType")%>;base64,<%=request.getAttribute("image")%>" style="height: 30%; width: 30%;">
    <br>


    <a href="/logout">Logout</a>
    <br>

    <h2>
        <table>
            <thead>
            <tr>
                <th>File name</th>
                <th>Size</th>
                <th>MIME</th>
                <th>Avatar</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.uplodedFiles}" var="item">
                <tr>
<%--                    <td>${item.substring(0, item.indexOf(" "))}</td>--%>
                    <td>
                        <a href="FWA/images/${item.substring(0, item.indexOf(" "))}" >${item.substring(0, item.indexOf(" "))}</a>
                    </td>
                    <td>${item.substring(item.indexOf(" "), item.lastIndexOf(" "))}</td>
                    <td>${item.substring(item.lastIndexOf(" "))}</td>
                    <td>
                        <a href="setAvatar/${item.substring(0, item.indexOf(" "))}" >Set</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </h2>

</body>
</html>
