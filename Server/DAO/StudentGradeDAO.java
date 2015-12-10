package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.StudentGrade;

public class StudentGradeDAO implements DAO<StudentGrade>
{
	public static boolean exists(int gid, int sid)
	{
		try
		{
			String query = "select * from STUDENTGRADES where GID = ? and SID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, gid, Types.INTEGER);
			state.setObject(2, sid, Types.INTEGER);
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
	
	public boolean insert(StudentGrade obj)
	{
		try
		{
			String query = "insert into STUDENTGRADES values(?, ?, ?)";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getStudent().getUID(), Types.INTEGER);
			state.setObject(2, obj.getGrade().getGID(), Types.INTEGER);
			state.setObject(3, obj.getMark(), Types.DOUBLE);
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
	
	public boolean update(StudentGrade obj)
	{
		try
		{
			String query = "update STUDENTGRADES set MARK = ? where GID = ? and SID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getMark(), Types.DOUBLE);
			state.setObject(2, obj.getGrade().getGID(), Types.INTEGER);
			state.setObject(3, obj.getStudent().getUID(), Types.INTEGER);
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

	public boolean delete(StudentGrade obj)
	{
		try
		{
			String query = "delete from STUDENTGRADES where GID = ? and SID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getGrade().getGID(), Types.INTEGER);
			state.setObject(2, obj.getStudent().getUID(), Types.INTEGER);
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
	
	public static StudentGrade select(int gid, int sid)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from STUDENTGRADES where GID = '" + gid + "' and SID = '" + sid + "'";
			ResultSet result = state.executeQuery(query);
			
			StudentGrade studentGrade = new StudentGrade();
			int nb = 0;
			while(result.next())
			{
				studentGrade.setGrade(GradeDAO.select(result.getInt("GID")));
				studentGrade.setStudent(StudentDAO.select(result.getInt("SID")));
				studentGrade.setMark(result.getDouble("MARK"));
				
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return studentGrade;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static List<StudentGrade> getList(String clause)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select * from STUDENTGRADES" + (clause.length() > 0 ? " " + clause : "");
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<StudentGrade> studentGradesList = new ArrayList<StudentGrade>();
			
			while(result.next())
			{
				StudentGrade studentGrade = new StudentGrade();
				studentGrade.setGrade(GradeDAO.select(result.getInt("GID")));
				studentGrade.setStudent(StudentDAO.select(result.getInt("SID")));
				studentGrade.setMark(result.getDouble("MARK"));
				
				studentGradesList.add(studentGrade);
			}
			
			result.close();
			state.close();
			
			return studentGradesList;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static boolean deleteByStudentID(int sid)
	{
		try
		{
			String query = "delete from STUDENTGRADES where SID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, sid, Types.INTEGER);
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
	
	public static boolean deleteByGradeID(int gid)
	{
		try
		{
			String query = "delete from STUDENTGRADES where GID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, gid, Types.INTEGER);
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
	
	public static boolean deleteByCourseID(int cid)
	{
		try
		{
			String query = "delete from STUDENTGRADES where GID in (select GID from GRADES where CID = ?)";
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