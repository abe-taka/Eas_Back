package com.example.demo.sendsockets;

//socket通信で送信するメッセージを保持するクラス
public class SendIssue {

	//変数
    private String issue;
    private String answer;

    //コンストラクタとオーバーロード
    public SendIssue() {
    }

    public SendIssue(String issue,String answer) {
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