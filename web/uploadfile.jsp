<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Upload Files</title>
</head>
<body>
	<h1>File Processor</h1>

	<form:form action="processExcel.do" method="post" enctype="multipart/form-data">
		<div>Excel File:</div>
		AZ File: <input name="azfile" type="file">
		User File: <input name="userFile" type="file">
		<input type="submit" value="Submit">
	</form:form>

    <form:form action="processOrders.do" method="post" enctype="multipart/form-data">
        <div>Sample app</div>
        Sample File: <input name="orderNumberFile" type="file">
        <input type="submit" value="Submit">
    </form:form>
	<hr>
</body>
</html>