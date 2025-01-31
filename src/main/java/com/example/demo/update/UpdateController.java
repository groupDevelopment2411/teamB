package com.example.demo.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UpdateController {
	@Autowired
	private UpdateService servise;
	
	@RequestMapping("/updateForm")
	public String updateForm() {
		return "updateForm";
	}
	
	@RequestMapping("/updateCheak")
	
	
	@PostMapping("/updateResult")
	public void update(
			Model m,
			@RequestParam("id") int id,
			@RequestParam("name") String name,
			@RequestParam("age") int age,
			@RequestParam("start_date") String start_date,
			@RequestParam("end_date") String end_date,
			@RequestParam("password") String password
	)
	{

		UpDate employee = new UpDate(id, name, age, start_date, end_date, password);
		servise.UpDate(employee);
		m.addAttribute("msg", "社員情報の更新が完了しました。");
		
		
	}
		
	

}
