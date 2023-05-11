package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBController;
import model.User;

/**
 * Servlet implementation class Course
 */
@WebServlet("/Course")
public class Course extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBController dbc;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Course() {
		super();
		dbc = new DBController();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String course_id = request.getParameter("courseId");
		System.out.println("-> " + course_id);

		if (course_id == null || course_id.equals("")) {
			System.out.println("redirecting to all courses");
			request.setAttribute("courses", dbc.getAllCourses());
			request.getRequestDispatcher("pages/AllCourses.jsp").forward(request, response);
		} else {
			// get course
			model.Course course = dbc.getCourse(course_id);
			request.setAttribute("courseId", course.getCourseId());
			request.setAttribute("courseTitle", course.getTitle());
			request.setAttribute("courseDescription", course.getDescription());
			request.setAttribute("assignedTeachers", dbc.getCourseTeachers(course_id));

			User user = (User) request.getSession().getAttribute("user");
			if (user == null)
				System.out.println("user is null");
			else
				user.show();

			if (user == null)
				;
			else if (user.getUserType().equals("teacher")) {
				request.setAttribute("entolledStudents", dbc.getEnrolledStudents(course_id));
			} else if (user.getUserType().equals("student")) {

				boolean flag = false;
				List<model.Course> courseList = dbc.getStudentCourses(user.getUsername());
				for (model.Course c : courseList)
					if (c.getCourseId().equals(course_id)) {
						flag = true;
						break;
					}

				request.setAttribute("enrolled", flag);
			} else if (user.getUserType().equals("admin")) {
				request.setAttribute("entolledStudents", dbc.getEnrolledStudents(course_id));
				request.setAttribute("otherTeachers", dbc.getNotAssigedTeacher(course_id));
			}

			request.getRequestDispatcher("pages/Course.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
