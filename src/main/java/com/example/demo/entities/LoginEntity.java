package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//ログイン
@Entity
@Table(name = "login_table")
public class LoginEntity {

	// ユーザー名
	@Id
	@Column(name = "username")
	private String name;

	// パスワード
	@Column(name = "password")
	private String password;

	// ゲッター、セッター
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}