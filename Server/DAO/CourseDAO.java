package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.Course;

public class CourseDAO implements DAO<Course>
{
	public static boolean exists(int id)
	{
		try
		{
			String query = "select * from COURSES where CID = ?";
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
	
	public boolean insert(Course obj)
	{
		try
		{
			String query = "insert into COURSES values(?, ?, ?, ?, ?)";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			if(obj.getCID() == 0)
			{
				state.setObject(1, getLastID() + 1, Types.INTEGER);
			}
			else
			{
				state.setObject(1, obj.getCID(), Types.INTEGER);
			}
			state.setObject(2, obj.getName(), Types.VARCHAR);
			state.setObject(3, obj.getYear(), Types.INTEGER);
			state.setObject(4, obj.isCompulsory(), Types.BOOLEAN);
			state.setObject(5, obj.getDepartement().getDID(), Types.INTEGER);
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
	
	public boolean update(Course obj)
	{
		try
		{
			String query = "update COURSES set CNAME = ?, CYEAR = ?, COMPULSORY = ?, DEPTID = ? where CID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getName(), Types.VARCHAR);
			state.setObject(2, obj.getYear(), Types.INTEGER);
			state.setObject(3, obj.isCompulsory(), Types.BOOLEAN);
			state.setObject(4, obj.getDepartement().getDID(), Types.INTEGER);
			state.setObject(5, obj.getCID(), Types.INTEGER);
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

	public boolean delete(Course obj)
	{
		try
		{
			// Dépendances
			EnrollmentDAO.deleteByCourseID(obj.getCID());
			GradeDAO.deleteByCourseID(obj.getCID());
			AssistsDAO.deleteByCourseID(obj.getCID());
			TeachesDAO.deleteByCourseID(obj.getCID());
			
			String query = "delete from COURSES where CID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getCID(), Types.INTEGER);
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
	
	public static Course select(int ID)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from COURSES where CID = '" + ID + "'";
			ResultSet result = state.executeQuery(query);
			
			Course course = new Course();
			int nb = 0;
			while(result.next())
			{
				course.setCID(result.getInt("CID"));
				course.setName(result.getString("CNAME"));
				course.setYear(result.getInt("CYEAR"));
				course.setCompulsory(result.getBoolean("COMPULSORY"));
				course.setDepartement(DepartmentDAO.select(result.getInt("DEPTID")));
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return course;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static List<Course> getList(String clause)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select * from COURSES" + (clause.length() > 0 ? " " + clause : "");
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<Course> coursesList = new ArrayList<Course>();
			
			while(result.next())
			{
				Course course = new Course();
				course.setCID(result.getInt("CID"));
				course.setName(result.getString("CNAME"));
				course.setYear(result.getInt("CYEAR"));
				course.setCompulsory(result.getBoolean("COMPULSORY"));
				course.setDepartement(DepartmentDAO.select(result.getInt("DEPTID")));
				
				coursesList.add(course);
			}
			
			result.close();
			state.close();
			
			return coursesList;
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
			
			String query = "select max(CID) as CID from COURSES";
			ResultSet result = state.executeQuery(query);
			
			int id = 0;
			while(result.next())
			{
				id = result.getInt("CID");
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
	
	public static boolean deleteByDepartmentID(int deptid)
	{
		try
		{
			String query = "delete from COURSES where DEPTID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, deptid, Types.INTEGER);
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