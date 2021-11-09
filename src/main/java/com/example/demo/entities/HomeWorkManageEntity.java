package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//宿題管理テーブル
@Entity
@Table(name = "homeworkmanage_table")
public class HomeWorkManageEntity {

	// 宿題ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "homework_id")
	private int homeworkid;

	// 先生のメールアドレス
	@ManyToOne
	@JoinColumn(name = "teacher_address")
	private TeacherEntity teacher;

	// 宿題ファイル名
	@Column(name = "homework_filename")
	private String homeworkfilename;

	// 解答欄数
	@Column(name = "answercolumn_num")
	private int answercolumnnum;

	
	// 宿題提示テーブル
	@OneToMany(mappedBy = "homeworkmanage")
	private List<HomeWorkSubmissionEntity> homeworksubmission;
	
	// 科目名
	@Column(name = "homework_subject")
	private String homeworksubject;


	public String getHomeworksubject() {
		return homeworksubject;
	}

	public void setHomeworksubject(String homeworksubject) {
		this.homeworksubject = homeworksubject;
	}

	// ゲッター、セッター
	public int getHomeworkid() {
		return homeworkid;
	}

	public void setHomeworkid(int homeworkid) {
		this.homeworkid = homeworkid;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}

	public String getHomeworkfilename() {
		return homeworkfilename;
	}

	public void setHomeworkfilename(String homeworkfilename) {
		this.homeworkfilename = homeworkfilename;
	}

	public int getAnswercolumnnum() {
		return answercolumnnum;
	}

	public void setAnswercolumnnum(int answercolumnnum) {
		this.answercolumnnum = answercolumnnum;
	}

	public List<HomeWorkSubmissionEntity> getHomeworksubmission() {
		return homeworksubmission;
	}

	public void setHomeworksubmission(List<HomeWorkSubmissionEntity> homeworksubmission) {
		this.homeworksubmission = homeworksubmission;
	}
}