/**
 * 
 */
document.addEventListener("DOMContentLoaded", function() {
	    // エラーメッセージを取得
		const errorMessageElements = document.querySelectorAll("[DataErrorMessage]");
		const id = document.getElementById("id").value.trim();
		
		let messages = [];
		errorMessageElements.forEach(el => {
			let message = el.getAttribute("DataErrorMessage");
			if(message && message.trim() !== ""){
				messages.push(message);
			}
		});
		
		if(messages.length > 0){
			alert(messages.join("\n"));
			}
		});