package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBController;
import model.User;

/**
 * Servlet implementation class Redirect
 */
@WebServlet("/RedirectLogin")
public class RedirectLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RedirectLogin() {
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
		User user = (User) request.getSession().getAttribute("user");

		if (user == null || user.isNull()) {
			request.getRequestDispatcher("pages/Login.jsp").forward(request, response);
		} else if (user.getUserType().equals("Student")) {

			DBController dbc = new DBController();
			request.setAttribute("enrolledCourses", dbc.getRegisteredCoursesDetails(user.getUsername()));
			request.setAttribute("otherCourses", dbc.getNotRegisteredCoursesDetails(user.getUsername()));
			request.getRequestDispatcher("pages/MyLearning.jsp").forward(request, response);
		} else if (user.getUserType().equals("Teachers")) {

			DBController dbc = new DBController();
			request.setAttribute("assignedCourses", dbc.getAssignedCoursesDetails(user.getUsername()));
			request.getRequestDispatcher("pages/MyCourses.jsp").forward(request, response);
		} else if (user.getUserType().equals("Admin")) {
			// redirect to admin portal
		} else {
			request.getRequestDispatcher("pages/Error.jsp").forward(request, response);
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
