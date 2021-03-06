package course.hibernate.xml;

public class Employee {
	
	private int id;
	private String firstName;
	private String lastName;
	private int salary;
	
	public Employee() {
		
	}

	public Employee(String first_name, String last_name, int salary) {
		this.firstName = first_name;
		this.lastName = last_name;
		this.salary = salary;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

}
