/* 学生ホーム */
var stompClient = null; // Websocket接続用変数
var time_id = null; //授業入室の確認で取得した時間割ID

//授業入室の確認、時限数の取得
function ConfirmEnterTime(classid){
	// リロードを防ぐ
	$("form").on('button', function(e) {
		e.preventDefault();
	})
	// CSRFトークンの取得
	const token = $("meta[name='_csrf']").attr("content");
	//「参加する」ボタンの要素を取得
	const join_btn = document.getElementById("send");

	// 非同期通信(授業入室の確認)
	$.ajax({
		type : "POST",
		url : "/rest/confirm_entertime",
		headers : {
			"X-CSRF-TOKEN" : token
		},
		data : {
			js_classid : classid,
		},
		dataType : "json"
	})
	.then(function(response_data) {
		// 成功時
		//時間割IDを取得
		time_id = (response_data["timeid"]);
		
		// 非同期通信(時間割IDを基に時限数を取得)
		$.ajax({
			type : "POST",
			url : "/rest/time_period",
			headers : {
				"X-CSRF-TOKEN" : token
			},
			data : {
				js_time_id : time_id,
			},
			dataType : "json"
		})
		.then(function(period_response_data) {
			// 成功時
			console.log("time_period：",period_response_data);
			
			if(period_response_data != 0){
				//授業データがある場合
				//入室許可が下りているかを確認
				$.ajax({
					type : "POST",
					url : "/rest/check_enterflag",
					headers : {
						"X-CSRF-TOKEN" : token
					},
					data : {
						js_periodid : period_response_data,
						js_classid : classid
					},
					dataType : "json"
				})
				.then(function(enter_flag){
					if(enter_flag == 1){
						//降りている場合
						//id「time_period」に追加
						var Element_timeperiod = document.getElementById("time_period");
						Element_timeperiod.value = period_response_data;
						//id「headerに追加」
						$("#header").empty();
						$("#header").append("以下の授業に参加しますか");
						//id「subject」に追加
						$("#subject").empty();
						$("#subject").append(response_data["subjectname"]);
						//「参加するボタン」表示
						join_btn.style.display ="block";
						//modalを表示
						$("#myModal").modal("show");
						
						// 非同期通信
						//セッションidの一時保存
						const student_sessionid = $("#student_sessionid").val();
						$.ajax({
							type : "POST",
							url : "/rest/session",
							headers : {
								"X-CSRF-TOKEN" : token
							},
							data : {
								js_session_id : student_sessionid,
							},
							dataType : "json"
						})
						.then(function(response_data) {
							// 成功時
							console.log("response_data",response_data);
							if(response_data == 1){
								//登録成功
							}
							else{
								//登録失敗
							}
						}, function() {
							// 失敗時
							console.log('[$.ajax]"/rest/session" Fail');
						});
					}
					else if(enter_flag == 0){
						//降りていない場合
						//id「headerに追加」
						$("#header").empty();
						$("#header").append("もうしばらくお待ちください");
						//id「subject」に追加
						$("#subject").empty();
						$("#subject").append("まもなく先生が入室の許可をします");
						// 「参加する」ボタンを非表示
						join_btn.style.display ="none";
						//modalを表示
						$("#myModal").modal("show");
						
						// CSRFトークンの取得
						const token = $("meta[name='_csrf']").attr("content");
						
						// 非同期通信
						//セッションidの一時保存
						const student_sessionid = $("#student_sessionid").val();
						$.ajax({
							type : "POST",
							url : "/rest/session",
							headers : {
								"X-CSRF-TOKEN" : token
							},
							data : {
								js_session_id : student_sessionid,
							},
							dataType : "json"
						})
						.then(function(response_data) {
							// 成功時
							console.log("response_data",response_data);
							if(response_data == 1){
								//登録成功
							}
							else{
								//登録失敗
							}
						}, function() {
							// 失敗時
							console.log('[$.ajax]"/rest/session" Fail');
						});
						
						//socket通信(先生からの許可の受け取り)
						// アクセスするエンドポイントを設定
						var socket = new SockJS('/socket_endpoint');
						stompClient = Stomp.over(socket);
						// エンドポイントに対して接続
						stompClient.connect({}, function (frame) {
							stompClient.subscribe('/user/queue/notice_enter', function(response_data) {
								//授業画面に遷移
								console.log("######",response_data);
								window.location.href = '/class/enterprocess?classid=' +  encodeURIComponent(classid) + '&time_period=' + encodeURIComponent(period_response_data);
							});
						})
						
						
					}else{
						//失敗時(入室許可が下りているかを確認、response値が「0」または「1」ではない時)
						//id「headerに追加」
						$("#header").empty();
						$("#header").append("システムエラー[1]");
						//id「subject」に追加
						$("#subject").empty();
						$("#subject").append("先生に報告してください");
						// 「参加する」ボタンを非表示
						join_btn.style.display ="none";
						//modalを表示
						$("#myModal").modal("show");
					}
				}
				,function(){
					//失敗時(入室許可が下りているかを確認、処理エラー)
					//id「headerに追加」
					$("#header").empty();
					$("#header").append("システムエラー[2]");
					//id「subject」に追加
					$("#subject").empty();
					$("#subject").append("先生に報告してください");
					// 「参加する」ボタンを非表示
					join_btn.style.display ="none";
					//modalを表示
					$("#myModal").modal("show");
					console.log('[$.ajax]"/rest/check_enterflag" Fail');
				});
			}
		},
		function() {
			// 失敗時(時間割時間idの取得、処理エラー)
			$("#header").empty();
			$("#header").append("入室は授業開始10分前から入室してください");
			//id「subject」に追加
			$("#subject").empty();
			$("#subject").append("入室する時間が早い、または本日は授業がありません");
			// 「参加する」ボタンを非表示
			join_btn.style.display ="none";
			//modalを表示
			$("#myModal").modal("show");
			console.log('[$.ajax]"/rest/time_period" Fail');
		});
	},
	function() {
		// 失敗時(授業入室の確認、Ajax通信エラー)
		$("#header").empty();
		$("#header").append("システムエラー[3]");
		//id「subject」に追加
		$("#subject").empty();
		$("#subject").append("先生に報告してください");
		// 「参加する」ボタンを非表示
		join_btn.style.display ="none";
		//modalを表示
		$("#myModal").modal("show");
		console.log('[$.ajax]"/rest/confirm_entertime" Fail');
	});
}