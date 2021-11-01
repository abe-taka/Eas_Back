package com.example.demo.forms;

public class HomeworkForm {
	
	// ファイル名
	private String homework_filename;

	// 解答欄の数
	private int answercolumn_num;
	
	//宿題id
	private int homework_id;
	
	public int getHomework_id() {
		return homework_id;
	}

	public void setHomework_id(int homework_id) {
		this.homework_id = homework_id;
	}

	public String getHomework_filename() {
		return homework_filename;
	}

	public void setHomework_filename(String homework_filename) {
		this.homework_filename = homework_filename;
	}

	public int getAnswercolumn_num() {
		return answercolumn_num;
	}

	public void setAnswercolumn_num(int answercolumn_num) {
		this.answercolumn_num = answercolumn_num;
	}

}
