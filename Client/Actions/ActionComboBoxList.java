package Actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JComboBox;
import javax.swing.SwingWorker;

import InterfacesGUI.ClientGUI;
import Modeles.MComboBox;

public class ActionComboBoxList<T> extends SwingWorker<List<T>, Object>
{
	private ClientGUI fenetre = null;
	private MComboBox<T> comboBoxModele = null;
	private JComboBox<T> comboBox = null;
	private T selectedObject = null;
	private String cmd = null;
	
	public ActionComboBoxList(ClientGUI fenetre, MComboBox<T> comboBoxModele, JComboBox<T> comboBox, T selectedObject, String cmd)
	{
		this.fenetre = fenetre;
		this.comboBoxModele = comboBoxModele;
		this.comboBox = comboBox;
		this.selectedObject = selectedObject;
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

			// Envoie de la commande
			fenetre.getOut().writeObject(cmd);
			fenetre.getOut().flush();
			
			if(cmd.contains("StudentComboBox"))
			{
				fenetre.getOut().writeObject(fenetre.getCurrentStudent());
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
			comboBoxModele.setData(get());
			// Sélectionne automatiquement l'étudiant loggué dans la liste des étudiants
			if(cmd.contains("students") && fenetre.getCurrentUser().getType().equals("STUDENT"))
			{
				comboBox.setSelectedItem(fenetre.getCurrentStudent());
			}
			else if(selectedObject != null)
			comboBox.setSelectedItem(selectedObject);
		}
		catch (InterruptedException | ExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}