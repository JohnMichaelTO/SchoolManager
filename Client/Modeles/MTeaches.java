package Modeles;

import java.util.ArrayList;

import Objets.Teaches;

public class MTeaches extends MDefaultTable<Teaches>
{
	private static final long serialVersionUID = 1L;
	
	public MTeaches()
	{
		super();
		data = new ArrayList<Teaches>();
		
		String[] newheaders = {"Cours", "Professeur"};
		setHeaders(newheaders);
	}
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
            	return data.get(rowIndex).getCourse();
            case 1:
            	return data.get(rowIndex).getTeacher();
            default:
                return null;
        }
    }
}
