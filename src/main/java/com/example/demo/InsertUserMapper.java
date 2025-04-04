package com.example.demo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InsertUserMapper{
	
	
	@Insert("INSERT INTO employee (name,age,start_date,password) VALUES (#{name},#{age},now(),#{password})")
	void insertUser(InsertUser insertuser);
}