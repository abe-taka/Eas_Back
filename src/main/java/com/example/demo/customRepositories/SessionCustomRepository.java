package com.example.demo.customRepositories;

import java.util.List;

import com.example.demo.entities.SessionEntity;

public interface SessionCustomRepository {

	//検索クラスに所属している学生を取得
	public List<SessionEntity> SearchStudentInClass(String classid);
	
	//メールアドエスを基に取得
	public SessionEntity SearchIdByStudent(String mailaddress);
}