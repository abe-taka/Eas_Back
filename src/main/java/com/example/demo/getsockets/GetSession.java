package com.example.demo.getsockets;

//先生セッションid受け取りクラス
public class GetSession {

	// 変数
	private String session_id;
	
	//コンストラクタとオーバーロード
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
}