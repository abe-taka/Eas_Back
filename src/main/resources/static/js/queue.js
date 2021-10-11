// Websocket接続変数
var stompClient = null;

// connectボタンのデザイン設定
function setConnected(connected) {
	// trueなら「connect」ボタン、falseなら「connect」ボタンをクリック無効にする
	// connectボタンを押すとtrueになる、disconnectボタンを押すとfalseになる
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);

	// trueなら表示部分を表示
	if (connected) {
		$("#conversation").show();
	}
	// falseならなら表示部分を隠す
	else {
		$("#conversation").hide();
	}
}

// 「1」
// 接続
function connect() {
	// エンドポイントに接続
	var socket = new SockJS('/socket_endpoint');
	stompClient = Stomp.over(socket);
	
	// エンドポイントに対して接続
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('接続しました ' + frame);
		//受信
		// ここで、「1P1」か「1対多」かの指定を行っている
		stompClient.subscribe('/user/queue/greetings', function(response_message) {
			// 結果を受け取り、表示機能に渡す
			// 最後の「.message」がSocketMessageクラスのgetMessage()だと思われる
			console.log("##受信データ##", JSON.parse(response_message.body).message);
			showMessage(JSON.parse(response_message.body).message);
		});
	});
}

// 「2」
// 接続の遮断
function disconnect() {
	// 接続中なら切断する
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	// 「disconnectボタン」のクリック無効、表示部分を隠す
	setConnected(false);
	console.log("通信遮断");
}

// 「3」
// 送信(送信先のパス、データの設定)
function sendMessage() {
	stompClient.send("/socket_prefix/send_answer", {}, JSON.stringify({
		'name' : $("#name").val(),
		'message' : $("#message").val(),
		'sendtoname' : $("#send_username").val()
	}));
	// 送信後、id「show」に対して中身を空にする
	$("#message").val('');
}

// 表示
function showMessage(message) {
	// id「show」に対して変数「message」をセットする
	$("#show").append("<tr><td>" + message + "</td></tr>");
}

//ボタンクリック処理
$(function() {
	// formの機能である、「遷移先のURLを定義していない場合にその場のページをリロードする」これを無効化する → 非同期処理を可能にする
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	// 「1」の処理が走る
	$("#connect").click(function() {
		connect();
	});
	// 「2」の処理が走る
	$("#disconnect").click(function() {
		disconnect();
	});
	// 「3」の処理が走る
	$("#send").click(function() {
		sendMessage();
	});
});