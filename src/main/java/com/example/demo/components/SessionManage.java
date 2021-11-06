package com.example.demo.components;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

//セッション管理コーポネント
@Component
@SessionScope
public class SessionManage {

	// セッションid存在有無チェック
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
	//セッション情報(名前)
	private String session_name;
	//セッション情報(クラスID)
	private String session_classid;
	
	// ゲッター、セッター
	public String getSession_mail() {
		return session_mail;
	}
	
	public void setSession_mail(String session_mail) {
		this.session_mail = session_mail;
	}

	public String getSession_name() {
		return session_name;
	}

	public void setSession_name(String session_name) {
		this.session_name = session_name;
	}

	public String getSession_classid() {
		return session_classid;
	}

	public void setSession_classid(String session_classid) {
		this.session_classid = session_classid;
	}
	
}