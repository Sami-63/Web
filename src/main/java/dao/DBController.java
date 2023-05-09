package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Course;
import model.User;

public class DBController {

	private Connection conn;
	private String url = "jdbc:mysql://localhost:3306/web_project";
	private String user = "root", password = "web1234%";

	public DBController() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void makeConn() throws SQLException {
		this.conn = DriverManager.getConnection(url, user, password);
	}

	public void createTables() {
		String sql1 = "create table users (\n"
				+ "    name varchar(30),\n"
				+ "    username varchar(20),\n"
				+ "    password varchar(32),\n"
				+ "    email varchar(30),\n"
				+ "    user_type varchar(10) check(user_type in ('admin', 'student', 'teacher')),\n"
				+ "    primary key (username, email)\n"
				+ ");";
		String sql2 = "create table course(\n"
				+ "    course_id varchar(20),\n"
				+ "    title varchar(50),\n"
				+ "    description text,\n"
				+ "    primary key (course_id, title)\n"
				+ ");";
		String sql3 = "create table takes(\n"
				+ "    username varchar(20),\n"
				+ "    course_id varchar(20),\n"
				+ "    primary key (username, course_id),\n"
				+ "    foreign key (username) references users(username) on delete cascade,\n"
				+ "    foreign key (course_id) references course(course_id) on delete cascade\n"
				+ ");";
		String sql4 = "create table teaches(\n"
				+ "    username varchar(20),\n"
				+ "    course_id varchar(20),\n"
				+ "    primary key (username, course_id),\n"
				+ "    foreign key (username) references users(username) on delete cascade,\n"
				+ "    foreign key (course_id) references course(course_id) on delete cascade\n"
				+ ");";

		try {
			makeConn();
			Statement stm = conn.createStatement();
			stm.execute(sql1);
			stm.execute(sql2);
			stm.execute(sql3);
			stm.execute(sql4);

			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void addUser(String name, String username, String pass, String email, String type) {
		String sql = "insert into users values(?, ?, ?, ?, ?);";

		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, username);
			ps.setString(3, pass);
			ps.setString(4, email);
			ps.setString(5, type);
			ps.execute();

			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void addCourse(String id, String title, String Description) {
		String sql = "insert into course values(?, ?, ?);";

		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, title);
			ps.setString(3, Description);
			ps.execute();

			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void assignTeacherToCourse(String username, String course_id) {
		String sql = "insert into teaches values(?, ?);";

		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, course_id);
			ps.execute();

			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void registerCourse(String username, String course_id) {
		String sql = "insert into takes values(?, ?);";

		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, course_id);
			ps.execute();

			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public boolean checkLogin(String username, String pass) {
		String sql = "select * from users where username=? and password=?;";
		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, pass);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String db_username = rs.getString(1);
				String db_pass = rs.getString(2);
//				System.out.println("Authorized " + db_username + " - " + db_pass);
				conn.close();
				return true;
			} else {
//				System.out.println("Unauthorized");
				conn.close();
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public List getStudentCourses(String username) {
		String sql = "select takes.course_id, title, description from takes, course "
				+ "where takes.username = ? and takes.course_id = course.course_id;";
		List<Course> courseList = new ArrayList<>();
		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
//				System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
				String id = rs.getString(1);
				String title = rs.getString(2);
				String description = rs.getString(3);
				Course cs = new Course(id, title, description);
				courseList.add(cs);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return courseList;
	}

	public List getTeacherCourses(String username) {
		String sql = "select course.course_id, title, description from teaches, course \n"
				+ "where teaches.username = ? and teaches.course_id = course.course_id;";
		List<Course> courseList = new ArrayList<>();
		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
//				System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
				String id = rs.getString(1);
				String title = rs.getString(2);
				String description = rs.getString(3);
				Course cs = new Course(id, title, description);
				courseList.add(cs);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return courseList;
	}

	public List getAllCourses() {
		String sql = "select * from course;";
		
		List<Course> courseList = new ArrayList<>();
		
		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
//				System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
				String id = rs.getString(1);
				String title = rs.getString(2);
				String description = rs.getString(3);
				Course cs = new Course(id, title, description);
				courseList.add(cs);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return courseList;
	}
	
	public List getAllTeachers() {
		String sql = "select * from users where user_type = 'teacher';";
		
		List<User> teacherList = new ArrayList<>();
		
		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
//				System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
				String name = rs.getString(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				User user = new User(name, username, password, email, "Teacher");
				teacherList.add(user);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return teacherList;
	}
	
	private void deleteDB() {
		String sql1 = "drop table users;";
		String sql2 = "drop table course;";
		String sql3 = "drop table takes;";
		String sql4 = "drop table teaches;";

		try {
			makeConn();
			Statement stm = conn.createStatement();
			stm.execute(sql3);
			stm.execute(sql4);
			stm.execute(sql1);
			stm.execute(sql2);

			System.out.println("All tables deleted");
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public User getUser(String username) {
		User user = new User();
		
		String sql = "select * from users where username = ?";
		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			user.setName(rs.getString(1));
			user.setUsername(rs.getString(2));
			user.setPassword(rs.getString(3));
			user.setEmail(rs.getString(4));
			user.setUserType(rs.getString(5));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return user;
	}
	
	public Course getCourse(String course_id) {
		Course course = new Course();
		
		String sql = "select * from course where course_id = ?";
		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, course_id);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			course.setCourseId(rs.getString(1));
			course.setTitle(rs.getString(2));
			course.setDescription(rs.getString(3));
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return course;
	}
	
	public List getCourseTeachers(String courseId) {
		String sql = "select users.name, users.username from teaches, users where course_id = ? and teaches.username = users.username;";
		
		List<User> teacherList = new ArrayList<>();
		
		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, courseId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String name = rs.getString(1);
				String username = rs.getString(2);
				User user = new User(name, username, "", "", "Teacher");
				teacherList.add(user);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return teacherList;
	}
	
	public List getEnrolledStudents(String courseId) {
		String sql = "select users.name, users.username from takes, users where course_id = ? and takes.username = users.username;";
		
		List<User> studentList = new ArrayList<>();
		
		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, courseId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String name = rs.getString(1);
				String username = rs.getString(2);
				User user = new User(name, username, "", "", "Student");
				studentList.add(user);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return studentList;
	}
	
	public List getNotRegisteredCourses(String username) {
		String sql = "select course_id, title, description \n"
				+ "from course \n"
				+ "where course_id not in (\n"
				+ "    select course.course_id \n"
				+ "    from course, takes \n"
				+ "    where takes.course_id = course.course_id and takes.username = ?\n"
				+ ");";
		
		List<Course> courseList = new ArrayList<>();
		
		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String id = rs.getString(1);
				String title = rs.getString(2);
				String description = rs.getString(3);
				Course cs = new Course(id, title, description);
				courseList.add(cs);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return courseList;
	}
	
	public List getRegisteredCourses(String username) {
		String sql = "select course.course_id, title, description from course, takes where takes.course_id = course.course_id and takes.username = ?;";
		
		List<Course> courseList = new ArrayList<>();
		
		try {
			makeConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String id = rs.getString(1);
				String title = rs.getString(2);
				String description = rs.getString(3);
				Course cs = new Course(id, title, description);
				courseList.add(cs);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return courseList;
	}
	
	public static void main(String[] args) {
//		System.out.println("Lets start");
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");	
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_project", "root", "web1234%");
//			// here sonoo is database name, root is username and password
////			Statement stmt = con.createStatement();
////			ResultSet rs = stmt.executeQuery("select * from st");
////			while (rs.next())
////				System.out.println(rs.getString(1) + "  " + rs.getString(2));
//			con.close();
//			System.out.println("OK");
//		} catch (Exception e) {
//			System.out.println("Not OK");
//			System.out.println(e);
//		}
		DBController dbc = new DBController();
//		dbc.deleteDB();
		
//		dbc.createTables();
//		dbc.addUser("Sadman Sami", "sami", "123", "sami@gmail.com", "admin");
//		dbc.addUser("Blue Dragon", "blue", "124", "blue@gmail.com", "admin");
//		dbc.addUser("Nihan Ali", "nihan", "125", "nihan@gmail.com", "teacher");
//		dbc.addUser("Alomgir Hossain", "alom", "126", "alom@gmail.com", "teacher");
//		dbc.addUser("Abul Miah", "abul", "127", "abul@gmail.com", "student");
//		dbc.addUser("Babul Miah", "babul", "127", "babul@gmail.com", "student");
//		dbc.addUser("Minhaz Rahman", "minhaz", "657", "minhaz@gmail.com", "teacher");
//
//		dbc.addCourse("101", "Physics", "Basic Physics");
//		dbc.addCourse("102", "Chemistry", "Basic Chemistry");
//		dbc.addCourse("103", "Math", "Basic Math");
//		dbc.addCourse("104", "Advance Math", "Linear Algebra");
//		dbc.addCourse("105", "Calculas", "Diffetentiation and Integration");
//		dbc.addCourse("106", "Stuctured Programing Language", "C Programing");
//		dbc.addCourse("107", "Data Stucture", "Data Structure with C++");
//
//		dbc.assignTeacherToCourse("nihan", "101");
//		dbc.assignTeacherToCourse("nihan", "103");
//		dbc.assignTeacherToCourse("nihan", "106");
//		dbc.assignTeacherToCourse("alom", "106");
//		dbc.assignTeacherToCourse("alom", "107");
//		dbc.assignTeacherToCourse("alom", "105");
//		dbc.assignTeacherToCourse("alom", "104");
//
//		dbc.registerCourse("abul", "101");
//		dbc.registerCourse("abul", "102");
//		dbc.registerCourse("abul", "103");
//		dbc.registerCourse("abul", "104");
//		dbc.registerCourse("abul", "105");
//		dbc.registerCourse("babul", "101");
//		dbc.registerCourse("babul", "104");
//		dbc.registerCourse("babul", "105");
//		dbc.registerCourse("babul", "106");

//		System.out.println( dbc.checkLogin("sami", "12") );
//		dbc.getTeacherCourses("alom");
		
//		Map<String, String>mp = new HashMap<>();
//		mp.put("sami", "63");
//		
//		for(Map.Entry<String, String> m : mp.entrySet() ) {
//			System.out.println(m.getKey() + " - " + m.getValue());
//		}
		
//		List<User> list = dbc.getEnrolledStudents("101");
//		for(User c : list) {
//			System.out.println("name : " + c.getName() + " | username : " + c.getUsername());
//			System.out.println("email: " + c.getEmail());
//			System.out.println("");
//		}
		List<Course> list = dbc.getRegisteredCourses("babul");
		for(Course c : list) {
			System.out.println("id : " + c.getCourseId() + " | title : " + c.getTitle());
			System.out.println("description: " + c.getDescription());
			System.out.println("");
		}
		System.out.println("---------------------");
		List<Course> list1 = dbc.getNotRegisteredCourses("babul");
		for(Course c : list1) {
			System.out.println("id : " + c.getCourseId() + " | title : " + c.getTitle());
			System.out.println("description: " + c.getDescription());
			System.out.println("");
		}
//		Course c = dbc.getCourse("101");
//		System.out.println(c.getCourseId());
//		System.out.println(c.getTitle());
//		System.out.println(c.getDescription());
//		
//		System.out.println("All done");
	}

}
