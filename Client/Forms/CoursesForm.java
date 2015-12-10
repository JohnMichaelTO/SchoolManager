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

import javax.swing.ButtonGroup;
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
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;

import Actions.ActionComboBoxList;
import InterfacesGUI.ClientGUI;
import Modeles.MComboBox;
import Objets.Course;
import Objets.Department;

public class CoursesForm extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField nomField;
	private ButtonGroup compulsoryRadio = new ButtonGroup();
	private JRadioButton compulsoryRadioOui = new JRadioButton("Oui");
	private JRadioButton compulsoryRadioNon = new JRadioButton("Non");
	
	private JButton okButton = null;
	private JButton cancelButton = new JButton("Annuler");
	
	private ClientGUI fenetre = null;
	private Course courseSelected = null;
	
	private MComboBox<Department> comboBoxDepartmentModele = new MComboBox<Department>();
	private JComboBox<Department> comboBoxDepartment = null;
	
	private JComboBox<String> comboBoxYear = null;
	
	/**
	 * Create the dialog.
	 */
	public CoursesForm(ClientGUI fenetre, Course courseSelected)
	{
		this.fenetre = fenetre;
		this.courseSelected = courseSelected;
		
		if(courseSelected == null)
		{
			setIconImage(Toolkit.getDefaultToolkit().getImage(CoursesForm.class.getResource("/Images/coursesAdd.png")));
			setTitle("Ajouter un cours");
			okButton = new JButton("Ajouter");
			okButton.setIcon(new ImageIcon(CoursesForm.class.getResource("/Images/coursesAdd.png")));
		}
		else
		{
			setIconImage(Toolkit.getDefaultToolkit().getImage(CoursesForm.class.getResource("/Images/coursesUpdate.png")));
			setTitle("Modifier un cours");
			okButton = new JButton("Modifier");
			okButton.setIcon(new ImageIcon(CoursesForm.class.getResource("/Images/coursesUpdate.png")));
		}
		cancelButton.setIcon(new ImageIcon(CoursesForm.class.getResource("/Images/coursesDel.png")));
		
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 228);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		comboBoxDepartment = new JComboBox<Department>(comboBoxDepartmentModele);
		comboBoxYear = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] {"Tous niveaux", "L1", "L2", "L3", "M1", "M2"}));
		
		ActionComboBoxList<Department> actionGetDepartments = new ActionComboBoxList<Department>(fenetre, comboBoxDepartmentModele, comboBoxDepartment, (courseSelected != null) ? courseSelected.getDepartement() : null, "departmentsList");
		actionGetDepartments.execute();
		
		initGUI();
		createEvent();
	}
	
	public void initGUI()
	{
		JLabel lblNom = new JLabel("Nom :");
		
		nomField = new JTextField();
		nomField.setColumns(10);
		
		JLabel lblDepartment = new JLabel("Département :");
		
		JLabel lblCompulsory = new JLabel("Cours obligatoire :");
		compulsoryRadio.add(compulsoryRadioOui);
		compulsoryRadio.add(compulsoryRadioNon);
		compulsoryRadioOui.setSelected(true);
		
		JLabel lblYear = new JLabel("Niveau :");
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblYear)
						.addComponent(lblCompulsory)
						.addComponent(lblDepartment)
						.addComponent(lblNom))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(comboBoxDepartment, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(nomField, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(compulsoryRadioOui)
							.addGap(18)
							.addComponent(compulsoryRadioNon))
						.addComponent(comboBoxYear, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDepartment)
						.addComponent(comboBoxDepartment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNom)
						.addComponent(nomField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompulsory)
						.addComponent(compulsoryRadioOui)
						.addComponent(compulsoryRadioNon))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYear)
						.addComponent(comboBoxYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(47, Short.MAX_VALUE))
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
		
		if(courseSelected != null)
		{
			nomField.setText(courseSelected.getName());
			comboBoxYear.setSelectedIndex(courseSelected.getYear());
			if(courseSelected.isCompulsory())
				compulsoryRadioOui.setSelected(true);
			else
				compulsoryRadioNon.setSelected(true);
		}
	}
	
	public void createEvent()
	{
		okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(nomField.getText().isEmpty() || comboBoxDepartment.getSelectedItem() == null)
				{
					JOptionPane.showMessageDialog(null, "Vous devez remplir tous les champs.", "Champs requis", JOptionPane.ERROR_MESSAGE);
				}
				else if(compulsoryRadioOui.isSelected() && comboBoxYear.getSelectedItem() == null)
				{
					JOptionPane.showMessageDialog(null, "Vous devez choisir un niveau.", "Champs requis", JOptionPane.ERROR_MESSAGE);
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
				if(courseSelected != null)
				{
					actionCommande = "Update";
					id = courseSelected.getCID();
				}

				Course course = new Course();
				course.setCID(id);
				course.setName(nomField.getText());
				course.setDepartement((Department) comboBoxDepartment.getSelectedItem());
				course.setYear(comboBoxYear.getSelectedIndex());
				course.setCompulsory(compulsoryRadioOui.isSelected());
				
				// Envoie commande
				fenetre.getOut().writeObject("course" + actionCommande);
				fenetre.getOut().flush();
				// Envoie objet
				fenetre.getOut().writeObject(course);
				fenetre.getOut().flush();
				
				if(fenetre.getIn() == null)
				fenetre.setIn(new ObjectInputStream(new BufferedInputStream(fenetre.getServer().getInputStream())));
				
				boolean result = (boolean) fenetre.getIn().readObject();
				if(result) return course;
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
				Course course = (Course) get();
				if(course == null)
				{
					if(courseSelected == null)
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'ajout.\nVérifiez que le cours n'est pas déjà présent dans la base de données.", "Erreur d'ajout", JOptionPane.ERROR_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la modification.\nVérifiez que le cours n'est pas déjà présent dans la base de données.", "Erreur de modification", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(courseSelected == null)
					{
						fenetre.getCoursesListGUI().getModele().add(course);
	
						JOptionPane.showMessageDialog(null, "Cours ajouté avec succès.", "Ajout OK", JOptionPane.INFORMATION_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null, "Cours modifié avec succès.", "Modification OK", JOptionPane.INFORMATION_MESSAGE);
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
