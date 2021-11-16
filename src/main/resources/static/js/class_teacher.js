/*　先生側　*/
var stompClgient = null;　// Websocket接続用変数
let recognition = ''; // 音声認識クラス変数
let finalTranscript = ''; // 確定した認識結果
var flag_speech = ''; // 音声認識最中かを判断するフラグ
var i = 0; // 認識数

/* ロード処理 */
$(document).ready(() => {
	
	// アクセスするエンドポイントを設定
    var socket = new SockJS('/socket_endpoint');
    stompClient = Stomp.over(socket);
    
    // エンドポイントに対して接続
    stompClient.connect({}, function (frame) {
    	// 音声認識、送信
        vr_function();
        // 受信
        stompClient.subscribe('/topic/voice_recog', function (response_data) {
        	i++;
        	console.log(i + "回目データ受け取り",JSON.parse(response_data.body).voicetext);	
        	showGreeting(JSON.parse(response_data.body).voicetext);
        });
        
        // 自信のセッションidを取得
    	const session_id = document.socket_form.sendername.value;
        stompClient.send("/socket_prefix/send_sessionid", {}, JSON.stringify({'session_id': session_id}));
        // 出席学生の情報取得
        GetNotice();
    });
})

/* 処理 */
// 授業内問題送信処理
function sendMessage() {
	stompClient.send("/socket_prefix/send_answer", {}, JSON.stringify({
		'issue' : $("#issue").val(),
		'answer' : $("#answer").val(),
	}));
	
	// ボタンタグ生成
	var newTag = document.createElement('button');
	newTag.value= 'SYNCER';
	newTag.id= 'situation_button';
	newTag.onclick = 'situation_Answer()';
	// let innerTag = `<button id="situation_button"
	// th:onclick="situation_Answer();">test</button>`;
	// $("#situation_buttonlist").append(innerTag);
	var addData = document.createTextNode("1");
	// //対象要素.apeendChild(追加する要素);
	// console.log('333');
		newTag.appendChild(addData);
	// //newTag.classList.add();
	// //表示する場所の取得
	// console.log('444');
	var addPlace = document.getElementById("situation_buttonlist");
	addPlace.appendChild(newTag);
	// //document.getElementById('situation_button').value = "SYNCER" ;
	// console.log('$$$$', document.getElementById('situation_button').value);
}

// 通知受け取り処理
function GetNotice(){
		// エンドポイントに接続
		var socket = new SockJS('/socket_endpoint');
		stompClient = Stomp.over(socket);
		
		// エンドポイントに対して接続
		stompClient.connect({}, function(frame) {
			// 受信
			// ここで、「1P1」か「1対多」かの指定を行っている
			stompClient.subscribe('/user/queue/notice', function(response_data) {
				// 表示メソッドにデータを渡す
		    	  ShowStudent(JSON.parse(response_data.body).student_name,JSON.parse(response_data.body).student_classno);
			});
		});
}

// 音声認識処理
function vr_function(){
	// Chromeの音声認識の対応付け、オブジェクト生成
	SpeechRecognition = webkitSpeechRecognition || SpeechRecognition;
	recognition = new SpeechRecognition();

	// 言語の指定(日本語)
	recognition.lang = 'ja-JP';
	// 認識している途中にも結果を得るよう設定
	recognition.interimResults = true;
	// 認識しっぱなしにする
	recognition.continuous = true;
	// 音声認識開始
	recognition.start();
	
 	recognition.onsoundstart = function() {
        console.log("認識中");
    };
    recognition.onnomatch = function() {
    	console.log("もう一度試してください");
    };
    recognition.onerror = function() {
    	// 音声認識中なら関数を呼び出す
        if(flag_speech == 0){
          vr_function();
        }  
    };
    // 音声認識検出終了
    recognition.onsoundend = function() {
    	console.log("停止中");
    	// 繰り返す
    	vr_function();
    };
    
	// 音声認識開始
  	recognition.onresult = (event) => {
  		// 暫定の認識結果変数
    	let interimTranscript = '';
  		
    	for (let i = event.resultIndex; i < event.results.length; i++) {
      		let transcript = event.results[i][0].transcript;
      		if (event.results[i].isFinal)
            {
      			finalTranscript += transcript;
      			vr_function();
            }
            else
            {
            	interimTranscript = transcript;
                flag_speech = 1;
            }
    	}
  		
  		// 送信(送信先のパス、データの設定)
    	stompClient.send("/socket_prefix/voice_recog", {}, JSON.stringify({'voicetext': finalTranscript}));
    	// テキストログ
  		console.log('##websocketに送信しました##',finalTranscript);
  		// 初期化
    	finalTranscript = '';
    	interimTranscript = '';
    	flag_speech = 0;
  	}
  	
}

/* 表示 */
function ShowModal(){
	// 表示中のページと最終ページ番号
	var page, max = 2;

	$(function() {
		// Modalオープンボタン
		page = 1;
		drawModal();
		$("#myModal").modal("show");

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
	});
}

// 解答状況表示
function situation_Answer() {
	console.log("解答状況閲覧　「到達」");
	$("#myModal").modal("show");
}

// 授業出席学生の表示
function ShowStudent(student_name,student_classno){
	$("#student_list").append(student_classno + student_name);
}

// 字幕表示
function showGreeting(message) {
	$("#result-div").append("<tr><td>" + message + "</td></tr>");
}