package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//時間割時間テーブル
@Entity
@Table(name = "timetabletime_table")
public class TimetabletimeEntity {

	// 時間割時間ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonBackReference("json_school_timetabletime")
	@Column(name = "timetable_id")
	private int timetable_id;

	// 学校コード
	@ManyToOne
	@JoinColumn(name = "school_id")
	private SchoolEntity school;

	// 時限数
	@Column(name = "time_period")
	private int timeperiod;

	// 開始時間
	@Column(name = "start_time")
	private String starttime;

	// 終了時間
	@Column(name = "end_time")
	private String endtime;

	// 時間割テーブル
	@OneToMany(mappedBy = "timetabletime")
	@JsonBackReference("json_timetabletime_timetable")
	private List<TimetableEntity> timetable;

	// 時間割時間テーブル
	@OneToMany(mappedBy = "timetabletime")
	@JsonBackReference("json_timetabletime_enterexit")
	private List<EnterExitEntity> enterexit;

	// ゲッター、セッター
	public int getTimetable_id() {
		return timetable_id;
	}

	public void setTimetable_id(int timetable_id) {
		this.timetable_id = timetable_id;
	}

	public SchoolEntity getSchool() {
		return school;
	}

	public void setSchool(SchoolEntity school) {
		this.school = school;
	}

	public int getTimeperiod() {
		return timeperiod;
	}

	public void setTimeperiod(int timeperiod) {
		this.timeperiod = timeperiod;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<TimetableEntity> getTimetable() {
		return timetable;
	}

	public void setTimetable(List<TimetableEntity> timetable) {
		this.timetable = timetable;
	}

	public List<EnterExitEntity> getEnterexit() {
		return enterexit;
	}

	public void setEnterexit(List<EnterExitEntity> enterexit) {
		this.enterexit = enterexit;
	}
	
}