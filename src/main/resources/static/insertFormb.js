// ページが読み込まれたときにエラーメッセージがあればポップアップを表示する
window.onload = function() {
    var errorMessage = document.getElementById("errorMessage").innerText;
    if (errorMessage) {
        alert(errorMessage);  // エラーメッセージがあればポップアップ表示
        
    }
};
