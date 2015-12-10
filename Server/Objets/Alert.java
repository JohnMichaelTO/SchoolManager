package Objets;

import java.io.Serializable;

public class Alert implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Course course;
	private Student student;
	private double moyenne;
	
	// Constructor
	public Alert()
	{
		
	}

	// Getters
	public Course getCourse()
	{
		return course;
	}

	public Student getStudent()
	{
		return student;
	}
	
	// Setters
	public void setCourse(Course course)
	{
		this.course = course;
	}

	public void setStudent(Student student)
	{
		this.student = student;
	}
	
	@Override
	public String toString()
	{
		return student + " " + course;
	}

	public double getMoyenne()
	{
		return moyenne;
	}

	public void setMoyenne(double moyenne)
	{
		this.moyenne = moyenne;
	}
}
