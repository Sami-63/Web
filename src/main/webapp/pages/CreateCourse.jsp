<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ page isELIgnored="false" %> <%@taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Register</title>
    <link rel="stylesheet" href="css/Login.css">
    <link rel="stylesheet" href="css/AddCourse.css">
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
          <form action="Logout" method="post">
            <button class="logout">Logout</button>
          </form>
        </c:if>
      </div>
    </nav>

     <div class="rest">
        <div class="box">

            <h2>Add New Course</h2>
            <form action="CreateCourse" method="post">
                <div class="input-row">
                    <label for="name">Course ID</label>
                    <!-- <input type="text" id="name" name="courseId" value="${requestScope.courseId}" /> -->
                    <input type="text" id="name" name="courseId" />
                </div>

                <div class="input-row">
                    <label for="title">Title</label>
                    <!-- <input type="text" id="title" name="courseTitle" value="${requestScope.courseTitle}" /> -->
                    <input type="text" id="title" name="courseTitle" />
                </div>

                <div class="input-row">
                    <label for="description">Description</label>
                    <!-- <textarea id="description" rows="7" name="courseDescription"
                        value="${requestScope.courseDescription}"></textarea> -->
                    <textarea id="description" rows="5" name="courseDescription"
                        placeholder="Write Description About The Course"></textarea>
                </div>

                <button type="submit" class="submit-login">Create</button>
            </form>

        </div>
    </div>
  </body>
</html>
