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
 * Servlet implementation class MyLearning
 */
@WebServlet("/MyLearning")
public class MyLearning extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBController dbc;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyLearning() {
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
		User user = (User) request.getSession().getAttribute("user");
		System.out.println(user == null);
		user.show();

		if (user.getUserType().equals("student")) {
			request.setAttribute("enrolledCourses", dbc.getRegisteredCoursesDetails(user.getUsername()));
			request.setAttribute("otherCourses", dbc.getNotRegisteredCoursesDetails(user.getUsername()));
			request.getRequestDispatcher("pages/MyLearning.jsp").forward(request, response);
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
