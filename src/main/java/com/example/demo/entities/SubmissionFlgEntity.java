package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//宿題提出確認テーブル
@Entity
@Table(name = "submission_flg_table")
public class SubmissionFlgEntity {
	
	// 宿題確認ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "submission_id")
	private int submission_id;
	
	// 宿題ID
	@ManyToOne
	@JoinColumn(name = "homework_id")
	private HomeWorkManageEntity homeworkmanage;
	
	// クラスID
	@ManyToOne
	@JoinColumn(name = "class_id")
	private ClassEntity classentity;
	
	//出席番号
	@Column(name = "class_no")
	private int classno;
	
	//提出管理フラグ
	@Column(name = "submission_flg")
	private int submissionflg;

	public int getSubmission_id() {
		return submission_id;
	}

	public void setSubmission_id(int submission_id) {
		this.submission_id = submission_id;
	}

	public HomeWorkManageEntity getHomeworkmanage() {
		return homeworkmanage;
	}

	public void setHomeworkmanage(HomeWorkManageEntity homeworkmanage) {
		this.homeworkmanage = homeworkmanage;
	}

	public ClassEntity getClassentity() {
		return classentity;
	}

	public void setClassentity(ClassEntity classentity) {
		this.classentity = classentity;
	}

	public int getClassno() {
		return classno;
	}

	public void setClassno(int classno) {
		this.classno = classno;
	}

	public int getSubmissionflg() {
		return submissionflg;
	}

	public void setSubmissionflg(int submissionflg) {
		this.submissionflg = submissionflg;
	}
	
	

}
