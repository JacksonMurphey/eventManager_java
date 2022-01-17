<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ page isErrorPage="true" %> 

<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU"
                crossorigin="anonymous">
<meta charset="UTF-8">
<title>Login or Register</title>
</head>
<body>
<jsp:include page="base.jsp"></jsp:include>

<div class="container">
    
    <div class="row">
        <div class="col bg-light m-2 border">
            <h2>Register</h2>
            <div class=" container">
                    <div class="container p-3 mb-2">

                        <strong>
                            <p class="text-lg-left" style="font-size: larger;"><form:errors path="user.*"/></p>
                        </strong>

                    </div>
                </div>
            <form:form action="/" method="POST" class="m-1" modelAttribute="user">
                
                <div class="form-group m-1">
                    <form:label path="firstName">First Name</form:label>
                    <form:input class="form-control" path="firstName" />
                </div>
                <div class="form-group m-1">
                    <form:label path="lastName">Last Name</form:label>
                    <form:input class="form-control" path="lastName" />
                </div>
                <div class="form-group m-1">
                    <form:label path="email">Email</form:label>
                    <form:input type="email" class="form-control" path="email" />
                </div>
                <div class="form-group m-1">
                    <form:label path="city">City</form:label>
                    <form:input class="form-control" path="city" />
                </div>
                <div class="form-group m-1">
                    <form:label path="state">State</form:label>
                    <form:select path="state">
                        <c:forEach items="${states}" var="state">
                            <option value="${state}">${state}</option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="form-group m-1">
                    <form:label path="password">Password</form:label>
                    <form:password class="form-control" path="password" />
                </div>
                <div class="form-group m-1">
                    <form:label path="passwordConfirmation">Confirm Password</form:label>
                    <form:password class="form-control" path="passwordConfirmation" />
                </div>

                <input type="submit" name="" id="" value="Register" class="btn btn-success m-2">
            </form:form>

        </div>


        <div class="col bg-light m-2 border">
            <h2>Login</h2>

            <form action="/login" method="POST" class="m-1">
                <div class=" container">
                    <div class="container p-3 mb-2">

                        <strong>
                            <p class="text-lg-left" style="font-size: larger;">${error}</p>
                        </strong>
                    </div>
                </div>
                <div class="form-group m-1">
                    <label for="email">Email</label>
					<input type="email" name="email" id="email" class="form-control" />
                </div>
                <div class="form-group m-1">
                    <label for="password">Password</label>
			        <input type="password" name="password" id="password" class="form-control" />
                </div>
                <input type="submit" value="Login" class="btn btn-success m-2">
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
        crossorigin="anonymous"></script>

</body>
</html>