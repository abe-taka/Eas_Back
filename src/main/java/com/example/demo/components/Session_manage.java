package com.example.demo.components;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//セッション検索クラス
@Component
public class Session_manage {

	public Boolean Get_SessionName(String session_name) {
		//セッションチェック
		session_name = SecurityContextHolder.getContext().getAuthentication().getName();
		if (session_name != "anonymousUser") {
			//ある場合
			return true;
		} else {
			//無い場合
			return false;
		}
	}
}