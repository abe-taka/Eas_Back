package com.example.demo.customRepositories;

import com.example.demo.entities.TimetabletimeEntity;

public interface TimetabletimeCustomRepository {

	//時限数、学校コードを基にデータを取得
	public TimetabletimeEntity SearchIdByPeriodAndSchoolCode(int time_period,int school_code);
}