package com.example.demo.customRepositories;

import com.example.demo.entities.TeacherEntity;

//先生
public interface TeacherCustomRepository {
	// メールアドレスを基に先生データを取得
	public TeacherEntity SearchTeacher(String teacheraddress);

	// 検索ユーザーの存在有無
	public Boolean CheckTeacher(String teacheraddress);
}