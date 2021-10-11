package com.example.demo.sendsockets;

//socket通信で送信するメッセージを保持するクラス
public class SocketMessage {

	private String number;

	// コンストラクタとオーバーロード
	public SocketMessage() {
	}

	public SocketMessage(String number) {
		this.number = number;
	}

	public String getMessage() {
		return number;
	}
}