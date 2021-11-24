package com.example.demo.forms;

import org.springframework.stereotype.Component;

// クラスid、先生セッションid保持クラス(音声認識用)
@Component
public class VoiceRecognitionForm {

	// クラスid
	private String classid;
	
	//先生セッションid
	private String teacher_sessionid;

	
	// ゲッター、セッター
	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getTeacher_sessionid() {
		return teacher_sessionid;
	}

	public void setTeacher_sessionid(String teacher_sessionid) {
		this.teacher_sessionid = teacher_sessionid;
	}
}
