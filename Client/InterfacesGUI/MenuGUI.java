package InterfacesGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Actions.ActionList;
import Actions.ActionLogout;
import Actions.ActionRemove;
import Forms.AssistantsForm;
import Forms.CoursesForm;
import Forms.DepartmentsForm;
import Forms.EnrollmentsForm;
import Forms.GradesForm;
import Forms.LoginForm;
import Forms.TeachesForm;
import Forms.UsersForm;
import Objets.Alert;
import Objets.Assists;
import Objets.Course;
import Objets.Department;
import Objets.Enrollment;
import Objets.Grade;
import Objets.Student;
import Objets.StudentGrade;
import Objets.Teacher;
import Objets.Teaches;
import Objets.User;

public class MenuGUI
{
	// Fenetre principale
	private ClientGUI fenetre = null;
	
	private String type = "";
	
	// Menu
	private JMenuBar menuBar;
	private JMenu menuMyAccount;
	private JMenuItem itemLogout;
	private JMenuItem itemPasswordModify;
	private JMenuItem itemQuit;
	private JMenu menuUsers;
	private JMenuItem itemUsersList;
	private JMenuItem itemUsersAdd;
	private JMenuItem itemUsersUpdate;
	private JMenuItem itemUsersRemove;
	private JMenuItem itemStudentsList;
	private JMenuItem itemTeachersList;
	private JMenu menuDepartments;
	private JMenuItem itemDepartmentsList;
	private JMenuItem itemDepartmentsAdd;
	private JMenuItem itemDepartmentsUpdate;
	private JMenuItem itemDepartmentsRemove;
	private JMenu menuCourses;
	private JMenuItem itemCoursesList;
	private JMenuItem itemCoursesAdd;
	private JMenuItem itemCoursesUpdate;
	private JMenuItem itemCoursesRemove;
	private JMenu menuTeaches;
	private JMenuItem itemTeachesList;
	private JMenuItem itemTeachesAdd;
	private JMenuItem itemTeachesRemove;
	private JMenu menuEnrollments;
	private JMenuItem itemEnrollmentsList;
	private JMenuItem itemEnrollmentsAdd;
	private JMenuItem itemEnrollmentsRemove;
	private JMenu menuAssistants;
	private JMenuItem itemAssistantsList;
	private JMenuItem itemAssistantsAdd;
	private JMenuItem itemAssistantsRemove;
	private JMenu menuGrades;
	private JMenuItem itemGradesList;
	private JMenuItem itemGradesAdd;
	private JMenuItem itemGradesUpdate;
	private JMenuItem itemGradesRemove;
	private JMenuItem itemStudentGradesList;
	private JMenu menuAlerts;
	private JMenuItem itemAlertsList;
	
	public MenuGUI(ClientGUI fenetre)
	{
		this.fenetre = fenetre;
		
		createElements();
		init();
		createEvent();
	}
	
