package Objets;

import java.io.Serializable;

public class Department implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int DID;
	private String name;
	
	// Constructor
	public Department()
	{
	}
	
	public Department(int DID, String name)
	{
		this.setDID(DID);
		this.setName(name);
	}
	
	// Getters
	public String getName()
	{
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}
	
	// Setters
	public void setName(String name)
	{
		this.name = name.toLowerCase();
	}

	public int getDID()
	{
		return DID;
	}

	public void setDID(int DID)
	{
		this.DID = DID;
	}

	@Override
	public String toString()
	{
		return getName();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + DID;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Department other = (Department) obj;
		if (DID != other.DID) return false;
		if (name == null)
		{
			if (other.name != null) return false;
		}
		else if (!name.equals(other.name)) return false;
		return true;
	}
}
