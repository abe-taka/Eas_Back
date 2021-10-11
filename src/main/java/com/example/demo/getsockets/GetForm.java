package com.example.demo.getsockets;

//クライアントから送信されたデータを保持するクラス
public class GetForm {

	//変数
    private String name;
    private String message;
    private String sendtoname;

    //コンストラクタとオーバーロード
    public GetForm() {
    }

    public GetForm(String name,String message) {
        this.name = name;
        this.message = message;
    }
    
//    public GetForm(String id,String time,String issue,String answer) {
//        this.id = id;
//        this.time = time;
//        this.issue = issue;
//        this.answer = answer;
//    }

    //ゲッター、セッター
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getSendtoname() {
		return sendtoname;
	}

	public void setSendtoname(String sendtoname) {
		this.sendtoname = sendtoname;
	}
}
