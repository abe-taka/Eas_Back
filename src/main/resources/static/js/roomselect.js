var flag = "0";

// 学年ごとの「組」情報の取得
function clickGrade(school_year) {
	// リロードを防ぐ
	$("form").on('button', function(e) {
		e.preventDefault();
	})
	// CSRFトークンの取得
	const token = $("meta[name='_csrf']").attr("content");

	// / 非同期通信
	$.ajax({
		type : "POST",
		url : "/rest/room_select",
		headers : {
			"X-CSRF-TOKEN" : token
		},
		data : {
			js_schoolyear : school_year,
		},
		dataType : "json"
	}).then(function(response_data) {
		// 成功時
		console.log("response_data", response_data);

		if (flag == "0") {
			flag = "1";
			// Get時の組を消す
			var delete_element = document.getElementById('list_id');
			delete_element.remove('list_class');
		} else if (flag == "1") {
			// Rest取得の組を消す
			var delete_element2 = document.querySelectorAll('disp_data');
			$("#disp_data").empty();
		}

		/*-- 表示処理 --*/
		for (var i = 0; i < response_data.length; i++) {
			// URLパス用のクラスidを設定
			const input = document.createElement("button");
			input.setAttribute("type", "button");
			input.setAttribute("text", "button");
			input.setAttribute("name", "classid");
			input.setAttribute("value", response_data[i]["classid"]);
			input.setAttribute("data-parameter1", response_data[i]["classid"]);
			input.setAttribute('onclick', "GetAction_Class(this.getAttribute('data-parameter1'))");
			var addPlace = document.getElementById("disp_data");
			addPlace.appendChild(input);
		}
	}, function() {
		// 失敗時
		var delete_element = document.getElementById('list_id');
		delete_element.remove('list_class');
		var delete_element2 = document.querySelectorAll('disp_data');
		$("#disp_data").empty();
		
		console.log('clickGrade:fail');
	});
}

//授業画面に遷移
function GetAction_Class(classid){
	window.location.href = '/class/teacherclass?classid=' +  encodeURIComponent(classid);
}