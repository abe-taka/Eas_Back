package com.example.demo.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//セッションテーブル
@Entity
@Table(name = "session_table")
public class SessionEntity implements Serializable {

	// 学生メールアドレス
	@Id
	@ManyToOne
	@JoinColumn(name = "student_address")
	private StudentEntity student;

	// セッションid
	@Column(name = "session_id")
	private int sessionid;

	// ゲッター、セッター
	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public int getSessionid() {
		return sessionid;
	}

	public void setSessionid(int sessionid) {
		this.sessionid = sessionid;
	}
}