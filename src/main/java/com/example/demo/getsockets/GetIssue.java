package com.example.demo.getsockets;

//クライアントから送信されたデータを保持するクラス
public class GetIssue {

	//変数
    private String issue;
    private String answer;

    //コンストラクタとオーバーロード
    public GetIssue() {
    }

    public GetIssue(String issue,String answer) {
        this.issue = issue;
        this.answer = answer;
    }

    //ゲッター、セッター
	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}