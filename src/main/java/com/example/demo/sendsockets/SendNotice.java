package com.example.demo.sendsockets;

//通知送信クラス
public class SendNotice {

	// 学生名
	private String student_name;

	// 学生番号
	private String student_classno;

	// コンストラクタ、オーバーロード
	public SendNotice() {
	}

	public SendNotice(String student_name, String student_classno) {
		this.student_name = student_name;
		this.student_classno = student_classno;
	}

	// ゲッター、セッター
	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getStudent_classno() {
		return student_classno;
	}

	public void setStudent_classno(String student_classno) {
		this.student_classno = student_classno;
	}

}