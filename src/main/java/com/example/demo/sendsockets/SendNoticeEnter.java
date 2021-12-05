package com.example.demo.sendsockets;

//先生セッションid送信クラス
public class SendNoticeEnter {

	//メッセージ
	private String message;
	
	//コンストラクタ
	public SendNoticeEnter(String message){
		this.message = message;
	}

	//ゲッター、セッター
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}