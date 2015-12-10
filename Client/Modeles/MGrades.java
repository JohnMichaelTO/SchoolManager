package Modeles;

import java.util.ArrayList;

import Objets.Grade;

public class MGrades extends MDefaultTable<Grade>
{
	private static final long serialVersionUID = 1L;
	
	public MGrades()
	{
		super();
		data = new ArrayList<Grade>();
		
		String[] newheaders = {"Cours", "Type", "Coefficient"};
		setHeaders(newheaders);
	}
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
                return data.get(rowIndex).getCourse().getName() + " " + data.get(rowIndex).getCourse().getNiveau();
            case 1:
            	return data.get(rowIndex).getType();
            case 2:
            	return data.get(rowIndex).getCoef();
            default:
                return null;
        }
    }
}
