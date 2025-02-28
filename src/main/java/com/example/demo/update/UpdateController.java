package com.example.demo.update;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UpdateController {
	@Autowired
	private UpdateService service;
	
	@RequestMapping("/idSearch")
	public String idSearch() {
		return "idSearch";
	}
	
	@PostMapping("/updateForm")
	public String updateForm(
			Model m, RedirectAttributes r,
			@RequestParam(value = "id", required = false) int id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "age", required = false) Integer age,
			@RequestParam(value = "start_date", required = false) String start_date,
			@RequestParam(value = "end_date", required = false) String end_date,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "passwordCheck", required = false) String passwordCheck
			//↑それぞれに「required = false」を記述し、パラメーターが無くてもnullが代入されるようにしている。(@RequestParamではデフォルトだと値の値が必須で、何も無いとエラーになる為。)
			//idSearchで入力するのはID(id)のみで、名前(name)等はリクエストに含まれない(どうしてもパラメーター無しになりエラーに繋がる)が、nullが代入されることでエラーを避けられる。
			//「value = ""」は「required = false」を用いる際は記述しないと警告文が発生した。
			) {
		 //↓ID検索画面からの処理用if文。ID検索画面はID以外の入力が無い(ID以外がnull)になる為、ID以外がnullの場合は検索を行う様にif文で誘導している。
		if (name == null || age == null || start_date == null || end_date == null || password == null || passwordCheck == null) {
			List<UpDate> employeeList = service.selectById(id);
			if(employeeList.size() == 0) {
				employeeList = null;
				r.addFlashAttribute("error", "入力された社員IDと一致するデータが見つかりませんでした。");
				return "redirect:/idSearch";
			}
			UpDate employee = employeeList.get(0);
			//↑employeeテーブルからデータを取得するList<UpDate>から最初の要素を取得する文。UpDateオブジェクトを取得する。employeeにはUpDateクラスのデータが格納される。
            m.addAttribute("id", employee.getId());
            m.addAttribute("name", employee.getName());
            m.addAttribute("age", employee.getAge());
            m.addAttribute("start_date", employee.getStart_date());
            m.addAttribute("end_date", employee.getEnd_date());
            m.addAttribute("password", employee.getPassword());
            m.addAttribute("passwordCheck", employee.getPassword());
          //↑updateFormの各入力欄に対し、取得したUpDateクラスのデータをセットする文たち。
			//m.addAttribute("employee",employee);
		}
		//↓更新内容確認画面から更新内容入力画面へ戻る際のポスト処理。ID以外の入力がある場合はこちらが処理されるように誘導し、入力内容を保持したままの遷移を可能に。
		else {
		    m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("start_date", start_date);
	        m.addAttribute("end_date", end_date);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
		}
		return "updateForm";
		
	}
	
	@PostMapping("/updateCheck")
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
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = (null);
		Date endDate = (null);

		try {
		    startDate = dateFormat.parse(start_date);
		    endDate = dateFormat.parse(end_date);
		}catch(ParseException e){
			e.printStackTrace();
		}
		//↑String型のstart_dateとend_dateをDate型へ変換する処理。parseメソッドで変更する際はtry/catchで例外処理を行う必要があるとのこと。
		 m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("startDate", startDate);
	        m.addAttribute("endDate", endDate);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
		
		m.addAttribute("msg", "こちらで更新します。よろしいですか？");
		return "updateCheck";
	}
	
	@PostMapping("/updateResult")
	public String updateResult(
			Model m,
			@RequestParam("id") int id,
			@RequestParam("name") String name,
			@RequestParam("age") int age,
			@RequestParam("start_date") String start_date,
			@RequestParam("end_date") String end_date,
			@RequestParam("password") String password
	)
	{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
		    startDate = dateFormat.parse(start_date);
		    endDate = dateFormat.parse(end_date);
		}catch(ParseException e){
			e.printStackTrace();
		}

		UpDate employee = new UpDate(id, name, age, startDate, endDate, password);
		service.update(employee);
		 m.addAttribute("msg", "社員情報の更新が完了しました。");
		 return "updateResult";	
		
	}	

}
