package Modeles;

import java.util.ArrayList;

import Objets.User;

public class MUsers extends MDefaultTable<User>
{
	private static final long serialVersionUID = 1L;
	
	public MUsers()
	{
		super();
		data = new ArrayList<User>();
		
		String[] newheaders = {"Nom", "Prénom", "Email", "Type"};
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
            	return data.get(rowIndex).getTypeText();
            default:
                return null;
        }
    }
}
