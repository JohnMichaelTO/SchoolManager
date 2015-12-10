package ServerLauncher;

import java.net.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class AddConnection implements Runnable
{
	// Socket serveur
	private ServerSocket serverSocket = null;
	// Socket client
	private Socket clientSocket = null;
	// NB clients connectés
	public static int nbClients = 0;
	// Date Log
	private DateFormat logDate = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("EN","en"));
	public Thread thread;
	
	// Constructor
	public AddConnection(ServerSocket serverSocket)
	{
		this.serverSocket = serverSocket;
	}
	
	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				// Nouveau client
				clientSocket = serverSocket.accept();
				nbClients++;
				// Log
				System.out.println(logDate.format(new Date()) + " - [SERVER CONNECTION] - IP : " + clientSocket.getInetAddress());
				System.out.println(nbClients + " client(s) connected!");
				
				// Lancement du threat d'attente des commandes
				thread = new Thread(new Commandes(clientSocket));
				thread.start();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
