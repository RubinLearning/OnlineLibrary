<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>
    <link href="<c:url value="/resources/css/reset.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/structure.css"/>" rel="stylesheet">
    <title>Login</title>
</head>

<body>

    <c:url var="registrationUrl" value="/registration"/>

    <form class="box login" action="login" method="POST">
        <fieldset class="boxBody">
            <label for="username">Username</label>
            <input type="text" id="username" name = "username" placeholder="username" required/>
            <label for="password">Password</label>
            <input type="password" id="password" name = "password" placeholder="password" required/>
        </fieldset>
        <footer>
            <label><input type="checkbox" name="_spring_security_remember_me">Remember me</label>
            <input type="submit" class="btnLogin" value="Login"/>
        </footer>
        <footer>
            <a href="${registrationUrl}">Register new user</a>
        </footer>
    </form>

</body>

</html>
