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
			// 組を取得
			var schoolclass = response_data[i]["schoolclass"] + "組";
			// ボタンタグ生成
			var newTag = document.createElement("button");
			// 表示する値をセット
			var addData = document.createTextNode(schoolclass);
			// 対象要素.apeendChild(追加する要素);
			newTag.appendChild(addData);

			// 表示する場所の取得
			var addPlace = document.getElementById("disp_data");
			addPlace.appendChild(newTag); // 対象要素.apeendChild(追加する要素);

			// URLパス用のクラスidを設定
			const input = document.createElement("input");
			input.setAttribute("type", "hidden");
			input.setAttribute("name", "classid");
			input.setAttribute("value", response_data[i]["classid"]);
			addPlace.appendChild(input);
		}
	}, function() {
		// 失敗時
		var delete_element2 = document.querySelectorAll('disp_data');
		$("#disp_data").empty();
		console.log('clickGrade:fail');
	});
}