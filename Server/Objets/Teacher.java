package Objets;

import java.io.Serializable;

public class Teacher extends User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public Teacher()
	{
		this.setType("TEACHER");
	}
	
	public Teacher(int UID, String lastname, String firstname, String email, String password)
	{
		super(UID, lastname, firstname, email, password, "TEACHER");
	}
}