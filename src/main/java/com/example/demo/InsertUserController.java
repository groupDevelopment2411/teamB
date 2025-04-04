package com.example.demo;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class InsertUserController {
    @Autowired
    private InsertUserService service;

    @RequestMapping("/insertFormb")
    public String insertFormb(Model model) {
        return "insertFormb";
    }

    @PostMapping("/insert")
    public String insertUser(
            Model m,
            @RequestParam("name") String name,
            @RequestParam("age") String ageStr,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2
    ) {
        if (name == null || name.isEmpty()) { 
            m.addAttribute("errorMessage", "社員名は必須入力です。");
            return "insertFormb";
        }

        if (ageStr == null || ageStr.isEmpty()) {
            m.addAttribute("errorMessage", "年齢は必須入力です。");
            return "insertFormb";
        }

        if (password == null || password.isEmpty()) { 
            m.addAttribute("errorMessage", "パスワードは必須入力です。");
            return "insertFormb";
        }

        if (!isValidPassword(password)) {
            m.addAttribute("errorMessage", "パスワードは半角英数字のみ、大文字を含み、8文字以上である必要があります。");
            return "insertFormb";
        }

        Integer age = null;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            m.addAttribute("errorMessage", "年齢は数値で入力してください。");
            return "insertFormb";
        }

        if (age < 0) {
            m.addAttribute("errorMessage", "年齢は0以上の数値である必要があります。");
            return "insertFormb";
        }

        if (!password.equals(password2)) {
            m.addAttribute("errorMessage", "パスワードが一致しません。");
            return "insertFormb";
        }

        InsertUser insertuser = new InsertUser(name, age, password, password2);
        service.insert(insertuser);

        m.addAttribute("msg", "登録が正常に完了しました");
        return "resultb";
    }

    @PostMapping("/confirm")
    public String confirmUser(
            HttpSession session,
            @RequestParam("name") String name,
            @RequestParam(value = "age", required = true) String ageStr,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            Model model
    ) {
        model.addAttribute("name", name);
        model.addAttribute("age", ageStr);
        model.addAttribute("password", password);
        model.addAttribute("password2", password2);

        if (name == null || name.isEmpty()) { 
            model.addAttribute("errorMessage", "社員名は必須入力です。");
            return "insertFormb";
        }

        if (ageStr == null || ageStr.isEmpty()) {
            model.addAttribute("errorMessage", "年齢は必須入力です。");
            return "insertFormb";
        }

        if (password == null || password.isEmpty()) { 
            model.addAttribute("errorMessage", "パスワードは必須入力です。");
            return "insertFormb";
        }

        Integer age = null;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "年齢は数値で入力してください。");
            return "insertFormb";
        }

        if (age < 0) {
            model.addAttribute("errorMessage", "年齢は0以上の数値である必要があります。");
            return "insertFormb";
        }

        if (!isValidPassword(password)) {
            model.addAttribute("errorMessage", "パスワードは半角英数字のみ、大文字を含み、8文字以上である必要があります。");
            return "insertFormb";
        }

        if (!password.equals(password2)) {
            model.addAttribute("errorMessage", "パスワードが一致しません。");
            return "insertFormb";
        }

        return "confirm";
    }

    @PostMapping("/goBack")
    public String goBack(@RequestParam String name, @RequestParam int age, 
                         @RequestParam String password, Model model) 
    {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        model.addAttribute("password", password);
        return "insertFormb";  
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)[A-Za-z0-9]{8,}$";
        return Pattern.matches(passwordRegex, password);
    }
}
