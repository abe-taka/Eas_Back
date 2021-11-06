package com.example.demo.customRepositories;

import com.example.demo.entities.StudentEntity;

//学生
public interface StudentCustomRepository {
	// メールアドレスを基に学生データを取得
	public StudentEntity SearchStudent(String mailaddress);

	// 検索ユーザーの存在有無
	public Boolean CheckStudent(String mailaddress);
}