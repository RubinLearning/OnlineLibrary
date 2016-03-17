<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Book</title>
</head>
<body>

<c:url var="addToFavoritesUrl" value="/favorites/add?id=${book.id}" />
<c:url var="downloadUrl" value="/book/content/download?id=${book.id}" />
<c:url var="readUrl" value="/book/content/read?id=${book.id}" />

<c:choose>
    <c:when test="${type=='edit'}">
		<c:url var="saveUrl" value="/book/edit?id=${book.id}" />
		<h2>edit Book</h2>
    </c:when>
    <c:when test="${type=='add'}">
    	<c:url var="saveUrl" value="/book/add" />
    	<h2>new Book</h2>
    </c:when>
</c:choose>

<form:form modelAttribute="book" method="POST" action="${saveUrl}" enctype="multipart/form-data">

	<table>

		<tr>
			<td><form:label path="title">Title:</form:label></td>
			<td><form:input path="title"/></td>
		</tr>

		<tr>
			<td><form:label path="author">Author:</form:label></td>
			<td><form:input path="author"/></td>
		</tr>

		<tr>
			<td><form:label path="year">Year:</form:label></td>
			<td><form:input path="year" type="number" min="0" max="2050" step="1"/></td>
		</tr>

		<tr>
			<td><form:label path="genre">Genre:</form:label></td>
			<td><form:select path="genre" required="required" >
			<c:forEach items="${genres}" var="genre">
            	<option value="${genre.id}" <c:if test="${genre.id==book.genre.id}">selected</c:if>>${genre.name}</option>
            </c:forEach>
           	</form:select></td>
        </tr>

		<tr>
			<td><form:label path="description">Description:</form:label></td>
			<td><form:textarea path="description"/></td>
		</tr>

	</table>

	<br/>
	Upload pdf: <input type="file" name="file" accept=".pdf"><br/>
	<br/>
	
	<input type="submit" value="Save" />

</form:form>

<p><a href="${addToFavoritesUrl}">Add to Favorites</a></p>

<c:if test="${contentAvailable==true}">
	<p><a href="${downloadUrl}">Download</a></p>
	<p><a href="${readUrl}">Read</a></p>
</c:if>

</body>
</html>