	public void createElements()
	{
		menuBar = new JMenuBar();
		fenetre.setJMenuBar(menuBar);
		
		menuMyAccount = new JMenu("Mon compte");
		
		itemLogout = new JMenuItem("Déconnexion");
		itemLogout.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/logout.png")));
		
		itemPasswordModify = new JMenuItem("Modifier mot de passe");
		itemPasswordModify.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/password.png")));
		
		itemQuit = new JMenuItem("Quitter");
		itemQuit.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/quit.png")));
		
		menuUsers = new JMenu("Utilisateurs");
		
		itemUsersList = new JMenuItem("Afficher la liste des utilisateurs");
		itemUsersList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/usersList.png")));
		
		itemUsersAdd = new JMenuItem("Ajouter un utilisateur");
		itemUsersAdd.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/usersAdd.png")));
		
		itemUsersUpdate = new JMenuItem("Modifier un utilisateur");
		itemUsersUpdate.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/usersUpdate.png")));
		
		itemUsersRemove = new JMenuItem("Supprimer un utilisateur");
		itemUsersRemove.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/usersDel.png")));
		
		itemStudentsList = new JMenuItem("Afficher la liste des étudiants");
		itemStudentsList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/studentsList.png")));
		
		itemTeachersList = new JMenuItem("Afficher la liste des enseignants");
		itemTeachersList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/teachersList.png")));
		
		menuDepartments = new JMenu("Départements");
		
		itemDepartmentsList = new JMenuItem("Afficher la liste des départements");
		itemDepartmentsList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/departmentsList.png")));
		
		itemDepartmentsAdd = new JMenuItem("Ajouter un département");
		itemDepartmentsAdd.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/departmentsAdd.png")));
		
		itemDepartmentsUpdate = new JMenuItem("Modifier un département");
		itemDepartmentsUpdate.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/departmentsUpdate.png")));
		
		itemDepartmentsRemove = new JMenuItem("Supprimer un département");
		itemDepartmentsRemove.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/departmentsDel.png")));
		
		menuCourses = new JMenu("Cours");
		
		itemCoursesList = new JMenuItem("Afficher la liste des courss");
		itemCoursesList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/coursesList.png")));
		
		itemCoursesAdd = new JMenuItem("Ajouter un cours");
		itemCoursesAdd.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/coursesAdd.png")));
		
		itemCoursesUpdate = new JMenuItem("Modifier un cours");
		itemCoursesUpdate.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/coursesUpdate.png")));
		
		itemCoursesRemove = new JMenuItem("Supprimer un cours");
		itemCoursesRemove.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/coursesDel.png")));
		
		menuTeaches = new JMenu("Enseignements");
		
		itemTeachesList = new JMenuItem("Afficher les enseignements");
		itemTeachesList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/teachesList.png")));
		
		itemTeachesAdd = new JMenuItem("Affecter un enseignant à un cours");
		itemTeachesAdd.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/teachesAdd.png")));
		
		itemTeachesRemove = new JMenuItem("Désaffecter un enseignant à un cours");
		itemTeachesRemove.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/teachesDel.png")));
		
		menuEnrollments = new JMenu("Inscriptions");
		
		itemEnrollmentsList = new JMenuItem("Afficher les inscriptions");
		itemEnrollmentsList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/enrollmentsList.png")));
		
		itemEnrollmentsAdd = new JMenuItem("Inscrire un étudiant à un cours");
		itemEnrollmentsAdd.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/enrollmentsAdd.png")));
		
		itemEnrollmentsRemove = new JMenuItem("Désinscrire un étudiant à un cours");
		itemEnrollmentsRemove.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/enrollmentsDel.png")));
		
		menuAssistants = new JMenu("Assistants");
		
		itemAssistantsList = new JMenuItem("Afficher la liste");
		itemAssistantsList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/assistantsList.png")));
		
		itemAssistantsAdd = new JMenuItem("Affecter un assistant à un cours");
		itemAssistantsAdd.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/assistantsAdd.png")));
		
		itemAssistantsRemove = new JMenuItem("Désaffecter un assistant à un cours");
		itemAssistantsRemove.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/assistantsDel.png")));
		
		menuGrades = new JMenu("Notes");
		
		itemGradesList = new JMenuItem("Afficher la liste des types de notes");
		itemGradesList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/gradesList.png")));
		
		itemGradesAdd = new JMenuItem("Ajouter un type");
		itemGradesAdd.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/gradesAdd.png")));
		
		itemGradesUpdate = new JMenuItem("Modifier un type");
		itemGradesUpdate.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/gradesUpdate.png")));
		
		itemGradesRemove = new JMenuItem("Supprimer un type");
		itemGradesRemove.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/gradesDel.png")));
		
		itemStudentGradesList = new JMenuItem("Afficher la liste des notes");
		itemStudentGradesList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/studentGradesList.png")));
		
		menuAlerts = new JMenu("Alertes");
		itemAlertsList = new JMenuItem("Alertes");
		itemAlertsList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/alertsList.png")));
	}
	
	public void init()
	{	
		removeAll();
		
		menuBar.add(menuMyAccount);
		menuMyAccount.add(itemQuit);
	}
	
