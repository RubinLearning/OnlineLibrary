<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cash manager</title>
</head>
<body>

<c:choose>
    <c:when test="${type=='edit'}">
		<c:url var="saveUrl" value="/app/account/edit?id=${accountAttribute.id}" />
		<h2>edit Account</h2>
    </c:when>
    <c:when test="${type=='add'}">
    	<c:url var="saveUrl" value="/app/account/add" />
    	<h2>new Account</h2>
    </c:when>
</c:choose>

<form:form modelAttribute="accountAttribute" method="POST" action="${saveUrl}">
	<table>
		<tr>
			<td><form:label path="name">Name:</form:label></td>
			<td><form:input path="name"/></td>
		</tr>

		<tr>
			<td><form:label path="currency">Currency:</form:label></td>
			<td><form:select required="required" path="currency">
			<c:forEach items="${currencyAttribute}" var="currency">
            	<option value="${currency.id}" <c:if test="${currency.id==accountAttribute.currency.id}">selected</c:if>>${currency.name}</option>
            </c:forEach>
           	</form:select></td>
        </tr>

	</table>
	
	<input type="submit" value="Save" />
</form:form>

</body>
</html>