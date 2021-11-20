package com.example.demo.sendsockets;

//一括退出
public class SendBulkExit {

	//変数
    private String message;

    //コンストラクタとオーバーロード
    public SendBulkExit() {
    }

    public SendBulkExit(String message) {
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