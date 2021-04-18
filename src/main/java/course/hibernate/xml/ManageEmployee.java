package course.hibernate.xml;

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
			factory = new Configuration()
							.configure()
							.buildSessionFactory();
		} catch (Throwable ex) {
			System.out.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		ManageEmployee ME = new ManageEmployee();
		
		/* Add few employee records in database 增加一些員工記錄資料 */
		Integer empID1 = ME.addEmployee("Zara","Ali", 1000);
		Integer empID2 = ME.addEmployee("Daisy","Das", 5000);
		Integer empID3 = ME.addEmployee("John","Paul", 10000);
		
		/* get employees */		
//		Employee employee = ME.getEmployee(1);
//		System.out.println(employee.getFirstName());
		
		/* List down all the employees */
		ME.listEmployees();
		
		/* Update employee's records */
	    //ME.updateEmployee(empID1, 5000);
		
	    /* Delete an employee from the database */
	    //ME.deleteEmployee(empID2);
	    
	    System.out.println("---------------------------------------------------");
	    /* List down new list of the employees */
	   // ME.listEmployees();
	}
	
	/* Method to CREATE an employee in the database */
	public Integer addEmployee(String fname,String lname, int salary) {
/*開啟Session不會馬上取得Connection，而是在最後真正需要連接資料庫進行更新或查詢時才
會取 得Connection，如果有設定Connection pool，則從Connection pool中取得Connection
，而關閉Session時，如果有設定Connection pool，則是將Connection歸還給Connection pool
，而不是直接關閉Connection。
在Hibernate中，開啟一個Session會建立一個Persistence context，它可以進行快取管理、
dirty check等，而所有的讀取、更新、插入等動作，則是在Transaction中完成。*/
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		
		try {
			tx = session.beginTransaction();
			Employee employee = new Employee(fname,lname,salary);
/*儲存資料
 save()之後，不會馬上對資料庫進行更新，而是在Transaction的commit()之後才會對資料庫
 進行更新，在Transaction之間 的操作要就全部成功，要就全部失敗，如果更新失敗，則在
 資料庫層面會撤消所有更新操作，然而記憶體中的持久物件是不會回復原有狀態的，事實上，
 當 Transaction失敗，這一次的Session就要馬上跟著失效，放棄所有記憶體中的物件，而不
 是嘗試以原物件再進行更新的動作。  
*/
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
	
	/* Method to  READ a the employees */
	public Employee getEmployee(Integer EmployeeID) {
		Session session = factory.openSession();
		Transaction tx = null;
		Employee employee=null;
		try {
			tx = session.beginTransaction();
			employee = (Employee)session.get(Employee.class,EmployeeID);
			tx.commit();
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return employee;
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
				System.out.print("id: " + employee.getId()); 
				System.out.print("     FirstName: " + employee.getFirstName()); 
	            System.out.print("     LastName: " + employee.getLastName()); 
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
