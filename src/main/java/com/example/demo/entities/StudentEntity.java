package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//学生ユーザーテーブル
@Entity
@Table(name = "student_table")
public class StudentEntity {

	// メールアドレス
	@Id
	@Column(name = "student_address")
	private String studentaddress;

	// クラスid
	@ManyToOne
	@JoinColumn(name = "class_id")
	private ClassEntity classentity;

	// 出席番号
	@Column(name = "class_no")
	private int classno;

	
	// 宿題提示テーブル
	@OneToMany(mappedBy = "student")
	private List<HomeWorkSubmissionEntity> homeworksubmission;
	
	// 入退出テーブル
	@OneToMany(mappedBy = "student")
	private List<EnterExitEntity> enterexit;
	

	// ゲッター、セッター
	public String getStudentaddress() {
		return studentaddress;
	}

	public void setStudentaddress(String studentaddress) {
		this.studentaddress = studentaddress;
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

	public List<HomeWorkSubmissionEntity> getHomeworksubmission() {
		return homeworksubmission;
	}

	public void setHomeworksubmission(List<HomeWorkSubmissionEntity> homeworksubmission) {
		this.homeworksubmission = homeworksubmission;
	}

	public List<EnterExitEntity> getEnterexit() {
		return enterexit;
	}

	public void setEnterexit(List<EnterExitEntity> enterexit) {
		this.enterexit = enterexit;
	}
}