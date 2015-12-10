package Modeles;

import java.util.ArrayList;

import Objets.Enrollment;

public class MEnrollments extends MDefaultTable<Enrollment>
{
	private static final long serialVersionUID = 1L;
	
	public MEnrollments()
	{
		super();
		data = new ArrayList<Enrollment>();
		
		String[] newheaders = {"Cours", "Etudiant"};
		setHeaders(newheaders);
	}
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
            	return data.get(rowIndex).getCourse();
            case 1:
            	return data.get(rowIndex).getStudent();
            default:
                return null;
        }
    }
}
