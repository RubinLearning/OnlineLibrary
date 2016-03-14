<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page session="true"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Successful registration</title>
</head>
<body>

<c:url var="loginUrl" value="/login"/>

<div>
    <h3>
	<p>You registered successfully!</p>
	<p>Now you can simply log in using your name and password.</p>
	</h3>
<a href="${loginUrl}">Login here</a>
</div>

</body>
</html>