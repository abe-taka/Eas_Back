package com.example.demo.components;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

//セッション管理クラス
@Component
@SessionScope
public class SessionManage {

	// セッションidチェック
	public Boolean Check_SessionId(String session_id) {
		session_id = SecurityContextHolder.getContext().getAuthentication().getName();
		if (session_id != "anonymousUser") {
			// ある場合
			return true;
		} else {
			// 無い場合
			return false;
		}
	}

	// セッションid取得
	public String Get_SessionId(String session_id) {
		session_id = SecurityContextHolder.getContext().getAuthentication().getName();
		return session_id;
	}

	//セッション情報(メールアドレス)
	private String session_mail;

	// ゲッター、セッター
	public String getSession_mail() {
		return session_mail;
	}

	public void setSession_mail(String session_mail) {
		this.session_mail = session_mail;
	}
}