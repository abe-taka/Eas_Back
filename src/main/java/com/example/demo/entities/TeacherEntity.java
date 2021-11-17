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

//先生ユーザーテーブル
@Entity
@Table(name = "teacher_table")
public class TeacherEntity {

	// メールアドレス
	@Id
	@Column(name = "teacher_address")
	@JsonBackReference("json_school_teacher")
	private String teacheraddress;

	// 先生名
	@Column(name = "teacher_name")
	private String teachername;

	// 学校コード
	@ManyToOne
	@JoinColumn(name = "school_code")
	private SchoolEntity school;
	
	
	//時間割テーブル
	@OneToMany(mappedBy="teacher")
	@JsonBackReference("json_teacher_timetable")
	private List<TimetableEntity> timetable;
	
	//宿題管理テーブル
	@OneToMany(mappedBy="teacher")
	private List<HomeWorkManageEntity> homeworkmanage;

	
	// ゲッター、セッター
	public String getTeacheraddress() {
		return teacheraddress;
	}

	public void setTeacheraddress(String teacheraddress) {
		this.teacheraddress = teacheraddress;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public SchoolEntity getSchool() {
		return school;
	}

	public void setSchool(SchoolEntity school) {
		this.school = school;
	}

	public List<TimetableEntity> getTimetable() {
		return timetable;
	}

	public void setTimetable(List<TimetableEntity> timetable) {
		this.timetable = timetable;
	}

	public List<HomeWorkManageEntity> getHomeworkmanage() {
		return homeworkmanage;
	}

	public void setHomeworkmanage(List<HomeWorkManageEntity> homeworkmanage) {
		this.homeworkmanage = homeworkmanage;
	}
	
}