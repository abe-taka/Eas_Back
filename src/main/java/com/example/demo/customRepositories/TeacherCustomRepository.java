package com.example.demo.customRepositories;

import java.util.List;

import com.example.demo.entities.TeacherEntity;

//教師
public interface TeacherCustomRepository {
	//学校コード検索
	public TeacherEntity SearchSchoolCode(String teacheraddress);
	
	//メールアドレス検索
	public Boolean SearchMailAddress(String teacheraddress);
}