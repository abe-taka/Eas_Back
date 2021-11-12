package com.example.demo.forms;

public class HomeworkSubmiForm {
	
	//学校コード
	private int school_code;
	
	//学年
	private int school_year;
	
	//組
	private int school_class;
	
	//科目名
	private String homework_subject;
	
	//宿題id
	private int homework_id;
	
	// ファイル名
	private String homework_filename;

	public int getSchool_code() {
		return school_code;
	}

	public void setSchool_code(int school_code) {
		this.school_code = school_code;
	}

	public int getSchool_year() {
		return school_year;
	}

	public void setSchool_year(int school_year) {
		this.school_year = school_year;
	}

	public int getSchool_class() {
		return school_class;
	}

	public void setSchool_class(int school_class) {
		this.school_class = school_class;
	}

	public String getHomework_subject() {
		return homework_subject;
	}

	public void setHomework_subject(String homework_subject) {
		this.homework_subject = homework_subject;
	}

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
	
}
