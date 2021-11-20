package com.example.demo.getsockets;

//一括退出
public class GetBulkExit {

	//変数
    private String class_id;

    //コンストラクタとオーバーロード
    public GetBulkExit() {
    }

    public GetBulkExit(String class_id) {
        this.class_id = class_id;
    }

    //ゲッター、セッター
	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
}