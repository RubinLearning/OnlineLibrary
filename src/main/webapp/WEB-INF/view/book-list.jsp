<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Library</title>
</head>
<body>

<c:url var="homeUrl" value="/" />
<c:url var="deleteImgUrl" value="/resources/img/delete.png" />
<c:url var="addUrl" value="/book/add" />
<c:url var="logoutUrl" value="/logout"/>

<table style="width: 100%">

<tr>
<td style="text-align:left">
<h1>Books</h1>
<p><a href="${homeUrl}">Go back to home page</a></p>
</td>
<td style="text-align:right; vertical-align:top">
Logged as <b>${username}</b>
<p><a href="${logoutUrl}">Logout</a></p>
</td>
</tr>

<tr>
<td COLSPAN=2>

<table style="border: 1px solid; width: 100%; text-align:center">
	<thead style="background:#d3dce3">
		<tr>
			<th>Name</th>
			<th>Author</th>
            <th>Year</th>
			<th></th>
		</tr>
	</thead>
	<tbody style="background:#ccc">
	<c:forEach items="${books}" var="book">
		<c:url var="deleteUrl" value="/book/delete?id=${book.id}" />
		<c:url var="editUrl" value="/book/edit?id=${book.id}" />
		<tr>
			<td><a href="${editUrl}"><c:out value="${book.name}"/></a></td>
			<td><c:out value="${book.author}" /></td>
			<td><c:out value="${book.year}" /></td>
			<td style = "width: 40px"><a href="${deleteUrl}"><img src="${deleteImgUrl}"/></a></td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<c:if test="${empty books}">
	No books available.
</c:if>

<p><a href="${addUrl}">Add new book</a></p>

</td>
</tr>

</table>

</body>
</html>