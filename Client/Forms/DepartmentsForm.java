package Forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutionException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;

import InterfacesGUI.ClientGUI;
import Objets.Department;

public class DepartmentsForm extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField nomField;
	
	private JButton okButton = null;
	private JButton cancelButton = new JButton("Annuler");
	
	private ClientGUI fenetre = null;
	private Department departmentSelected = null;
	
	/**
	 * Create the dialog.
	 */
	public DepartmentsForm(ClientGUI fenetre, Department departmentSelected)
	{
		this.fenetre = fenetre;
		this.departmentSelected = departmentSelected;
		
		if(departmentSelected == null)
		{
			setIconImage(Toolkit.getDefaultToolkit().getImage(DepartmentsForm.class.getResource("/Images/departmentsAdd.png")));
			setTitle("Ajouter un département");
			okButton = new JButton("Ajouter");
			okButton.setIcon(new ImageIcon(DepartmentsForm.class.getResource("/Images/departmentsAdd.png")));
		}
		else
		{
			setIconImage(Toolkit.getDefaultToolkit().getImage(DepartmentsForm.class.getResource("/Images/departmentsUpdate.png")));
			setTitle("Modifier un département");
			okButton = new JButton("Modifier");
			okButton.setIcon(new ImageIcon(DepartmentsForm.class.getResource("/Images/departmentsUpdate.png")));
		}
		cancelButton.setIcon(new ImageIcon(DepartmentsForm.class.getResource("/Images/departmentsDel.png")));
		
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 140);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		initGUI();
		createEvent();
	}
	
	public void initGUI()
	{
		JLabel lblNom = new JLabel("Nom :");
		
		nomField = new JTextField();
		nomField.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(31)
					.addComponent(lblNom)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(nomField, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(41, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNom)
						.addComponent(nomField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(166, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		if(departmentSelected != null)
		{
			nomField.setText(departmentSelected.getName());
		}
	}
	
	public void createEvent()
	{
		okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(nomField.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Vous devez remplir tous les champs.", "Champs requis", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					ActionSave actionSave = new ActionSave();
					actionSave.execute();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				dispose();
			}
		});
	}
	
	private class ActionSave extends SwingWorker<Object, Object>
	{
		@Override
		protected Object doInBackground() throws Exception
		{
			try
			{
				if(fenetre.getOut() == null) 
				fenetre.setOut(new ObjectOutputStream(new BufferedOutputStream(fenetre.getServer().getOutputStream())));
				
				String actionCommande = "Add";
				int id = 0;
				if(departmentSelected != null)
				{
					actionCommande = "Update";
					id = departmentSelected.getDID();
				}

				Department department = new Department();
				department.setDID(id);
				department.setName(nomField.getText());
				
				// Envoie commande
				fenetre.getOut().writeObject("department" + actionCommande);
				fenetre.getOut().flush();
				// Envoie objet
				fenetre.getOut().writeObject(department);
				fenetre.getOut().flush();
				
				if(fenetre.getIn() == null)
				fenetre.setIn(new ObjectInputStream(new BufferedInputStream(fenetre.getServer().getInputStream())));
				
				boolean result = (boolean) fenetre.getIn().readObject();
				if(result) return department;
			}
			catch (IOException e)
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
				Department department = (Department) get();
				if(department == null)
				{
					if(departmentSelected == null)
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'ajout.\nVérifiez que le département n'est pas déjà présent dans la base de données.", "Erreur d'ajout", JOptionPane.ERROR_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la modification.\nVérifiez que le département n'est pas déjà présent dans la base de données.", "Erreur de modification", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(departmentSelected == null)
					{
						fenetre.getDepartmentsListGUI().getModele().add(department);
	
						JOptionPane.showMessageDialog(null, "Département ajouté avec succès.", "Ajout OK", JOptionPane.INFORMATION_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null, "Département modifié avec succès.", "Modification OK", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			}
			catch (InterruptedException | ExecutionException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
