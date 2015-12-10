package Objets;

import java.io.Serializable;

public class Teaches implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Course course;
	private Teacher teacher;
	
	// Constructor
	public Teaches()
	{
		
	}
	
	@Override
	public String toString()
	{
		return course + " " + teacher;
	}

	// Getters
	public Course getCourse()
	{
		return course;
	}

	public Teacher getTeacher()
	{
		return teacher;
	}
	
	// Setters
	public void setCourse(Course course)
	{
		this.course = course;
	}

	public void setTeacher(Teacher teacher)
	{
		this.teacher = teacher;
	}
}
