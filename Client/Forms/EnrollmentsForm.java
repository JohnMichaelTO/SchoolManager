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
import Objets.Enrollment;
import Objets.Student;

public class EnrollmentsForm extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JButton okButton = null;
	private JButton cancelButton = new JButton("Annuler");
	
	private ClientGUI fenetre = null;
	
	private MComboBox<Course> comboBoxCourseModele = new MComboBox<Course>();
	private JComboBox<Course> comboBoxCourse = null;
	
	private MComboBox<Student> comboBoxStudentModele = new MComboBox<Student>();
	private JComboBox<Student> comboBoxStudent = null;
	
	/**
	 * Create the dialog.
	 */
	public EnrollmentsForm(ClientGUI fenetre)
	{
		this.fenetre = fenetre;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(EnrollmentsForm.class.getResource("/Images/enrollmentsAdd.png")));
		setTitle("Inscription à un cours");
		okButton = new JButton("Inscrire");
		okButton.setIcon(new ImageIcon(EnrollmentsForm.class.getResource("/Images/enrollmentsAdd.png")));
		cancelButton.setIcon(new ImageIcon(EnrollmentsForm.class.getResource("/Images/enrollmentsDel.png")));
		
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 199);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		comboBoxCourse = new JComboBox<Course>(comboBoxCourseModele);
		comboBoxStudent = new JComboBox<Student>(comboBoxStudentModele);
		
		// Restreindre le choix de l'étudiant si c'est un étudiant qui est loggué
		String cmd = "";
		if(fenetre.getCurrentUser().getType().equals("STUDENT"))
		{
			comboBoxStudent.setEnabled(false);
			cmd = "StudentComboBox";
		}
		
		// Récupération des listes dans les comboBox
		ActionComboBoxList<Course> actionGetCourses = new ActionComboBoxList<Course>(fenetre, comboBoxCourseModele, comboBoxCourse, null, "coursesList" + cmd);
		ActionComboBoxList<Student> actionGetStudents = new ActionComboBoxList<Student>(fenetre, comboBoxStudentModele, comboBoxStudent, null, "studentsList");
		
		// Initialisation des SwingWorkers dans une file d'attente
		new FIFO(actionGetCourses, actionGetStudents).execute();
		
		initGUI();
		createEvent();
	}
	
	public void initGUI()
	{
		JLabel lblStudent = new JLabel("Etudiant :");
		
		JLabel lblCourse = new JLabel("Cours :");
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(52)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblCourse)
						.addComponent(lblStudent))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(comboBoxCourse, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBoxStudent, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(lblStudent)
						.addComponent(comboBoxStudent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
				if(comboBoxStudent.getSelectedItem() == null || comboBoxCourse.getSelectedItem() == null)
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

				Enrollment enrollment = new Enrollment();
				enrollment.setCourse((Course) comboBoxCourse.getSelectedItem());
				enrollment.setStudent((Student) comboBoxStudent.getSelectedItem());
				
				// Envoie commande
				fenetre.getOut().writeObject("enrollment" + actionCommande);
				fenetre.getOut().flush();
				// Envoie objet
				fenetre.getOut().writeObject(enrollment);
				fenetre.getOut().flush();
				
				if(fenetre.getIn() == null)
				fenetre.setIn(new ObjectInputStream(new BufferedInputStream(fenetre.getServer().getInputStream())));
				
				boolean result = (boolean) fenetre.getIn().readObject();
				if(result) return enrollment;
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
				Enrollment enrollment = (Enrollment) get();
				if(enrollment == null)
				{
					JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'ajout.", "Erreur d'ajout", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					fenetre.getEnrollmentsListGUI().getModele().add(enrollment);
	
					JOptionPane.showMessageDialog(null, "Inscription ajoutée avec succès.", "Ajout OK", JOptionPane.INFORMATION_MESSAGE);
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
