package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.Assists;

public class AssistsDAO implements DAO<Assists>
{
	public static boolean exists(int cid, int uid)
	{
		try
		{
			String query = "select * from ASSISTS where CID = ? and UID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, cid, Types.INTEGER);
			state.setObject(2, uid, Types.INTEGER);
			ResultSet result = state.executeQuery();
			
			int nbRows = 0;
			while(result.next()) { nbRows++; }
			
			result.close();
			state.close();
			
			if(nbRows == 1) return true;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return false;
	}
	
	public boolean insert(Assists obj)
	{
		try
		{
			String query = "insert into ASSISTS values(?, ?)";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getCourse().getCID(), Types.INTEGER);
			state.setObject(2, obj.getAssistant().getUID(), Types.INTEGER);
			state.executeUpdate();
			
			state.close();
			return true;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return false;
	}
	
	public boolean update(Assists obj)
	{
		return false;
	}

	public boolean delete(Assists obj)
	{
		try
		{
			String query = "delete from ASSISTS where CID = ? and UID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getCourse().getCID(), Types.INTEGER);
			state.setObject(2, obj.getAssistant().getUID(), Types.INTEGER);
			state.executeUpdate();
			
			state.close();
			
			// Suppression des relations avec la table
			// TODO : A faire
			
			return true;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return false;
	}
	
	public static Assists select(int cid, int uid)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from ASSISTS where CID = '" + cid + "' and UID = '" + uid + "'";
			ResultSet result = state.executeQuery(query);
			
			Assists assistant = new Assists();
			int nb = 0;
			while(result.next())
			{
				assistant.setCourse(CourseDAO.select(result.getInt("CID")));
				assistant.setAssistant(UserDAO.select(result.getInt("UID")));
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return assistant;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static List<Assists> getList(String clause)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select * from ASSISTS" + (clause.length() > 0 ? " " + clause : "");
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<Assists> assistantsList = new ArrayList<Assists>();
			
			while(result.next())
			{
				Assists assistant = new Assists();
				assistant.setCourse(CourseDAO.select(result.getInt("CID")));
				assistant.setAssistant(UserDAO.select(result.getInt("UID")));
				
				assistantsList.add(assistant);
			}
			
			result.close();
			state.close();
			
			return assistantsList;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static boolean deleteByCourseID(int cid)
	{
		try
		{
			String query = "delete from ASSISTS where CID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, cid, Types.INTEGER);
			state.executeUpdate();
			
			state.close();
			return true;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return false;
	}
}