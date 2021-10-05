// Websocket接続用変数
var stompClient = null;
// 確定した認識結果変数
let finalTranscript = '';
// 音声認識実装クラス変数
let recognition = '';
// 音声認識最中かを判断するフラグ
var flag_speech = '';
var i = 0;
var j = 0;
var second_flag = true;

// connectボタンのデザイン設定
function setConnected(connected) {
    $("#connect").prop("disabled",connected);
    $("#disconnect").prop("disabled",!connected);
}

// 接続
function connect() {
	// アクセスするエンドポイントを設定
    var socket = new SockJS('/socket_endpoint');
    stompClient = Stomp.over(socket);
    // エンドポイントに対して接続
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Websocket接続: ' + frame);
        // 受信
        stompClient.subscribe('/topic/voice_recog', function (response_data) {
        	//ログ
        	i++;
        	console.log(i + "回目データ受け取り",JSON.parse(response_data.body).voicetext);
        	
        	//データが送られて来た場合
//        	if(JSON.parse(response_data.body).voicetext != ""){
//        		//1回目の表示の時
//        		if(second_flag == true){
//        			//表示
        			showGreeting(JSON.parse(response_data.body).voicetext);
//        			//if(){}先生と学生の判別条件をここに書く 
//        			second_flag = false;
//        		}
//        		//2回目の場合
//        		else{
//        			second_flag = true;
//        		}
//        	}
        });
    });
}

// 接続の遮断
function disconnect() {
	// 接続中なら切断
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    // ボタンクリック無効の切り替え
    setConnected(false);
    // 音声認識を停止
    recognition.stop();
    
    console.log("Websocket遮断");
}

//音声認識
function vr_function(){
	  
	// Chromeの音声認識の対応付け、オブジェクト生成
	SpeechRecognition = webkitSpeechRecognition || SpeechRecognition;
	recognition = new SpeechRecognition();

	// 言語の指定(日本語)
	recognition.lang = 'ja-JP';
	// 認識している途中にも結果を得るよう設定
	recognition.interimResults = true;
	// 認識しっぱなしにする(1分ぐらいの沈黙で終了?)
	recognition.continuous = true;
	//音声認識開始
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

// 表示
function showGreeting(message) {
	$("#result-div").append("<tr><td>" + message + "</td></tr>");
}

// クリック処理
$(function () {
	// リロードを防ぐ
    $("form").on('button', function (e) {
        e.preventDefault();
    });
    // 「connect」ボタンクリック処理
    $( "#connect" ).click(function() { connect(); });
    $( "#connect" ).click(function() { vr_function(); });
    // 「disconnect」ボタンクリック処理
    $( "#disconnect" ).click(function() { disconnect(); });
});
setTimeout("connect()", 3000);