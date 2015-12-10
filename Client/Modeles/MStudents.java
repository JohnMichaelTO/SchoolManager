package Modeles;

import java.util.ArrayList;

import Objets.Student;

public class MStudents extends MDefaultTable<Student>
{
	private static final long serialVersionUID = 1L;
	
	public MStudents()
	{
		super();
		data = new ArrayList<Student>();
		
		String[] newheaders = {"Nom", "Prénom", "Email", "Promo", "Tuteur"};
		setHeaders(newheaders);
	}

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
                return data.get(rowIndex).getLastname();
            case 1:
                return data.get(rowIndex).getFirstname();
            case 2:
                return data.get(rowIndex).getEmail();
            case 3:
            	return data.get(rowIndex).getNiveau();
            case 4:
            	return data.get(rowIndex).getTutor();
            default:
                return null;
        }
    }
}
