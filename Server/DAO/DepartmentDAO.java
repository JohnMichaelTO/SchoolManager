package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import BDD.Connect;
import Objets.*;

public class DepartmentDAO implements DAO<Department>
{
	public static boolean exists(int id)
	{
		try
		{
			String query = "select * from DEPARTMENTS where DID = ?";
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
	
	public boolean insert(Department obj)
	{
		try
		{
			String query = "insert into DEPARTMENTS values(?, ?)";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			if(obj.getDID() == 0)
			{
				state.setObject(1, getLastID() + 1, Types.INTEGER);
			}
			else
			{
				state.setObject(1, obj.getDID(), Types.INTEGER);
			}
			state.setObject(2, obj.getName(), Types.VARCHAR);
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
	
	public boolean update(Department obj)
	{
		try
		{
			String query = "update DEPARTMENTS set DNAME = ? where DID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getName(), Types.VARCHAR);
			state.setObject(2, obj.getDID(), Types.INTEGER);
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

	public boolean delete(Department obj)
	{
		try
		{
			// Suppression des dépendances
			CourseDAO.deleteByDepartmentID(obj.getDID());
			
			String query = "delete from DEPARTMENTS where DID = ?";
			PreparedStatement state = Connect.getConnection().prepareStatement(query);
			state.setObject(1, obj.getDID(), Types.INTEGER);
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
	
	public static Department select(int ID)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from DEPARTMENTS where DID = '" + ID + "'";
			ResultSet result = state.executeQuery(query);
			
			Department Department = new Department();
			int nb = 0;
			while(result.next())
			{
				Department.setDID(result.getInt("DID"));
				Department.setName(result.getString("DNAME"));
				nb++;
			}
			
			result.close();
			state.close();
			
			if(nb == 1) return Department;
		}
		catch (SQLException e)
		{
			// Message d'erreur
		}
		return null;
	}
	
	public static List<Department> getList(String clause)
	{
		try
		{
			Statement state = Connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String query = "select * from DEPARTMENTS" + (clause.length() > 0 ? " " + clause : "");
			ResultSet result = state.executeQuery(query);
			
			// Création de l'objet conteneur de la liste des résultats
			List<Department> departmentsList = new ArrayList<Department>();
			
			while(result.next())
			{
				Department Department = new Department();
				Department.setDID(result.getInt("DID"));
				Department.setName(result.getString("DNAME"));
				
				departmentsList.add(Department);
			}
			
			result.close();
			state.close();
			
			return departmentsList;
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
			
			String query = "select max(DID) as DID from DEPARTMENTS";
			ResultSet result = state.executeQuery(query);
			
			int id = 0;
			while(result.next())
			{
				id = result.getInt("DID");
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
}