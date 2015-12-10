package InterfacesGUI;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import Modeles.MDefaultTable;

public class ListGUI<T> extends JScrollPane
{
	private static final long serialVersionUID = 1L;
	
	// JTable
	private MDefaultTable<T> modele = null;
	private JTable table = null;
	private JScrollPane panel = null;
	
	public ListGUI(MDefaultTable<T> modele)
	{
		this.modele = modele;
		table = new JTable(modele);
		
		panel = new JScrollPane(table);
	}

	public MDefaultTable<T> getModele()
	{
		return modele;
	}

	public JTable getTable()
	{
		return table;
	}

	public void setModele(MDefaultTable<T> modele)
	{
		this.modele = modele;
	}

	public void setTable(JTable table)
	{
		this.table = table;
	}

	public JScrollPane getPanel()
	{
		return panel;
	}

	public void setPanel(JScrollPane panel)
	{
		this.panel = panel;
	}
}
