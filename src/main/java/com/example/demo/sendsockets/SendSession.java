package com.example.demo.sendsockets;

//先生セッションid送信クラス
public class SendSession {

	//セッションid
	private String session_id;
	//クラスid
	private String class_id;
	
	//コンストラクタ
	public SendSession(String session_id){
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