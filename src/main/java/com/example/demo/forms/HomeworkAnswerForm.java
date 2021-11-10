package com.example.demo.forms;

public class HomeworkAnswerForm {
	
	//解答内容
	private String answer_content;
	
	//解答番号
	private int answer_no;
	
	//出席番号
	private int class_no;
	
	//宿題提示ID
	private int homeworksubmission_id;

	public String getAnswer_content() {
		return answer_content;
	}

	public void setAnswer_content(String answer_content) {
		this.answer_content = answer_content;
	}

	public int getAnswer_no() {
		return answer_no;
	}

	public void setAnswer_no(int answer_no) {
		this.answer_no = answer_no;
	}

	public int getClass_no() {
		return class_no;
	}

	public void setClass_no(int class_no) {
		this.class_no = class_no;
	}

	public int getHomeworksubmission_id() {
		return homeworksubmission_id;
	}

	public void setHomeworksubmission_id(int homeworksubmission_id) {
		this.homeworksubmission_id = homeworksubmission_id;
	}

}
