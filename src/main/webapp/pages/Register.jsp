<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ page isELIgnored="false"%> <%@taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Register</title>
    <link rel="stylesheet" href="css/Register.css" />
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

    <div class="rest">
      <div class="box">
        <h2>Register</h2>
        <form action="Register" method="post">
          <div class="input-row">
            <label for="username">Username</label>
            <input
              type="text"
              id="username"
              name="username"
              value="${requestScope.username }"
              required
            />

            <c:if test="${requestScope.registerUsernameError!= null }">
              <div class="error">
                <p>*${requestScope.registerUsernameError}</p>
              </div>
            </c:if>
          </div>
          <div class="input-row">
            <label for="name">Name</label>
            <input
              type="text"
              id="name"
              name="name"
              value="${requestScope.name }"
              required
            />
          </div>
          <div class="input-row">
            <label for="email">Email</label>
            <input
              type="text"
              id="email"
              name="email"
              value="${requestScope.email }"
              required
            />

            <c:if test="${requestScope.registerEmailFail!= null }">
              <div class="error">
                <p>*${requestScope.registerEmailFail}</p>
              </div>
            </c:if>
          </div>
          <div class="input-row">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required />
          </div>

          <div class="check">
            <div class="value">
              <input
                type="radio"
                name="usertype"
                value="student"
                id="flexRadioDefault1"
                checked
              />
              <label for="flexRadioDefault1"> As Student </label>
            </div>
            <div class="value">
              <input
                type="radio"
                name="usertype"
                value="teacher"
                id="flexRadioDefault2"
              />
              <label for="flexRadioDefault2"> As Teacher </label>
            </div>
          </div>

          <button type="submit" class="submit-login">
            <p>Register</p>
          </button>
        </form>
      </div>
    </div>
  </body>
</html>
