package com.example.demo.components;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//セッション検索クラス
@Component
public class SessionManage {

	//セッションチェック
	public Boolean Get_SessionFlag(String session_name) {
		session_name = SecurityContextHolder.getContext().getAuthentication().getName();
		if (session_name != "anonymousUser") {
			//ある場合
			return true;
		} else {
			//無い場合
			return false;
		}
	}
	
	//セッション名取得
	public String Get_SessionName(String session_name) {
		session_name = SecurityContextHolder.getContext().getAuthentication().getName();
		return session_name;
	}
}