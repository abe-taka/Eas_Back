package com.example.demo.components;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;

//日付・時間に関するコーポネント
@Component
public class DateTimeComponent {

	static Calendar calendar = Calendar.getInstance();

	// 曜日取得
	public static String Get_DayOfTheWeekShort() {
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY:
			return "日";
		case Calendar.MONDAY:
			return "月";
		case Calendar.TUESDAY:
			return "火";
		case Calendar.WEDNESDAY:
			return "水";
		case Calendar.THURSDAY:
			return "木";
		case Calendar.FRIDAY:
			return "金";
		case Calendar.SATURDAY:
			return "土";
		}
		throw new IllegalStateException();
	}

	// 月日取得
	public String Get_MonthDate() {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
		Date dateObj = new Date();
		calendar.setTime(dateObj);
		dateObj = calendar.getTime();
		String str_md = format.format(dateObj);
		return str_md;
	}

	// 年月日を取得
	public String Get_YearMonthDate() {
		Date dateObj = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 日時情報を指定フォーマットの文字列で取得
		String str_ymd = format.format(dateObj);
		return str_ymd;
	}
	
	// 時間を取得
	public String Get_Time() {
		Date dateObj = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String str_time = format.format(dateObj);
		return str_time;
	}
}