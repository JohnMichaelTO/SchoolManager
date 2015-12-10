package ClientLauncher;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

import javax.swing.JOptionPane;

import InterfacesGUI.ClientGUI;

public class MainC
{
	public static Socket server = null;
	public static String host;
	public static int port;
	
	public static void main(String[] args)
	{
		// Chargement fichier de configuration
		String configPath = "client.config";
		Properties properties = new Properties();
		try
		{
			FileInputStream in = new FileInputStream(configPath);
			
			properties.load(in);
			in.close();
			
			host = properties.getProperty("host", "localhost");
			port = Integer.parseInt(properties.getProperty("port", "3000"));
		}
		catch (IOException e)
		{
			System.err.println("Le fichier de configuration client.config n'a pu être chargé correctement.");
		}
		
		// Connexion au serveur
		try
		{
			System.out.println("Connexion au serveur : " + host + ":" + port);
			server = new Socket(host, port);
			
			ClientGUI client = new ClientGUI(server);
			client.setVisible(true);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Erreur de connexion au serveur, veuillez vérifier le fichier de configuration.", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
			try
			{
				if(server != null) server.close();
			}
			catch (IOException e1)
			{
				
			}
			System.err.println("Fermeture de la connexion au serveur");
		}
	}
}
