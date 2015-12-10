package ServerLauncher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import DAO.AdminDAO;
import DAO.AlertDAO;
import DAO.AssistsDAO;
import DAO.CourseDAO;
import DAO.DepartmentDAO;
import DAO.EnrollmentDAO;
import DAO.GradeDAO;
import DAO.StudentDAO;
import DAO.StudentGradeDAO;
import DAO.TeacherDAO;
import DAO.TeachesDAO;
import DAO.UserDAO;
import Objets.Admin;
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

public class Commandes implements Runnable
{
	// Serveur et flux IO
	private Socket clientSocket;
	private ObjectInput in = null;
	private ObjectOutput out = null;
	
	// Commande reçue
	private String cmd = null;
	
	// Date Log
	private DateFormat logDate = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("EN","en"));
	
	// Constructor
	public Commandes(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run()
	{
		try
		{
			// Flux d'objets entrant
			if(in == null)
			in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			
			// Flux d'objets sortant
			if(out == null)
			out = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			
			// Lit le flux entrant, en attente d'une commande
			while((cmd = (String) in.readObject()) != null)
			{
				// Commande login
				if(cmd.equalsIgnoreCase("login"))
				{
					String email = (String) in.readObject();
					String password = (String) in.readObject();

					User user = UserDAO.login(email, password);
					
					if(user != null)
					{
						// Renvoie login OK
						out.writeObject("login_true");
						out.flush();
						// Renvoie l'objet user au client
						out.writeObject(user);
						out.flush();
						// Log
						System.out.println(logDate.format(new Date()) + " - [LOGIN OK] - Login : " + email + " - IP : " + clientSocket.getInetAddress());
					}
					else
					{
						// Renvoie login non OK
						out.writeObject("login_false");
						out.flush();
						// Log
						System.out.println(logDate.format(new Date()) + " - [LOGIN ERROR] - Login : " + email + " - IP : " + clientSocket.getInetAddress());
					}
				}
				else if(cmd.equalsIgnoreCase("logout"))
				{
					String email = (String) in.readObject();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [LOGOUT] - Login : " + email + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("usersList"))
				{
					List<User> usersList = UserDAO.getList("");

					out.writeObject(usersList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [USERS LIST] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("usersRemove"))
				{
					List<User> usersRemove = (List<User>) in.readObject();
					
					for(int i = 0; i < usersRemove.size(); i++)
					{
						boolean result = false;
						switch(usersRemove.get(i).getType())
						{
							case "ADMIN" :
								Admin admin = new Admin();
								admin = AdminDAO.select(usersRemove.get(i).getUID());
								
								AdminDAO adminDAO = new AdminDAO();
								result = adminDAO.delete(admin);
								break;
							case "STUDENT" :
								Student student = new Student();
								student = StudentDAO.select(usersRemove.get(i).getUID());
								
								StudentDAO studentDAO = new StudentDAO();
								result = studentDAO.delete(student);
								break;
							case "TEACHER" :
								Teacher teacher = new Teacher();
								teacher = TeacherDAO.select(usersRemove.get(i).getUID());
								
								TeacherDAO teacherDAO = new TeacherDAO();
								result = teacherDAO.delete(teacher);
								break;
						}
						
						out.writeObject(result);
						out.flush();
						
						// Log
						if(result)
						System.out.println(logDate.format(new Date()) + " - [" + usersRemove.get(i).getType() + " REMOVE OK] - " + "User removed : " + usersRemove.get(i).getEmail() + " - IP : " + clientSocket.getInetAddress());
						else
						System.out.println(logDate.format(new Date()) + " - [" + usersRemove.get(i).getType() + " REMOVE ERROR] - " + "User removed : " + usersRemove.get(i).getEmail() + " - IP : " + clientSocket.getInetAddress());
					}
				}
				else if(cmd.equalsIgnoreCase("teachersList"))
				{
					List<Teacher> teachersList = TeacherDAO.getList("");

					out.writeObject(teachersList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [TEACHERS LIST] - IP : " + clientSocket.getInetAddress());
				}
				/*
				else if(cmd.equalsIgnoreCase("studentsList"))
				{
					List<Student> studentsList = StudentDAO.getList("");

					out.writeObject(studentsList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [STUDENTS LIST] - IP : " + clientSocket.getInetAddress());
				}
				*/
				else if(cmd.equalsIgnoreCase("adminAdd"))
				{
					Admin admin = (Admin) in.readObject();
					
					AdminDAO adminDAO = new AdminDAO();
					boolean result = adminDAO.insert(admin);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [ADMIN ADD OK] - Admin added : " + admin.getEmail() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [ADMIN ADD ERROR] - Admin added : " + admin.getEmail() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("teacherAdd"))
				{
					Teacher teacher = (Teacher) in.readObject();
					
					TeacherDAO teacherDAO = new TeacherDAO();
					boolean result = teacherDAO.insert(teacher);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [TEACHER ADD OK] - Teacher added : " + teacher.getEmail() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [TEACHER ADD ERROR] - Teacher added : " + teacher.getEmail() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("studentAdd"))
				{
					Student student = (Student) in.readObject();
					
					StudentDAO studentDAO = new StudentDAO();
					boolean result = studentDAO.insert(student);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [STUDENT ADD OK] - Student added : " + student.getEmail() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [STUDENT ADD ERROR] - Student added : " + student.getEmail() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("adminUpdate"))
				{
					Admin admin = (Admin) in.readObject();
					
					AdminDAO adminDAO = new AdminDAO();
					boolean result = adminDAO.update(admin);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [ADMIN UPDATE OK] - Admin updated : " + admin.getEmail() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [ADMIN UPDATE ERROR] - Admin updated : " + admin.getEmail() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("teacherUpdate"))
				{
					Teacher teacher = (Teacher) in.readObject();
					
					TeacherDAO teacherDAO = new TeacherDAO();
					boolean result = teacherDAO.update(teacher);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [TEACHER UPDATE OK] - Teacher updated : " + teacher.getEmail() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [TEACHER UPDATE ERROR] - Teacher updated : " + teacher.getEmail() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("studentUpdate"))
				{
					Student student = (Student) in.readObject();
					
					StudentDAO studentDAO = new StudentDAO();
					boolean result = studentDAO.update(student);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [STUDENT UPDATE OK] - Student updated : " + student.getEmail() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [STUDENT UPDATE ERROR] - Student updated : " + student.getEmail() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("userGet"))
				{
					int id = (int) in.readObject();
					
					out.writeObject(UserDAO.select(id));
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [USER GET] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("adminGet"))
				{
					int id = (int) in.readObject();
					
					out.writeObject(AdminDAO.select(id));
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [ADMIN GET] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("teacherGet"))
				{
					int id = (int) in.readObject();
					
					out.writeObject(TeacherDAO.select(id));
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [TEACHER GET] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("studentGet"))
				{
					int id = (int) in.readObject();
					
					out.writeObject(StudentDAO.select(id));
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [STUDENT GET] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("departmentsList"))
				{
					List<Department> departmentsList = DepartmentDAO.getList("");

					out.writeObject(departmentsList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [DEPARTMENTS LIST] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("departmentAdd"))
				{
					Department department = (Department) in.readObject();
					
					DepartmentDAO departmentDAO = new DepartmentDAO();
					boolean result = departmentDAO.insert(department);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [DEPARTMENT ADD OK] - Department added : " + department.getName() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [DEPARTMENT ADD ERROR] - Department added : " + department.getName() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("departmentUpdate"))
				{
					Department department = (Department) in.readObject();
					
					DepartmentDAO departmentDAO = new DepartmentDAO();
					boolean result = departmentDAO.update(department);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [DEPARTMENT UPDATE OK] - Department updated : " + department.getName() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [DEPARTMENT UPDATE ERROR] - Department updated : " + department.getName() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("departmentsRemove"))
				{
					List<Department> departmentsRemove = (List<Department>) in.readObject();
					
					for(int i = 0; i < departmentsRemove.size(); i++)
					{
						boolean result = false;
						Department department = new Department();
						department = DepartmentDAO.select(departmentsRemove.get(i).getDID());
						
						DepartmentDAO departmentDAO = new DepartmentDAO();
						result = departmentDAO.delete(department);
						out.writeObject(result);
						out.flush();
						
						// Log
						if(result)
						System.out.println(logDate.format(new Date()) + " - [DEPARTMENT REMOVE OK] - Department removed : " + departmentsRemove.get(i).getName() + " - IP : " + clientSocket.getInetAddress());
						else
						System.out.println(logDate.format(new Date()) + " - [DEPARTMENT REMOVE ERROR] - Department removed : " + departmentsRemove.get(i).getName() + " - IP : " + clientSocket.getInetAddress());
					}
				}
				/*
				else if(cmd.equalsIgnoreCase("coursesList"))
				{
					List<Course> coursesList = CourseDAO.getList("order by CNAME ASC");

					out.writeObject(coursesList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [COURSES LIST] - IP : " + clientSocket.getInetAddress());
				}
				*/
				else if(cmd.equalsIgnoreCase("courseAdd"))
				{
					Course course = (Course) in.readObject();
					
					CourseDAO courseDAO = new CourseDAO();
					boolean result = courseDAO.insert(course);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [COURSE ADD OK] - Course added : " + course.getName() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [COURSE ADD ERROR] - Course added : " + course.getName() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("courseUpdate"))
				{
					Course course = (Course) in.readObject();
					
					CourseDAO courseDAO = new CourseDAO();
					boolean result = courseDAO.update(course);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [COURSE UPDATE OK] - Course updated : " + course.getName() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [COURSE UPDATE ERROR] - Course updated : " + course.getName() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("coursesRemove"))
				{
					List<Course> coursesRemove = (List<Course>) in.readObject();
					
					for(int i = 0; i < coursesRemove.size(); i++)
					{
						boolean result = false;
						Course course = new Course();
						course = CourseDAO.select(coursesRemove.get(i).getCID());
						
						CourseDAO courseDAO = new CourseDAO();
						result = courseDAO.delete(course);
						out.writeObject(result);
						out.flush();
						
						// Log
						if(result)
						System.out.println(logDate.format(new Date()) + " - [COURSE REMOVE OK] - Course removed : " + coursesRemove.get(i).getName() + " - IP : " + clientSocket.getInetAddress());
						else
						System.out.println(logDate.format(new Date()) + " - [COURSE REMOVE ERROR] - Course removed : " + coursesRemove.get(i).getName() + " - IP : " + clientSocket.getInetAddress());
					}
				}
				/*
				else if(cmd.equalsIgnoreCase("gradesList"))
				{
					List<Grade> gradesList = GradeDAO.getList("");

					out.writeObject(gradesList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [GRADES LIST] - IP : " + clientSocket.getInetAddress());
				}
				*/
				else if(cmd.equalsIgnoreCase("gradeAdd"))
				{
					Grade grade = (Grade) in.readObject();
					
					GradeDAO gradeDAO = new GradeDAO();
					boolean result = gradeDAO.insert(grade);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [GRADE ADD OK] - Grade added : " + grade.getGID() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [GRADE ADD ERROR] - Grade added : " + grade.getGID() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("gradeUpdate"))
				{
					Grade grade = (Grade) in.readObject();
					
					GradeDAO gradeDAO = new GradeDAO();
					boolean result = gradeDAO.update(grade);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [GRADE UPDATE OK] - Grade updated : " + grade.getGID() + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [GRADE UPDATE ERROR] - Grade updated : " + grade.getGID() + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("gradesRemove"))
				{
					List<Grade> gradesRemove = (List<Grade>) in.readObject();
					
					for(int i = 0; i < gradesRemove.size(); i++)
					{
						boolean result = false;
						Grade grade = new Grade();
						grade = GradeDAO.select(gradesRemove.get(i).getGID());
						
						GradeDAO gradeDAO = new GradeDAO();
						result = gradeDAO.delete(grade);
						out.writeObject(result);
						out.flush();
						
						// Log
						if(result)
						System.out.println(logDate.format(new Date()) + " - [GRADE REMOVE OK] - Grade removed : " + gradesRemove.get(i).getGID() + " - IP : " + clientSocket.getInetAddress());
						else
						System.out.println(logDate.format(new Date()) + " - [GRADE REMOVE ERROR] - Grade removed : " + gradesRemove.get(i).getGID() + " - IP : " + clientSocket.getInetAddress());
					}
				}
				/*
				else if(cmd.equalsIgnoreCase("studentGradesList"))
				{
					List<StudentGrade> studentGradesList = StudentGradeDAO.getList("");

					out.writeObject(studentGradesList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [STUDENTGRADES LIST] - IP : " + clientSocket.getInetAddress());
				}
				*/
				else if(cmd.equalsIgnoreCase("studentGradeAdd"))
				{
					StudentGrade studentGrade = (StudentGrade) in.readObject();
					
					StudentGradeDAO studentGradeDAO = new StudentGradeDAO();
					boolean result = studentGradeDAO.insert(studentGrade);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [STUDENTGRADE ADD OK] - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [STUDENTGRADE ADD ERROR] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("studentGradeUpdate"))
				{
					StudentGrade studentGrade = (StudentGrade) in.readObject();
					
					StudentGradeDAO studentGradeDAO = new StudentGradeDAO();
					boolean result = studentGradeDAO.update(studentGrade);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [STUDENTGRADE UPDATE OK] - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [STUDENTGRADE UPDATE ERROR] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("studentGradesRemove"))
				{
					List<StudentGrade> studentGradesRemove = (List<StudentGrade>) in.readObject();
					
					for(int i = 0; i < studentGradesRemove.size(); i++)
					{
						boolean result = false;
						StudentGrade studentGrade = new StudentGrade();
						studentGrade = StudentGradeDAO.select(studentGradesRemove.get(i).getGrade().getGID(), studentGradesRemove.get(i).getStudent().getUID());
						
						StudentGradeDAO studentGradeDAO = new StudentGradeDAO();
						result = studentGradeDAO.delete(studentGrade);
						out.writeObject(result);
						out.flush();
						
						// Log
						if(result)
						System.out.println(logDate.format(new Date()) + " - [STUDENTGRADE REMOVE OK] - IP : " + clientSocket.getInetAddress());
						else
						System.out.println(logDate.format(new Date()) + " - [STUDENTGRADE REMOVE ERROR] - IP : " + clientSocket.getInetAddress());
					}
				}
				/*
				else if(cmd.equalsIgnoreCase("enrollmentsList"))
				{
					List<Enrollment> enrollmentsList = EnrollmentDAO.getList("");

					out.writeObject(enrollmentsList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [ENROLLMENTS LIST] - IP : " + clientSocket.getInetAddress());
				}
				*/
				else if(cmd.equalsIgnoreCase("enrollmentAdd"))
				{
					Enrollment enrollment = (Enrollment) in.readObject();
					
					EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
					boolean result = enrollmentDAO.insert(enrollment);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [ENROLLMENT ADD OK] - Enrollment added : " + enrollment + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [ENROLLMENT ADD ERROR] - Enrollment added : " + enrollment + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("enrollmentsRemove"))
				{
					List<Enrollment> enrollmentsRemove = (List<Enrollment>) in.readObject();
					
					for(int i = 0; i < enrollmentsRemove.size(); i++)
					{
						boolean result = false;
						Enrollment enrollment = new Enrollment();
						enrollment = EnrollmentDAO.select(enrollmentsRemove.get(i).getCourse().getCID(), enrollmentsRemove.get(i).getStudent().getUID());
						
						EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
						result = enrollmentDAO.delete(enrollment);
						out.writeObject(result);
						out.flush();
						
						// Log
						if(result)
						System.out.println(logDate.format(new Date()) + " - [ENROLLMENT REMOVE OK] - Enrollment removed : " + enrollmentsRemove.get(i) + " - IP : " + clientSocket.getInetAddress());
						else
						System.out.println(logDate.format(new Date()) + " - [ENROLLMENT REMOVE ERROR] - Enrollment removed : " + enrollmentsRemove.get(i) + " - IP : " + clientSocket.getInetAddress());
					}
				}
				else if(cmd.equalsIgnoreCase("teachesList"))
				{
					List<Teaches> teachesList = TeachesDAO.getList("");

					out.writeObject(teachesList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [TEACHES LIST] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("teachesAdd"))
				{
					Teaches teaches = (Teaches) in.readObject();
					
					TeachesDAO teachesDAO = new TeachesDAO();
					boolean result = teachesDAO.insert(teaches);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [TEACHES ADD OK] - Teaches added : " + teaches + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [TEACHES ADD ERROR] - Teaches added : " + teaches + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("teachesRemove"))
				{
					List<Teaches> teachesRemove = (List<Teaches>) in.readObject();
					
					for(int i = 0; i < teachesRemove.size(); i++)
					{
						boolean result = false;
						Teaches teaches = new Teaches();
						teaches = TeachesDAO.select(teachesRemove.get(i).getCourse().getCID(), teachesRemove.get(i).getTeacher().getUID());
						
						TeachesDAO teachesDAO = new TeachesDAO();
						result = teachesDAO.delete(teaches);
						out.writeObject(result);
						out.flush();
						
						// Log
						if(result)
						System.out.println(logDate.format(new Date()) + " - [TEACHES REMOVE OK] - Teaches removed : " + teachesRemove.get(i) + " - IP : " + clientSocket.getInetAddress());
						else
						System.out.println(logDate.format(new Date()) + " - [TEACHES REMOVE ERROR] - Teaches removed : " + teachesRemove.get(i) + " - IP : " + clientSocket.getInetAddress());
					}
				}
				/*
				else if(cmd.equalsIgnoreCase("assistsList"))
				{
					List<Assists> assistsList = AssistsDAO.getList("");

					out.writeObject(assistsList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [ASSISTS LIST] - IP : " + clientSocket.getInetAddress());
				}
				*/
				else if(cmd.equalsIgnoreCase("assistsAdd"))
				{
					Assists assists = (Assists) in.readObject();
					
					AssistsDAO assistsDAO = new AssistsDAO();
					boolean result = assistsDAO.insert(assists);
					out.writeObject(result);
					out.flush();
					
					// Log
					if(result)
					System.out.println(logDate.format(new Date()) + " - [ASSISTS ADD OK] - Assists added : " + assists + " - IP : " + clientSocket.getInetAddress());
					else
					System.out.println(logDate.format(new Date()) + " - [ASSISTS ADD ERROR] - Assists added : " + assists + " - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.equalsIgnoreCase("assistsRemove"))
				{
					List<Assists> assistsRemove = (List<Assists>) in.readObject();
					
					for(int i = 0; i < assistsRemove.size(); i++)
					{
						boolean result = false;
						Assists assists = new Assists();
						assists = AssistsDAO.select(assistsRemove.get(i).getCourse().getCID(), assistsRemove.get(i).getAssistant().getUID());
						
						AssistsDAO assistsDAO = new AssistsDAO();
						result = assistsDAO.delete(assists);
						out.writeObject(result);
						out.flush();
						
						// Log
						if(result)
						System.out.println(logDate.format(new Date()) + " - [ASSISTS REMOVE OK] - Assists removed : " + assistsRemove.get(i) + " - IP : " + clientSocket.getInetAddress());
						else
						System.out.println(logDate.format(new Date()) + " - [ASSISTS REMOVE ERROR] - Assists removed : " + assistsRemove.get(i) + " - IP : " + clientSocket.getInetAddress());
					}
				}
				else if(cmd.equalsIgnoreCase("assistantsList"))
				{
					List<User> usersList = UserDAO.getList("where UTYPE <> \"ADMIN\"");

					out.writeObject(usersList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [ASSISTANTS LIST] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.contains("coursesList"))
				{
					List<Course> coursesList = null;
					if(cmd.contains("StudentComboBox"))
					{
						Student student = (Student) in.readObject();
						coursesList = CourseDAO.getList("where (CYEAR = " + student.getPromo() + " or CYEAR is null) and COMPULSORY = 0 and CID not in (select CID from ENROLLMENTS where SID = " + student.getUID() + ") order by CNAME ASC");
					}
					else if(cmd.contains("Student"))
					{
						Student student = (Student) in.readObject();
						coursesList = CourseDAO.getList("where CYEAR = " + student.getPromo() + " or CYEAR is null order by CNAME ASC");
					}
					else if(cmd.contains("Teacher"))
					{
						Teacher teacher = (Teacher) in.readObject();
						coursesList = CourseDAO.getList("natural join TEACHES where TID = " + teacher.getUID() + " order by CNAME ASC");
					}
					else
					{
						coursesList = CourseDAO.getList("order by CNAME ASC");
					}

					out.writeObject(coursesList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [COURSES LIST] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.contains("studentsList"))
				{
					List<Student> studentsList = null;
					/*
					if(cmd.contains("Student"))
					{
						Student student = (Student) in.readObject();
						studentsList = StudentDAO.getList("");
					}
					else */if(cmd.contains("Teacher"))
					{
						Teacher teacher = (Teacher) in.readObject();
						studentsList = StudentDAO.getList("where TUTOR = " + teacher.getUID());
					}
					else
					{
						studentsList = StudentDAO.getList("");
					}
					
					out.writeObject(studentsList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [STUDENTS LIST] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.contains("assistsList"))
				{
					List<Assists> assistsList = null;
					if(cmd.contains("Student"))
					{
						Student student = (Student) in.readObject();
						assistsList = AssistsDAO.getList("where UID = " + student.getUID());
					}
					else if(cmd.contains("Teacher"))
					{
						Teacher teacher = (Teacher) in.readObject();
						assistsList = AssistsDAO.getList("where UID = " + teacher.getUID());
					}
					else
					{
						assistsList = AssistsDAO.getList("");
					}
					
					out.writeObject(assistsList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [ASSISTS LIST] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.contains("studentGradesList"))
				{
					List<StudentGrade> studentGradesList = null;
					if(cmd.contains("Student"))
					{
						Student student = (Student) in.readObject();
						studentGradesList = StudentGradeDAO.getList("where SID = " + student.getUID());
					}
					else if(cmd.contains("Teacher"))
					{
						Teacher teacher = (Teacher) in.readObject();
						studentGradesList = StudentGradeDAO.getList("natural join GRADES natural join TEACHES where TID = " + teacher.getUID());
					}
					else
					{
						studentGradesList = StudentGradeDAO.getList("");
					}

					out.writeObject(studentGradesList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [STUDENTGRADES LIST] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.contains("gradesList"))
				{
					List<Grade> gradesList = null;
					if(cmd.contains("Student"))
					{
						Student student = (Student) in.readObject();
						gradesList = GradeDAO.getList("natural join ENROLLMENTS where SID = " + student.getUID());
					}
					else if(cmd.contains("Teacher"))
					{
						Teacher teacher = (Teacher) in.readObject();
						gradesList = GradeDAO.getList("natural join TEACHES where TID = " + teacher.getUID());
					}
					else
					{
						gradesList = GradeDAO.getList("");
					}
					
					out.writeObject(gradesList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [GRADES LIST] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.contains("enrollmentsList"))
				{
					List<Enrollment> enrollmentsList = null;
					if(cmd.contains("Student"))
					{
						Student student = (Student) in.readObject();
						enrollmentsList = EnrollmentDAO.getList("where SID = " + student.getUID());
					}
					/*
					else if(cmd.contains("Teacher"))
					{
						Teacher teacher = (Teacher) in.readObject();
						enrollmentsList = EnrollmentDAO.getList("");
					}
					*/
					else
					{
						enrollmentsList = EnrollmentDAO.getList("");
					}
					
					out.writeObject(enrollmentsList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [ENROLLMENTS LIST] - IP : " + clientSocket.getInetAddress());
				}
				else if(cmd.contains("alertsList"))
				{
					List<Alert> alertsList = null;
					
					if(cmd.contains("Student"))
					{
						Student student = (Student) in.readObject();
						alertsList = AlertDAO.getList(student.getUID(), "student");
					}
					else
					{
						Teacher teacher = (Teacher) in.readObject();
						alertsList = AlertDAO.getList(teacher.getUID(), "teacher");
					}

					out.writeObject(alertsList);
					out.flush();
					
					// Log
					System.out.println(logDate.format(new Date()) + " - [ALERTS LIST] - IP : " + clientSocket.getInetAddress());
				}
				// Autre
				else
				{
					System.out.println(cmd);
				}
			}
		}
		catch (Exception e)
		{
			// Déconnexion client
			AddConnection.nbClients--;
			// Log
			System.out.println(logDate.format(new Date()) + " - [SERVER DISCONNECTION] - IP : " + clientSocket.getInetAddress());
			System.out.println(AddConnection.nbClients + " client(s) connected!");
		}
	}
}
