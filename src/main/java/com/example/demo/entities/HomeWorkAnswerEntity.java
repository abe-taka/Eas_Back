package com.example.demo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//宿題解答テーブル
@Entity
@Table(name = "homeworkanswer_table")
public class HomeWorkAnswerEntity implements Serializable{
	
	//宿題解答ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "homework_answer_id")
	private int homework_answer_id;	
	
	//出席番号
	@Column(name = "class_no")
	private int classno;
	
	// 解答番号
	@Column(name = "answer_no")
	private int answerno;
	
	// 解答内容
	@Column(name = "answer_content")
	private String answer_content;
	
	// 宿題提示ID
	@ManyToOne
	@JoinColumn(name = "homeworksubmission_id")
	private HomeWorkSubmissionEntity homeworksubmissionid;

	// ゲッター、セッター
	public int getHomework_answer_id() {
		return homework_answer_id;
	}

	public void setHomework_answer_id(int homework_answer_id) {
		this.homework_answer_id = homework_answer_id;
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

	public HomeWorkSubmissionEntity getHomeworksubmissionid() {
		return homeworksubmissionid;
	}

	public void setHomeworksubmissionid(HomeWorkSubmissionEntity homeworksubmissionid) {
		this.homeworksubmissionid = homeworksubmissionid;
	}
	
	
	
	
	
}