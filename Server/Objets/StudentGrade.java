package Objets;

import java.io.Serializable;

public class StudentGrade implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Student student;
	private Grade grade;
	private double mark;
	
	// Constructor
	public StudentGrade()
	{
		
	}
	
	// Getters
	public Student getStudent()
	{
		return student;
	}

	public Grade getGrade()
	{
		return grade;
	}

	public double getMark()
	{
		return mark;
	}
	
	// Setters
	public void setStudent(Student student)
	{
		this.student = student;
	}

	public void setGrade(Grade grade)
	{
		this.grade = grade;
	}

	public void setMark(double mark)
	{
		this.mark = mark;
	}
}
