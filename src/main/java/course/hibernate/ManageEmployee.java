package course.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ManageEmployee {
	private static SessionFactory factory;
	
	public static void main(String[] args) {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.out.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		ManageEmployee me = new ManageEmployee();
		
		/* Add few employee records in database 增加一些員工記錄資料 */
		Integer empID1 = me.addEmployee("Zara","Ali", 1000);
		Integer empID2 = me.addEmployee("Daisy","Das", 5000);
		Integer empID3 = me.addEmployee("John","Paul", 10000);
		
		 /* List down all the employees */
		me.listEmployees();
		
	}
	
	/* Method to CREATE an employee in the database */
	public Integer addEmployee(String fname,String lname, int salary) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		
		try {
			tx = session.beginTransaction();
			Employee employee = new Employee(fname,lname,salary);
			employeeID = (Integer)session.save(employee);
			tx.commit();
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
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
			List employees = session.createQuery("FROM Employee").list();
			for(Iterator iterator = employees.iterator();iterator.hasNext();) {
				Employee employee = (Employee)iterator.next();
				System.out.print("FirstName: " + employee.getFirstName()); 
	            System.out.print("   LastName: " + employee.getLastName()); 
	            System.out.println("   Salary: " + employee.getSalary()); 
			}
			tx.commit();
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
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
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
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
			session.delete(employee);;
			tx.commit();
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
}
