package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@JsonBackReference("json_student_enterexit")
	@JoinColumn(name = "student_address")
	private StudentEntity student;

	// 入室時間
	@Column(name = "enter_time")
	private String entertime;

	// 退室時間
	@Column(name = "exit_time")
	private String exittime;

	// 時間割時間ID
	@ManyToOne
	@JsonBackReference("json_timetabletime_enterexit")
	@JoinColumn(name = "timetable_id")
	private TimetabletimeEntity timetabletime;

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

	public TimetabletimeEntity getTimetabletime() {
		return timetabletime;
	}

	public void setTimetabletime(TimetabletimeEntity timetabletime) {
		this.timetabletime = timetabletime;
	}
}
