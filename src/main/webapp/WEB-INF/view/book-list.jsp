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
<c:url var="listUrl" value="/book/list"/>

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

		<td style="width: 15%">

			<b><a href="${listUrl}"><c:out value="ALL GENRES"/></a></b>

			<p>...</p>

			<c:forEach items="${genres}" var="genre">
				<c:url var="genreUrl" value="/book/list?gid=${genre.id}" />
				<p><a href="${genreUrl}"><c:out value="${genre.name}"/></a></p>
			</c:forEach>

			<p>...</p>

			<p><a href="${addUrl}">Add new book</a></p>

		</td>

		<td style="vertical-align: top">

			<form name="search_form" method="GET">
				<input type="text" name="search" value="" placeholder="Enter book title on author name" size="110"/>
				<input type="submit" value="Search" />
			</form>

			<p/>

			Books founded: ${booksQuantity}

			<p/>

			<table style="border: 1px solid; width: 100%; text-align:center">
				<thead style="background:#d3dce3">
				<tr>
					<th>Title</th>
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
						<td><a href="${editUrl}"><c:out value="${book.title}"/></a></td>
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

		</td>
	</tr>

</table>

</body>
</html>