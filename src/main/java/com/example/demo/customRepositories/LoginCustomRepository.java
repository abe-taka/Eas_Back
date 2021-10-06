package com.example.demo.customRepositories;

import org.springframework.data.repository.query.Param;

import com.example.demo.entities.LoginEntity;

//ログイン
public interface LoginCustomRepository {
	//ユーザー名検索
	LoginEntity Login(@Param("name") String name);
}