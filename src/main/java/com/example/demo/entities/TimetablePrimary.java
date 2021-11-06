package com.example.demo.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

//複合キー設定クラス(時間割テーブル)
@Embeddable
public class TimetablePrimary implements Serializable {
	// 時間割id
	private TimetabletimeEntity timetabletime;
	// 曜日
	private String dayofweek;
}