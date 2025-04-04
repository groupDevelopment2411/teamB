package com.example.demo;

import java.time.LocalDate;
import java.util.Date;

public class InsertUser {
	private String name;
	private int age;
	private String password;
	private String password2;
	private LocalDate startDate = LocalDate.now(); 
	public InsertUser(String name, int age, String password,String password2) {
		this.name=name;
		this.age=age;
		this.password=password;
		this.setPassword2(password2);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getage() {
		return age;
	}
	public void setage(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public Date getStart_date() {
		return start_date();
	}
	private Date start_date() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	public void setStart_date(Date start_date) {
	}
}//Getter.Setter 作成
