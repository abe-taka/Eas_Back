/*　先生側　*/
var stompClgient = null;　// Websocket接続用変数
let recognition = ''; // 音声認識クラス変数
let finalTranscript = ''; // 確定した認識結果
var flag_speech = ''; // 音声認識最中かを判断するフラグ
var i = 0; // 認識数
var flag = 0; //解答状況閲覧ボタンtext

/* ロード処理 */
$(document).ready(() => {
	
	// CSRFトークンの取得
	const token = $("meta[name='_csrf']").attr("content");
	
	//クラスidの取得
	const classid = $("#classid").val();
	
	// 非同期通信
	// 入室の許可をする
	$.ajax({
		type : "POST",
		url : "/rest/update_enterflag",
		headers : {
			"X-CSRF-TOKEN" : token
		},
		data : {
			"js_classid" : classid
		}
	})
	.then(function(response_data){
		//成功時
		if(response_data != 0){
			console.log("成功");
			const element_timeid = document.getElementById("timeid");
			element_timeid.value = response_data;
		}
		else{
			console.log("失敗");
		}
	}
	,function(){
		//失敗時
		console.log('[$.ajax]"/rest/update_enterflag" Fail');
	});
	
	// アクセスするエンドポイントを設定
    var socket = new SockJS('/socket_endpoint');
    stompClient = Stomp.over(socket);
    
    // エンドポイントに対して接続
    stompClient.connect({}, function (frame) {
    	// 音声認識、送信
    	VoiceRecognition();
        // 音声認識受信
        stompClient.subscribe('/user/queue/voice_recog', function (response_data) {
        	i++;
        	console.log(i + "回目データ受け取り",JSON.parse(response_data.body).voicetext);	
        	ShowSubtitles(JSON.parse(response_data.body).voicetext);
        });
        
        // 出席学生の情報の受信
		stompClient.subscribe('/user/queue/notice', function(response_data) {
			// 表示メソッドにデータを渡す
		    ShowStudent(JSON.parse(response_data.body).student_name,JSON.parse(response_data.body).student_classno);
		});
		// 生徒の問題解答の受信
		stompClient.subscribe('/user/queue/student_answer', function(response_data) {
			// 表示メソッドにデータを渡す
		    ShowStudentAnswer(JSON.parse(response_data.body).studentname,JSON.parse(response_data.body).class_no,JSON.parse(response_data.body).answer);
		});

	    //入室許可の通知
	    stompClient.send("/socket_prefix/notice_enter", {}, JSON.stringify({'class_id': classid}));
    });
    
})

/* 処理 */
/* 授業画面退出時 */
window.onload = function(){
	window.onunload = function(){
		//退出ログ
		ReturnEnterFlag();
	}
}

//入室許可フラグを基に戻す
function ReturnEnterFlag(){
	// CSRFトークンの取得
	const token = $("meta[name='_csrf']").attr("content");
	//クラスid、timeidを取得
	const classid = $("#classid").val();
	const timeid = $("#timeid").val();
	
	$.ajax({
		type : "POST",
		url : "/rest/return_enterflag",
		headers : {
			"X-CSRF-TOKEN" : token
		},
		data : {
			js_classid : classid,
			js_timeid : timeid
		}
	})
	.then(function(response_data){
		//成功
	},function(){
		//失敗
	});
}

// 授業内問題送信処理
function SendIssue() {
	//問題の送信
	stompClient.send("/socket_prefix/send_answer", {}, JSON.stringify({
		'issue' : $("#issue").val(),
		'answer' : $("#answer").val(),
		'classid' : $("#classid").val()
	}));
	
	//解答状況閲覧用ボタン生成
	flag += 1;
	var btn = document.createElement('button');
	btn.textContent = '問題' + flag;
	btn.setAttribute("type", "button");
	btn.setAttribute("id", 'situation_button');
	btn.setAttribute('onclick', 'ShowSituationAnswer()');
	var addPlace = document.getElementById("situation_buttonlist");
	addPlace.appendChild(btn);
}

// 音声認識処理
function VoiceRecognition(){
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
        	VoiceRecognition();
        }  
    };
    // 音声認識検出終了
    recognition.onsoundend = function() {
    	console.log("停止中");
    	// 繰り返す
    	VoiceRecognition();
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
      			VoiceRecognition();
            }
            else
            {
            	interimTranscript = transcript;
                flag_speech = 1;
            }
    	}
  		
  		// 送信(送信先のパス、データの設定)
    	stompClient.send("/socket_prefix/voice_recog", {}, JSON.stringify({'voicetext': finalTranscript}));
  		// 初期化
    	finalTranscript = '';
    	interimTranscript = '';
    	flag_speech = 0;
  	}
  	
}

//一括退出
function Bulkexit(class_id){
	// アクセスするエンドポイントを設定
	var socket = new SockJS('/socket_endpoint');
	stompClient = null;
	stompClient = Stomp.over(socket);
	// エンドポイントに対して接続
	stompClient.connect({}, function (frame) {
		stompClient.send("/socket_prefix/bulkexit", {}, JSON.stringify({'class_id':class_id}));
	})
	$("#myModal2").modal("show");
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
function ShowSituationAnswer() {
	$("#myModal3").modal("show");
}

// 授業出席学生の表示
function ShowStudent(student_name,student_classno){
	$("#student_list").append(student_classno + student_name);
}

// 字幕表示
function ShowSubtitles(message) {
	$("#result-div").append("<tr><td>" + message + "</td></tr>");
}

//生徒の問題解答の表示
function ShowStudentAnswer(student_name,student_classno,student_answer){
	$("#answer_situation").append(student_classno + "番     " + student_name + "  " + student_answer + "<br>");
}