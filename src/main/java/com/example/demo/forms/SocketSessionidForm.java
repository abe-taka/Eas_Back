package com.example.demo.forms;

import org.springframework.stereotype.Component;

// 先生セッションid保持クラス(授業用)
@Component
public class SocketSessionidForm {
	
	//先生セッションid
	private String teacher_sessionid;

	// ゲッター、セッター
	public String getTeacher_sessionid() {
		return teacher_sessionid;
	}

	public void setTeacher_sessionid(String teacher_sessionid) {
		this.teacher_sessionid = teacher_sessionid;
	}
}
