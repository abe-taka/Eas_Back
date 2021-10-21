package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//入退出テーブル
@Entity
@Table(name = "enterexit_table")
public class EnterExitEntity {

	// 入退室ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "enterexit_id")
	private int enterexitid;

	// 学生メールアドレス
	@ManyToOne
	@JoinColumn(name = "student_address")
	private StudentEntity student;

	// 入室時間
	@Column(name = "enter_time")
	private String entertime;

	// 退室時間
	@Column(name = "exit_time")
	private String exittime;

	// ゲッター、セッター
	public int getEnterexitid() {
		return enterexitid;
	}

	public void setEnterexitid(int enterexitid) {
		this.enterexitid = enterexitid;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public String getEntertime() {
		return entertime;
	}

	public void setEntertime(String entertime) {
		this.entertime = entertime;
	}

	public String getExittime() {
		return exittime;
	}

	public void setExittime(String exittime) {
		this.exittime = exittime;
	}
}
