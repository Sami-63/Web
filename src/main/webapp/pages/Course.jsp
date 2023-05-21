<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ page isELIgnored="false" %> <%@taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Course</title>
    <link rel="stylesheet" href="css/Home2.css" />
    <link rel="stylesheet" href="css/Course.css" />
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

    <div class="screen">
      <div class="child-screen left">
        <!-- <p class="name">${requestScope.courseTitle }</p> -->
        <p class="name">Structured Programming Language</p>
        <!-- <p class="description">${requestScope.courseDescription }</p> -->
        <p class="description">
          Lorem Ipsum is simply dummy text of the printing and typesetting
          industry. Lorem Ipsum has been the industry's standard dummy text ever
          since the 1500s, when an unknown printer took a galley of type and
          scrambled it to make a type specimen book. It has survived not only
          five centuries, but also the leap into electronic typesetting,
          remaining essentially unchanged. It was popularised in the 1960s with
          the release of Letraset sheets containing Lorem Ipsum passages, and
          more recently with desktop publishing software like Aldus PageMaker
          including versions of Lorem Ipsum.
        </p>

        <c:if
          test="${ sessionScope.user != null &&  sessionScope.user.getUserType() == 'admin'}"
        >
          <div class="scroll">
            <h3>Other Teachers</h3>
            <ul class="list">
              <c:forEach var="teacher" items="${otherTeachers }">
                <li class="list_item">
                  <a>${teacher.getName() }</a>
                  <form
                    action="AssignTeacher?courseId=${ requestScope.courseId }&username=${teacher.getUsername() }"
                    method="post"
                  >
                    <button type="submit" class="submit-login">Assign</button>
                  </form>
                </li>
              </c:forEach>
            </ul>
          </div>
          <!-- <div>
                  <div>
                      <h3 style="padding-top: 5rem">Other Teachers</h3>
                      <ul class="list-group">
                          <c:forEach var="teacher" items="${otherTeachers }">
                              <li class="list-group-item" style="
                                  display: flex;
                                  justify-content: space-between;
                                  align-items: center;
                                  ">
                                  ${teacher.getName() }
                                  <form
                                      action="AssignTeacher?courseId=${ requestScope.courseId }&username=${teacher.getUsername() }"
                                      method="post">
                                      <button type="submit" class="submit-login">
                                          Assign Teacher
                                      </button>
                                  </form>
                              </li>
                          </c:forEach>
                      </ul>
                  </div>
              </div> -->
        </c:if>

        <c:if test="${ requestScope.enrolled == false }">
          <div class="enroll">
            <form
              action="EnrollCourse?courseId=${requestScope.courseId }"
              method="post"
            >
              <button class="submit-login">Enroll Course</button>
            </form>
          </div>
        </c:if>
        
      </div>
      <div class="child-screen right">
        <div class="scroll">
          <h3>Assigned Teachers</h3>
          <ul class="list">
            <c:forEach var="teacher" items="${requestScope.assignedTeachers}">
              <li class="list_item">
                <a href="Profile?username=${teacher.getUsername() }">
                  ${teacher.getName() }
                </a>
              </li>
            </c:forEach>
          </ul>
        </div>

        <c:if
          test="${ sessionScope.user != null &&  requestScope.showEnrolledStudents == true }"
        >
          <div class="scroll">
            <h3>Enrolled Students</h3>
            <ul class="list">
              <c:forEach
                var="student"
                items="${requestScope.entolledStudents }"
              >
                <li class="list_item">
                  <a href="Profile?username=${student.getUsername() }">
                    ${student.getName() }
                  </a>
                </li>
              </c:forEach>
            </ul>
          </div>
        </c:if>
      </div>
    </div>
  </body>
</html>
