package Modeles;

import java.util.ArrayList;

import Objets.Course;

public class MCourses extends MDefaultTable<Course>
{
	private static final long serialVersionUID = 1L;
	
	public MCourses()
	{
		super();
		data = new ArrayList<Course>();
		
		String[] newheaders = {"Département", "Nom", "Année", "Obligatoire"};
		setHeaders(newheaders);
	}
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
                return data.get(rowIndex).getDepartement().getName();
            case 1:
                return data.get(rowIndex).getName();
            case 2:
            	return data.get(rowIndex).getNiveau();
            case 3:
                return data.get(rowIndex).isCompulsory() ? "Oui" : "Non";
            default:
                return null;
        }
    }
}
