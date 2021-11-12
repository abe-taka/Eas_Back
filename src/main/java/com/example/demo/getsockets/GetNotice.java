package com.example.demo.getsockets;

//通知取得クラス
public class GetNotice {

	//学生名
	private String student_name;

	//学生番号
	private String student_classno;
	
	//先生のセッションid
	private String teacher_sessionid;

	// コンストラクタ、オーバーロード
	public GetNotice() {
	}

	public GetNotice(String student_name, String student_classno, String teacher_sessionid) {
			this.student_name = student_name;
			this.student_classno = student_classno;
			this.teacher_sessionid = teacher_sessionid;
	}

	//ゲッター、セッター
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

	public String getTeacher_sessionid() {
		return teacher_sessionid;
	}

	public void setTeacher_sessionid(String teacher_sessionid) {
		this.teacher_sessionid = teacher_sessionid;
	}

}