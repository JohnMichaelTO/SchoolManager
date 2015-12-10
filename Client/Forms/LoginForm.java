package Forms;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import InterfacesGUI.ClientGUI;
import Objets.Student;
import Objets.Teacher;
import Objets.User;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class LoginForm extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	// Fenetre principale
	private ClientGUI fenetre = null;
	
	// Bouton et champs de login
	private JButton btnLogin, btnReset;
	private JTextField emailField;
	private JPasswordField passwordField;
	
	/**
	 * Create the panel.
	 */
	public LoginForm(ClientGUI fenetre, String login)
	{
		this.fenetre = fenetre;
		
		setLayout(new MigLayout("", "[grow][418px][grow]", "[grow][241px][grow]"));
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBorder(new TitledBorder(null, "Authentification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		add(loginPanel, "cell 1 1, center");
		
		loginPanel.setLayout(
				new FormLayout
				(
					new ColumnSpec[]
					{
						ColumnSpec.decode("0px"),
						ColumnSpec.decode("76px"),
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						ColumnSpec.decode("70px"),
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						ColumnSpec.decode("90px"),
					},
					new RowSpec[]
					{
						RowSpec.decode("36px"),
						RowSpec.decode("22px"),
						FormFactory.NARROW_LINE_GAP_ROWSPEC,
						RowSpec.decode("22px"),
						RowSpec.decode("33px"),
						RowSpec.decode("25px"),
					}
				)
		);
		
		JLabel lblEmail = new JLabel("Email");
		loginPanel.add(lblEmail, "2, 2, right, center");
		
		emailField = new JTextField();
		loginPanel.add(emailField, "4, 2, 3, 1, default, top");
		emailField.setColumns(10);
		
		if(login != null) emailField.setText(login);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		loginPanel.add(lblMotDePasse, "2, 4, left, center");
		
		passwordField = new JPasswordField();
		loginPanel.add(passwordField, "4, 4, 3, 1, default, top");
		
		btnReset = new JButton("Reset");
		loginPanel.add(btnReset, "4, 6, left, top");
		btnReset.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				emailField.setText("");
				passwordField.setText("");
			}
		});
		
		btnLogin = new JButton("Login");
		loginPanel.add(btnLogin, "6, 6, left, top");
		
		fenetre.getRootPane().setDefaultButton(btnLogin);
		
		btnLogin.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(emailField.getText().isEmpty() && passwordField.getPassword().length == 0)
				{
					JOptionPane.showMessageDialog(null, "Vous devez saisir un email et un mot de passe.", "Champs requis", JOptionPane.ERROR_MESSAGE);
				}
				else if(emailField.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Vous devez saisir un email.", "Champs requis", JOptionPane.ERROR_MESSAGE);
				}
				else if(passwordField.getPassword().length == 0)
				{
					JOptionPane.showMessageDialog(null, "Vous devez saisir un mot de passe.", "Champs requis", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					btnLogin.setText("Login...");
					btnLogin.setEnabled(false);
		    		btnReset.setEnabled(false);
		    		emailField.setEnabled(false);
		    		passwordField.setEnabled(false);
		    		
		    		ActionLogin actionLogin = new ActionLogin();
		    		actionLogin.execute();
				}
				
			}
		});
	}
	
	private class ActionLogin extends SwingWorker<String, Object>
	{
		@Override
		protected String doInBackground() throws Exception
		{
			try
    		{
	    		if(fenetre.getOut() == null)
	    		fenetre.setOut(new ObjectOutputStream(new BufferedOutputStream(fenetre.getServer().getOutputStream())));

	    		// Commande login
	    		fenetre.getOut().writeObject("login");
	    		fenetre.getOut().flush();
	    		// Envoie de l'email
	    		fenetre.getOut().writeObject(emailField.getText().toString());
	    		fenetre.getOut().flush();
	    		// Envoie du mot de passe
	    		fenetre.getOut().writeObject(new String(passwordField.getPassword()));
	    		fenetre.getOut().flush();

	    		if(fenetre.getIn() == null)
	    		fenetre.setIn(new ObjectInputStream(new BufferedInputStream(fenetre.getServer().getInputStream())));
	    		if(((String) fenetre.getIn().readObject()).equals("login_true"))
	    		{
		    		fenetre.setCurrentUser((User) fenetre.getIn().readObject());
		    		
		    		switch(fenetre.getCurrentUser().getType())
		    		{
		    			case "STUDENT":
		    				fenetre.getOut().writeObject("studentGet");
				    		fenetre.getOut().flush();
				    		
				    		fenetre.getOut().writeObject(fenetre.getCurrentUser().getUID());
				    		fenetre.getOut().flush();
				    		
				    		fenetre.setCurrentStudent((Student) fenetre.getIn().readObject());
		    				break;
		    			case "TEACHER":
		    				fenetre.getOut().writeObject("teacherGet");
				    		fenetre.getOut().flush();
				    		
				    		fenetre.getOut().writeObject(fenetre.getCurrentUser().getUID());
				    		fenetre.getOut().flush();
				    		
				    		fenetre.setCurrentTeacher((Teacher) fenetre.getIn().readObject());
		    				break;
		    			default:
		    		}
		    		
		    		return fenetre.getCurrentUser().getType();
	    		}
    		}
    		catch (IOException | ClassNotFoundException e)
    		{
    			e.printStackTrace();
    		}
			return null;
		}
		
		@Override
		protected void done()
		{
			try
			{
				btnLogin.setText("Login");
	    		emailField.setEnabled(true);
	    		passwordField.setEnabled(true);
	    		passwordField.setText("");
	    		btnLogin.setEnabled(true);
	    		btnReset.setEnabled(true);
				if(get() != null)
				{
					JOptionPane.showMessageDialog(null, "Vous êtes connecté !", "Login OK", JOptionPane.INFORMATION_MESSAGE);
					
					// Suppression de tout le panel
					fenetre.getPanel().removeAll();
					
					switch(fenetre.getCurrentUser().getType())
					{
						case "ADMIN" :
								fenetre.getMenu().initAdmin();
								// Ajout du panel admin
								// fenetre.getPanel().add(fenetre.getUsersListGUI().getPanel(), BorderLayout.CENTER);
							break;
						case "TEACHER" :
								fenetre.getMenu().initTeacher();
								// Ajout du panel teacher
								// fenetre.getPanel().add(fenetre.getUsersListGUI().getPanel(), BorderLayout.CENTER);
							break;
						case "STUDENT" :
								fenetre.getMenu().initStudent();
								// Ajout du panel student
								// fenetre.getPanel().add(fenetre.getUsersListGUI().getPanel(), BorderLayout.CENTER);
							break;
					}
					
					fenetre.setVisible(true);
					fenetre.repaint();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Erreur d'identification, veuillez vérifier que votre email et votre mot de passe sont corrects.", "Erreur d'identification", JOptionPane.ERROR_MESSAGE);
				}
			}
			catch (HeadlessException | InterruptedException | ExecutionException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
