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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(dbc.checkLogin(username, password)) {
			User user = dbc.getUser(username);
			
			if(user.getUserType().equals("Student")) {
				request.getSession().setAttribute("User", user);
				request.setAttribute("enrolledCourses", dbc.getRegisteredCourses(username));
				request.setAttribute("otherCourses", dbc.getNotRegisteredCourses(username));
				request.getRequestDispatcher("MyLearning.jsp").forward(request, response);
			}
				
		}else {
			request.setAttribute("loginFail", true);
			request.getRequestDispatcher("RedirectLogin").forward(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
