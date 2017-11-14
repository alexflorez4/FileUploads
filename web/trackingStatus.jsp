<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Tracking Results</title>
</head>
<body>

    <li><a href='uploadfile.jsp'>Home</a></li>

	<h2>Tracking Results</h2>
	<h5>Success:</h5>
	<ol>
        <c:forEach items="${orders}" var="order">
			<c:if test = "${order.status == 'Success'}">
			    <li>${order.orderNumber}</li>
			</c:if>
		</c:forEach>
	</ol>

    <h5>Failed:</h5>
    <ol>
        <c:forEach items="${orders}" var="order">
            <c:if test = "${order.status == 'Fail'}">
                <li>${order.orderNumber}</li>
            </c:if>
        </c:forEach>
    </ol>


</body>
</html>