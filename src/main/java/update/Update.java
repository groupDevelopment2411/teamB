package update;

public class Update {
	
	private int id;
	private String name;
	private int age;
	private String start_date;
	private String end_date;
	private String password;
	
	public Update() {};
	public Update(int id, String name, int age, String start_date, String end_date, String password) {
		
		this.id = id;
		this.name = name;
		this.age = age;
		this.start_date = start_date;
		this.end_date = end_date;
		this.password = password;
		
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getStart_date() {
		return this.start_date;
	}
	
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	public String getEnd_date() {
		return this.end_date;
	}
	
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
