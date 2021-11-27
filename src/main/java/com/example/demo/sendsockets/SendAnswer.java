package com.example.demo.sendsockets;

//授業内問題解答
public class SendAnswer {

	// 学生名
	private String studentname;

	// 出席番号
	private String class_no;

	// 解答
	private String answer;

	// コンストラクタとオーバーロード
	public SendAnswer() {
	}

	public SendAnswer(String studentname,String class_no,String answer) {
	        this.studentname = studentname;
	        this.class_no = class_no;
	        this.answer = answer;
	}

	// ゲッター、セッター
	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getClass_no() {
		return class_no;
	}

	public void setClass_no(String class_no) {
		this.class_no = class_no;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}