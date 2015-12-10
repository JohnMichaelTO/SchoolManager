package Objets;

import java.io.Serializable;

public class Admin extends User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public Admin()
	{
		this.setType("ADMIN");
	}
	
	public Admin(int UID, String lastname, String firstname, String email, String password)
	{
		super(UID, lastname, firstname, email, password, "ADMIN");
	}
}