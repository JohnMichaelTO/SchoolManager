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

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import Actions.ActionComboBoxList;
import InterfacesGUI.ClientGUI;
import Modeles.MComboBox;
import Objets.Admin;
import Objets.Student;
import Objets.Teacher;
import Objets.User;

public class UsersForm extends JDialog
{
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private ClientGUI fenetre = null;
	
	private JTextField nomField;
	private JTextField prenomField;
	private JTextField emailField;
	private JPasswordField passwordField;
	
	private JComboBox<String> comboBoxType = new JComboBox<String>();
	private JComboBox<String> comboBoxPromo = null;
	private JComboBox<Teacher> comboBoxTuteur = null;
	
	private MComboBox<Teacher> comboBoxTuteurModele = new MComboBox<Teacher>();
	
	private JButton okButton = null;
	private JButton cancelButton = new JButton("Annuler");
	
	private User userSelected = null;
	
	/**
	 * Create the dialog.
	 */
	public UsersForm(ClientGUI fenetre, User userSelected)
	{
		this.fenetre = fenetre;
		this.userSelected = userSelected;

		if(userSelected == null)
		{
			setIconImage(Toolkit.getDefaultToolkit().getImage(UsersForm.class.getResource("/Images/usersAdd.png")));
			setTitle("Ajouter un utilisateur");
			okButton = new JButton("Ajouter");
			okButton.setIcon(new ImageIcon(UsersForm.class.getResource("/Images/usersAdd.png")));
		}
		else
		{
			setIconImage(Toolkit.getDefaultToolkit().getImage(UsersForm.class.getResource("/Images/usersUpdate.png")));
			setTitle("Modifier un utilisateur");
			okButton = new JButton("Modifier");
			okButton.setIcon(new ImageIcon(UsersForm.class.getResource("/Images/usersUpdate.png")));
		}
		cancelButton.setIcon(new ImageIcon(UsersForm.class.getResource("/Images/usersDel.png")));
		
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 413, 358);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		comboBoxPromo = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] {"L1", "L2", "L3", "M1", "M2"}));
		comboBoxTuteur = new JComboBox<Teacher>(comboBoxTuteurModele);
		
		ActionComboBoxList<Teacher> actionGetTutors = new ActionComboBoxList<Teacher>(fenetre, comboBoxTuteurModele, comboBoxTuteur, null, "teachersList");
		actionGetTutors.execute();
		
		initGUI();
		createEvent();
	}
	
	public void initGUI()
	{
		contentPanel.setBorder(new TitledBorder(null, "Ajouter un utilisateur", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel labelType = new JLabel("Type :");
		comboBoxType.setModel(new DefaultComboBoxModel<String>(new String[] {"Admin", "Etudiant", "Professeur"}));
		JLabel labelNom = new JLabel("Nom :");
		nomField = new JTextField();
		nomField.setColumns(10);
		JLabel labelPrenom = new JLabel("Prénom :");
		prenomField = new JTextField();
		prenomField.setColumns(10);
		JLabel labelEmail = new JLabel("Email :");
		emailField = new JTextField();
		emailField.setColumns(10);
		JLabel labelPassword = new JLabel("Mot de passe :");
		passwordField = new JPasswordField();
		JLabel labelPromo = new JLabel("Promo :");
		comboBoxPromo.setEnabled(false);
		JLabel labelTutor = new JLabel("Professeur tuteur :");
		comboBoxTuteur.setEnabled(false);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(71)
							.addComponent(labelType, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(73)
							.addComponent(labelNom, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(nomField, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(55)
							.addComponent(labelPrenom, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(prenomField, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(68)
							.addComponent(labelEmail, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(emailField, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(23)
							.addComponent(labelPassword, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(62)
							.addComponent(labelPromo, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(comboBoxPromo, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(labelTutor, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(comboBoxTuteur, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(188, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(6)
							.addComponent(labelType))
						.addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(labelNom))
						.addComponent(nomField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(labelPrenom))
						.addComponent(prenomField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(labelEmail))
						.addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(labelPassword))
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(labelPromo))
						.addComponent(comboBoxPromo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(labelTutor)
						.addComponent(comboBoxTuteur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(89, Short.MAX_VALUE))
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
		
		if(userSelected != null)
		{
			comboBoxType.setEnabled(false);
			nomField.setText(userSelected.getLastname());
			prenomField.setText(userSelected.getFirstname());
			emailField.setText(userSelected.getEmail());
			passwordField.setText(userSelected.getPassword());
			
			ActionSelectTypeComboBox actionSelectComboBox = new ActionSelectTypeComboBox();
			actionSelectComboBox.execute();
		}
	}
	
	public void createEvent()
	{
		okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(nomField.getText().isEmpty() || prenomField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getPassword().length == 0)
				{
					JOptionPane.showMessageDialog(null, "Vous devez remplir tous les champs.", "Champs requis", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(comboBoxType.getSelectedItem().toString() == "Etudiant" && (comboBoxPromo.getSelectedItem() == null || comboBoxPromo.getSelectedItem() == null))
					{
						JOptionPane.showMessageDialog(null, "Vous devez remplir tous les champs.", "Champs requis", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						ActionSave actionSave = new ActionSave();
						actionSave.execute();
					}
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
		
		comboBoxType.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(comboBoxType.getSelectedItem().toString() == "Etudiant")
				{
					comboBoxPromo.setEnabled(true);
					comboBoxTuteur.setEnabled(true);
				}
				else
				{
					comboBoxPromo.setEnabled(false);
					comboBoxTuteur.setEnabled(false);
				}
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
				Object user = null;
				if(fenetre.getOut() == null) 
				fenetre.setOut(new ObjectOutputStream(new BufferedOutputStream(fenetre.getServer().getOutputStream())));
				
				String actionCommande = "Add";
				int uid = 0;
				if(userSelected != null)
				{
					actionCommande = "Update";
					uid = userSelected.getUID();
				}
				
				switch(comboBoxType.getSelectedItem().toString())
				{
					case "Etudiant" :
						Student student = new Student();
						student.setUID(uid);
						student.setLastname(nomField.getText());
						student.setFirstname(prenomField.getText());
						student.setEmail(emailField.getText());
						student.setPassword(new String(passwordField.getPassword()));
						student.setPromo(comboBoxPromo.getSelectedIndex() + 1);
						student.setTutor((Teacher) comboBoxTuteur.getSelectedItem());
						
						user = (Student) student;
						
						// Envoie commande
						fenetre.getOut().writeObject("student" + actionCommande);
						fenetre.getOut().flush();
						// Envoie objet
						fenetre.getOut().writeObject(student);
						fenetre.getOut().flush();
						break;
					case "Admin" :
						Admin admin = new Admin();
						admin.setUID(uid);
						admin.setLastname(nomField.getText());
						admin.setFirstname(prenomField.getText());
						admin.setEmail(emailField.getText());
						admin.setPassword(new String(passwordField.getPassword()));
						
						user = (Admin) admin;
						
						// Envoie commande
						fenetre.getOut().writeObject("admin" + actionCommande);
						fenetre.getOut().flush();
						// Envoie objet
						fenetre.getOut().writeObject(admin);
						fenetre.getOut().flush();
						break;
					case "Professeur":
						Teacher teacher = new Teacher();
						teacher.setUID(uid);
						teacher.setLastname(nomField.getText());
						teacher.setFirstname(prenomField.getText());
						teacher.setEmail(emailField.getText());
						teacher.setPassword(new String(passwordField.getPassword()));
						
						user = (Teacher) teacher;
						
						// Envoie commande
						fenetre.getOut().writeObject("teacher" + actionCommande);
						fenetre.getOut().flush();
						// Envoie objet
						fenetre.getOut().writeObject(teacher);
						fenetre.getOut().flush();
						break;
				}
				
				if(fenetre.getIn() == null)
				fenetre.setIn(new ObjectInputStream(new BufferedInputStream(fenetre.getServer().getInputStream())));
				
				boolean result = (boolean) fenetre.getIn().readObject();
				if(result) return user;
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
				User user = (User) get();
				if(user == null)
				{
					if(userSelected == null)
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'ajout.\nVérifiez que l'adresse email n'est pas déjà présente dans la base de données.", "Erreur d'ajout", JOptionPane.ERROR_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la modification.\nVérifiez que l'adresse email n'est pas déjà présente dans la base de données.", "Erreur de modification", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(userSelected == null)
					{
						fenetre.getUsersListGUI().getModele().add(user);
	
						JOptionPane.showMessageDialog(null, "Utilisateur ajouté avec succès.", "Ajout OK", JOptionPane.INFORMATION_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null, "Utilisateur modifié avec succès.", "Modification OK", JOptionPane.INFORMATION_MESSAGE);
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
	
	private class ActionSelectTypeComboBox extends SwingWorker<Object, Object>
	{
		@Override
		protected Object doInBackground() throws Exception
		{
			try
			{
				if(fenetre.getOut() == null) 
				fenetre.setOut(new ObjectOutputStream(new BufferedOutputStream(fenetre.getServer().getOutputStream())));
			
				switch(userSelected.getType())
				{
					case "ADMIN" :
						comboBoxType.setSelectedIndex(0);
						/*
						// Envoie commande
						fenetre.out.writeObject("adminGet");
						fenetre.out.flush();
						
						fenetre.out.writeObject(userSelected.getUID());
						fenetre.out.flush();
						
						if(fenetre.in == null)
						fenetre.in = new ObjectInputStream(new BufferedInputStream(ClientInterface.server.getInputStream()));
						
						// Réception objet
						Admin adminSelected = (Admin) fenetre.in.readObject();
						*/
						break;
					case "STUDENT" :
						comboBoxType.setSelectedIndex(1);
						comboBoxPromo.setEnabled(true);
						comboBoxTuteur.setEnabled(true);
						
						// Envoie commande
						fenetre.getOut().writeObject("studentGet");
						fenetre.getOut().flush();
						
						fenetre.getOut().writeObject(userSelected.getUID());
						fenetre.getOut().flush();
						
						if(fenetre.getIn() == null)
						fenetre.setIn(new ObjectInputStream(new BufferedInputStream(fenetre.getServer().getInputStream())));
						
						// Réception objet
						Student studentSelected = (Student) fenetre.getIn().readObject();
						
						return studentSelected;
					case "TEACHER" :
						comboBoxType.setSelectedIndex(2);
						/*
						// Envoie commande
						fenetre.out.writeObject("teacherGet");
						fenetre.out.flush();
						
						fenetre.out.writeObject(userSelected.getUID());
						fenetre.out.flush();
						
						
						if(fenetre.in == null)
						fenetre.in = new ObjectInputStream(new BufferedInputStream(ClientInterface.server.getInputStream()));
						
						// Réception objet
						Teacher teacherSelected = (Teacher) fenetre.in.readObject();
						*/
						break;
				}
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
				Student student = (Student) get();
				if(student != null)
				{
					comboBoxPromo.setSelectedIndex(student.getPromo() - 1);
					comboBoxTuteur.setSelectedItem(student.getTutor());
				}
			}
			catch (InterruptedException | ExecutionException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
