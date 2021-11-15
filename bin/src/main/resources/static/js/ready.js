/*　先生側ページ遷移後処理　*/
var stompClgient = null;　// Websocket接続用変数

$(document).ready(() => {
	//自信のセッションidを取得
	const session_id = document.socket_form.sendername.value;
  
	// アクセスするエンドポイントを設定
	var socket = new SockJS('/socket_endpoint');
	stompClient = Stomp.over(socket);
	// エンドポイントに対して接続
	stompClient.connect({}, function (frame) {
		stompClient.send("/socket_prefix/send_sessionid", {}, JSON.stringify({'session_id': session_id}));
		console.log('セッションid送信しました');
	    // 受信
	    stompClient.subscribe('/topic/send_sessionid', function (response_data) {
	    	// 表示メソッドにデータを渡す
	    	ShowTeacherSession(JSON.parse(response_data.body).session_id);
	    	// 接続を遮断
	    	//stompClient.disconnect();
	      });
	   })
})

// 表示(セッションid)
function ShowTeacherSession(teacher_sessionid){
	//if(class_id == teacher_classid){
		$("#send_username").append(teacher_sessionid);
	//}else{
		//console.log("クラスidが違います");
	//}
		GetNotice();
}

//通知受け取り
function GetNotice(){
		// エンドポイントに接続
		var socket = new SockJS('/socket_endpoint');
		stompClient = Stomp.over(socket);
		
		// エンドポイントに対して接続
		stompClient.connect({}, function(frame) {
			//受信
			// ここで、「1P1」か「1対多」かの指定を行っている
			stompClient.subscribe('/user/queue/notice', function(response_data) {
				// 表示メソッドにデータを渡す
		    	  ShowStudent(JSON.parse(response_data.body).student_name,JSON.parse(response_data.body).student_classno);
			});
		});
}

//表示(生徒)
function ShowStudent(student_name,student_classno){
	$("#student_list").append(student_classno + student_name);
}