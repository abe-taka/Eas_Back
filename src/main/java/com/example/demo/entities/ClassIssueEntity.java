package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//授業内問題テーブル
@Entity
@Table(name = "classissue_table")
public class ClassIssueEntity {

	// 授業内問題解答ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "homework_id")
	private int homeworkid;

	// 学生メールアドレス
	@ManyToOne
	@JoinColumn(name = "student_address")
	private StudentEntity student;

	// 解答日時
	@Column(name = "class_answer_date")
	private String classanswerdate;

	// 解答内容
	@Column(name = "class_answer_content")
	private String classanswercontent;

	// ゲッター、セッター
	public int getHomeworkid() {
		return homeworkid;
	}

	public void setHomeworkid(int homeworkid) {
		this.homeworkid = homeworkid;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public String getClassanswerdate() {
		return classanswerdate;
	}

	public void setClassanswerdate(String classanswerdate) {
		this.classanswerdate = classanswerdate;
	}

	public String getClassanswercontent() {
		return classanswercontent;
	}

	public void setClassanswercontent(String classanswercontent) {
		this.classanswercontent = classanswercontent;
	}
}