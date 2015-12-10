package Objets;

import java.io.Serializable;

public class Student extends User implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int promo;
	private Teacher tutor;
	
	public Student()
	{
		this.setType("STUDENT");
	}
	
	// Constructor
	public Student(int UID, String lastname, String firstname, String email, String password, int promo, Teacher tutor)
	{
		super(UID, lastname, firstname, email, password, "STUDENT");
		this.setPromo(promo);
		this.setTutor(tutor);
	}
	
	// Getters
	public int getPromo()
	{
		return promo;
	}

	public Teacher getTutor()
	{
		return tutor;
	}
	
	// Setters
	public void setPromo(int promo)
	{
		this.promo = promo;
	}

	public void setTutor(Teacher tutor)
	{
		this.tutor = tutor;
	}
	
	public String getNiveau()
	{
		switch(promo)
		{
			case 0:
				return "Tous niveaux";
			case 1:
				return "L1";
			case 2:
				return "L2";
			case 3:
				return "L3";
			case 4:
				return "M1";
			case 5:
				return "M2";
		}
		return "";
	}
}