package Actions;

import java.io.IOException;

import javax.swing.SwingWorker;

import InterfacesGUI.ClientGUI;

public class ActionLogout extends SwingWorker<Object, Object>
{
	private ClientGUI fenetre = null;
	
	public ActionLogout(ClientGUI fenetre)
	{
		this.fenetre = fenetre;
	}
	
	@Override
	protected Object doInBackground() throws Exception
	{
		try
		{
			// Commande logout
			fenetre.getOut().writeObject("logout");
			fenetre.getOut().flush();
			
			// Envoie du login déconnecté
			fenetre.getOut().writeObject(fenetre.getCurrentUser().getEmail());
			fenetre.getOut().flush();
			return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}