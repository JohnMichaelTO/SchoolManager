package Actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import InterfacesGUI.ClientGUI;

public class ActionUpdate<T> extends SwingWorker<Boolean, Object>
{
	private ClientGUI fenetre = null;
	private T update = null;
	private String cmd = null;
	
	public ActionUpdate(ClientGUI fenetre, T update, String cmd)
	{
		this.fenetre = fenetre;
		this.update = update;
		this.cmd = cmd;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception
	{
		try
		{
			if(fenetre.getOut() == null)
			fenetre.setOut(new ObjectOutputStream(new BufferedOutputStream(fenetre.getServer().getOutputStream())));

			// Envoie de la commande
			fenetre.getOut().writeObject(cmd + "Update");
			fenetre.getOut().flush();
			
			// Envoie de l'objet
			fenetre.getOut().writeObject(update);
			fenetre.getOut().flush();

			if(fenetre.getIn() == null)
			fenetre.setIn(new ObjectInputStream(new BufferedInputStream(fenetre.getServer().getInputStream())));

			// Réception de la réponse
			return (boolean) fenetre.getIn().readObject();
		}
		catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void done()
	{
		try
		{
			if(!get())
			{
				JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la modification.", "Erreur de modification", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (InterruptedException | ExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}