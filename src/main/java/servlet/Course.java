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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String course_id = request.getParameter("courseId");
//		System.out.println("-> " + course_id );
		
		if(course_id==null || course_id.equals("")) {
			request.setAttribute("courses", dbc.getAllCourses());
			request.getRequestDispatcher("pages/AllCourse.jsp").forward(request, response);
		}else {
			// get course
			model.Course course = dbc.getCourse(course_id);
			request.setAttribute("courseTitle", course.getTitle());
			request.setAttribute("courseDescription", course.getDescription());
			request.setAttribute("assignedTeachers", dbc.getCourseTeachers(course_id));
			
			User user = (User)request.getSession().getAttribute("user");
			if(user == null);
			else if(user.getUserType().equals("Teachers") || user.getUserType().equals("Admin")){
				request.setAttribute("entolledStudents", dbc.getEnrolledStudents(course_id));
			}
			
			request.getRequestDispatcher("pages/Course.jsp").forward(request, response);
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
