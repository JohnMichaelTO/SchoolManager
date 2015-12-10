package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.*;

public class TeacherDAO implements DAO<Teacher>
{
	public static boolean exists(int UID)
	{
		try
		{
			String query = "select A.UID from USERS A join TEACHERS B on A.UID = B.TID where A.UID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, UID, Types.INTEGER);
			ResultSet result = state.executeQuery();
			
			int nbRows = 0;
			while(result.next()) { nbRows++; }
			
			result.close();
			state.close();
			
			if(nbRows == 1) return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insert(Teacher obj)
	{
		if(obj.getUID() == 0) obj.setUID(UserDAO.getLastID() + 1);
		// Insertion du professeur en tant qu'utilisateur
		UserDAO user = new UserDAO();
		user.insert((User) obj);
		try
		{
			String query = "insert into TEACHERS values(?)";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getUID(), Types.INTEGER);
			state.executeUpdate();
			
			state.close();
			return true;
		}
		catch (SQLException e)
		{
			// Message d'erreur
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Teacher obj)
	{
		// Modification des champs utilisateurs de teacher
		UserDAO user = new UserDAO();
		return user.update((User) obj);
	}

	public boolean delete(Teacher obj)
	{
		try
		{
			String query = "delete from TEACHERS where TID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getUID(), Types.INTEGER);
			state.executeUpdate();
			
			state.close();
			
			// Suppression de l'utilisateur
			UserDAO user = new UserDAO();
			user.delete((User) obj);
			
			return true;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return false;
	}
	
	public static Teacher select(int UID)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from USERS join TEACHERS on TID = UID where TID = '" + UID + "'";
			ResultSet result = state.executeQuery(query);
			
			Teacher Teacher = new Teacher();
			int nb = 0;
			while(result.next())
			{
				Teacher.setUID(result.getInt("UID"));
				Teacher.setLastname(result.getString("LASTNAME"));
				Teacher.setFirstname(result.getString("FIRSTNAME"));
				Teacher.setEmail(result.getString("EMAIL"));
				Teacher.setPassword(result.getString("PASSWORD"));
				Teacher.setType(result.getString("UTYPE"));
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return Teacher;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static List<Teacher> getList(String clause)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select * from USERS join TEACHERS on UID = TID" + (clause.length() > 0 ? " " + clause : "");
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<Teacher> teachersList = new ArrayList<Teacher>();
			
			while(result.next())
			{
				Teacher teacher = new Teacher();
				teacher.setUID(result.getInt("UID"));
				teacher.setLastname(result.getString("LASTNAME"));
				teacher.setFirstname(result.getString("FIRSTNAME"));
				teacher.setEmail(result.getString("EMAIL"));
				teacher.setPassword(result.getString("PASSWORD"));
				
				teachersList.add(teacher);
			}
			
			result.close();
			state.close();
			
			return teachersList;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
}