package Modeles;

import java.util.ArrayList;

import Objets.Teacher;

public class MTeachers extends MDefaultTable<Teacher>
{
	private static final long serialVersionUID = 1L;
	
	public MTeachers()
	{
		super();
		data = new ArrayList<Teacher>();
		
		String[] newheaders = {"Nom", "Prénom", "Email"};
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
            default:
                return null;
        }
    }
}
