/**
 * 
 */
document.getElementById("idSearch").addEventListener("submit",function(e){
	const id = document.getElementById("id").value;
	
	if(!id.trim()){ //trim()を使うことで、入力フィールドにスペース(空白)だけが入っている場合もエラーにできる(空白を空文字とし、未入力として扱えるようになる)。
		alert("IDが入力されていません。")
		e.preventDefault()
		return;
	  }
	
	})
	
	function formReset() {
		document.getElementById("idSearch").reset();
		
		document.getElementById("id").value = "";
		
	}	