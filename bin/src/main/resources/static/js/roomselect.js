/* 授業選択 */

//組データ削除判別フラグ
var flag = "0";

// 学年ごとの組データの取得
function clickGrade(school_year) {
	// CSRFトークンの取得
	const token = $("meta[name='_csrf']").attr("content");

	// 非同期通信
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
	})
	.then(function(response_data) {
		// 成功時
		console.log("response_data", response_data);
		
		if (flag == "0") {
			flag = "1";
			// Get時の組を消す
			console.log("GETデータを削除",flag);
			var delete_element = document.getElementById('list_id');
			delete_element.remove('list_class');
		} else if (flag == "1") {
			// Rest取得の組を消す
			console.log("Restデータを削除",flag);
			var delete_element2 = document.querySelectorAll('disp_data');
			$("#disp_data").empty();
		}

		/*-- 表示処理 --*/
		for (var i = 0; i < response_data.length; i++){
			
			
			var btn = document.createElement('button');
			btn.textContent = response_data[i]["schoolclass"] + '組';
			btn.setAttribute("type", "button");
			//btn.setAttribute("name", "classid");
			btn.setAttribute("name", i);
			btn.setAttribute("value", response_data[i]["schoolclass"]);
			btn.setAttribute("data-parameter1", response_data[i]["classid"]);
			btn.setAttribute('onclick', "GetAction_Class(this.getAttribute('data-parameter1'))");
			var addPlace = document.getElementById("disp_data");
			addPlace.appendChild(btn);
		}
	}, function() {
		// 失敗時
		var delete_element = document.getElementById('list_id');
		delete_element.remove('list_class');
		var delete_element2 = document.querySelectorAll('disp_data');
		$("#disp_data").empty();
		
		console.log('[$.ajax]"/rest/room_select" Fail');
	});
}

//授業画面に遷移
function GetAction_Class(classid){
	window.location.href = '/class/teacherclass?classid=' +  encodeURIComponent(classid);
}