	public void initAdmin()
	{
		removeAll();
		type = "";
		
		menuBar.add(menuMyAccount);
		menuMyAccount.add(itemLogout);
		menuMyAccount.add(itemPasswordModify);
		menuMyAccount.add(itemQuit);
		menuBar.add(menuUsers);
		menuUsers.add(itemUsersList);
		menuUsers.add(itemUsersAdd);
		menuUsers.add(itemUsersUpdate);
		menuUsers.add(itemUsersRemove);
		menuUsers.add(itemStudentsList);
		menuUsers.add(itemTeachersList);
		menuBar.add(menuDepartments);
		menuDepartments.add(itemDepartmentsList);
		menuDepartments.add(itemDepartmentsAdd);
		menuDepartments.add(itemDepartmentsUpdate);
		menuDepartments.add(itemDepartmentsRemove);
		menuBar.add(menuCourses);
		menuCourses.add(itemCoursesList);
		menuCourses.add(itemCoursesAdd);
		menuCourses.add(itemCoursesUpdate);
		menuCourses.add(itemCoursesRemove);
		menuBar.add(menuTeaches);
		menuTeaches.add(itemTeachesList);
		menuTeaches.add(itemTeachesAdd);
		menuTeaches.add(itemTeachesRemove);
		menuBar.add(menuEnrollments);
		menuEnrollments.add(itemEnrollmentsList);
		menuEnrollments.add(itemEnrollmentsAdd);
		menuEnrollments.add(itemEnrollmentsRemove);
		menuBar.add(menuAssistants);
		menuAssistants.add(itemAssistantsList);
		menuAssistants.add(itemAssistantsAdd);
		menuAssistants.add(itemAssistantsRemove);
		menuBar.add(menuGrades);
		menuGrades.add(itemGradesList);
		menuGrades.add(itemGradesAdd);
		menuGrades.add(itemGradesUpdate);
		menuGrades.add(itemGradesRemove);
		menuGrades.add(itemStudentGradesList);
	}
	
	public void initTeacher()
	{
		removeAll();
		type = "Teacher";
		
		menuBar.add(menuMyAccount);
		menuMyAccount.add(itemLogout);
		menuMyAccount.add(itemPasswordModify);
		menuMyAccount.add(itemQuit);
		menuBar.add(menuUsers);
		menuUsers.add(itemStudentsList);
		menuBar.add(menuCourses);
		menuCourses.add(itemCoursesList);
		menuBar.add(menuAssistants);
		menuAssistants.add(itemAssistantsList);
		menuBar.add(menuGrades);
		menuGrades.add(itemGradesList);
		menuGrades.add(itemStudentGradesList);
		menuBar.add(menuAlerts);
		menuAlerts.add(itemAlertsList);
		itemAlertsList.setText("Alertes");
		itemAlertsList.setIcon(new ImageIcon(ClientGUI.class.getResource("/Images/alertsList.png")));
	}
	
	public void initStudent()
	{
		removeAll();
		type = "Student";
		
		menuBar.add(menuMyAccount);
		menuMyAccount.add(itemLogout);
		menuMyAccount.add(itemPasswordModify);
		menuMyAccount.add(itemQuit);
		menuBar.add(menuCourses);
		menuCourses.add(itemCoursesList);
		menuBar.add(menuEnrollments);
		menuEnrollments.add(itemEnrollmentsList);
		menuEnrollments.add(itemEnrollmentsAdd);
		menuBar.add(menuAssistants);
		menuAssistants.add(itemAssistantsList);
		menuBar.add(menuGrades);
		menuGrades.add(itemGradesList);
		menuGrades.add(itemStudentGradesList);
		menuGrades.add(itemAlertsList);
		itemAlertsList.setText("Mes moyennes");
		itemAlertsList.setIcon(null);
	}
	
	public void removeAll()
	{	
		menuMyAccount.removeAll();
		menuUsers.removeAll();
		menuDepartments.removeAll();
		menuCourses.removeAll();
		menuTeaches.removeAll();
		menuEnrollments.removeAll();
		menuAssistants.removeAll();
		menuGrades.removeAll();
		menuAlerts.removeAll();
		
		menuBar.removeAll();
	}
	
