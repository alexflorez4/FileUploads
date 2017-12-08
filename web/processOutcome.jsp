<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Upload Files</title>
</head>
<body>

    <li><a href='uploadfile.jsp'>Home</a></li>

	<h3>Accounts Inspector</h3>
	<h4>Items out of stock</h4>
	<ol>
        <c:forEach items="${itemSetResult}" var="item">
			<c:if test = "${fn:contains(item.notes, 'outOfStock')}">
			    <li>${item.sku} - ${item.account}</li>
			</c:if>
		</c:forEach>
	</ol>

	<h4>Items now available.</h4>
    <ol>
        <c:forEach items="${itemSetResult}" var="item">
            <c:if test = "${fn:contains(item.notes, 'available')}">
                <li>${item.sku} - ${item.account}</li>
            </c:if>
        </c:forEach>
    </ol>

    <h4>Price increase</h4>
    <ol>
        <c:forEach items="${itemSetResult}" var="item">
            <c:if test = "${fn:contains(item.notes, 'pIncreased')}">
                <li>${item.sku} - ${item.account}</li>
            </c:if>
        </c:forEach>
     </ol>

    <h4>Price decreased</h4>
    <ol>
        <c:forEach items="${itemSetResult}" var="item">
            <c:if test = "${fn:contains(item.notes, 'pDecreased')}">
                <li>${item.sku} - ${item.account}</li>
            </c:if>
        </c:forEach>
    </ol>

    <h4>Items Not Found</h4>
    <ol>
        <c:forEach items="${itemSetResult}" var="item">
            <c:if test = "${fn:contains(item.notes, 'notFound')}">
                <li>${item.sku}</li>
            </c:if>
        </c:forEach>
    </ol>
</body>
</html>