package Objets;

import java.io.Serializable;

public class Grade implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int GID;
	private Course course;
	private double coef;
	private String type;
	
	// Constructor
	public Grade()
	{
		
	}

	public Course getCourse()
	{
		return course;
	}

	public double getCoef()
	{
		return coef;
	}

	public String getType()
	{
		return type.toUpperCase();
	}
	
	// Setters

	public void setCourse(Course course)
	{
		this.course = course;
	}

	public void setCoef(double coef)
	{
		this.coef = coef;
	}

	public void setType(String type)
	{
		this.type = type.toUpperCase();
	}

	public int getGID()
	{
		return GID;
	}

	public void setGID(int GID)
	{
		this.GID = GID;
	}	
}
