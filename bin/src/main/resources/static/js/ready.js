// Websocket接続用変数
var stompClient = null;

$(document).ready(() => {
  console.log('DOM is loaded')
  const session_id = document.socket_form.sendername.value;
  console.log("########",session_id);
  const flag = document.form_flag.flag.value;
  console.log("###flag###",flag);
  
// アクセスするエンドポイントを設定
  
	  var socket = new SockJS('/socket_endpoint');
	  stompClient = Stomp.over(socket);
	  // エンドポイントに対して接続
	  stompClient.connect({}, function (frame) {
	      console.log('Websocket接続: ' + frame);
	      if(flag == "true"){
	    	  stompClient.send("/socket_prefix/send_sessionid", {}, JSON.stringify({'session_id': session_id}));
	      }
	      // 受信
	      stompClient.subscribe('/topic/send_sessionid', function (response_data) {
	    	  // 表示メソッドにデータを渡す
	    	  ShowTeacherSession(JSON.parse(response_data.body).session_id);
	    	  // 接続を遮断
	    	  stompClient.disconnect();
	      });
	   })
})

// 表示
function ShowTeacherSession(sessionid){
	document.getElementById( "send_username" ).value = sessionid ;
	$("#send_username").append(sessionid);
}