package Modeles;

import java.util.ArrayList;

import Objets.Alert;

public class MAlerts extends MDefaultTable<Alert>
{
	private static final long serialVersionUID = 1L;
	
	public MAlerts()
	{
		super();
		data = new ArrayList<Alert>();
		
		String[] newheaders = {"Etudiant", "Promo", "Cours", "Moyenne"};
		setHeaders(newheaders);
	}

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
                return data.get(rowIndex).getStudent();
            case 1:
                return data.get(rowIndex).getStudent().getNiveau();
            case 2:
                return data.get(rowIndex).getCourse();
            case 3:
            	return data.get(rowIndex).getMoyenne();
            default:
                return null;
        }
    }
}
