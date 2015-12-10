package BDD;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigFile
{
	public static String type;
	public static String host;
	public static int port;
	public static String database;
	public static String user;
	public static String password;
	public static int serverport;
	
	public ConfigFile()
	{
		String configPath = "server.config";
		Properties properties = new Properties();
		try
		{
			FileInputStream in = new FileInputStream(configPath);
	
			properties.load(in);
			in.close();
			
			type = properties.getProperty("type", "mysql");
			host = properties.getProperty("host", "localhost");
			port = Integer.parseInt(properties.getProperty("port", "3306"));
			database = properties.getProperty("database", "database");
			user = properties.getProperty("user", "root");
			password = properties.getProperty("password", "");
			serverport = Integer.parseInt(properties.getProperty("serverport", "3000"));
		}
		catch (IOException e)
		{
			System.err.println("Le fichier de configuration server.config n'a pu être chargé correctement.");
		}
	}
}
