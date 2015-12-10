package Modeles;

import java.util.ArrayList;

import Actions.ActionUpdate;
import InterfacesGUI.ClientGUI;
import Objets.StudentGrade;

public class MStudentGrades extends MDefaultTable<StudentGrade>
{
	private static final long serialVersionUID = 1L;
	private ClientGUI fenetre = null;
	
	public MStudentGrades(ClientGUI fenetre)
	{
		super();
		data = new ArrayList<StudentGrade>();
		this.fenetre = fenetre;
		
		String[] newheaders = {"Cours", "Etudiant", "Note"};
		setHeaders(newheaders);
	}
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
            	return data.get(rowIndex).getGrade().getType() + " " + data.get(rowIndex).getGrade().getCourse();
            case 1:
            	return data.get(rowIndex).getStudent();
            case 2:
            	return data.get(rowIndex).getMark();
            default:
                return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
    	if(columnIndex == 2 && !fenetre.getCurrentUser().getType().equals("STUDENT")) return true;
    	return false;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
        if(value != null)
        {
            StudentGrade studentGrade = data.get(rowIndex);
     
            switch(columnIndex)
            {
                case 2:
                	studentGrade.setMark((double) value);
                	ActionUpdate<StudentGrade> updateMark = new ActionUpdate<StudentGrade>(fenetre, studentGrade, "studentGrade");
                	updateMark.execute();
                    break;
            }
        }
    }
    
    @Override
    public Class getColumnClass(int columnIndex)
    {
        switch(columnIndex)
        {
            case 2:
                return Double.class;
            default:
                return Object.class;
        }
    }
}
