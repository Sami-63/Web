<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Assign Teacher</title>
<link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
      crossorigin="anonymous"
    />
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

				
				<c:if test="${sessionScope.user == null }">
					<form action="RedirectLogin" method="post">
						<button class="btn btn-primary mx-3">Login</button>
					</form>
					<form action="RedirectRegister" method="post">
						<button class="btn btn-outline-primary">Register</button>
					</form>
				</c:if>
				
				<c:if test="${sessionScope.user != null }">
					<form action="Logout" method="post">
						<button class="btn btn-outline-primary">Logout</button>
					</form>
				</c:if>
			</div>
		</div>
	</nav>
	
	
	
</body>
</html>