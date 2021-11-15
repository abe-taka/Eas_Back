/*　生徒側ページ遷移後処理　*/

var stompClient = null; // Websocket接続用変数
const class_id = null; //クラスid
//var second_flag = true;

//セッションid受信
$(document).ready(() => {
	const session_id = document.socket_form.sendername.value;
  
	// アクセスするエンドポイントを設定
	var socket = new SockJS('/socket_endpoint');
	stompClient = Stomp.over(socket);
	// エンドポイントに対して接続
	stompClient.connect({}, function (frame) {
		// 受信
	    stompClient.subscribe('/topic/send_sessionid', function (response_data) {
	    	  console.log("先生側セッション受信",JSON.parse(response_data.body).session_id);
	    	  // 表示メソッドにデータを渡す
	    	  ShowTeacherSession(JSON.parse(response_data.body).session_id);
	    	  // 接続を遮断
	    	  //stompClient.disconnect();
	      });
	   })
})

// 先生側セッションid表示
function ShowTeacherSession(teacher_sessionid){
	//if(class_id == teacher_classid){
		$("#send_username").append(teacher_sessionid);
		SendToNotice(teacher_sessionid);
	//}else{
		//console.log("クラスidが違います");
	//}
}

//先生側に授業に来たことを知らせる
function SendToNotice(teacher_sessionid){
	const student_name = document.getElementById('student_name').value;
	const student_classno = document.getElementById('student_classno').value;
	
	// アクセスするエンドポイントを設定
	var socket = new SockJS('/socket_endpoint');
	stompClient = null;
	stompClient = Stomp.over(socket);
	// エンドポイントに対して接続
	stompClient.connect({}, function (frame) {
		stompClient.send("/socket_prefix/send_notice", {}, JSON.stringify({'student_name':student_name,'student_classno':student_classno,'teacher_sessionid':teacher_sessionid}));
	    console.log("先生側に通知しました");
	})
}

//音声認識取得
function get_voice_recog(){
	var socket = new SockJS('/socket_endpoint');
	stompClient = Stomp.over(socket);
	// エンドポイントに対して接続
	stompClient.connect({}, function (frame) {
	    // 受信
	    stompClient.subscribe('/topic/voice_recog', function (response_data) {
	    	//ログ
	    	//データが送られて来た場合
//	    	if(JSON.parse(response_data.body).voicetext != ""){
//	    		//1回目の表示の時
//	    		if(second_flag == true){
	    			//表示
	    			showGreeting(JSON.parse(response_data.body).voicetext);
//	    			//if(){}先生と学生の判別条件をここに書く 
//	    			second_flag = false;
//	    		}
//	    		//2回目の場合
//	    		else{
//	    			second_flag = true;
//	    		}
//	    	}
	    });
	});
}

//字幕表示
function showGreeting(message) {
	$("#result-div").append("<tr><td>" + message + "</td></tr>");
}