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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;

import Actions.ActionComboBoxList;
import InterfacesGUI.ClientGUI;
import Modeles.MComboBox;
import Objets.Course;
import Objets.Grade;

public class GradesForm extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField typeField;
	private JTextField coefField;
	
	private JButton okButton = null;
	private JButton cancelButton = new JButton("Annuler");
	
	private ClientGUI fenetre = null;
	private Grade gradeSelected = null;
	
	private MComboBox<Course> comboBoxCourseModele = new MComboBox<Course>();
	private JComboBox<Course> comboBoxCourse = null;
	
	/**
	 * Create the dialog.
	 */
	public GradesForm(ClientGUI fenetre, Grade gradeSelected)
	{
		this.fenetre = fenetre;
		this.gradeSelected = gradeSelected;
		
		if(gradeSelected == null)
		{
			setIconImage(Toolkit.getDefaultToolkit().getImage(GradesForm.class.getResource("/Images/gradesAdd.png")));
			setTitle("Ajouter un note");
			okButton = new JButton("Ajouter");
			okButton.setIcon(new ImageIcon(GradesForm.class.getResource("/Images/gradesAdd.png")));
		}
		else
		{
			setIconImage(Toolkit.getDefaultToolkit().getImage(GradesForm.class.getResource("/Images/gradesUpdate.png")));
			setTitle("Modifier un note");
			okButton = new JButton("Modifier");
			okButton.setIcon(new ImageIcon(GradesForm.class.getResource("/Images/gradesUpdate.png")));
		}
		cancelButton.setIcon(new ImageIcon(GradesForm.class.getResource("/Images/gradesDel.png")));
		
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 199);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		comboBoxCourse = new JComboBox<Course>(comboBoxCourseModele);
		
		ActionComboBoxList<Course> actionGetCourses = new ActionComboBoxList<Course>(fenetre, comboBoxCourseModele, comboBoxCourse, (gradeSelected != null) ? gradeSelected.getCourse() : null, "coursesList");
		actionGetCourses.execute();
		
		initGUI();
		createEvent();
	}
	
	public void initGUI()
	{
		JLabel lblType = new JLabel("Type :");
		
		typeField = new JTextField();
		typeField.setColumns(10);
		
		JLabel lblCourse = new JLabel("Cours :");
		
		JLabel lblCoefficient = new JLabel("Coefficient :");
		
		coefField = new JTextField();
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblCoefficient)
						.addComponent(lblCourse)
						.addComponent(lblType))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(comboBoxCourse, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(typeField, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE))
						.addComponent(coefField, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(34, Short.MAX_VALUE))
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
						.addComponent(lblType)
						.addComponent(typeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCoefficient)
						.addComponent(coefField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(50, Short.MAX_VALUE))
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
		
		if(gradeSelected != null)
		{
			typeField.setText(gradeSelected.getType());
			coefField.setText(Double.toString(gradeSelected.getCoef()));
		}
	}
	
	public void createEvent()
	{
		okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(typeField.getText().isEmpty() || coefField.getText().isEmpty() || comboBoxCourse.getSelectedItem() == null)
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
				if(gradeSelected != null)
				{
					actionCommande = "Update";
					id = gradeSelected.getGID();
				}

				Grade grade = new Grade();
				grade.setGID(id);
				grade.setType(typeField.getText());
				grade.setCourse((Course) comboBoxCourse.getSelectedItem());
				grade.setCoef(Double.parseDouble(coefField.getText()));
				
				// Envoie commande
				fenetre.getOut().writeObject("grade" + actionCommande);
				fenetre.getOut().flush();
				// Envoie objet
				fenetre.getOut().writeObject(grade);
				fenetre.getOut().flush();
				
				if(fenetre.getIn() == null)
				fenetre.setIn(new ObjectInputStream(new BufferedInputStream(fenetre.getServer().getInputStream())));
				
				boolean result = (boolean) fenetre.getIn().readObject();
				if(result) return grade;
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
				Grade grade = (Grade) get();
				if(grade == null)
				{
					if(gradeSelected == null)
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'ajout.\nVérifiez que la note n'est pas déjà présente dans la base de données.\nVérifiez que le coefficient saisi est correct.", "Erreur d'ajout", JOptionPane.ERROR_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la modification.\nVérifiez que la note n'est pas déjà présente dans la base de données.\nVérifiez que le coefficient saisi est correct.", "Erreur de modification", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(gradeSelected == null)
					{
						fenetre.getGradesListGUI().getModele().add(grade);
	
						JOptionPane.showMessageDialog(null, "Note ajoutée avec succès.", "Ajout OK", JOptionPane.INFORMATION_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null, "Note modifiée avec succès.", "Modification OK", JOptionPane.INFORMATION_MESSAGE);
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
