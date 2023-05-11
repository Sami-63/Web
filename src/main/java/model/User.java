package model;

public class User {
	private String name;
	private String username;
	private String password;
	private String email;
	private String userType;

	public User() {
		super();
		name = "";
	}

	public boolean isNull() {
		return name.equals("");
	}

	public User(String name, String username, String password, String email, String userType) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void show() {
		System.out.println("Name : " + this.name);
		System.out.println("Username : " + this.username + " | Type : " + this.userType);
		System.out.println("Email " + this.email);
	}

}
