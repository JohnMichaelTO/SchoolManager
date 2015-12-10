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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;

import Actions.ActionComboBoxList;
import Actions.FIFO;
import InterfacesGUI.ClientGUI;
import Modeles.MComboBox;
import Objets.Course;
import Objets.Teaches;
import Objets.Teacher;

public class TeachesForm extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JButton okButton = null;
	private JButton cancelButton = new JButton("Annuler");
	
	private ClientGUI fenetre = null;
	
	private MComboBox<Course> comboBoxCourseModele = new MComboBox<Course>();
	private JComboBox<Course> comboBoxCourse = null;
	
	private MComboBox<Teacher> comboBoxTeacherModele = new MComboBox<Teacher>();
	private JComboBox<Teacher> comboBoxTeacher = null;
	
	/**
	 * Create the dialog.
	 */
	public TeachesForm(ClientGUI fenetre)
	{
		this.fenetre = fenetre;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(TeachesForm.class.getResource("/Images/teachesAdd.png")));
		setTitle("Affecter un enseignant à un cours");
		okButton = new JButton("Affecter");
		okButton.setIcon(new ImageIcon(TeachesForm.class.getResource("/Images/teachesAdd.png")));
		cancelButton.setIcon(new ImageIcon(TeachesForm.class.getResource("/Images/teachesDel.png")));
		
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 199);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		comboBoxCourse = new JComboBox<Course>(comboBoxCourseModele);
		comboBoxTeacher = new JComboBox<Teacher>(comboBoxTeacherModele);
		
		// Récupération des listes dans les comboBox
		ActionComboBoxList<Course> actionGetCourses = new ActionComboBoxList<Course>(fenetre, comboBoxCourseModele, comboBoxCourse, null, "coursesList");
		ActionComboBoxList<Teacher> actionGetTeachers = new ActionComboBoxList<Teacher>(fenetre, comboBoxTeacherModele, comboBoxTeacher, null, "teachersList");
		// Initialisation des SwingWorkers dans une file d'attente
		new FIFO(actionGetCourses, actionGetTeachers).execute();
		
		initGUI();
		createEvent();
	}
	
	public void initGUI()
	{
		JLabel lblTeacher = new JLabel("Enseignant :");
		
		JLabel lblCourse = new JLabel("Cours :");
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(52)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblCourse)
						.addComponent(lblTeacher))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(comboBoxCourse, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBoxTeacher, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCourse)
						.addComponent(comboBoxCourse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTeacher)
						.addComponent(comboBoxTeacher, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(70, Short.MAX_VALUE))
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
	}
	
	public void createEvent()
	{
		okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(comboBoxTeacher.getSelectedItem() == null || comboBoxCourse.getSelectedItem() == null)
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

				Teaches teaches = new Teaches();
				teaches.setCourse((Course) comboBoxCourse.getSelectedItem());
				teaches.setTeacher((Teacher) comboBoxTeacher.getSelectedItem());
				
				// Envoie commande
				fenetre.getOut().writeObject("teaches" + actionCommande);
				fenetre.getOut().flush();
				// Envoie objet
				fenetre.getOut().writeObject(teaches);
				fenetre.getOut().flush();
				
				if(fenetre.getIn() == null)
				fenetre.setIn(new ObjectInputStream(new BufferedInputStream(fenetre.getServer().getInputStream())));
				
				boolean result = (boolean) fenetre.getIn().readObject();
				if(result) return teaches;
			}
			catch (IOException | NumberFormatException e)
			{
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void done()
		{
			try
			{
				Teaches teaches = (Teaches) get();
				if(teaches == null)
				{
					JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'ajout.", "Erreur d'ajout", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					fenetre.getTeachesListGUI().getModele().add(teaches);
	
					JOptionPane.showMessageDialog(null, "Enseignant affecté avec succès.", "Ajout OK", JOptionPane.INFORMATION_MESSAGE);
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
