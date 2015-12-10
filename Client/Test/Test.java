package Test;

import java.util.Scanner;

import BDD.Connect;
import DAO.AdminDAO;
import DAO.DAO;
import DAO.UserDAO;
import Objets.Admin;
import Objets.User;

public class Test
{
	public static void UserAdd(int UID, String lastname, String firstname, String email, String password, String type)
	{
		User user = new User(UID, lastname, firstname, email, password, type);
		DAO<User> userDAO = new UserDAO();
		userDAO.insert(user);
		
		System.out.println("Ajout utilisateur : " + UserDAO.exists(UID));
	}
	
	public static void UserUpdate(int UID, String lastname, String firstname, String email, String password, String type)
	{
		User user = new User(UID, lastname, firstname, email, password, type);
		DAO<User> userDAO = new UserDAO();
		userDAO.update(user);
		
		System.out.println("Modification utilisateur : " + UserDAO.exists(UID));
	}
	
	public static void UserDelete(int UID, String lastname, String firstname, String email, String password, String type)
	{
		User user = new User(UID, lastname, firstname, email, password, type);
		DAO<User> userDAO = new UserDAO();
		userDAO.delete(user);
		
		System.out.println("Suppression utilisateur : " + !UserDAO.exists(UID));
	}
	
	public static void AdminAdd(int UID, String lastname, String firstname, String email, String password)
	{
		Admin admin = new Admin(UID, lastname, firstname, email, password);
		DAO<Admin> adminDAO = new AdminDAO();
		adminDAO.insert(admin);
		
		System.out.println("Ajout admin : " + AdminDAO.exists(UID));
	}
	
	public static void AdminUpdate(int UID, String lastname, String firstname, String email, String password)
	{
		Admin admin = new Admin(UID, lastname, firstname, email, password);
		DAO<Admin> adminDAO = new AdminDAO();
		adminDAO.update(admin);
		
		System.out.println("Update admin : " + AdminDAO.exists(UID));
	}
	
	public static void AdminDelete(int UID, String lastname, String firstname, String email, String password)
	{
		Admin admin = new Admin(UID, lastname, firstname, email, password);
		DAO<Admin> adminDAO = new AdminDAO();
		adminDAO.delete(admin);
		
		System.out.println("Delete admin : " + !AdminDAO.exists(UID));
	}
	
	public static void main(String[] args)
	{
		// InterfaceLogin test = new InterfaceLogin();
		new Connect();
		
		// Saisie du Login et du Password
		Scanner sc = new Scanner(System.in);
		System.out.println("--- Connection ---");  
		System.out.println("Veuillez saisir votre login :");
		String email = sc.nextLine();
		System.out.println("Veuillez saisir votre Password :");
		String password = sc.nextLine();
		
		if(UserDAO.login(email, password) != null) System.out.println("Login OK!\n");
		else System.out.println("Erreur d'identifiants!\n");
		
		// UserAdd(7, "test", "test", "test@test3", "test", "STUDENT");
		// UserUpdate(7, "test", "test", "blabla", "test", "STUDENT");
		// UserDelete(7, "test", "test", "blabla", "test", "STUDENT");
		// AdminAdd(8, "test", "test", "zeezrzerderze", "test");
		// AdminUpdate(8, "test", "testezrzerez", "zeezrzerderze", "test");
		// AdminDelete(8, "test", "testezrzerez", "zeezrzerderze", "test");
	}
}