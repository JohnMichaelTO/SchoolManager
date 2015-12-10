package Objets;

import java.io.Serializable;

public class Assists implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Course course;
	private User assistant;
	
	// Constructor
	public Assists()
	{
		
	}
	
	@Override
	public String toString()
	{
		return course + " " + assistant;
	}

	// Getters
	public Course getCourse()
	{
		return course;
	}

	public User getAssistant()
	{
		return assistant;
	}
	
	// Setters
	public void setCourse(Course course)
	{
		this.course = course;
	}

	public void setAssistant(User assistant)
	{
		this.assistant = assistant;
	}
}
