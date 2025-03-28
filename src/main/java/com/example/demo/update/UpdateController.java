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
	
	//社員ID検索画面
	@RequestMapping("/idSearch")
	public String idSearch(
			Model m,
			@RequestParam(value = "id", required = false) String id//「Integer」でnullを許容。
			//「required = false」を記述し、パラメーターが無くてもnullが代入されるようにしている。(@RequestParamではデフォルトだと値の値が必須で、何も無いとエラーになる為。)
			//入力画面から戻るボタンを使ってID検索画面へ戻る際のIDポスト処理で使用。
			) {
		m.addAttribute("id",id);
		
		try {
			 Integer.parseInt(id); 
	        } catch (NumberFormatException e) {
	        	m.addAttribute("id", id);
	            return "idSearch";
	        }
		
		//入力された社員IDに該当するデータが存在しなかった際のメッセージ表示 
		List<UpDate> employeeList = service.selectById(id);	
		    if(employeeList.size() == 0) {
                employeeList = null;
                m.addAttribute("id", id);
				return "idSearch";
			}
		
		m.addAttribute("id", id);
		return "idSearch";
	}
	
	//updateFormの戻るボタン処理(idSearchへリダイレクトさせる)
	@PostMapping("idSearch")
	public String idSearch(
			Model m,
			RedirectAttributes r,
			@RequestParam("id") String id
			) {
		m.addAttribute("id", id);
		 r.addFlashAttribute("id", id);
		 return "idSearch";
	}
	
	
	//↓社員IDを検索し社員情報入力画面へ
	@PostMapping("/updateForm")
	public String updateForm(
			Model m, 
			RedirectAttributes r,
			@RequestParam(value = "id", required = false) String id//「Integer」でnullを許容。
			//「required = false」を記述し、パラメーターが無くてもnullが代入されるようにしている。(@RequestParamではデフォルトだと値の値が必須で、何も無いとエラーになる為。)
			//idSearchで入力するのはID(id)のみで、名前(name)等はリクエストに含まれない(どうしてもパラメーター無しになりエラーに繋がる)が、nullが代入されることでエラーを避けられる。
			//「value = ""」は「required = false」を用いる際は記述しないと警告文が発生した。

			) {
		//社員idの入力チェック・バリデーション
		 try {
			 Integer.parseInt(id); 
	        } catch (NumberFormatException e) {
	        	m.addAttribute("id", id);
	            r.addFlashAttribute("IdError", "社員IDは数値で入力してください。");
	            return "redirect:/idSearch";
	        }
		
		//入力された社員IDに該当するデータが存在しなかった際のメッセージ表示 
		List<UpDate> employeeList = service.selectById(id);	
		    if(employeeList.size() == 0) {
                 employeeList = null;
                 m.addAttribute("id", id);
                 r.addFlashAttribute("error", "社員ID【" + id + "】に該当する社員のデータが見つかりませんでした。");
				return "redirect:/idSearch";
			}
		    
		  //employeeテーブルからデータを取得するList<UpDate>から最初の要素を取得する文。UpDateオブジェクトを取得する。employeeにはUpDateクラスのデータが格納される。
			UpDate employee = employeeList.get(0);
			 //updateFormの各入力欄に対し、取得したUpDateクラスのデータをセットする文たち。
            m.addAttribute("id", employee.getId());
            m.addAttribute("name", employee.getName());
            m.addAttribute("age", employee.getAge());
            m.addAttribute("start_date", employee.getStart_date());
            m.addAttribute("end_date", employee.getEnd_date());
            m.addAttribute("password", employee.getPassword());
            m.addAttribute("passwordCheck", employee.getPassword()); 
            return "updateForm";
		}
	
	//更新内容確認画面
	@PostMapping("/updateCheck")
	public String updateCheak(
			Model m,
			RedirectAttributes r,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "age", required = false) String age,
			@RequestParam(value = "start_date", required = false) String start_date,
			@RequestParam(value = "end_date", required = false) String end_date,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "passwordCheck", required = false) String passwordCheck
			) {
		
		//エラーメッセージ表示時にも各フォームに入力されていた内容を保持する為の記述。
		 m.addAttribute("id", id);
		 m.addAttribute("name", name);
		 m.addAttribute("age", age);
		 m.addAttribute("start_date", start_date);  
		 m.addAttribute("end_date", end_date);     
		 m.addAttribute("password", password);
		 m.addAttribute("passwordCheck", passwordCheck);

		//String型のstart_dateとend_dateをDate型へ変換する処理。parseメソッドで変更する際はtry/catchで例外処理を行う必要があるとのこと。
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = (null);
		Date endDate = (null);

		try {
		    startDate = dateFormat.parse(start_date);
		    endDate = dateFormat.parse(end_date);
		}catch(ParseException e){
			e.printStackTrace();
		}

		//社員idの入力チェック・バリデーション
		try {
			 Integer.parseInt(id); 
	        } catch (NumberFormatException e) {
	        	m.addAttribute("id", id);
		        m.addAttribute("name", name);
		        m.addAttribute("age", age);
		        m.addAttribute("startDate", startDate);
		        m.addAttribute("endDate", endDate);
		        m.addAttribute("password", password);
		        m.addAttribute("passwordCheck", passwordCheck);
	            m.addAttribute("IdError", "数値で入力してください。");
	            return "updateForm";
	        }
		
		//名前の入力チェック・バリデーション
	    if (name == null || name.trim().isEmpty() || name.length() > 50 || !name.matches("^[ぁ-んァ-ヶ一-龠々]+$")) {
	    	m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("startDate", startDate);
	        m.addAttribute("endDate", endDate);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
	        m.addAttribute("NameError", "日本語で入力してください。");
	        return "updateForm";
	    }
		//年齢の入力チェック・バリデーション
		 try {
	            Integer.parseInt(age);
	        } catch (NumberFormatException e) {
	        	m.addAttribute("id", id);
		        m.addAttribute("name", name);
		        m.addAttribute("age", age);
		        m.addAttribute("startDate", startDate);
		        m.addAttribute("endDate", endDate);
		        m.addAttribute("password", password);
		        m.addAttribute("passwordCheck", passwordCheck);
	            m.addAttribute("AgeError", "数値で入力してください。");
	            return "updateForm";
	        }
		 
		//パスワードの入力チェック（英数字8文字以上・確認用と一致）
	    if (password == null || !password.matches("^[a-zA-Z0-9]+$") || password.length() < 8) {
	    	m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("startDate", startDate);
	        m.addAttribute("endDate", endDate);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
		     m.addAttribute("PwError", "8文字以上の英数字で入力してください。");
		     return "updateForm";
		    }
		if (!password.equals(passwordCheck)) {
			m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("startDate", startDate);
	        m.addAttribute("endDate", endDate);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
		     m.addAttribute("PwcError", "確認用パスワードが一致しません。");
		     return "updateForm";
		    }
		
		//開始日・終了日の形式チェック
	    if (start_date == null || !start_date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	    	m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("startDate", startDate);
	        m.addAttribute("endDate", endDate);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
	        m.addAttribute("SdError", "開始日は YYYY-MM-DD 形式で入力してください。");
	        return "updateForm";
	    }
	    if (end_date != null && !end_date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	    	m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("startDate", startDate);
	        m.addAttribute("endDate", endDate);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
	        m.addAttribute("EdError", "終了日は YYYY-MM-DD 形式で入力してください。");
	        return "updateForm";
	    }
		

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
			@RequestParam("id") String id,
			@RequestParam("name") String name,
			@RequestParam("age") String age,
			@RequestParam("start_date") String start_date,
			@RequestParam("end_date") String end_date,
			@RequestParam("password") String password,
			@RequestParam("passwordCheck") String passwordCheck
	)
	{
		//エラーメッセージ表示時にも各フォームに入力されていた内容を保持する為の記述。
		 m.addAttribute("id", id);
		 m.addAttribute("name", name);
		 m.addAttribute("age", age);
		 m.addAttribute("start_date", start_date);  
		 m.addAttribute("end_date", end_date);     
		 m.addAttribute("password", password);
		 m.addAttribute("passwordCheck", passwordCheck);
		
		//String型のstart_dateとend_dateをDate型へ変換する処理。parseメソッドで変更する際はtry/catchで例外処理を行う必要があるとのこと。
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
		    startDate = dateFormat.parse(start_date);
		    endDate = dateFormat.parse(end_date);
		}catch(ParseException e){
			e.printStackTrace();
		}
		
		//社員idの入力チェック・バリデーション
		try {
			 Integer.parseInt(id); 
		} catch (NumberFormatException e) {        	
			m.addAttribute("id", id);
			m.addAttribute("name", name);
			m.addAttribute("age", age);
			m.addAttribute("startDate", startDate);
			m.addAttribute("endDate", endDate);
			m.addAttribute("password", password);
			m.addAttribute("passwordCheck", passwordCheck);
			m.addAttribute("IdError", "数値で入力してください。");
			return "updateForm";
		}
		
		//名前の入力チェック・バリデーション
	    if (name == null || name.trim().isEmpty() || name.length() > 50 || !name.matches("^[ぁ-んァ-ヶ一-龠々]+$")) {
	    	m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("startDate", startDate);
	        m.addAttribute("endDate", endDate);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
	        m.addAttribute("NameError", "日本語で入力してください。");
	        return "updateForm";
	    }
		//年齢の入力チェック・バリデーション
		 try {
	            Integer.parseInt(age);
	        } catch (NumberFormatException e) {
	        	m.addAttribute("id", id);
		        m.addAttribute("name", name);
		        m.addAttribute("age", age);
		        m.addAttribute("startDate", startDate);
		        m.addAttribute("endDate", endDate);
		        m.addAttribute("password", password);
		        m.addAttribute("passwordCheck", passwordCheck);
	            m.addAttribute("AgeError", "数値で入力してください。");
	            return "updateForm";
	        }
		 
		//パスワードの入力チェック（英数字8文字以上・確認用と一致）
	    if (password == null || !password.matches("^[a-zA-Z0-9]+$") || password.length() < 8) {
	    	m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("startDate", startDate);
	        m.addAttribute("endDate", endDate);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
		     m.addAttribute("PwError", "8文字以上の英数字で入力してください。");
		     return "updateForm";
		    }
		if (!password.equals(passwordCheck)) {
			m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("startDate", startDate);
	        m.addAttribute("endDate", endDate);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
		     m.addAttribute("PwcError", "確認用パスワードが一致しません。");
		     return "updateForm";
		    }
		
		//開始日・終了日の形式チェック
	    if (start_date == null || !start_date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	    	m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("startDate", startDate);
	        m.addAttribute("endDate", endDate);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
	        m.addAttribute("SdError", "開始日は YYYY-MM-DD 形式で入力してください。");
	        return "updateForm";
	    }
	    if (end_date != null && !end_date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	    	m.addAttribute("id", id);
	        m.addAttribute("name", name);
	        m.addAttribute("age", age);
	        m.addAttribute("startDate", startDate);
	        m.addAttribute("endDate", endDate);
	        m.addAttribute("password", password);
	        m.addAttribute("passwordCheck", passwordCheck);
	        m.addAttribute("EdError", "終了日は YYYY-MM-DD 形式で入力してください。");
	        return "updateForm";
	    }

		UpDate employee = new UpDate(id, name, age, startDate, endDate, password);
		service.update(employee);
		 m.addAttribute("msg", "社員情報の更新が完了しました。");
		 return "updateResult";	
		
	}	

}
