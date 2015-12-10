package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.Alert;

public class AlertDAO implements DAO<Alert>
{
	public boolean insert(Alert obj)
	{
		return false;
	}
	
	public boolean update(Alert obj) // Pas besoin de modification
	{
		return false;
	}

	public boolean delete(Alert obj)
	{
		return false;
	}
	
	public static List<Alert> getList(int ID, String type)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "";
			if(type.equals("student"))
			{
				query = "select SID, CID, sum(MARK * COEF) as SOMME, sum(case when MARK is null then 0 else COEF end) as SOMME_COEF from STUDENTGRADES natural join GRADES where SID = " + ID + " group by SID, CID";
			}
			else
			{
				query = "select SID, CID, sum(MARK * COEF) as SOMME, sum(case when MARK is null then 0 else COEF end) as SOMME_COEF, TID from STUDENTGRADES natural join GRADES natural join TEACHES where TID = " + ID + " group by SID, CID";
			}
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<Alert> alertsList = new ArrayList<Alert>();
			
			while(result.next())
			{
				Alert alert = new Alert();
				alert.setCourse(CourseDAO.select(result.getInt("CID")));
				alert.setStudent(StudentDAO.select(result.getInt("SID")));
				alert.setMoyenne(result.getDouble("SOMME") / result.getDouble("SOMME_COEF"));
				
				if(type.equals("student") || alert.getMoyenne() < 10.0) alertsList.add(alert);
			}
			
			result.close();
			state.close();
			
			return alertsList;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
}