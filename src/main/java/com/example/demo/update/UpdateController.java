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
	
	//↓社員ID検索画面
	@RequestMapping("/idSearch")
	public String idSearch(
			Model m,
			@RequestParam(value = "id", required = false) Integer id//「Integer」でnullを許容。
			//↑「required = false」を記述し、パラメーターが無くてもnullが代入されるようにしている。(@RequestParamではデフォルトだと値の値が必須で、何も無いとエラーになる為。)
			//入力画面から戻るボタンを使ってID検索画面へ戻る際のIDポスト処理で使用。
			) {
		if(id != null) {
		List<UpDate> employeeList = service.selectById(id);
		
	    if (employeeList.isEmpty()) {
	    	m.addAttribute("id", id);
	        m.addAttribute("error", "入力された社員IDと一致するデータが見つかりませんでした。");
	      //IDの入力チェック
		    if (id == null || id <= 0) {
		        m.addAttribute("error", "IDは1以上の数字で入力してください。");
		        return "idSearch";
		    }
	      }
	    }
		m.addAttribute("id", id);
		return "idSearch";
	}
	//↓updateFormの戻るボタン処理(idSearchへリダイレクトさせる)
	@PostMapping("idSearch")
	public String idSearch(
			RedirectAttributes r,
			@RequestParam("id") Integer id
			) {
		 r.addFlashAttribute("id", id);
		 return "redirect:/idSearch";
	}
	
	
	//↓社員IDを検索し社員情報入力画面へ
	@PostMapping("/updateForm")
	public String updateForm(
			Model m, 
			RedirectAttributes r,
			@RequestParam(value = "id", required = false) Integer id//「Integer」でnullを許容。
			//「required = false」を記述し、パラメーターが無くてもnullが代入されるようにしている。(@RequestParamではデフォルトだと値の値が必須で、何も無いとエラーになる為。)
			//idSearchで入力するのはID(id)のみで、名前(name)等はリクエストに含まれない(どうしてもパラメーター無しになりエラーに繋がる)が、nullが代入されることでエラーを避けられる。
			//「value = ""」は「required = false」を用いる際は記述しないと警告文が発生した。
			) {

			List<UpDate> employeeList = service.selectById(id);
			
			 if(employeeList.size() == 0) {
                 employeeList = null;
                 r.addFlashAttribute("error", "入力された社員IDと一致するデータが見つかりませんでした。");
                 if (id != null) {
                   r.addFlashAttribute("id", id);
                 }
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
            return "updateForm";
		}
	
	//更新内容確認画面
	@PostMapping("/updateCheck")
	public String updateCheak(
			Model m,
			RedirectAttributes r,
			@RequestParam("id") int id,
			@RequestParam("name") String name,
			@RequestParam("age") String age,
			@RequestParam("start_date") String start_date,
			@RequestParam("end_date") String end_date,
			@RequestParam("password") String password,
			@RequestParam("passwordCheck") String passwordCheck
			) {
		
		 try {
	            Integer.parseInt(age);
	        } catch (NumberFormatException e) {
	            m.addAttribute("AgeError", "年齢は数値で入力してください。");
	            return "updateForm";
	        }
		
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
	
	//更新処理及び更新完了画面
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
