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

<p>You are logged as <b>${username}</b></p>
<p><a href="${loginUrl}">Login</a> or <a href="${registrationUrl}">Register new user</a></p>
<p><a href="${logoutUrl}">Logout</a></p>

<h1>Favorites</h1>

<p>To create your own book list, you have to log in first</p>

<table style="width: 100%" border="1">
    <tr>
        <td>№</td>
        <td>Name</td>
        <td>Author</td>
        <td>Genre</td>
        <td>Year</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
</table>

</body>

</html>
