package Actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import InterfacesGUI.ClientGUI;
import InterfacesGUI.ListGUI;

public class ActionList<T> extends SwingWorker<List<T>, Object>
{
	private ClientGUI fenetre = null;
	private ListGUI<T> list = null;
	private String cmd = null;
	
	public ActionList(ClientGUI fenetre, ListGUI<T> list, String cmd)
	{
		this.fenetre = fenetre;
		this.list = list;
		this.cmd = cmd;
	}
	
	@Override
	protected List<T> doInBackground() throws Exception
	{
		try
		{
			List<T> data = null;
			if(fenetre.getOut() == null)
			fenetre.setOut(new ObjectOutputStream(new BufferedOutputStream(fenetre.getServer().getOutputStream())));

			// Envoie de la commande : demande de la liste
			fenetre.getOut().writeObject(cmd);
			fenetre.getOut().flush();
			
			if(cmd.contains("Student"))
			{
				fenetre.getOut().writeObject(fenetre.getCurrentStudent());
				fenetre.getOut().flush();
			}
			else if(cmd.contains("Teacher"))
			{
				fenetre.getOut().writeObject(fenetre.getCurrentTeacher());
				fenetre.getOut().flush();
			}

			if(fenetre.getIn() == null)
			fenetre.setIn(new ObjectInputStream(new BufferedInputStream(fenetre.getServer().getInputStream())));

			// Réception de la liste
			data = (List<T>) fenetre.getIn().readObject();
			
			return data;
		}
		catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void done()
	{
		try
		{
			list.getModele().setData(get());
		}
		catch (InterruptedException | ExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}