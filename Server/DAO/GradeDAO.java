package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.Grade;

public class GradeDAO implements DAO<Grade>
{
	public static boolean exists(int id)
	{
		try
		{
			String query = "select * from GRADES where GID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, id, Types.INTEGER);
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
	
	public boolean insert(Grade obj)
	{
		try
		{
			String query = "insert into GRADES values(?, ?, ?, ?)";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			if(obj.getGID() == 0)
			{
				state.setObject(1, getLastID() + 1, Types.INTEGER);
			}
			else
			{
				state.setObject(1, obj.getGID(), Types.INTEGER);
			}
			state.setObject(2, obj.getCourse().getCID(), Types.INTEGER);
			state.setObject(3, obj.getCoef(), Types.DOUBLE);
			state.setObject(4, obj.getType(), Types.VARCHAR);
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
	
	public boolean update(Grade obj)
	{
		try
		{
			String query = "update GRADES set CID = ?, COEF = ?, GTYPE = ? where GID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getCourse().getCID(), Types.INTEGER);
			state.setObject(2, obj.getCoef(), Types.DOUBLE);
			state.setObject(3, obj.getType(), Types.VARCHAR);
			state.setObject(4, obj.getGID(), Types.INTEGER);
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

	public boolean delete(Grade obj)
	{
		try
		{
			// Dépendances
			StudentGradeDAO.deleteByGradeID(obj.getGID());
			
			String query = "delete from GRADES where GID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getGID(), Types.INTEGER);
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
	
	public static Grade select(int ID)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from GRADES where GID = '" + ID + "'";
			ResultSet result = state.executeQuery(query);
			
			Grade grade = new Grade();
			int nb = 0;
			while(result.next())
			{
				grade.setGID(result.getInt("GID"));
				grade.setCourse(CourseDAO.select(result.getInt("CID")));
				grade.setCoef(result.getDouble("COEF"));
				grade.setType(result.getString("GTYPE"));
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return grade;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static List<Grade> getList(String clause)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select * from GRADES" + (clause.length() > 0 ? " " + clause : "");
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<Grade> gradesList = new ArrayList<Grade>();
			
			while(result.next())
			{
				Grade grade = new Grade();
				grade.setGID(result.getInt("GID"));
				grade.setCourse(CourseDAO.select(result.getInt("CID")));
				grade.setCoef(result.getDouble("COEF"));
				grade.setType(result.getString("GTYPE"));
				
				gradesList.add(grade);
			}
			
			result.close();
			state.close();
			
			return gradesList;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static int getLastID()
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select max(GID) as GID from GRADES";
			ResultSet result = state.executeQuery(query);
			
			int id = 0;
			while(result.next())
			{
				id = result.getInt("GID");
			}
			
			result.close();
			state.close();
			
			return id;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return 0;
	}
	
	public static boolean deleteByCourseID(int cid)
	{
		try
		{
			// Dépendances
			StudentGradeDAO.deleteByCourseID(cid);
			
			String query = "delete from GRADES where CID = ?";
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