/* 学生ホーム */

//授業入室の確認
function ConfirmEnterTime(classid){
	// リロードを防ぐ
	$("form").on('button', function(e) {
		e.preventDefault();
	})
	// CSRFトークンの取得
	const token = $("meta[name='_csrf']").attr("content");

	// 非同期通信
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
		console.log("response_data", response_data);
		$("#subject").empty;
		$("#subject").append(response_data["subjectname"]);

		$("#myModal").modal("show");		
	}, function() {
		// 失敗時
		console.log('[$.ajax]"/rest/confirm_entertime" Fail');
	});
}