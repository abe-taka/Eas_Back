package com.example.demo.rests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.JsonConversion;
import com.example.demo.entities.TimetableEntity;
import com.example.demo.repositories.TimetableRepository;

@RestController
public class RestHomeController {
	
	@Autowired
	DateTimeComponent date_time;
	@Autowired
	TimetableRepository timetableRepository;
	@Autowired
	JsonConversion json;

	//授業に参加できるかの確認
	@PostMapping(value="/rest/confirm_entertime")
	public String RestConfirmEnterTime(@RequestParam("js_classid") String classid) {
		
		try {
			//現在時刻、曜日を取得
			String dayofweek = date_time.Get_DayOfTheWeekShort();
			String current_time = date_time.Get_Time();
		    //10分後のじかんを取得
		    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		    Date dt = sdf.parse(current_time);
		    // Calendarクラスのインスタンスを生成
		    Calendar cal = Calendar.getInstance();
		    // 1日加算
		    cal.setTime(dt);
		    cal.add(Calendar.MINUTE, 10);
		    String tenafter_time = String.valueOf(sdf.format(cal.getTime()));
			
			//DB検索
		    try {
		    	TimetableEntity time_table = new TimetableEntity();
		    	time_table = timetableRepository.CheckClassJoin(dayofweek, classid, current_time, tenafter_time);
		    	return json.ObjectToJSON(time_table);
		    }
		    catch(NullPointerException e) {
		    	System.out.println("[Rest]RestConfirmEnterTime[Null]　：　" + e);
		    	return "0";
		    }
		}
		catch(Exception e) {
	    	System.out.println("[Rest]RestConfirmEnterTime　：　" + e);
	    	return "0";
	    } 
	}
}