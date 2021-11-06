package com.example.demo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//時間割テーブル
@Entity
@Table(name = "timetable_table")
@IdClass(value= TimetablePrimary.class)
public class TimetableEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	// 時間割ID
	@Id
	@ManyToOne
	@JoinColumn(name = "timetable_id")
	private TimetabletimeEntity timetabletime;

	// 曜日
	@Id
	private String dayofweek;

	// クラスID
	@ManyToOne
	@JoinColumn(name = "class_id")
	private ClassEntity classentity;

	// 科目
	@Column(name = "subject_name")
	private String subjectname;

	// 担当先生
	@ManyToOne
	@JoinColumn(name = "teacher_address")
	private TeacherEntity teacher;

	// ゲッター、セッター
	public TimetabletimeEntity getTimetabletime() {
		return timetabletime;
	}

	public void setTimetabletime(TimetabletimeEntity timetabletime) {
		this.timetabletime = timetabletime;
	}

	public String getDayofweek() {
		return dayofweek;
	}

	public void setDayofweek(String dayofweek) {
		this.dayofweek = dayofweek;
	}

	public ClassEntity getClassentity() {
		return classentity;
	}

	public void setClassentity(ClassEntity classentity) {
		this.classentity = classentity;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}
}