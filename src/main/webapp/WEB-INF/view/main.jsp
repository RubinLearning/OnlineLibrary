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

<p>Hello ${username}</p>
<p><a href="${loginUrl}">Login here</a><p>
<p><a href="${registrationUrl}">Register new user</a><p>

</body>

</html>
