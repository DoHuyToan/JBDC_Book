<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/15/2021
  Time: 4:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>BOOKS MANAGER</h1>
<h2><a href="/books">LIST OF BOOKS</a></h2>
<h2><a HREF="/books?action=create">ADD NEW BOOK</a></h2>
<table border="1" cellpadding="5">
    <tr><h2>LIST OF BOOKS</h2></tr>
    <tr>
        <td>ID</td>
        <td>NAME</td>
        <td>PRICE</td>
        <td>DESCRIPTION</td>
        <td>CATEGORY</td>
        <td>EDIT</td>
        <td>DELETE</td>
    </tr>
    <c:forEach var="b" items="${bookList}">
        <tr>
            <td>${b.id}</td>
            <td>${b.name}</td>
            <td>${b.price}</td>
            <td>${b.description}</td>
            <td><c:forEach items="${b.getCategoryList()}" var="c">
                ${c.name}<br>
            </c:forEach></td>
            <td><a href="/books?action=edit&id=${b.id}">EDIT</a></td>
            <td><a href="/books?action=delete&id=${b.id}">DELETE</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
