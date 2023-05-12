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
 * Servlet implementation class RedirectCreateCourse
 */
@WebServlet("/RedirectCreateCourse")
public class RedirectCreateCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedirectCreateCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in create course");
		User user = (User)request.getSession().getAttribute("user");
		DBController dbc = new DBController();
		
		if(user == null) {
			request.setAttribute("courses", dbc.getAllCourses());
			request.setAttribute("teachers", dbc.getAllTeachers());
			request.getRequestDispatcher("pages/Home.jsp").forward(request, response);		
			
		}else if(user.getUserType().equals("student")) {

			request.setAttribute("enrolledCourses", dbc.getRegisteredCourses(user.getUsername()));
			request.setAttribute("otherCourses", dbc.getNotRegisteredCourses(user.getUsername()));
			request.getRequestDispatcher("pages/MyLearning.jsp").forward(request, response);
			
		}else if(user.getUserType().equals("teacher")) {
			
			request.setAttribute("assignedCourses", dbc.getAssignedCourses(user.getUsername()));
			request.getRequestDispatcher("pages/MyCourses.jsp").forward(request, response);
			
		}else if(user.getUserType().equals("admin")) {
			
			request.setAttribute("courseId", "");
			request.setAttribute("courseTitle", "");
			request.setAttribute("courseDescription", "");
			request.getRequestDispatcher("pages/CreateCourse	.jsp").forward(request, response);
			
		}else {
			request.getRequestDispatcher("pages/Error.jsp").forward(request, response);
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
