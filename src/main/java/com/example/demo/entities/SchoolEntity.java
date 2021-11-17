package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//学校テーブル
@Entity
@Table(name = "school_table")
public class SchoolEntity {

	// 学校コード
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "school_code")
	private int schoolcode;

	// 学校名
	@Column(name = "school_name")
	private String schoolname;

	// 管理者名
	@Column(name = "administrator_name")
	private String administratorname;

	// 管理者メールアドレス
	@Column(name = "administrator_address")
	private String administratoraddress;

	
	// 先生テーブル
	@OneToMany(mappedBy = "school")
	@JsonBackReference("json_school_teacher")
	private List<TeacherEntity> teacher;

	// クラステーブル
	@OneToMany(mappedBy = "school")
	@JsonBackReference("json_school_class")
	private List<ClassEntity> classentity;

	// 時間割時間テーブル
	@OneToMany(mappedBy = "school")
	@JsonBackReference("json_school_timetabletime")
	private List<TimetabletimeEntity> timetabletime;

	
	// ゲッター、セッター
	public int getSchoolcode() {
		return schoolcode;
	}

	public void setSchoolcode(int schoolcode) {
		this.schoolcode = schoolcode;
	}
	
	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getAdministratorname() {
		return administratorname;
	}

	public void setAdministratorname(String administratorname) {
		this.administratorname = administratorname;
	}

	public String getAdministratoraddress() {
		return administratoraddress;
	}

	public void setAdministratoraddress(String administratoraddress) {
		this.administratoraddress = administratoraddress;
	}

	public List<TeacherEntity> getTeacher() {
		return teacher;
	}

	public void setTeacher(List<TeacherEntity> teacher) {
		this.teacher = teacher;
	}

	public List<ClassEntity> getClassentity() {
		return classentity;
	}

	public void setClassentity(List<ClassEntity> classentity) {
		this.classentity = classentity;
	}

	public List<TimetabletimeEntity> getTimetabletime() {
		return timetabletime;
	}

	public void setTimetabletime(List<TimetabletimeEntity> timetabletime) {
		this.timetabletime = timetabletime;
	}
	
}