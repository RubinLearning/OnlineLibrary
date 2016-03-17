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
<c:url var="uploadUrl" value="/book/upload" />

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

<form:form modelAttribute="book" method="POST" action="${saveUrl}">

	<table>

		<tr>
			<td><form:label path="name">Name:</form:label></td>
			<td><form:input path="name"/></td>
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
	
	<input type="submit" value="Save" />

</form:form>

<p><a href="${addToFavoritesUrl}">Add to Favorites</a></p>

<h3>File Upload:</h3>
Select a file to upload: <br />
<form method="POST" action="${uploadUrl}" enctype="multipart/form-data">
	File to upload: <input type="file" name="file"><br />
	Name: <input type="text" name="name"><br /> <br />
	<input type="submit" value="Upload"> Press here to upload the file!
</form>

</body>
</html>