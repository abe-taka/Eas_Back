package com.example.demo.customRepositories;

import org.springframework.data.repository.query.Param;

import com.example.demo.entities.LoginEntity;

//ログイン
public interface LoginCustomRepository<T> {

	//ログイン検索
	LoginEntity Login(@Param("id") int id,@Param("name") String name);
}
