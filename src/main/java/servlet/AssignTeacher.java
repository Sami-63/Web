package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBController;

/**
 * Servlet implementation class AssignTeacher
 */
@WebServlet("/AssignTeacher")
public class AssignTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AssignTeacher() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String courseId = request.getParameter("courseId");
		String username = request.getParameter("username");

		DBController dbc = new DBController();
		dbc.assignTeacherToCourse(username, courseId);
		request.setAttribute("entolledStudents", dbc.getEnrolledStudents(courseId));
		request.setAttribute("otherTeachers", dbc.getNotAssigedTeacher(courseId));
		request.getRequestDispatcher("/pages/Course.jsp").forward(request, response);
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
