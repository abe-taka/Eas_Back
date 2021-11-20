package com.example.demo.customRepositories;

import java.util.List;

import com.example.demo.entities.TimetableEntity;

public interface TimetableCustomRepository {

	//今日のスケジュールを取得(先生)
	public List<TimetableEntity> SearchTodayTeacherSchedule(String dayofweek,String mailaddress);
	
	//今日のスケジュールを取得(学生)
	public List<TimetableEntity> SearchTodayStudentSchedule(String dayofweek,String classid);

	// 授業に参加できるかの確認
	public TimetableEntity CheckClassJoin(String dayofweek, String classid, String current_time, String tenafter_time);
}