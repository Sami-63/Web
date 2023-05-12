<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ page isELIgnored="false" %> <%@taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Home</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="css/Home.css" />
  </head>
  <body>
    <nav class="navbar navbar-expand-lg">
      <div class="container">
        <a class="navbar-brand mr-5" href="Home">Dream Academy</a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link" href="Course">Courses</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="Teachers">Teachers</a>
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

    <!-- Hero section -->
    <div
      class="p-5 text-center bg-image rounded-3"
      style="height: 400px; background-color: rgba(0, 0, 0, 0.2)"
    >
      <div class="mask">
        <div class="d-flex justify-content-center align-items-center h-100">
          <div>
            <h1 class="display-1 mb-3" style="margin-top: 25vh">
              Dream Academy
            </h1>
          </div>
        </div>
      </div>
    </div>

    <!-- Course section -->
    <div class="container-fluid mx-5" style="margin-top: 4rem">
      <h1 class="display-5 my-2" style="padding-left: 2rem">All courses</h1>
      <div class="row">
        <c:forEach var="course" items="${requestScope.courses}">
          <div class="col-3 mx-5 my-4 __card">
            <a
              href="Course?courseId=${course.getCourseId() }"
              style="margin: 0; padding: 0; text-decoration: none; color: black"
            >
              ${course.getTitle() }
            </a>
          </div>
        </c:forEach>

        <c:if test="${ sessionScope.user != null && sessionScope.user.getUserType() == 'admin'}">
          <div class="col-3 mx-5 my-4 __card">
            <a
              href="RedirectCreateCourse"
              style="margin: 0; padding: 0; text-decoration: none; color: black"
            >
              Add course
            </a>
          </div>
        </c:if>
        <!-- 
			<div class="col-3 mx-5 my-4 __card">Science</div>
			<div class="col-3 mx-5 my-4 __card">Statistics</div>
			<div class="col-3 mx-5 my-4 __card">Programming</div>
			<div class="col-3 mx-5 my-4 __card">Chemistry</div>
			<div class="col-3 mx-5 my-4 __card">Biology</div>
			<div class="col-3 mx-5 my-4 __card">Physics</div>
			<div class="col-3 mx-5 my-4 __card">English</div>
			<div class="col-3 mx-5 my-4 __card">jshakjhs</div>
			 -->
      </div>
    </div>
    <!-- Teacher section -->
    <div class="container-fluid mx-5" style="margin-top: 4rem">
      <h1 class="display-5 my-2" style="padding-left: 2rem">Our Teachers</h1>
      <div class="row">
        <c:forEach var="teacher" items="${requestScope.teachers}">
          <div class="col-3 mx-5 my-4 __card2">
            <a
              href="Profile?username=${teacher.getUsername() }"
              style="margin: 0; padding: 0; text-decoration: none; color: black"
            >
              ${teacher.getName() }
            </a>
          </div>
        </c:forEach>
        <!-- 
			<div class="col-3 mx-5 my-4 __card2">Math</div>
			<div class="col-3 mx-5 my-4 __card2">Science</div>
			<div class="col-3 mx-5 my-4 __card2">Statistics</div>
			<div class="col-3 mx-5 my-4 __card2">Programming</div>
			<div class="col-3 mx-5 my-4 __card2">Chemistry</div>
			<div class="col-3 mx-5 my-4 __card2">Biology</div>
			<div class="col-3 mx-5 my-4 __card2">Physics</div>
			<div class="col-3 mx-5 my-4 __card2">English</div>
			<div class="col-3 mx-5 my-4 __card2">jshakjhs</div>
			 -->
      </div>
    </div>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
      integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js"
      integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
