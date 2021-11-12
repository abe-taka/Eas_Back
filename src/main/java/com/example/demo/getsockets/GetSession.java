package com.example.demo.getsockets;

//先生セッションid受け取りクラス
public class GetSession {

	// セッションid
	private String session_id;
	// クラスid
	private String class_id;

	// コンストラクタ
	public GetSession() {
	}

	public GetSession(String session_id) {
		this.session_id = session_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
}