package ServerLauncher;

import java.io.IOException;
import java.net.*;

import BDD.*;

public class MainS
{
	// Socket serveur
	public static ServerSocket serverSocket = null;
	public static Thread thread;
	
	public static void main(String[] args)
	{
		try
		{
			// Chargement du fichier de configuration
			new ConfigFile();
			// Connexion au serveur BDD
			new Connect();
			
			if(Connect.isValid())
			{
				System.out.println("Connection to the database ok!");
				
				// Lancement du serveur
				serverSocket = new ServerSocket(ConfigFile.serverport);
				System.out.println("Server started and is listening on port : " + ConfigFile.serverport);
				
				// Lancement du thread d'acceptation de client
				thread = new Thread(new AddConnection(serverSocket));
				thread.start();
			}
			else
			{
				System.err.println("Connection to the database failed!");
				System.err.println("The server couldn't start!");
			}
		}
		catch (BindException e)
		{
			try
			{
				if(serverSocket != null) serverSocket.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			System.err.println("Port " + ConfigFile.serverport + " is already used!");
			System.err.println("The server couldn't start!");
		}
		catch (Exception e)
		{
			try
			{
				if(serverSocket != null) serverSocket.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			System.out.println("Server stopped!");
		}
	}
}
