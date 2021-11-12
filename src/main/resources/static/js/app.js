/* 音声認識 + socket通信 */

var stompClient = null; // Websocket接続変数
let recognition = ''; //音声認識クラス変数
let finalTranscript = ''; // 確定した認識結果
var flag_speech = ''; // 音声認識最中かを判断するフラグ
var i = 0; //認識数

// 接続
$(document).ready(() => {
	// アクセスするエンドポイントを設定
    var socket = new SockJS('/socket_endpoint');
    stompClient = Stomp.over(socket);
    
    // エンドポイントに対して接続
    stompClient.connect({}, function (frame) {
    	//音声認識
        vr_function();
        // 受信
        stompClient.subscribe('/topic/voice_recog', function (response_data) {
        	i++;
        	console.log(i + "回目データ受け取り",JSON.parse(response_data.body).voicetext);	
        	showGreeting(JSON.parse(response_data.body).voicetext);
        });
    });
})

// 接続の遮断
function disconnect() {
	// 接続中なら切断
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    // 音声認識を停止
    recognition.stop();
    console.log("音声認識、Websocket遮断");
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
	// 認識しっぱなしにする
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
    	//繰り返す
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

// 字幕表示
function showGreeting(message) {
	$("#result-div").append("<tr><td>" + message + "</td></tr>");
}