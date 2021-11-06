package com.example.demo.customRepositories;

import java.util.List;

import com.example.demo.entities.ClassEntity;

//クラス
public interface ClassCustomRepository {
	
	// 所属学校の学年データを取得
	public List<String> SearchSchoolyearBySchoolcode(int school_code);
	
	// 学年ごとの組データを取得
	public List<ClassEntity> SearchSchoolclassBySchoolyear(int school_code, int school_year);
}