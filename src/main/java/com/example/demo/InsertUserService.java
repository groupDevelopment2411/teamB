package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertUserService {
    @Autowired
    private InsertUserMapper mapper;

    public void insert(InsertUser insertUser) { 
    	mapper.insertUser(insertUser); 
    }

	}