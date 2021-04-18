package course.hibernate.annotation;

import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

public class ManageEmployee {
	private static SessionFactory factory;

	public static void main(String[] args) {
		
		try {
			factory = new AnnotationConfiguration()
							.configure()
							//以下這句可以用hibernate.cfn.xml的<mapping class="course.hibernate.annotation.Employee"/>取代
							.addAnnotatedClass(course.hibernate.annotation.Employee.class)
							.buildSessionFactory();
		} catch (Throwable ex) {
			System.out.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		ManageEmployee ME = new ManageEmployee();

	      /* Add few employee records in database */
	      Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
	      Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
	      Integer empID3 = ME.addEmployee("John", "Paul", 10000);

	      /* List down all the employees */
	      ME.listEmployees();

	      /* Update employee's records */
	      //ME.updateEmployee(empID1, 5000);

	      /* Delete an employee from the database */
	      //ME.deleteEmployee(empID2);

	      /* List down new list of the employees */
	      //ME.listEmployees();
	}
	
	 /* Method to CREATE an employee in the database */
	public Integer addEmployee(String fname,String lname,int salary) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		
		try {
			tx = session.beginTransaction();
			Employee employee = new Employee();
			employee.setFirstName(fname);
			employee.setLastName(lname);
			employee.setSalary(salary);
			employeeID = (Integer)session.save(employee);
			tx.commit();
		} catch (HibernateException e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return employeeID;
	}
	
	/* Method to  READ all the employees */
	public void listEmployees() {
		Session session = factory.openSession();
		Transaction tx = null;
		
				
		try {
			tx = session.beginTransaction();
			//Hibernate查詢語言（HQL）是一種面向對象的查詢語言，與SQL相似，將完整的持久性對象加載到內存中
			List employees= session.createQuery("FROM course.hibernate.annotation.Employee").list();
			for(Iterator iterator = employees.iterator();iterator.hasNext();) {
				Employee employee = (Employee)iterator.next();
				System.out.print("id: " + employee.getId()); 
				System.out.print("     FirstName: " + employee.getFirstName()); 
	            System.out.print("     LastName: " + employee.getLastName()); 
	            System.out.println("   Salary: " + employee.getSalary());
			}
			tx.commit();
		} catch (HibernateException e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	
	/* Method to UPDATE salary for an employee */
	public void updateEmployee(Integer EmployeeID, int salary) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			Employee employee = (Employee)session.get(Employee.class,EmployeeID);
			employee.setSalary(salary);
			session.update(employee);
			tx.commit();
		} catch (HibernateException e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	
	/* Method to DELETE an employee from the records */
	public void deleteEmployee(Integer EmployeeID) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			Employee employee = (Employee)session.get(Employee.class,EmployeeID);
			session.delete(employee);
			tx.commit();
		} catch (HibernateException e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

}
