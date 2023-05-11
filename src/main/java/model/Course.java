package model;

public class Course {
	private String courseId;
	private String title;
	private String description;

	public Course() {
	}

	public Course(String courseId, String title, String description) {
		this.courseId = courseId;
		this.title = title;
		this.description = description;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
