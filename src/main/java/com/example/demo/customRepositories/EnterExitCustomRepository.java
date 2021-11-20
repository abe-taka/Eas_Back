package com.example.demo.customRepositories;

import com.example.demo.entities.EnterExitEntity;

//入室、退出
public interface EnterExitCustomRepository {

	// 最新入室時刻を取得
	public EnterExitEntity SearchIdAndTimeForEnterTime(String mailaddress);
}