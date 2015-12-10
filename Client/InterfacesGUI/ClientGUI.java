package InterfacesGUI;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.Socket;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Forms.LoginForm;
import Modeles.MAlerts;
import Modeles.MAssists;
import Modeles.MCourses;
import Modeles.MDepartments;
import Modeles.MEnrollments;
import Modeles.MGrades;
import Modeles.MStudentGrades;
import Modeles.MStudents;
import Modeles.MTeachers;
import Modeles.MTeaches;
import Modeles.MUsers;
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

public class ClientGUI extends JFrame
{
	private static final long serialVersionUID = 2L;
	
	// Socket serveur et flux
	private Socket server = null;
	private ObjectInput in = null;
	private ObjectOutput out = null;
	
	// Utilisateur courant
	private User currentUser = null;
	private Student currentStudent = null;
	private Teacher currentTeacher = null;
	
	private JPanel contentPane;
	private JPanel panel = new JPanel();
	
	// Menu
	private MenuGUI menu = null;
	
	// Panel
	private ListGUI<User> usersListGUI = null;
	private ListGUI<Department> departmentsListGUI = null;
	private ListGUI<Course> coursesListGUI = null;
	private ListGUI<Grade> gradesListGUI = null;
	private ListGUI<Student> studentsListGUI = null;
	private ListGUI<Teacher> teachersListGUI = null;
	private ListGUI<StudentGrade> studentGradesListGUI = null;
	private ListGUI<Enrollment> enrollmentsListGUI = null;
	private ListGUI<Teaches> teachesListGUI = null;
	private ListGUI<Assists> assistantsListGUI = null;
	private ListGUI<Alert> alertsListGUI = null;

	/**
	 * Create the frame.
	 */
	public ClientGUI(Socket server)
	{
		this.setServer(server);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientGUI.class.getResource("/Images/logo.png")));
		setTitle("School Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		setLocationRelativeTo(null);
		
		initDesktop();
		
		setMenu(new MenuGUI(this));
		
		setUsersListGUI(new ListGUI<User>(new MUsers()));
		setDepartmentsListGUI(new ListGUI<Department>(new MDepartments()));
		setCoursesListGUI(new ListGUI<Course>(new MCourses()));
		setGradesListGUI(new ListGUI<Grade>(new MGrades()));
		setStudentsListGUI(new ListGUI<Student>(new MStudents()));
		setTeachersListGUI(new ListGUI<Teacher>(new MTeachers()));
		setStudentGradesListGUI(new ListGUI<StudentGrade>(new MStudentGrades(this)));
		setEnrollmentsListGUI(new ListGUI<Enrollment>(new MEnrollments()));
		setTeachesListGUI(new ListGUI<Teaches>(new MTeaches()));
		setAssistantsListGUI(new ListGUI<Assists>(new MAssists()));
		setAlertsListGUI(new ListGUI<Alert>(new MAlerts()));
		
		panel.add(new LoginForm(this, null), BorderLayout.CENTER);
	}
	
	public void initDesktop()
	{
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
		);
		panel.setLayout(new BorderLayout(0, 0));
		contentPane.setLayout(gl_contentPane);
	}
	
	public JPanel getPanel()
	{
		return panel;
	}

	public void setPanel(JPanel panel)
	{
		this.panel = panel;
	}

	public MenuGUI getMenu()
	{
		return menu;
	}

	public void setMenu(MenuGUI menu)
	{
		this.menu = menu;
	}

	public ListGUI<User> getUsersListGUI()
	{
		return usersListGUI;
	}

	public void setUsersListGUI(ListGUI<User> usersListGUI)
	{
		this.usersListGUI = usersListGUI;
	}

	public User getCurrentUser()
	{
		return currentUser;
	}

	public void setCurrentUser(User currentUser)
	{
		this.currentUser = currentUser;
	}

	public ObjectOutput getOut()
	{
		return out;
	}

	public void setOut(ObjectOutput out)
	{
		this.out = out;
	}

	public ObjectInput getIn()
	{
		return in;
	}

	public void setIn(ObjectInput in)
	{
		this.in = in;
	}

	public Socket getServer()
	{
		return server;
	}

	public void setServer(Socket server)
	{
		this.server = server;
	}

	public ListGUI<Department> getDepartmentsListGUI()
	{
		return departmentsListGUI;
	}

	public void setDepartmentsListGUI(ListGUI<Department> departmentsListGUI)
	{
		this.departmentsListGUI = departmentsListGUI;
	}
	
	public ListGUI<Course> getCoursesListGUI()
	{
		return coursesListGUI;
	}

	public void setCoursesListGUI(ListGUI<Course> coursesListGUI)
	{
		this.coursesListGUI = coursesListGUI;
	}
	
	public ListGUI<Grade> getGradesListGUI()
	{
		return gradesListGUI;
	}

	public void setGradesListGUI(ListGUI<Grade> gradesListGUI)
	{
		this.gradesListGUI = gradesListGUI;
	}

	public ListGUI<Student> getStudentsListGUI()
	{
		return studentsListGUI;
	}

	public void setStudentsListGUI(ListGUI<Student> studentsListGUI)
	{
		this.studentsListGUI = studentsListGUI;
	}

	public ListGUI<Teacher> getTeachersListGUI()
	{
		return teachersListGUI;
	}

	public void setTeachersListGUI(ListGUI<Teacher> teachersListGUI)
	{
		this.teachersListGUI = teachersListGUI;
	}

	public ListGUI<StudentGrade> getStudentGradesListGUI()
	{
		return studentGradesListGUI;
	}

	public void setStudentGradesListGUI(ListGUI<StudentGrade> studentGradesListGUI)
	{
		this.studentGradesListGUI = studentGradesListGUI;
	}

	public ListGUI<Enrollment> getEnrollmentsListGUI()
	{
		return enrollmentsListGUI;
	}

	public void setEnrollmentsListGUI(ListGUI<Enrollment> enrollmentsListGUI)
	{
		this.enrollmentsListGUI = enrollmentsListGUI;
	}

	public ListGUI<Teaches> getTeachesListGUI()
	{
		return teachesListGUI;
	}

	public void setTeachesListGUI(ListGUI<Teaches> teachesListGUI)
	{
		this.teachesListGUI = teachesListGUI;
	}

	public ListGUI<Assists> getAssistantsListGUI()
	{
		return assistantsListGUI;
	}

	public void setAssistantsListGUI(ListGUI<Assists> assistantsListGUI)
	{
		this.assistantsListGUI = assistantsListGUI;
	}

	public Student getCurrentStudent()
	{
		return currentStudent;
	}

	public void setCurrentStudent(Student currentStudent)
	{
		this.currentStudent = currentStudent;
	}

	public Teacher getCurrentTeacher()
	{
		return currentTeacher;
	}

	public void setCurrentTeacher(Teacher currentTeacher)
	{
		this.currentTeacher = currentTeacher;
	}

	public ListGUI<Alert> getAlertsListGUI()
	{
		return alertsListGUI;
	}

	public void setAlertsListGUI(ListGUI<Alert> alertsListGUI)
	{
		this.alertsListGUI = alertsListGUI;
	}
}
