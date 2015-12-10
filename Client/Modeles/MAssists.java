package Modeles;

import java.util.ArrayList;

import Objets.Assists;

public class MAssists extends MDefaultTable<Assists>
{
	private static final long serialVersionUID = 1L;
	
	public MAssists()
	{
		super();
		data = new ArrayList<Assists>();
		
		String[] newheaders = {"Cours", "Assistant", "Type"};
		setHeaders(newheaders);
	}
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
            	return data.get(rowIndex).getCourse();
            case 1:
            	return data.get(rowIndex).getAssistant();
            case 2:
            	return data.get(rowIndex).getAssistant().getTypeText();
            default:
                return null;
        }
    }
}
