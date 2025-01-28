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

}
