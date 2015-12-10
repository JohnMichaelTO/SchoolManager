package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.Teaches;

public class TeachesDAO implements DAO<Teaches>
{
	public static boolean exists(int cid, int tid)
	{
		try
		{
			String query = "select * from TEACHES where CID = ? and TID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, cid, Types.INTEGER);
			state.setObject(2, tid, Types.INTEGER);
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
	
	public boolean insert(Teaches obj)
	{
		try
		{
			String query = "insert into TEACHES values(?, ?)";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getCourse().getCID(), Types.INTEGER);
			state.setObject(2, obj.getTeacher().getUID(), Types.INTEGER);
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
	
	public boolean update(Teaches obj)
	{
		return false;
	}

	public boolean delete(Teaches obj)
	{
		try
		{
			String query = "delete from TEACHES where CID = ? and TID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getCourse().getCID(), Types.INTEGER);
			state.setObject(2, obj.getTeacher().getUID(), Types.INTEGER);
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
	
	public static Teaches select(int cid, int tid)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from TEACHES where CID = '" + cid + "' and TID = '" + tid + "'";
			ResultSet result = state.executeQuery(query);
			
			Teaches teache = new Teaches();
			int nb = 0;
			while(result.next())
			{
				teache.setCourse(CourseDAO.select(result.getInt("CID")));
				teache.setTeacher(TeacherDAO.select(result.getInt("TID")));
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return teache;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static List<Teaches> getList(String clause)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select * from TEACHES" + (clause.length() > 0 ? " " + clause : "");
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<Teaches> teachesList = new ArrayList<Teaches>();
			
			while(result.next())
			{
				Teaches teache = new Teaches();
				teache.setCourse(CourseDAO.select(result.getInt("CID")));
				teache.setTeacher(TeacherDAO.select(result.getInt("TID")));
				
				teachesList.add(teache);
			}
			
			result.close();
			state.close();
			
			return teachesList;
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
			String query = "delete from TEACHES where CID = ?";
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