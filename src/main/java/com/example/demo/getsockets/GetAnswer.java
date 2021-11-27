package com.example.demo.getsockets;

//授業内問題解答
public class GetAnswer {

	//学生名
	private String studentname;
	
	//出席番号
	private String class_no;
	
	//解答
    private String answer;
    
    //先生のセッションid
    private String teacher_sessionid;

    //コンストラクタとオーバーロード
    public GetAnswer() {
    }

    public GetAnswer(String studentname,String class_no,String answer,String teacher_sessionid) {
        this.studentname = studentname;
        this.class_no = class_no;
        this.answer = answer;
        this.teacher_sessionid = teacher_sessionid;
    }

    //ゲッター、セッター
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

	public String getTeacher_sessionid() {
		return teacher_sessionid;
	}

	public void setTeacher_sessionid(String teacher_sessionid) {
		this.teacher_sessionid = teacher_sessionid;
	}
}