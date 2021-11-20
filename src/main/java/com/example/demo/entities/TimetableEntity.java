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

//時間割テーブル
@Entity
@Table(name = "timetable_table")
public class TimetableEntity {

	//時間ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="time_id")
	private int timeid;
	
	// 時間割ID
	@ManyToOne
	@JsonBackReference("json_timetabletime_timetable")
	@JoinColumn(name = "timetable_id")
	private TimetabletimeEntity timetabletime;

	// 曜日
	private String dayofweek;

	// クラスID
	@ManyToOne
	@JsonBackReference("json_class_timetable")
	@JoinColumn(name = "class_id")
	private ClassEntity classentity;

	// 科目
	@Column(name = "subject_name")
	private String subjectname;

	// 担当先生
	@ManyToOne
	@JsonBackReference("json_teacher_timetable")
	@JoinColumn(name = "teacher_address")
	private TeacherEntity teacher;

	
	// ゲッター、セッター
	public int getTimeid() {
		return timeid;
	}

	public void setTimeid(int timeid) {
		this.timeid = timeid;
	}	
	
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