<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
			rel="stylesheet" 
			integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
			crossorigin="anonymous">
		<link rel="stylesheet" href="/css/main.css" />
		<meta charset="ISO-8859-1">
	<title>Event Planner</title>
</head>
<jsp:include page="base.jsp"></jsp:include>
	<body>
		<div class="container">
            <br>
			<h2 class="text-decoration-underline">${ event.name }</h2>
            <br>

			<div class="event-details-side">
				<h5 class="m-1"><strong>Host:</strong> <span class="text-primary">${ event.planner.firstName }</span></h5>
				<h5 class="m-1"><strong>Date:</strong> <span class="text-primary"><fmt:formatDate type="date" value="${ event.eventDate }"/></span></h5>
				<h5 class="m-1"><strong>Location:</strong> <span class="text-primary">${ event.city }, ${ event.state }</span></h5>
				<h5 class="m-1"><strong>People attending:</strong> <span class="text-primary">${ event.attendees.size() }</span></h5>
                <br>
                <br>
                <h3 class="text-decoration-underline">People Attending this Event:</h3>
                
                
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Name</th>
							<th>Traveling From</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${ event.attendees }" var="user">
						<tr>
							<td>${ user.firstName } ${ user.lastName }</td>
							<td>${ user.city }, ${ user.state }</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
            <br>
			<div class="event-details-side">
				<h3 class="text-decoration-underline">Message Wall</h3>
				<div class="messages">
				<c:forEach items="${ event.messages }" var="message">
                <hr>
					<p> <strong>${ message.author.firstName } says:</strong> ${ message.content } </p>
                    <p class="m-0"><span class="text-primary"> Posted at: <fmt:formatDate type="both" timeStyle="short" value="${ message.createdAt }"/></span></p>
                    <hr>
				</c:forEach>
				</div>
				<form action="/events/${ event.id }/comment" method="post">
					<div class="form-group">
						<label for="comment">Add Comment</label>
						<span>${ error }</span>
						<textarea name="comment" id="comment" class="form-control"></textarea>
						<button>Submit</button>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>