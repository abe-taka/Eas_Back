package com.example.demo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//宿題解答テーブル
@Entity
@Table(name = "homeworkanswer_table")
public class HomeWorkAnswerEntity implements Serializable{

	// 宿題提示ID
	@Id
	@ManyToOne
	@JoinColumn(name = "homeworksubmission_id")
	private HomeWorkSubmissionEntity homeworksubmission;

	// 解答番号
	@Id
	@Column(name = "answer_no")
	private int answerno;

	// 解答内容
	@Column(name = "answer_content")
	private String answer_content;
	
	//出席番号
	@Column(name = "class_no")
	private int classno;

	// ゲッター、セッター
	public HomeWorkSubmissionEntity getHomeworksubmission() {
		return homeworksubmission;
	}

	public void setHomeworksubmission(HomeWorkSubmissionEntity homeworksubmission) {
		this.homeworksubmission = homeworksubmission;
	}
	
	public int getClassno() {
		return classno;
	}

	public void setClassno(int classno) {
		this.classno = classno;
	}

	public int getAnswerno() {
		return answerno;
	}

	public void setAnswerno(int answerno) {
		this.answerno = answerno;
	}

	public String getAnswer_content() {
		return answer_content;
	}

	public void setAnswer_content(String answer_content) {
		this.answer_content = answer_content;
	}
}