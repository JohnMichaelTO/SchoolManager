package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.Enrollment;

public class EnrollmentDAO implements DAO<Enrollment>
{
	public static boolean exists(int cid, int sid)
	{
		try
		{
			String query = "select * from ENROLLMENTS where CID = ? and SID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, cid, Types.INTEGER);
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
	
	public boolean insert(Enrollment obj)
	{
		try
		{
			String query = "insert into ENROLLMENTS values(?, ?)";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getCourse().getCID(), Types.INTEGER);
			state.setObject(2, obj.getStudent().getUID(), Types.INTEGER);
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
	
	public boolean update(Enrollment obj) // Pas besoin de modification
	{
		return false;
	}

	public boolean delete(Enrollment obj)
	{
		try
		{
			// Dépendances
			StudentGradeDAO.deleteByStudentID(obj.getStudent().getUID());
			
			String query = "delete from ENROLLMENTS where CID = ? and SID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getCourse().getCID(), Types.INTEGER);
			state.setObject(2, obj.getStudent().getUID(), Types.INTEGER);
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
	
	public static Enrollment select(int cid, int sid)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from ENROLLMENTS where CID = '" + cid + "' and SID = '" + sid + "'";
			ResultSet result = state.executeQuery(query);
			
			Enrollment enrollment = new Enrollment();
			int nb = 0;
			while(result.next())
			{
				enrollment.setCourse(CourseDAO.select(result.getInt("CID")));
				enrollment.setStudent(StudentDAO.select(result.getInt("SID")));
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return enrollment;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static List<Enrollment> getList(String clause)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select * from ENROLLMENTS" + (clause.length() > 0 ? " " + clause : "");
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<Enrollment> enrollmentsList = new ArrayList<Enrollment>();
			
			while(result.next())
			{
				Enrollment enrollment = new Enrollment();
				enrollment.setCourse(CourseDAO.select(result.getInt("CID")));
				enrollment.setStudent(StudentDAO.select(result.getInt("SID")));
				
				enrollmentsList.add(enrollment);
			}
			
			result.close();
			state.close();
			
			return enrollmentsList;
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
			// Dépendances
			StudentGradeDAO.deleteByStudentID(sid);
			
			String query = "delete from ENROLLMENTS where SID = ?";
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
	
	public static boolean deleteByCourseID(int cid)
	{
		try
		{
			// Dépendances
			StudentGradeDAO.deleteByCourseID(cid);
			
			String query = "delete from ENROLLMENTS where CID = ?";
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