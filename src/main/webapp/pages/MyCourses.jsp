<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ page isELIgnored="false"%> <%@taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>My Courses</title>
    <link rel="stylesheet" href="css/Home2.css" />
  </head>
  <body>
    <nav class="container">
      <div class="container1">
        <a class="logo" href="Home">Dream Academy</a>
        <ul class="">
          <li class=""><a class="" href="Course">Courses</a></li>
          <li class=""><a class="" href="Teachers">Teachers</a></li>
        </ul>
      </div>

      <div class="container2">
        <c:if test="${sessionScope.user == null }">
          <form action="RedirectLogin" method="post">
            <button class="login">Login</button>
          </form>
          <form action="RedirectRegister" method="post">
            <button class="register">Register</button>
          </form>
        </c:if>

        <c:if test="${sessionScope.user != null }">
          <a
            href="Profile?username=${sessionScope.user.getUsername() }"
            class="displayName"
          >
            ${sessionScope.user.getUsername() }
          </a>
          <form action="Logout" method="post">
            <button class="logout">Logout</button>
          </form>
        </c:if>
      </div>
    </nav>

    <div class="title">
      <p>${sessionScope.user.getName() }</p>
    </div>

    <div class="wrapper">
      <div class="course">
        <h3 class="">My Courses</h3>
        <div class="row">
          <c:forEach var="course" items="${requestScope.assignedCourses}">
            <div class="item">
              <a href="Course?courseId=${course.getCourseId() }">
                ${course.getTitle() }
              </a>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>
  </body>
</html>
