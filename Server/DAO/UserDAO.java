package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.User;

public class UserDAO implements DAO<User>
{
	// Vérifie qu'un utilisateur existe dans la table user de la BDD
	public static boolean exists(int UID)
	{
		try
		{
			String query = "select UID from USERS where UID = ?";
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
	
	public boolean insert(User obj)
	{
		try
		{
			String query = "insert into USERS values(?, ?, ?, ?, ?, ?)";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			if(obj.getUID() == 0)
			{
				state.setObject(1, getLastID() + 1, Types.INTEGER);
			}
			else
			{
				state.setObject(1, obj.getUID(), Types.INTEGER);
			}
			state.setObject(2, obj.getLastname(), Types.VARCHAR);
			state.setObject(3, obj.getFirstname(), Types.VARCHAR);
			state.setObject(4, obj.getEmail(), Types.VARCHAR);
			state.setObject(5, obj.getPassword(), Types.VARCHAR);
			state.setObject(6, obj.getType(), Types.VARCHAR);
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
	
	public boolean update(User obj)
	{
		try
		{
			String query = "update USERS set LASTNAME = ?, FIRSTNAME = ?, EMAIL = ?, PASSWORD = ?, UTYPE = ? where UID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getLastname(), Types.VARCHAR);
			state.setObject(2, obj.getFirstname(), Types.VARCHAR);
			state.setObject(3, obj.getEmail(), Types.VARCHAR);
			state.setObject(4, obj.getPassword(), Types.VARCHAR);
			state.setObject(5, obj.getType(), Types.VARCHAR);
			state.setObject(6, obj.getUID(), Types.INTEGER);
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

	public boolean delete(User obj)
	{
		try
		{
			String query = "delete from USERS where UID = ?";
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
	
	public static User select(int UID)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from USERS where UID = '" + UID + "'";
			ResultSet result = state.executeQuery(query);
			
			User user = new User();
			int nb = 0;
			while(result.next())
			{
				user.setUID(result.getInt("UID"));
				user.setLastname(result.getString("LASTNAME"));
				user.setFirstname(result.getString("FIRSTNAME"));
				user.setEmail(result.getString("EMAIL"));
				user.setPassword(result.getString("PASSWORD"));
				user.setType(result.getString("UTYPE"));
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return user;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	// Renvoie l'utilisateur courant loggué ou null si l'utilisateur n'existe pas
	public static User login(String email, String password)
	{
		// Tentative de connection avec les informations de login et de password
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			/**************************************************************************************************************/
			// TODO : Faire la gestion des erreurs pour différencier s'il y a une une erreur sur le login ou le password
			/**************************************************************************************************************/
			String query = "select * from USERS where EMAIL = lower('" + email + "') and PASSWORD = '" + password + "'";
			ResultSet result = state.executeQuery(query);
			
			User user = new User();
			int nb = 0;
			while(result.next())
			{
				user.setUID(result.getInt("UID"));
				user.setLastname(result.getString("LASTNAME"));
				user.setFirstname(result.getString("FIRSTNAME"));
				user.setEmail(result.getString("EMAIL"));
				user.setPassword(result.getString("PASSWORD"));
				user.setType(result.getString("UTYPE"));
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return user;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static List<User> getList(String clause)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select * from USERS" + (clause.length() > 0 ? " " + clause : "");
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<User> usersList = new ArrayList<User>();
			
			while(result.next())
			{
				User user = new User();
				user.setUID(result.getInt("UID"));
				user.setLastname(result.getString("LASTNAME"));
				user.setFirstname(result.getString("FIRSTNAME"));
				user.setEmail(result.getString("EMAIL"));
				user.setPassword(result.getString("PASSWORD"));
				user.setType(result.getString("UTYPE"));
				
				usersList.add(user);
			}
			
			result.close();
			state.close();
			
			return usersList;
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
			
			String query = "select max(UID) as UID from USERS";
			ResultSet result = state.executeQuery(query);
			
			int uid = 0;
			while(result.next())
			{
				uid = result.getInt("UID");
			}
			
			result.close();
			state.close();
			
			return uid;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return 0;
	}
}