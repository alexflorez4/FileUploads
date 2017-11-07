<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

	<link rel="stylesheet" href="js/css/screen.css">
	<link rel="stylesheet" href="js/css/cmxform.css">

	<script src="js/lib/jquery.js"></script>
	<script src="js/dist/jquery.validate.js"></script>
	<script src="js/dist/additional-methods.js"></script>
	<script src="js/validation/formValidator.js"></script>

    <title>Upload Files</title>
</head>
<body>
    <div class="container">
        <h1>File Processor</h1>

        <div class="jumbotron">
            <div class="form-group row">

                <form:form id="fileForm" action="processExcel.do" method="post" enctype="multipart/form-data">
                    <div class="row">
                        <h4 class="col-12">AZ Compare:</h4>
                    </div>
                    <div class="row">
                        <label for="azfileinput" class="col-sm-2 col-form-label">AZ File</label>
                        <div class="col-sm-10">
                            <input id="azfileinput" name="azfile"  type="file" class="form-control" required>
                        </div>
                    </div>
                    <div class="row">
                        <label for="userFileid" class="col-sm-2 col-form-label">User File: </label>
                        <div class="col-sm-10">
                            <input id="userFileid" name="userFile" type="file" class="form-control" required>
                        </div>
                    </div>
                    <input  type="submit" class="btn btn-success" value="Submit">
                </form:form>
            </div>
        </div>

        <div class="jumbotron">
            <div class="form-group row">
                <h4 class="col-sm-12">Tracking Update:</h4>

                <form:form id="fileForm1" action="processOrders.do" method="post" enctype="multipart/form-data">
                    <div class="row">
                        <label for="orderNumberFile" class="col-sm-2 col-form-label">File: </label>
                        <div class="col-sm-10">
                            <input id="orderNumberFile" name="orderNumberFile" type="file" class="form-control" required>
                        </div>
                    </div>
                    <input type="submit" class="btn btn-success" value="Submit">
                </form:form>
            </div>
        </div>

        <div class="jumbotron">
            <div class="form-group row">
                <h4 class="col-sm-12">Transactions CC:</h4>

                <form:form id="filecc" action="processTransactionsCC.do" method="post" enctype="multipart/form-data">
                    <div class="row">
                        <label for="FileTransCC" class="col-sm-2 col-form-label">CC File: </label>
                        <div class="col-sm-10">
                            <input id="FileTransCC" name="FileTransCC" type="file" class="form-control" required>
                        </div>
                    </div>
                    <input type="submit" class="btn btn-success" value="Submit">
                </form:form>
            </div>
        </div>

	</div> <!--container-->
</body>
</html>