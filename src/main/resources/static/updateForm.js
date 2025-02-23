/**
 * 
 */
document.getElementById("updateForm").addEventListener("submit",function(e){
	const id = document.getElementById("id").value;
	const name = document.getElementById("name").value;
	const age = document.getElementById("age").value;
	const password = document.getElementById("password").value;
	const passwordCheck = document.getElementById("passwordCheck").value;
	const start_date = document.getElementById("start_date").value;
	
	if(!id.trim()){ //trim()を使うことで、入力フィールドにスペース(空白)だけが入っている場合もエラーにできる(空白を空文字とし、未入力として扱えるようになる)。
		alert("IDが入力されていません。")
		e.preventDefault()
		return;
	}
	
	if(!name.trim()){ 
		alert("名前が入力されていません。");
		e.preventDefault();
		return;
	}
	
	if(!age.trim()){
		alert("年齢が入力されていません。");
		e.preventDefault();
		return;
	}
	
	if(!password.trim()){
		alert("パスワードが入力されていません。");
		e.preventDefault();
		return;
	}
	
	if(!/^(?=.*[A-Za-z])(?=.*\d).{8,}$/.test(password)){
		alert("パスワードは半角英字と半角数字の両方を使用してください。");
		e.preventDefault();
		return;
	}
	
	if(password !== passwordCheck){
		alert("パスワードが一致しません。");
		e.preventDefault();
		return;
	}
	
	if(!start_date.trim()){
		alert("開始日が入力されていません。");
		e.preventDefault();
		return;
	}
		
	
})

function formReset() {
	document.getElementById("updateForm").reset();
	
	document.getElementById("id").value = "";
	document.getElementById("name").value = "";
	document.getElementById("age").value = "";
	document.getElementById("password").value = "";
	document.getElementById("passwordCheck").value = "";
	document.getElementById("start_date").value = "";
	document.getElementById("end_date").value = "";
}