	public void createEvent()
	{
		itemLogout.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ActionLogout actionLogout = new ActionLogout(fenetre);
				actionLogout.execute();
				
				// Suppression de tout le panel
				fenetre.getPanel().removeAll();
				// Retour au formulaire de login
				//if(fenetre.getLoginForm() == null)
				//fenetre.setLoginForm(new LoginForm(fenetre));
				//fenetre.getPanel().add(fenetre.getLoginForm(), BorderLayout.CENTER);
				fenetre.getPanel().add(new LoginForm(fenetre, (fenetre.getCurrentUser() != null ? fenetre.getCurrentUser().getEmail() : null)), BorderLayout.CENTER);
				
				fenetre.getMenu().init();
				fenetre.setVisible(true);
				fenetre.repaint();
			}
		});
		
		//itemPasswordModify
		
		itemQuit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				fenetre.dispose();
			}
		});
		
		itemUsersList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fenetre.getPanel().removeAll();
				fenetre.getPanel().add(fenetre.getUsersListGUI().getPanel(), BorderLayout.CENTER);
				fenetre.setVisible(true);
				fenetre.repaint();
				
				ActionList<User> actionUsersList = new ActionList<User>(fenetre, fenetre.getUsersListGUI(), "usersList" + type);
				actionUsersList.execute();
			}
		});
		
		itemUsersAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				UsersForm UsersAddForm = new UsersForm(fenetre, null);
				UsersAddForm.setVisible(true);
				
				// Rafraichissement de la liste
	        	ActionList<User> actionUsersList = new ActionList<User>(fenetre, fenetre.getUsersListGUI(), "usersList" + type);
	        	actionUsersList.execute();
	        }
		});
		
		itemUsersUpdate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				int[] selection = fenetre.getUsersListGUI().getTable().getSelectedRows();
		        
		        if(selection.length == 0)
		        {
		        	JOptionPane.showMessageDialog(null, "Vous devez sélectionner un élément dans la liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
		        else if(selection.length != 1)
		        {
		        	JOptionPane.showMessageDialog(null, "Vous devez un seul élément à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
		        else
		        {
		        	 User userSelected = (User) fenetre.getUsersListGUI().getModele().get(selection[0]);
		        	 
		        	 UsersForm UsersAddForm = new UsersForm(fenetre, userSelected);
		        	 UsersAddForm.setVisible(true);
		        	 
		        	 // Rafraichissement de la liste
		        	 ActionList<User> actionUsersList = new ActionList<User>(fenetre, fenetre.getUsersListGUI(), "usersList" + type);
		        	 actionUsersList.execute();
		        }
			}
		});
		
		itemUsersRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ActionRemove<User> actionUsersRemove = new ActionRemove<User>(fenetre, fenetre.getUsersListGUI(), "users");
				actionUsersRemove.execute();
			}
		});
		
		itemStudentsList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fenetre.getPanel().removeAll();
				fenetre.getPanel().add(fenetre.getStudentsListGUI().getPanel(), BorderLayout.CENTER);
				fenetre.setVisible(true);
				fenetre.repaint();
				
				ActionList<Student> actionStudentsList = new ActionList<Student>(fenetre, fenetre.getStudentsListGUI(), "studentsList" + type);
				actionStudentsList.execute();
			}
		});
		
		itemTeachersList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fenetre.getPanel().removeAll();
				fenetre.getPanel().add(fenetre.getTeachersListGUI().getPanel(), BorderLayout.CENTER);
				fenetre.setVisible(true);
				fenetre.repaint();
				
				ActionList<Teacher> actionTeachersList = new ActionList<Teacher>(fenetre, fenetre.getTeachersListGUI(), "teachersList" + type);
				actionTeachersList.execute();
			}
		});
		
		itemDepartmentsList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fenetre.getPanel().removeAll();
				fenetre.getPanel().add(fenetre.getDepartmentsListGUI().getPanel(), BorderLayout.CENTER);
				fenetre.setVisible(true);
				fenetre.repaint();
				
				ActionList<Department> actionDepartmentsList = new ActionList<Department>(fenetre, fenetre.getDepartmentsListGUI(), "departmentsList" + type);
				actionDepartmentsList.execute();
			}
		});
		
		itemDepartmentsAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DepartmentsForm DepartmentsAddForm = new DepartmentsForm(fenetre, null);
				DepartmentsAddForm.setVisible(true);
				
				// Rafraichissement de la liste
	        	ActionList<Department> actionDepartmentsList = new ActionList<Department>(fenetre, fenetre.getDepartmentsListGUI(), "departmentsList" + type);
	        	actionDepartmentsList.execute();
	        }
		});
		
		itemDepartmentsUpdate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				int[] selection = fenetre.getDepartmentsListGUI().getTable().getSelectedRows();
		        
		        if(selection.length == 0)
		        {
		        	JOptionPane.showMessageDialog(null, "Vous devez sélectionner un élément dans la liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
		        else if(selection.length != 1)
		        {
		        	JOptionPane.showMessageDialog(null, "Vous devez un seul élément à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
		        else
		        {
		        	 Department departmentSelected = fenetre.getDepartmentsListGUI().getModele().get(selection[0]);
		        	 
		        	 DepartmentsForm DepartmentsAddForm = new DepartmentsForm(fenetre, departmentSelected);
		        	 DepartmentsAddForm.setVisible(true);
		        	 
		        	 // Rafraichissement de la liste
		        	 ActionList<Department> actionDepartmentsList = new ActionList<Department>(fenetre, fenetre.getDepartmentsListGUI(), "departmentsList" + type);
		        	 actionDepartmentsList.execute();
		        }
			}
		});
		
		itemDepartmentsRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ActionRemove<Department> actionDepartmentsRemove = new ActionRemove<Department>(fenetre, fenetre.getDepartmentsListGUI(), "departments");
				actionDepartmentsRemove.execute();
			}
		});
		
		itemCoursesList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fenetre.getPanel().removeAll();
				fenetre.getPanel().add(fenetre.getCoursesListGUI().getPanel(), BorderLayout.CENTER);
				fenetre.setVisible(true);
				fenetre.repaint();
				
				ActionList<Course> actionCoursesList = new ActionList<Course>(fenetre, fenetre.getCoursesListGUI(), "coursesList" + type);
				actionCoursesList.execute();
			}
		});
		
		itemCoursesAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				CoursesForm CoursesAddForm = new CoursesForm(fenetre, null);
				CoursesAddForm.setVisible(true);
				
				// Rafraichissement de la liste
	        	ActionList<Course> actionCoursesList = new ActionList<Course>(fenetre, fenetre.getCoursesListGUI(), "coursesList" + type);
	        	actionCoursesList.execute();
	        }
		});
		
		itemCoursesUpdate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				int[] selection = fenetre.getCoursesListGUI().getTable().getSelectedRows();
		        
		        if(selection.length == 0)
		        {
		        	JOptionPane.showMessageDialog(null, "Vous devez sélectionner un élément dans la liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
		        else if(selection.length != 1)
		        {
		        	JOptionPane.showMessageDialog(null, "Vous devez un seul élément à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
		        else
		        {
		        	 Course departmentSelected = fenetre.getCoursesListGUI().getModele().get(selection[0]);
		        	 
		        	 CoursesForm CoursesAddForm = new CoursesForm(fenetre, departmentSelected);
		        	 CoursesAddForm.setVisible(true);
		        	 
		        	 // Rafraichissement de la liste
		        	 ActionList<Course> actionCoursesList = new ActionList<Course>(fenetre, fenetre.getCoursesListGUI(), "coursesList" + type);
		        	 actionCoursesList.execute();
		        }
			}
		});
		
		itemCoursesRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ActionRemove<Course> actionCoursesRemove = new ActionRemove<Course>(fenetre, fenetre.getCoursesListGUI(), "courses");
				actionCoursesRemove.execute();
			}
		});
		
		itemTeachesList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fenetre.getPanel().removeAll();
				fenetre.getPanel().add(fenetre.getTeachesListGUI().getPanel(), BorderLayout.CENTER);
				fenetre.setVisible(true);
				fenetre.repaint();
				
				ActionList<Teaches> actionTeachesList = new ActionList<Teaches>(fenetre, fenetre.getTeachesListGUI(), "teachesList" + type);
				actionTeachesList.execute();
			}
		});
		
		itemTeachesAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				TeachesForm TeachesAddForm = new TeachesForm(fenetre);
				TeachesAddForm.setVisible(true);
				
				// Rafraichissement de la liste
	        	ActionList<Teaches> actionTeachesList = new ActionList<Teaches>(fenetre, fenetre.getTeachesListGUI(), "teachesList" + type);
	        	actionTeachesList.execute();
	        }
		});
		
		itemTeachesRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ActionRemove<Teaches> actionTeachesRemove = new ActionRemove<Teaches>(fenetre, fenetre.getTeachesListGUI(), "teaches");
				actionTeachesRemove.execute();
			}
		});
		
		itemEnrollmentsList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fenetre.getPanel().removeAll();
				fenetre.getPanel().add(fenetre.getEnrollmentsListGUI().getPanel(), BorderLayout.CENTER);
				fenetre.setVisible(true);
				fenetre.repaint();
				
				ActionList<Enrollment> actionEnrollmentsList = new ActionList<Enrollment>(fenetre, fenetre.getEnrollmentsListGUI(), "enrollmentsList" + type);
				actionEnrollmentsList.execute();
			}
		});
		
		itemEnrollmentsAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				EnrollmentsForm EnrollmentsAddForm = new EnrollmentsForm(fenetre);
				EnrollmentsAddForm.setVisible(true);
				
				// Rafraichissement de la liste
	        	ActionList<Enrollment> actionEnrollmentsList = new ActionList<Enrollment>(fenetre, fenetre.getEnrollmentsListGUI(), "enrollmentsList" + type);
	        	actionEnrollmentsList.execute();
	        }
		});
		
		itemEnrollmentsRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ActionRemove<Enrollment> actionEnrollmentsRemove = new ActionRemove<Enrollment>(fenetre, fenetre.getEnrollmentsListGUI(), "enrollments");
				actionEnrollmentsRemove.execute();
			}
		});
		
		itemAssistantsList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fenetre.getPanel().removeAll();
				fenetre.getPanel().add(fenetre.getAssistantsListGUI().getPanel(), BorderLayout.CENTER);
				fenetre.setVisible(true);
				fenetre.repaint();
				
				ActionList<Assists> actionAssistantsList = new ActionList<Assists>(fenetre, fenetre.getAssistantsListGUI(), "assistsList" + type);
				actionAssistantsList.execute();
			}
		});
		
		itemAssistantsAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				AssistantsForm AssistantsAddForm = new AssistantsForm(fenetre);
				AssistantsAddForm.setVisible(true);
				
				// Rafraichissement de la liste
	        	ActionList<Assists> actionAssistantsList = new ActionList<Assists>(fenetre, fenetre.getAssistantsListGUI(), "assistsList" + type);
	        	actionAssistantsList.execute();
	        }
		});
		
		itemAssistantsRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ActionRemove<Assists> actionAssistantsRemove = new ActionRemove<Assists>(fenetre, fenetre.getAssistantsListGUI(), "assists");
				actionAssistantsRemove.execute();
			}
		});
		
		itemGradesList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fenetre.getPanel().removeAll();
				fenetre.getPanel().add(fenetre.getGradesListGUI().getPanel(), BorderLayout.CENTER);
				fenetre.setVisible(true);
				fenetre.repaint();
				
				ActionList<Grade> actionGradesList = new ActionList<Grade>(fenetre, fenetre.getGradesListGUI(), "gradesList" + type);
				actionGradesList.execute();
			}
		});
		
		itemGradesAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GradesForm GradesAddForm = new GradesForm(fenetre, null);
				GradesAddForm.setVisible(true);
				
				// Rafraichissement de la liste
				ActionList<Grade> actionGradesList = new ActionList<Grade>(fenetre, fenetre.getGradesListGUI(), "gradesList" + type);
	        	actionGradesList.execute();
	        }
		});
		
		itemGradesUpdate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				int[] selection = fenetre.getGradesListGUI().getTable().getSelectedRows();
		        
		        if(selection.length == 0)
		        {
		        	JOptionPane.showMessageDialog(null, "Vous devez sélectionner un élément dans la liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
		        else if(selection.length != 1)
		        {
		        	JOptionPane.showMessageDialog(null, "Vous devez un seul élément à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
		        else
		        {
		        	 Grade departmentSelected = fenetre.getGradesListGUI().getModele().get(selection[0]);
		        	 
		        	 GradesForm GradesAddForm = new GradesForm(fenetre, departmentSelected);
		        	 GradesAddForm.setVisible(true);
		        	 
		        	 // Rafraichissement de la liste
		        	 ActionList<Grade> actionGradesList = new ActionList<Grade>(fenetre, fenetre.getGradesListGUI(), "gradesList" + type);
		        	 actionGradesList.execute();
		        }
			}
		});
		
		itemGradesRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ActionRemove<Grade> actionGradesRemove = new ActionRemove<Grade>(fenetre, fenetre.getGradesListGUI(), "grades");
				actionGradesRemove.execute();
			}
		});
		
		itemStudentGradesList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fenetre.getPanel().removeAll();
				fenetre.getPanel().add(fenetre.getStudentGradesListGUI().getPanel(), BorderLayout.CENTER);
				fenetre.setVisible(true);
				fenetre.repaint();
				
				ActionList<StudentGrade> actionGradesList = new ActionList<StudentGrade>(fenetre, fenetre.getStudentGradesListGUI(), "studentGradesList" + type);
				actionGradesList.execute();
			}
		});
		
		itemAlertsList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fenetre.getPanel().removeAll();
				fenetre.getPanel().add(fenetre.getAlertsListGUI().getPanel(), BorderLayout.CENTER);
				fenetre.setVisible(true);
				fenetre.repaint();
				
				ActionList<Alert> actionAlertsList = new ActionList<Alert>(fenetre, fenetre.getAlertsListGUI(), "alertsList" + type);
				actionAlertsList.execute();
			}
		});
	}
}
