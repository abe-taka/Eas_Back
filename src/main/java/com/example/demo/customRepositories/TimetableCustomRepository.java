package com.example.demo.customRepositories;

import java.util.List;

import com.example.demo.entities.TimetableEntity;

public interface TimetableCustomRepository {

	//今日のスケジュールを取得(先生)
	public List<TimetableEntity> SearchTodayTeacherSchedule(String dayofweek,String mailaddress);
	
	//今日のスケジュールを取得(学生)
	public List<TimetableEntity> SearchTodayStudentSchedule(String dayofweek,String classid);

	// 開始する授業データの取得
	public TimetableEntity SearchStartClass(String dayofweek, String classid, String current_time, String tenafter_time);
	
	// 授業データを取得
	public TimetableEntity SearchClass(String dayofweek, String classid, String periodid);
}