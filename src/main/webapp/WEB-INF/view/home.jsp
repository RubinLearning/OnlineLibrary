<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>
    <title>Home</title>
</head>

<body>

<c:url var="loginUrl" value="/login"/>
<c:url var="registrationUrl" value="/registration"/>
<c:url var="logoutUrl" value="/logout"/>
<c:url var="bookListUrl" value="/book/list"/>
<c:url var="deleteImgUrl" value="/resources/img/delete.png" />

<p>You are logged as <b>${username}</b></p>
<p><a href="${loginUrl}">Login</a> or <a href="${registrationUrl}">Register new user</a></p>
<p><a href="${logoutUrl}">Logout</a></p>

<h1>Favorites</h1>

<p>To create your own book list, you have to log in first</p>

<p><a href="${bookListUrl}">Search for book</a></p>

<table style="border: 1px solid; width: 100%; text-align:center">
    <thead style="background:#d3dce3">
    <tr>
        <td>Title</td>
        <td>Author</td>
        <td>Genre</td>
        <td>Year</td>
        <th></th>
    </tr>
    </thead>
    <tbody style="background:#ccc">
    <c:forEach items="${userBooks}" var="book">
        <c:url var="deleteUrl" value="/favorites/delete?id=${book.id}" />
        <c:url var="editUrl" value="/book/edit?id=${book.id}" />
        <tr>
            <td><a href="${editUrl}"><c:out value="${book.title}"/></a></td>
            <td><c:out value="${book.author}" /></td>
            <td><c:out value="${book.genre.name}" /></td>
            <td><c:out value="${book.year}" /></td>
            <td style = "width: 40px"><a href="${deleteUrl}"><img src="${deleteImgUrl}"/></a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty userBooks}">
    No books available.
</c:if>

</body>

</html>
