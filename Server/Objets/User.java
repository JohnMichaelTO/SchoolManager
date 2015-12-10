package Objets;

import java.io.Serializable;

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected int UID;
	protected String lastname;
	protected String firstname;
	protected String email;
	protected String password;
	protected String type;
	
	// Constructors
	public User() {};
	
	public User(int UID, String lastname, String firstname, String email, String password, String type)
	{
		this.setUID(UID);
		this.setLastname(lastname);
		this.setFirstname(firstname);
		this.setEmail(email);
		this.setPassword(password);
		this.setType(type);
	}
	
	// Getters
	public int getUID()
	{
		return UID;
	}
	public String getLastname()
	{
		return lastname.substring(0, 1).toUpperCase() + lastname.substring(1).toLowerCase();
	}
	public String getFirstname()
	{
		return firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase();
	}
	public String getEmail()
	{
		return email;
	}
	public String getPassword()
	{
		return password;
	}
	public String getType()
	{
		return type;
	}
	// Setters
	public void setUID(int UID)
	{
		this.UID = UID;
	}
	public void setLastname(String lastname)
	{
		this.lastname = lastname.toLowerCase();
	}
	public void setFirstname(String firstname)
	{
		this.firstname = firstname.toLowerCase();
	}
	public void setEmail(String email)
	{
		this.email = email.toLowerCase();
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getTypeText()
	{
		switch(getType())
    	{
    		case "ADMIN":
    			return "Administrateur";
    		case "STUDENT":
    			return "Etudiant";
    		case "TEACHER":
    			return "Enseignant";
    		default:
    			return "Extraterrestre ?"; // Ne devrait jamais retourner cette valeur
    	}
	}
	
	@Override
	public String toString()
	{
		
		return getLastname() + " " + getFirstname();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + UID;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		User other = (User) obj;
		if (UID != other.UID) return false;
		if (email == null)
		{
			if (other.email != null) return false;
		}
		else if (!email.equals(other.email)) return false;
		if (firstname == null)
		{
			if (other.firstname != null) return false;
		}
		else if (!firstname.equals(other.firstname)) return false;
		if (lastname == null)
		{
			if (other.lastname != null) return false;
		}
		else if (!lastname.equals(other.lastname)) return false;
		if (password == null)
		{
			if (other.password != null) return false;
		}
		else if (!password.equals(other.password)) return false;
		if (type == null)
		{
			if (other.type != null) return false;
		}
		else if (!type.equals(other.type)) return false;
		return true;
	}
}
