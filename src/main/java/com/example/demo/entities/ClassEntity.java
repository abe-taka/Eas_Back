package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//クラステーブル
@Entity
@Table(name = "class_table")
public class ClassEntity {

	// クラスid
	@Id
	@Column(name = "class_id")
	private String classid;

	// 学校コード
	@ManyToOne
	@JoinColumn(name = "school_code")
	@JsonBackReference("json_school_class")
	private SchoolEntity school;

	// 学年
	@Column(name = "school_year")
	private int schoolyear;

	// 組
	@Column(name = "school_class")
	private int schoolclass;

	
	//時間割テーブル
	@OneToMany(mappedBy="classentity")
	@JsonBackReference("json_class_timetable")
	private List<TimetableEntity> timetable;
	
	
	// ゲッター、セッター
	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public SchoolEntity getSchool() {
		return school;
	}

	public void setSchool(SchoolEntity school) {
		this.school = school;
	}

	public int getSchoolyear() {
		return schoolyear;
	}

	public void setSchoolyear(int schoolyear) {
		this.schoolyear = schoolyear;
	}

	public int getSchoolclass() {
		return schoolclass;
	}

	public void setSchoolclass(int schoolclass) {
		this.schoolclass = schoolclass;
	}

	public List<TimetableEntity> getTimetable() {
		return timetable;
	}

	public void setTimetable(List<TimetableEntity> timetable) {
		this.timetable = timetable;
	}
}