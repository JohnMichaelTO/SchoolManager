package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.*;

public class AdminDAO implements DAO<Admin>
{
	public static boolean exists(int UID)
	{
		try
		{
			String query = "select A.UID from USERS A join ADMINS B on A.UID = B.AID where A.UID = ?";
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
			// Message d'erreur
		}
		return false;
	}
	
	public boolean insert(Admin obj)
	{
		if(obj.getUID() == 0) obj.setUID(UserDAO.getLastID() + 1);
		
		// Insertion de l'admin en tant qu'utilisateur
		UserDAO user = new UserDAO();
		user.insert((User) obj);
		try
		{
			String query = "insert into ADMINS values(?)";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getUID(), Types.INTEGER);
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
	
	public boolean update(Admin obj)
	{
		// Modification des champs utilisateurs de l'admin
		UserDAO user = new UserDAO();
		return user.update((User) obj);
	}

	public boolean delete(Admin obj)
	{
		try
		{
			String query = "delete from ADMINS where AID = ?";
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
	
	public static Admin select(int UID)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from USERS join ADMINS on AID = UID where AID = '" + UID + "'";
			ResultSet result = state.executeQuery(query);
			
			Admin admin = new Admin();
			int nb = 0;
			while(result.next())
			{
				admin.setUID(result.getInt("UID"));
				admin.setLastname(result.getString("LASTNAME"));
				admin.setFirstname(result.getString("FIRSTNAME"));
				admin.setEmail(result.getString("EMAIL"));
				admin.setPassword(result.getString("PASSWORD"));
				admin.setType(result.getString("UTYPE"));
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return admin;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static List<Admin> getList(String clause)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select * from USERS join ADMINS on UID = AID" + (clause.length() > 0 ? " " + clause : "");
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<Admin> adminsList = new ArrayList<Admin>();
			
			while(result.next())
			{
				Admin admin = new Admin();
				admin.setUID(result.getInt("UID"));
				admin.setLastname(result.getString("LASTNAME"));
				admin.setFirstname(result.getString("FIRSTNAME"));
				admin.setEmail(result.getString("EMAIL"));
				admin.setPassword(result.getString("PASSWORD"));
				
				adminsList.add(admin);
			}
			
			result.close();
			state.close();
			
			return adminsList;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
}