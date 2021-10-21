package com.example.demo.sendsockets;

//先生セッションid送信クラス
public class SendSession {

	//変数
	private String session_id;
	
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
}