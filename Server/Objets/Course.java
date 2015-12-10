package Objets;

import java.io.Serializable;

public class Course implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int CID;
	private String name;
	private int year;
	private boolean compulsory;
	private Department departement;
	
	// Constructor
	public Course()
	{
		
	}
	
	// Getters
	public int getCID()
	{
		return CID;
	}

	public String getName()
	{
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	public int getYear()
	{
		return year;
	}

	public boolean isCompulsory()
	{
		return compulsory;
	}
	
	// Setters
	public void setCID(int CID)
	{
		this.CID = CID;
	}

	public void setName(String name)
	{
		this.name = name.toLowerCase();
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public void setCompulsory(boolean compulsory)
	{
		this.compulsory = compulsory;
	}

	public Department getDepartement()
	{
		return departement;
	}

	public void setDepartement(Department departement)
	{
		this.departement = departement;
	}
	
	public String getNiveau()
	{
		switch(year)
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

	@Override
	public String toString()
	{
		return getName() + " " + getNiveau();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + CID;
		result = prime * result + (compulsory ? 1231 : 1237);
		result = prime * result
				+ ((departement == null) ? 0 : departement.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Course other = (Course) obj;
		if (CID != other.CID) return false;
		if (compulsory != other.compulsory) return false;
		if (departement == null)
		{
			if (other.departement != null) return false;
		}
		else if (!departement.equals(other.departement)) return false;
		if (name == null)
		{
			if (other.name != null) return false;
		}
		else if (!name.equals(other.name)) return false;
		if (year != other.year) return false;
		return true;
	}
}
