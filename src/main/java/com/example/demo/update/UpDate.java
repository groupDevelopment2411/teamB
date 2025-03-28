package com.example.demo.update;

import java.util.Date;

public class UpDate {
	
	private String id;
	private String name;
	private String age;
	private Date start_date;
	private Date end_date;
	private String password;
	
	public UpDate() {};
	public UpDate(String id, String name, String age, Date start_date, Date end_date, String password) {
		
		this.id = id;
		this.name = name;
		this.age = age;
		this.start_date = start_date;
		this.end_date = end_date;
		this.password = password;
		
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAge() {
		return this.age;
	}
	
	public void setAge(String age) {
		this.age = age;
	}
	
	public Date getStart_date() {
		return this.start_date;
	}
	
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	public Date getEnd_date() {
		return this.end_date;
	}
	
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
