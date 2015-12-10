package BDD;
import java.sql.*;

public class Connect
{
	private static String host;
	private static int port;
	private static String database;
	private static String user;
	private static String passwd;
	private static String type;
	private static Connection conn;
	
	// Constructeur
	public Connect()
	{
		this.setType(ConfigFile.type);
		this.setHost(ConfigFile.host);
		this.setPort(ConfigFile.port);
		this.setDatabase(ConfigFile.database);
		this.setUser(ConfigFile.user);
		this.setPasswd(ConfigFile.password);
		
		System.setProperty("jdbc.drivers", "com." + ConfigFile.type + ".jdbc.Driver");
	}
	
	public static void close()
	{
		if(conn != null)
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			conn = null;
		}
	}

	public static Connection getConnection()
	{
		if(conn == null)
		{
			try
			{
				conn = DriverManager.getConnection("jdbc:" + type + "://" + host + ":" + port + "/" + database, user, passwd);
			}
			catch (Exception e)
			{
				return null;
			}
		}
		return conn;
	}
	
	public static boolean isValid()
	{
		if(Connect.getConnection() == null)
		{
			return false;
		}
		ResultSet ping = null;
		try
		{
			if(conn.isClosed())
			{
				return false;
			}
			ping = Connect.getConnection().createStatement().executeQuery("SELECT 1");
			return ping.next();
		}
		catch(SQLException sqle)
		{
			return false;
		}
		finally
		{
			if(ping != null)
			{
				try
				{
					ping.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		Connect.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		Connect.port = port;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		Connect.database = database;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		Connect.user = user;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		Connect.passwd = passwd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		Connect.type = type;
	}
}
