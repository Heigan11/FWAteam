<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <style>

        table {
            /*width: 300px; !* Ширина таблицы *!*/
            margin: auto; /* Выравниваем таблицу по центру */
        }

        .buttons {
            padding: 10px 0;
            border-width: 0;
            border-radius: 1em;
            display: block;
            width: 125px;
            height: auto;
            margin: 0 auto;
            background: #60e6c5;
            color: black;
            font-size: 15px;
            outline: none;
            text-transform: uppercase;
            text-align: center;
        }
        /*.tables {*/
        /*    width: 100%;*/
        /*    margin-bottom: 20px;*/
        /*    border: 1px solid black;*/
        /*    border-collapse: collapse;*/
        /*}*/
        /*.tables th {*/
        /*    font-weight: bold;*/
        /*    padding: 5px;*/
        /*    background: #efefef;*/
        /*    border: 1px solid black;*/
        /*    text-align: center;*/
        /*}*/
        /*.tables td {*/
        /*    border: 1px solid black;*/
        /*    padding: 5px;*/
        /*    text-align: center;*/
        /*}*/

        .tables {
            width: auto;
            border: none;
            margin: auto;
            border-collapse: separate;
            text-align: center;
        }
        .tables thead th {
            font-weight: bold;
            text-align: center;
            border: none;
            padding: 10px 15px;
            background: #EDEDED;
            font-size: 14px;
            border-top: 1px solid #ddd;
        }
        .tables tr th:first-child, .table tr td:first-child {
            border-left: 1px solid #ddd;
        }
        .tables tr th:last-child, .table tr td:last-child {
            border-right: 1px solid #ddd;
        }
        .tables thead tr th:first-child {
            border-radius: 20px 0 0 0;
        }
        .tables thead tr th:last-child {
            border-radius: 0 20px 0 0;
        }
        .tables tbody td {
            text-align: center;
            border: none;
            padding: 10px 15px;
            font-size: 14px;
            vertical-align: top;
        }
        .tables tbody tr:nth-child(even) {
            background: #F8F8F8;
        }
        .tables tbody tr:last-child td{
            border-bottom: 1px solid #ddd;
        }
        .tables tbody tr:last-child td:first-child {
            border-radius: 0 0 0 20px;
        }
        .tables tbody tr:last-child td:last-child {
            border-radius: 0 0 20px 0;
        }
    </style>
</head>
<body>
<table>
    <tbody>
    <tr>
        <td>

        <%--    <img src="${pageContext.request.contextPath}/images/no-img.jpg" width="150" height="180">--%>
            <img src="data:<%=request.getAttribute("mimeType")%>;base64,<%=request.getAttribute("image")%>"
                 width="280" height="320">
            <br>
            <br>
            <form method="post" action="/images" enctype="multipart/form-data">
                <input type="file" name="file-name">
                <button type="submit">Upload</button>
            </form>
        </td>
        <td>
            <h2>
                <c:out value="${sessionScope.authUser.name}"/>
                <c:out value="${sessionScope.authUser.surname}"/>
                <br>
                <c:out value="${sessionScope.authUser.email}"/>
            </h2>

            <div class="tables">
                <table>
                    <thead>
                    <tr>
                        <th><c:out value="Date"/></th>
                        <th><c:out value="Time"/></th>
                        <th><c:out value="IP"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.authArr}" var="item">
                        <tr>
                            <td><c:out value="${item.date}"/></td>
                            <td><c:out value="${item.time}"/></td>
                            <td><c:out value="${item.ip}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </td>
    </tr>
    </tbody>
</table>
<div class="buttons">
    <a href="/logout">Logout</a>
</div>
<br>

<div class="tables">
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
                    <a href="FWA/images/${item.substring(0, item.indexOf(" "))}">${item.substring(0, item.indexOf(" "))}</a>
                </td>
                <td>${item.substring(item.indexOf(" "), item.lastIndexOf(" "))}</td>
                <td>${item.substring(item.lastIndexOf(" "))}</td>
                <td>
                    <a href="setAvatar/${item.substring(0, item.indexOf(" "))}">Set</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
