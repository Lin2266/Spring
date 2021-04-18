package course.hibernate.annotation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*@Entity指定當前類是持久化實體類，不能省略，將此類標記為實體Bean，因此它必須具有無參數構造函數，
  該構造函數至少在受保護範圍內可見。*/
@Entity(name = "course.hibernate.annotation.Employee")	
@Table(name = "employee")/*指定實體類和數據庫表的對應關係（可以省略），省略後：表名=類名*/
public class Employee {
	/*每個實體bean將具有一個主鍵，您可以在類上使用@Id註釋對其進行註釋。主鍵可以是單個字段，
	 *也可以是多個字段的組合，具體取決於您的表結構。
	 *預設情況下，@ Id批註將自動確定要使用的最合適的主鍵生成策略，但是您可以通過應用@GeneratedValue
	 *註解來覆蓋此策略，該批註採用兩個參數策略和生成器，在此不再贅述，因此讓我們只使用默認的密鑰生成
	 *策略。讓Hibernate確定使用哪種生成器類型，可以使您的代碼在不同數據庫之間可移植。*/
	@Id @GeneratedValue
	@Column(name = "id")/*指定實體類和數據庫表的對應關係*/
	private int id;
	
	@Column(name = "first_name")// 非必要，在欄位名稱與屬性名稱不同時使用
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "salary")
	private int salary;
	
	// 必須要有一個預設的建構方法
    // 以使得Hibernate可以使用Constructor.newInstance()建立物件
	public Employee() {
		
	}

	public Employee(String firstName, String lastName, int salary) {
		this.firstName = firstName;
		this.lastName = lastName;
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
