/* 学生ホーム */

//授業入室の確認で取得した時間割ID
var time_id = null; 

//授業入室の確認、時限数の取得
function ConfirmEnterTime(classid){
	// リロードを防ぐ
	$("form").on('button', function(e) {
		e.preventDefault();
	})
	// CSRFトークンの取得
	const token = $("meta[name='_csrf']").attr("content");

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
				//id「time_period」に追加
				var Element_timeperiod = document.getElementById("time_period");
				Element_timeperiod.value = period_response_data;
				//id「subject」に追加
				$("#subject").empty;
				$("#subject").append(response_data["subjectname"]);
				//modalを表示
				$("#myModal").modal("show");
			}
			else{
				// 失敗時
				console.log('[$.ajax]"/rest/time_period" Fail(response_data : 0)');
			}
		}, function() {
			// 失敗時
			console.log('[$.ajax]"/rest/time_period" Fail');
		});
	}, function() {
		// 失敗時
		console.log('[$.ajax]"/rest/confirm_entertime" Fail');
	});
}