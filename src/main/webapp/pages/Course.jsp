<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Course</title>
<link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
      crossorigin="anonymous"
    />
  </head>
</head>
<body>
	<nav class="navbar navbar-expand-lg">
		<div class="container">
			<a class="navbar-brand mr-5" href="Home">Dream Academy</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" href="Course">Courses</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="Teachers">Teachers</a>
					</li>
				</ul>

				<form action="RedirectLogin" method="post">
					<button class="btn btn-primary mx-3">Login</button>
				</form>
				<form action="RedirectRegister" method="post">
					<button class="btn btn-outline-primary">Register</button>
				</form>
			</div>
		</div>
	</nav>
	
	<!--  -->	
	
	<div style="display: flex; justify-content: space-between">
      <div class="col-9">
        <div class="display-1">${requestScope.courseTitle }</div>
        <p>
          ${requestScope.courseDescription }
        </p>
      </div>
      <div class="col-2" style="margin-right: 50px">
        <div>
          <h3 style="padding-top: 5rem">Assigned Teachers</h3>
          <ul class="list-group">
          	<c:forEach var="teacher" items="${requestScope.assignedTeachers}" >
          		<li class="list-group-item">
          			<a href="Profile?username=${teacher.getUsername() }" style="margin:0; padding:0; text-decoration:none; color:black;">
				 		${teacher.getName() }
				 	</a>
				 </li>
          	</c:forEach>
          	<!-- 
            <li class="list-group-item">An active item</li>
            <li class="list-group-item">A second item</li>
            <li class="list-group-item">A third item</li>
            <li class="list-group-item">A fourth item</li>
            <li class="list-group-item">And a fifth one</li>
             -->
          </ul>
        </div>
		<c:if test="${ sessionScope.user != null &&  ( sessionScope.user.getUserType == 'Teacher' || sessionScope.user.getUserType == 'Admin')}">
        	<div>
	          <h3 style="padding-top: 5rem">Enrolled Students</h3>
	          <ul class="list-group">
	          	<c:forEach var="student" items="${requestScope.entolledStudents }">
	          		<li class="list-group-item">${student.getName() }</li>
	          	</c:forEach>
	          	<!-- 
	            <li class="list-group-item">An active item</li>
	            <li class="list-group-item">A second item</li>
	            <li class="list-group-item">A third item</li>
	            <li class="list-group-item">A fourth item</li>
	            <li class="list-group-item">And a fifth one</li>
	            -->
	          </ul>
	        </div>	     
        </c:if>

      </div>
    </div>
</body>
</html>