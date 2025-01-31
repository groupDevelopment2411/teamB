package com.example.demo.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {
	@Autowired
	private UpdateMapper mapper;
		
		public void UpDate(UpDate employee) {
			mapper.update(employee);
		}

}
