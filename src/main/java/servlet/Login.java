package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBController;
import model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBController dbc;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
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
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		System.out.println("-> " + username + " " + password + " " + dbc.validUsernameAndPassword(username, password));

		if (dbc.validUsernameAndPassword(username, password)) {
			User user = dbc.getUserDetails(username);
			HttpSession session = request.getSession(false);
			session.setAttribute("user", user);

			if (user.getUserDetailsType().equals("student")) {
				System.out.println("redirecting to MyLearning");
				request.setAttribute("enrolledCourses", dbc.getRegisteredCoursesDetails(user.getUsername()));
				request.setAttribute("otherCourses", dbc.getNotRegisteredCoursesDetails(user.getUsername()));
				request.getRequestDispatcher("pages/MyLearning.jsp").forward(request, response);
			} else if (user.getUserType().equals("teacher")) {
				request.setAttribute("assignedCourses", dbc.getAssignedCoursesDetails(user.getUsername()));
				request.getRequestDispatcher("pages/MyCourses.jsp").forward(request, response);
			} else if (user.getUserType().equals("admin")) {
				request.getRequestDispatcher("Home").forward(request, response);
			}

		} else {
			User user = dbc.getUserDetails(username);
			request.setAttribute("username", username);
			user.show();
			if (user.isNull())
				request.setAttribute("loginFailMessage", "username doesn't exists");
			else
				request.setAttribute("loginFailMessage", "incorrect password");
			request.getRequestDispatcher("RedirectLogin").forward(request, response);
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
