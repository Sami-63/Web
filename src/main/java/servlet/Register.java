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
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("-> in register");
		
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");

		System.out.println("username -> " + username + " | \n");
		System.out.println("name -> " + name  + " | \n");
		System.out.println("email -> " + email  + " | \n");
		System.out.println("password -> " + password  + " | \n");
		System.out.println("usertype -> " + usertype  + " | \n");
		
		
		if(name == null || username == null || email == null || password == null || usertype == null || 
			name.equals("") || username.equals("") || email.equals("") || password.equals("") || usertype.equals("")) {
			
			request.setAttribute("username", username);
			request.setAttribute("email", email);
			request.setAttribute("name", name);
			
			request.getRequestDispatcher("RedirectRegister").forward(request, response);
			return;
		}
		
		DBController dbc = new DBController();
		User user = dbc.getUser(username);
		boolean emailError = dbc.isEmailExists(email);
			
		user.show();
		
		System.out.println("email error -> " + emailError);
		System.out.println("user == null -> " + (user == null));
		System.out.println("user.isNull() -> " + (user.isNull()));
	
		if ((user == null || user.isNull()) && emailError == false) {
			System.out.println("user banaitesi");
			dbc.addUser(name, username, password, email, usertype);

			user = dbc.getUser(username);
			HttpSession session = request.getSession(false);
			session.setAttribute("user", user);
			
			if (user.getUserType().equals("student")) {

				request.setAttribute("enrolledCourses", dbc.getRegisteredCourses(user.getUsername()));
				request.setAttribute("otherCourses", dbc.getNotRegisteredCourses(user.getUsername()));
				request.getRequestDispatcher("pages/MyLearning.jsp").forward(request, response);

			} else if (user.getUserType().equals("teacher")) {

				request.setAttribute("assignedCourses", dbc.getAssignedCourses(user.getUsername()));
				request.getRequestDispatcher("pages/MyCourses.jsp").forward(request, response);

			} else if (user.getUserType().equals("admin")) {

				request.setAttribute("courses", dbc.getAllCourses());
				request.setAttribute("teachers", dbc.getAllTeachers());
				request.getRequestDispatcher("pages/Home.jsp").forward(request, response);

			} else {
				request.getRequestDispatcher("pages/Error.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("username", username);
			request.setAttribute("email", email);
			request.setAttribute("name", name);
			if(emailError)
				request.setAttribute("registerEmailFail", "This email is already registered by another user");
			if( user.isNull() == false)
				request.setAttribute("registerUsernameError", "Username already exixts!");
			request.getRequestDispatcher("pages/Register.jsp").forward(request, response);
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
