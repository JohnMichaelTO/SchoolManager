package Actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import InterfacesGUI.ClientGUI;
import InterfacesGUI.ListGUI;

public class ActionRemove<T> extends SwingWorker<List<Integer>, Object>
{
	private ClientGUI fenetre = null;
	private ListGUI<T> list = null;
	private String cmd = null;
	
	public ActionRemove(ClientGUI fenetre, ListGUI<T> list, String cmd)
	{
		this.fenetre = fenetre;
		this.list = list;
		this.cmd = cmd;
	}
	
	@Override
	protected List<Integer> doInBackground() throws Exception
	{
		int[] selection = list.getTable().getSelectedRows();
        
        if(selection.length == 0)
        {
        	JOptionPane.showMessageDialog(null, "Vous devez sélectionner au moins un élément dans la liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
        	return new ArrayList<Integer>();
        }
        else
        {
	        List<T> data = new ArrayList<T>();
	        List<Integer> ToDelete = new ArrayList<Integer>();
	        for(int i = selection.length - 1; i >= 0; i--)
	        {
	        	data.add(list.getModele().get(selection[i]));
	        	ToDelete.add(selection[i]);
	            //list.getModele().remove(selection[i]);
	        }

			try
			{
				if(fenetre.getOut() == null)
				fenetre.setOut(new ObjectOutputStream(new BufferedOutputStream(fenetre.getServer().getOutputStream())));

				// Envoie de la commande
				fenetre.getOut().writeObject(cmd + "Remove");
				fenetre.getOut().flush();
				
				// Envoie de la liste à supprimer
				fenetre.getOut().writeObject(data);
				fenetre.getOut().flush();
				
				if(fenetre.getIn() == null)
				fenetre.setIn(new ObjectInputStream(new BufferedInputStream(fenetre.getServer().getInputStream())));

				// Réception de la réponse
				if((boolean) fenetre.getIn().readObject())
				{
					return ToDelete;
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return null;
	}
	
	@Override
	public void done()
	{
		List<Integer> ToDelete;
		try
		{
			ToDelete = get();
			if(ToDelete != null && !ToDelete.isEmpty())
			{
				for(int selection : ToDelete)
				{
					list.getModele().remove(selection);
				}
			}
			else if(ToDelete == null)
			{
				JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la suppression.", "Erreur de suppression", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (InterruptedException | ExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}