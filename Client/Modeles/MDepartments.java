package Modeles;

import java.util.ArrayList;

import Objets.Department;

public class MDepartments extends MDefaultTable<Department>
{
	private static final long serialVersionUID = 1L;
	
	public MDepartments()
	{
		super();
		data = new ArrayList<Department>();
		
		String[] newheaders = {"Nom"};
		setHeaders(newheaders);
	}
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
                return data.get(rowIndex).getName();
            default:
                return null;
        }
    }
}
