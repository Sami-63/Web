<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ page isELIgnored="false" %> <%@taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>My Courses</title>
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

          <form action="Logout" method="post">
            <button class="btn btn-outline-primary">Logout</button>
          </form>
        </div>
      </div>
    </nav>

    <!-- Hero section -->
    <div
      class="p-5 text-center bg-image rounded-3"
      style="height: 300px; background-color: rgba(0, 0, 0, 0.2)"
    >
      <div class="mask">
        <div class="d-flex justify-content-center align-items-center h-100">
          <div>
            <h1 class="display-1 mb-3" style="margin-top: 25vh">
              ${sessionScope.user.getName() }
            </h1>
          </div>
        </div>
      </div>
    </div>

    <!-- My Course section -->
    <div class="container-fluid mx-5" style="margin-top: 4rem">
      <h1 class="display-5 my-2" style="padding-left: 2rem">My Courses</h1>
      <div class="row">
        <c:forEach var="course" items="${requestScope.assignedCourses}">
          <div class="col-3 mx-5 my-4 __card">
            <a
              href="Course?courseId=${course.getCourseId() }"
              style="margin: 0; padding: 0; text-decoration: none; color: black"
            >
              ${course.getTitle() }
            </a>
          </div>
        </c:forEach>
      </div>
    </div>
  </body>
</html>
