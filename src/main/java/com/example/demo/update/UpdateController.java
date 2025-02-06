package com.example.demo.update;

import java.util.Date;

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
	
	@PostMapping("/updateCheak")
	public String updateCheak(
			Model m,
			@RequestParam("id") int id,
			@RequestParam("name") String name,
			@RequestParam("age") int age,
			@RequestParam("start_date") String start_date,
			@RequestParam("end_date") String end_date,
			@RequestParam("password") String password,
			@RequestParam("passwordCheck") String passwordCheck
			) {
		 m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("start_Date", start_date);
	        m.addAttribute("end_Date", end_date);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
		
		m.addAttribute("msg", "こちらで更新します。よろしいですか？");
		return "updateCheak";
	}
	
	@PostMapping("/updateResult")
	public String updateResult(
			Model m,
			@RequestParam("id") int id,
			@RequestParam("name") String name,
			@RequestParam("age") int age,
			@RequestParam("start_date") Date start_date,
			@RequestParam("end_date") Date end_date,
			@RequestParam("password") String password
	)
	{

		UpDate employee = new UpDate(id, name, age, start_date, end_date, password);
		servise.update(employee);
		 m.addAttribute("msg", "社員情報の更新が完了しました。");
		 return "updateResult";	
		
	}
		
	

}
