package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.*;

public class StudentDAO implements DAO<Student>
{
	public static boolean exists(int UID)
	{
		try
		{
			String query = "select A.UID from USERS A join STUDENTS B on A.UID = B.SID where A.UID = ?";
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
	
	public boolean insert(Student obj)
	{
		if(obj.getUID() == 0) obj.setUID(UserDAO.getLastID() + 1);
		// Insertion de l'étudiant en tant qu'utilisateur
		UserDAO user = new UserDAO();
		user.insert((User) obj);
		try
		{
			String query = "insert into STUDENTS values(?, ?, ?)";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getUID(), Types.INTEGER);
			state.setObject(2, obj.getPromo(), Types.INTEGER);
			state.setObject(3, obj.getTutor().getUID(), Types.INTEGER);
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
	
	public boolean update(Student obj)
	{
		// Modification des champs utilisateurs de l'étudiant
		UserDAO user = new UserDAO();
		user.update((User) obj);
		try
		{
			String query = "update STUDENTS set PROMO = ?, TUTOR = ? where SID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getPromo(), Types.INTEGER);
			state.setObject(2, obj.getTutor().getUID(), Types.INTEGER);
			state.setObject(3, obj.getUID(), Types.INTEGER);
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

	public boolean delete(Student obj)
	{
		try
		{
			// Dépendances
			EnrollmentDAO.deleteByStudentID(obj.getUID());
			
			String query = "delete from STUDENTS where SID = ?";
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
	
	public static Student select(int UID)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from USERS join STUDENTS on SID = UID where SID = '" + UID + "'";
			ResultSet result = state.executeQuery(query);
			
			Student student = new Student();
			int nb = 0;
			while(result.next())
			{
				student.setUID(result.getInt("UID"));
				student.setLastname(result.getString("LASTNAME"));
				student.setFirstname(result.getString("FIRSTNAME"));
				student.setEmail(result.getString("EMAIL"));
				student.setPassword(result.getString("PASSWORD"));
				student.setType(result.getString("UTYPE"));
				
				student.setPromo(result.getInt("PROMO"));
				student.setTutor(TeacherDAO.select(result.getInt("TUTOR")));
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return student;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static List<Student> getList(String clause)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select * from USERS join STUDENTS on UID = SID" + (clause.length() > 0 ? " " + clause : "");
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<Student> studentsList = new ArrayList<Student>();
			
			while(result.next())
			{
				Student student = new Student();
				student.setUID(result.getInt("UID"));
				student.setLastname(result.getString("LASTNAME"));
				student.setFirstname(result.getString("FIRSTNAME"));
				student.setEmail(result.getString("EMAIL"));
				student.setPassword(result.getString("PASSWORD"));
				student.setPromo(result.getInt("PROMO"));
				student.setTutor(TeacherDAO.select(result.getInt("TUTOR")));
				
				studentsList.add(student);
			}
			
			result.close();
			state.close();
			
			return studentsList;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
}