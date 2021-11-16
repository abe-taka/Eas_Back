/* 授業内問題 */
var stompClient = null;　// Websocket接続変数
var issue_answer = null;　//授業内問題解答の保持変数

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

// 接続
function connect() {
	// エンドポイントに接続
	var socket = new SockJS('/socket_endpoint');
	stompClient = Stomp.over(socket);
	
	// エンドポイントに対して接続
	stompClient.connect({}, function(frame) {
		setConnected(true);
		//受信
		stompClient.subscribe('/user/queue/greetings', function(response_message) {
			showMessage(JSON.parse(response_message.body).issue,JSON.parse(response_message.body).answer);
		});
	});
}

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

// 送信(送信先のパス、データの設定)
function sendMessage() {
	stompClient.send("/socket_prefix/send_answer", {}, JSON.stringify({
		'issue' : $("#issue").val(),
		'answer' : $("#answer").val(),
	}));
	
	//ボタンタグ生成
	var newTag = document.createElement('button');
	newTag.value= 'SYNCER';
	newTag.id= 'situation_button';
	newTag.onclick = 'situation_Answer()';
	//let innerTag = `<button id="situation_button" th:onclick="situation_Answer();">test</button>`;
	//$("#situation_buttonlist").append(innerTag);
	var addData = document.createTextNode("1");
//	//対象要素.apeendChild(追加する要素);
//	console.log('333');
	newTag.appendChild(addData);
//	//newTag.classList.add();
//	//表示する場所の取得
//	console.log('444');
	var addPlace = document.getElementById("situation_buttonlist");
	addPlace.appendChild(newTag);
//	//document.getElementById('situation_button').value = "SYNCER" ;
//	console.log('$$$$', document.getElementById('situation_button').value); 
}

// modal表示
function showMessage(issue,answer) {
	// id「show」に対して変数「message」をセットする
	
	$("#show").append("<tr><td>" + issue + "</td></tr>");
	issue_answer = answer
	// Modalオープンボタン
	// 表示中のページと最終ページ番号
	var page, max = 2;
	page = null;
	page = 1;
	
	// 次へボタン
	$(".btnNext").click(function() {
		page++;
		drawModal();
	});

	// 前へボタン
	$(".btnPrev").click(function() {
		page--;
		drawModal();
	});

	// Modal内表示
	function drawModal() {
		for (var i = 1; i <= max; i++) {
			if (i == page)
				$("#modal-page" + i).show()
			else
				$("#modal-page" + i).hide()
		}
	}
	
	$("#myModal").modal("show");
}

// 解答送信
function sendAnswer() {
	const answer_value = document.getElementById('answer').value;
	console.log("answer_value" + answer_value);
	console.log("issue_answer" + issue_answer);
	if(answer_value == issue_answer){
		console.log("正しい");
		$("#answer_result").append("正解");
	}else{
		console.log("不正解");
		$("#answer_result").append("不正解");
		$("#result_value").append("正解は" + issue_answer + "です。");
	}
}

function situation_Answer() {
	console.log("解答状況閲覧　「到達」");
	$("#myModal").modal("show");
}

//ボタンクリック処理
$(function() {
	// リロード無効化
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	// 接続
	$("#connect").click(function() {
		connect();
	});
	// 遮断
	$("#disconnect").click(function() {
		disconnect();
	});
	// 授業内問題送信
	$("#send").click(function() {
		sendMessage();
	});
	// 解答送信
	$("#send_answer").click(function() {
		sendAnswer();
	});
	//解答状況閲覧
	$("#situation_button").click(function() {
		situation_Answer();
	});
});