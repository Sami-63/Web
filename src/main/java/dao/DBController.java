package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Course;
import model.User;

public class DBController {

	private Connection connection;
	private String url = "jdbc:mysql://localhost:3306/web_project";
	private String user = "root", password = "";

	/**
	 * //To-do
	 */
	public DBController() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this makes the connection with the database using usernaame and password
	 * 
	 * @throws SQLException - throws exception in case it fails to make connection
	 */
	private void makeConnection() throws SQLException {
		this.connection = DriverManager.getConnection(url, user, password);
	}

	/**
	 * this function creates tables with their specific characteristics
	 */
	public void createTables() {
		String createUsersTable = "create table users (\n"
				+ "    name varchar(30),\n"
				+ "    username varchar(20),\n"
				+ "    password varchar(32),\n"
				+ "    email varchar(30),\n"
				+ "    user_type varchar(10) check(user_type in ('admin', 'student', 'teacher')),\n"
				+ "    primary key (username, email)\n"
				+ ");";
		String createCourseTable = "create table course(\n"
				+ "    course_id varchar(20),\n"
				+ "    title varchar(50),\n"
				+ "    description text,\n"
				+ "    primary key (course_id, title)\n"
				+ ");";
		String createTakesTable = "create table takes(\n"
				+ "    username varchar(20),\n"
				+ "    course_id varchar(20),\n"
				+ "    primary key (username, course_id),\n"
				+ "    foreign key (username) references users(username) on delete cascade,\n"
				+ "    foreign key (course_id) references course(course_id) on delete cascade\n"
				+ ");";
		String createTeachesTable = "create table teaches(\n"
				+ "    username varchar(20),\n"
				+ "    course_id varchar(20),\n"
				+ "    primary key (username, course_id),\n"
				+ "    foreign key (username) references users(username) on delete cascade,\n"
				+ "    foreign key (course_id) references course(course_id) on delete cascade\n"
				+ ");";

		try {
			makeConnection();
			Statement query = connection.createStatement();
			query.execute(createUsersTable);
			query.execute(createCourseTable);
			query.execute(createTakesTable);
			query.execute(createTeachesTable);

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function adds new user to the 'users' table in database
	 * 
	 * @param name     - Full name of the user
	 * @param username - username of the user given by user
	 * @param pass     - password of the account
	 * @param email    - email of the user
	 * @param type     - user type (must be amoung "student", "teacher" and "admin")
	 */
	public void addNewUser(String name, String username, String pass, String email, String type) {
		String queryMould = "insert into users values(?, ?, ?, ?, ?);";

		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, name);
			query.setString(2, username);
			query.setString(3, pass);
			query.setString(4, email);
			query.setString(5, type);
			query.execute();

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function creates a new course by adding it to the 'course' table in
	 * database
	 * 
	 * @param course_id   - course id of a particular course which is unique
	 * @param title       - title of a course which is also unique
	 * @param Description - description about the course explaining what's the
	 *                    course containing
	 */
	public void createNewCourse(String course_id, String title, String description) {
		String queryMould = "insert into course values(?, ?, ?);";

		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, course_id);
			query.setString(2, title);
			query.setString(3, description);
			query.execute();

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function assigns a course to a particular teacher by adding the
	 * 'username' of the teacher and 'course_id' of that course to the 'teaches'
	 * table in database
	 * 
	 * @param username  - username of the teacher who is going to take a course
	 * @param course_id - course id of the course which will be assinged to a
	 *                  teacher
	 */
	public void assignCourseToTeacher(String username, String course_id) {
		String queryMould = "insert into teaches values(?, ?);";

		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, username);
			query.setString(2, course_id);
			query.execute();

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function registers a student to a particular course by putting
	 * 'username' of student and 'course_id' of that course to the 'takes' table in
	 * database
	 * 
	 * @param username  - username of a student who is gonna take the course
	 * @param course_id - course id of that particular course that the student gonna
	 *                  take
	 */
	public void registerCourse(String username, String course_id) {
		String queryMould = "insert into takes values(?, ?);";

		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, username);
			query.setString(2, course_id);
			query.execute();

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function checks the 'username' and 'password' of an account
	 * from the 'users' table if someone tries to login in their account from the
	 * login page
	 * 
	 * @param username - value of the 'username' field from the login page
	 * @param password - value of the 'password' field from the login page
	 * @return - if the given input of login page matches with the database then it
	 *         return true. Otherwise, false
	 */
	public boolean validUsernameAndPassword(String username, String password) {
		String queryMould = "select * from users where username=? and password=?;";
		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, username);
			query.setString(2, password);

			ResultSet result = query.executeQuery();
			if (result.next()) {
				// String db_username = result.getString(1);
				// String db_pass = result.getString(2);
				// System.out.println("Authorized " + db_username + " - " + db_pass);
				connection.close();
				return true;
			} else {
				// System.out.println("Unauthorized");
				connection.close();
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * this function helps to get all the courses along with the course details
	 * registered by a particular student
	 * using his/her username by joining 'takes' and 'course' table
	 * 
	 * @param username - username of a particular student
	 * @return - returns a list of course after completing the query by the username
	 *         of the student
	 */
	public List<Course> getAllCoursesDetailsRegisteredByStudent(String username) {
		List<Course> courseList = new ArrayList<>();

		String queryMould = "select takes.course_id, title, description from takes, course "
				+ "where takes.username = ? and takes.course_id = course.course_id;";
		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, username);

			ResultSet result = query.executeQuery();
			while (result.next()) {
				// System.out.println(result.getString(1) + " | " + result.getString(2) + " | "
				// +
				// result.getString(3));
				String course_id = result.getString(1);
				String title = result.getString(2);
				String description = result.getString(3);
				Course course = new Course(course_id, title, description);
				courseList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseList;
	}

	/**
	 * this function helps to get all the courses along with the course details
	 * taken by a particular teacher
	 * using his/her username by joining 'teaches' and 'course' table
	 * 
	 * @param username - username of a particular teacher
	 * @return - returns a list of course after completing the query by the username
	 *         of the teacher
	 */
	public List<Course> getAllCoursesDetailsAssignedToTeacher(String username) {
		List<Course> courseList = new ArrayList<>();

		String queryMould = "select course.course_id, title, description from teaches, course \n"
				+ "where teaches.username = ? and teaches.course_id = course.course_id;";
		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, username);

			ResultSet result = query.executeQuery();

			while (result.next()) {
				// System.out.println(result.getString(1) + " | " + result.getString(2) + " | "
				// +
				// result.getString(3));
				String course_id = result.getString(1);
				String title = result.getString(2);
				String description = result.getString(3);
				Course course = new Course(course_id, title, description);
				courseList.add(course);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseList;
	}

	/**
	 * this function gives the list of all the informations of all the courses
	 * created by admin from the 'course' table
	 * 
	 * @return - retuns the list of course after completing the query in 'course'
	 *         table
	 */
	public List<Course> getAllCoursesDetails() {
		List<Course> courseList = new ArrayList<>();

		String queryMould = "select * from course;";
		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			ResultSet result = query.executeQuery();

			while (result.next()) {
				// System.out.println(result.getString(1) + " | " + result.getString(2) + " | "
				// +
				// result.getString(3));
				String course_id = result.getString(1);
				String title = result.getString(2);
				String description = result.getString(3);
				Course course = new Course(course_id, title, description);
				courseList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return courseList;
	}

	/**
	 * this function gives the list of all the informations of all the teachers
	 * from the 'users' table using 'user_type' value
	 * 
	 * @return - retuns the list of teacher after completing the query in 'users'
	 *         table
	 */
	public List<User> getAllTeacher() {
		List<User> teacherList = new ArrayList<>();

		String queryMould = "select * from users where user_type = 'teacher';";
		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			ResultSet result = query.executeQuery();

			while (result.next()) {
				// System.out.println(result.getString(1) + " | " + result.getString(2) + " | "
				// +
				// result.getString(3));
				String name = result.getString(1);
				String username = result.getString(2);
				String password = result.getString(3);
				String email = result.getString(4);
				User user = new User(name, username, password, email, "Teacher");
				teacherList.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return teacherList;
	}

	// private void deleteDB() {
	// 	String deleteUsersTable = "drop table users;";
	// 	String deleteCourseTable = "drop table course;";
	// 	String deleteTakesTable = "drop table takes;";
	// 	String deleteTeachesTable = "drop table teaches;";

	// 	try {
	// 		makeConnection();
	// 		Statement query = connection.createStatement();
	// 		query.execute(deleteTakesTable);
	// 		query.execute(deleteTeachesTable);
	// 		query.execute(deleteUsersTable);
	// 		query.execute(deleteCourseTable);

	// 		System.out.println("All tables deleted");
	// 		connection.close();
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// }

	/**
	 * this function fetches all the details of a particular user by his/her
	 * username
	 * 
	 * @param username - username of a user
	 * @return - returns all the details of a user
	 */
	public User getUserDetails(String username) {
		User user = new User();

		String queryMould = "select * from users where username = ?";
		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, username);

			ResultSet result = query.executeQuery();
			if (result.next()) {
				user.setName(result.getString(1));
				user.setUsername(result.getString(2));
				user.setPassword(result.getString(3));
				user.setEmail(result.getString(4));
				user.setUserType(result.getString(5));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	/**
	 * this function fetches all the details of a particular course by course_id
	 * 
	 * @param course_id - course_id of a course
	 * @return - all the details of a course
	 */
	public Course getCourseDetails(String course_id) {
		Course course = new Course();

		String queryMould = "select * from course where course_id = ?";
		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, course_id);
			ResultSet result = query.executeQuery();

			if (result.next()) {
				course.setCourseId(result.getString(1));
				course.setTitle(result.getString(2));
				course.setDescription(result.getString(3));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return course;
	}

	/**
	 * this fucntion gives the name and username of the course teacher. Or the name
	 * and the username of those teachers who are assigned to a particular course
	 * using 'teaches' and 'users' table
	 * 
	 * @param course_id - course id of a course
	 * @return - name and username of those teacher who are assigned to that course
	 */
	public List<User> getCourseTeachersNameAndUsername(String course_id) {
		List<User> teacherList = new ArrayList<>();

		String queryMould = "select users.name, users.username from teaches, users where course_id = ? and teaches.username = users.username;";
		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, course_id);

			ResultSet result = query.executeQuery();
			while (result.next()) {
				String name = result.getString(1);
				String username = result.getString(2);
				User user = new User(name, username, "", "", "Teacher");
				teacherList.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return teacherList;
	}

	/**
	 * this fucntion gives all the information of the teachers who are not assigned
	 * to a particular course
	 * 
	 * @param course_id - course id of a course
	 * @return - list of user eho are not assigned to that specific course
	 */
	public List<User> getNotAssigedTeacher(String course_id) {
		List<User> teacherList = new ArrayList<>();

		String queryMould = "select * from users\n"
				+ "where user_type = 'teacher' and username not in \n"
				+ "(\n"
				+ "    select users.username from users, teaches \n"
				+ "    where teaches.username = users.username and course_id = ?\n"
				+ ");";

		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, course_id);

			ResultSet result = query.executeQuery();
			while (result.next()) {
				String name = result.getString(1);
				String username = result.getString(2);
				User user = new User(name, username, "", "", "Student");
				teacherList.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return teacherList;
	}

	/**
	 * this fucntion gives the name and username of the students who are enrolled in
	 * a specific course
	 * 
	 * @param course_id - course id of a course
	 * @return - name and username of those students who are enrolled in that course
	 */
	public List<User> getEnrolledStudentsNameAndUsername(String course_id) {
		List<User> studentList = new ArrayList<>();

		String queryMould = "select users.name, users.username from takes, users where course_id = ? and takes.username = users.username;";
		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, course_id);

			ResultSet result = query.executeQuery();
			while (result.next()) {
				String name = result.getString(1);
				String username = result.getString(2);
				User user = new User(name, username, "", "", "Student");
				studentList.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return studentList;
	}

	/**
	 * this function gives the list the courses, in which a specific student has
	 * registerd him/herself to take the course
	 * 
	 * @param username - username of a student
	 * @return - list of the registered or enrolled courses of that student
	 */
	public List<Course> getRegisteredCoursesDetails(String username) {
		List<Course> courseList = new ArrayList<>();

		String queryMould = "select course.course_id, title, description from course, takes where takes.course_id = course.course_id and takes.username = ?;";
		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, username);
			ResultSet result = query.executeQuery();

			while (result.next()) {
				String course_id = result.getString(1);
				String title = result.getString(2);
				String description = result.getString(3);
				Course course = new Course(course_id, title, description);
				courseList.add(course);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return courseList;
	}

	/**
	 * this function gives the list of the courses in which a particular student has
	 * not registerd yet or did not enroll him/herself.
	 * 
	 * @param username - username of a student
	 * @return - list of the not registered or enrolled courses of that specific
	 *         student
	 */
	public List<Course> getNotRegisteredCoursesDetails(String username) {
		List<Course> courseList = new ArrayList<>();

		String queryMould = "select course_id, title, description \n"
				+ "from course \n"
				+ "where course_id not in (\n"
				+ "    select course.course_id \n"
				+ "    from course, takes \n"
				+ "    where takes.course_id = course.course_id and takes.username = ?\n"
				+ ");";

		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, username);

			ResultSet result = query.executeQuery();
			while (result.next()) {
				String course_id = result.getString(1);
				String title = result.getString(2);
				String description = result.getString(3);
				Course course = new Course(course_id, title, description);
				courseList.add(course);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return courseList;
	}

	/**
	 * this function helps to extract the information of the cources which are
	 * assigned to a particular teacher
	 * 
	 * @param username - username of a teacher
	 * @return - list of the assigned course for a specific teacher
	 */
	public List<Course> getAssignedCoursesDetails(String username) {
		List<Course> courseList = new ArrayList<>();

		String queryMould = "select course.course_id, title, description \n"
				+ "from course, teaches \n"
				+ "where course.course_id = teaches.course_id and teaches.username = ?;";

		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, username);

			ResultSet result = query.executeQuery();
			while (result.next()) {
				String course_id = result.getString(1);
				String title = result.getString(2);
				String description = result.getString(3);
				Course course = new Course(course_id, title, description);
				courseList.add(course);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return courseList;
	}

	/**
	 * this fucntion checks wheater a particular teaches teaches a particular course or not
	 * 
	 * @param username 	- username of a teacher
	 * @param course_id	- course id fo a course
	 * @return - true, if the teacher teaches that course 
	 * @return - false, if the teacher does not teach the course
	 */
	public boolean isTeachesCourse(String username, String course_id) {
		String queryMould = "select * from users, teaches \n"
				+ "where users.username = teaches.username \n"
				+ "and course_id = ? and users.username = ?;";

		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, course_id);
			query.setString(2, username);
			ResultSet result = query.executeQuery();

			if (result.next())
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * this fucntion checks wheather a email already registerd to our system or not
	 * 
	 * @param email - input of the 'email' field from the registration page
	 * @return - true, if the email exists in our database
	 * @return - false, if the email does not exist
	 */
	public boolean isEmailExists(String email) {
		String queryMould = "select * from users \n"
				+ "where email = ?";

		try {
			makeConnection();
			PreparedStatement query = connection.prepareStatement(queryMould);
			query.setString(1, email);

			ResultSet result = query.executeQuery();
			if (result.next())
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static void main(String[] args) {
		// System.out.println("Lets start");
		// try {
		// Class.forName("com.mysql.cj.jdbc.Driver");
		// Connection con =
		// DriverManager.getConnection("jdbc:mysql://localhost:3306/web_project",
		// "root", "web1234%");
		// // here sonoo is database name, root is username and password
		//// Statement stmt = con.createStatement();
		//// ResultSet result = stmt.executeQuery("select * from st");
		//// while (result.next())
		//// System.out.println(result.getString(1) + " " + result.getString(2));
		// con.close();
		// System.out.println("OK");
		// } catch (Exception e) {
		// System.out.println("Not OK");
		// System.out.println(e);
		// }
		DBController dbc = new DBController();
		// dbc.deleteDB();

		// dbc.createTables();
		// dbc.addNewUser("Sadman Sami", "sami", "123", "sami@gmail.com", "admin");
		// dbc.addNewUser("Blue Dragon", "blue", "124", "blue@gmail.com", "admin");
		// dbc.addNewUser("Nihan Ali", "nihan", "125", "nihan@gmail.com", "teacher");
		// dbc.addNewUser("Alomgir Hossain", "alom", "126", "alom@gmail.com",
		// "teacher");
		// dbc.addNewUser("Abul Miah", "abul", "127", "abul@gmail.com", "student");
		// dbc.addNewUser("Babul Miah", "babul", "127", "babul@gmail.com", "student");
		// dbc.addNewUser("Minhaz Rahman", "minhaz", "657", "minhaz@gmail.com",
		// "teacher");
		//
		// dbc.createNewCourse("101", "Physics", "Basic Physics");
		// dbc.createNewCourse("102", "Chemistry", "Basic Chemistry");
		// dbc.createNewCourse("103", "Math", "Basic Math");
		// dbc.createNewCourse("104", "Advance Math", "Linear Algebra");
		// dbc.createNewCourse("105", "Calculas", "Diffetentiation and Integration");
		// dbc.createNewCourse("106", "Stuctured Programing Language", "C Programing");
		// dbc.createNewCourse("107", "Data Stucture", "Data Structure with C++");
		//
		// dbc.assignCourseToTeacher("nihan", "101");
		// dbc.assignCourseToTeacher("nihan", "103");
		// dbc.assignCourseToTeacher("nihan", "106");
		// dbc.assignCourseToTeacher("alom", "106");
		// dbc.assignCourseToTeacher("alom", "107");
		// dbc.assignCourseToTeacher("alom", "105");
		// dbc.assignCourseToTeacher("alom", "104");
		//
		// dbc.registerCourse("abul", "101");
		// dbc.registerCourse("abul", "102");
		// dbc.registerCourse("abul", "103");
		// dbc.registerCourse("abul", "104");
		// dbc.registerCourse("abul", "105");
		// dbc.registerCourse("babul", "101");
		// dbc.registerCourse("babul", "104");
		// dbc.registerCourse("babul", "105");
		// dbc.registerCourse("babul", "106");

		// System.out.println( dbc.validUsernameAndPassword("sami", "12") );
		// dbc.getAllCoursesDetailsAssignedToTeacher("alom");

		// Map<String, String>mp = new HashMap<>();
		// mp.put("sami", "63");
		//
		// for(Map.Entry<String, String> m : mp.entrySet() ) {
		// System.out.println(m.getKey() + " - " + m.getValue());
		// }

		// List<User> list = dbc.getNotAssigedTeacher("101");
		// for (User c : list) {
		// System.out.println("name : " + c.getName() + " | username : " +
		// c.getUsername());
		// System.out.println("email: " + c.getEmail());
		// System.out.println("");
		// }
		// List<Course> list = dbc.getAssignedCoursesDetails("alom");
		// for(Course c : list) {
		// System.out.println("id : " + c.getCourseId() + " | title : " + c.getTitle());
		// System.out.println("description: " + c.getDescription());
		// System.out.println("");
		// }
		// System.out.println("---------------------");
		// List<Course> list1 = dbc.getNotRegisteredCoursesDetails("babul");
		// for(Course c : list1) {
		// System.out.println("id : " + c.getCourseId() + " | title : " + c.getTitle());
		// System.out.println("description: " + c.getDescription());
		// System.out.println("");
		// }
		// Course c = dbc.getCourseDetails("101");
		// System.out.println(c.getCourseId());
		// System.out.println(c.getTitle());
		// System.out.println(c.getDescription());
		//
		User user = dbc.getUserDetails("sami");
		System.out.println("Name = " + user.getName() + " | Username = " + user.getUsername());
		System.out.println("Email = " + user.getEmail() + " | password " + user.getPassword() + " | Usertype = "
				+ user.getUserType());

		System.out.println("All done " + dbc.isEmailExists("sam@gmail.com"));
	}

}
