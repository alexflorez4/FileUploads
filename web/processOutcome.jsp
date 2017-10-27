<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

	<h2>File Processor</h2>
	<h5>Items out of stock - Remove from Amz:</h5>
	<ol>
        <c:forEach items="${itemSetResult}" var="item">
			<c:if test = "${item.notes == 'Supplier is out of stock.'}">
			    <li>${item.sku}</li>
			</c:if>
		</c:forEach>
	</ol>

	<h5>Items now available. Update Amz:</h5>
    <ol>
        <c:forEach items="${itemSetResult}" var="item">
            <c:if test = "${item.notes == 'Item is now available. Update Amz.'}">
                <li>${item.sku}</li>
            </c:if>
        </c:forEach>
    </ol>

    <h5>Items Price increase</h5>
    <ol>
        <c:forEach items="${itemSetResult}" var="item">
            <c:if test = "${item.notes == 'Attention: Price increased'}">
                <li>${item.sku}</li>
            </c:if>
        </c:forEach>
     </ol>

    <h5>Items price decreased</h5>
    <ol>
        <c:forEach items="${itemSetResult}" var="item">
            <c:if test = "${item.notes == 'Attention: Price decreased'}">
                <li>${item.sku}</li>
            </c:if>
        </c:forEach>
    </ol>

    <h5>Items Not Found</h5>
    <ol>
        <c:forEach items="${itemSetResult}" var="item">
            <c:if test = "${item.notes == 'SKU is not on supplier list.'}">
                <li>${item.sku}</li>
            </c:if>
        </c:forEach>
    </ol>
</body>
</html>