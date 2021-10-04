package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.LoginCustomRepository;
import com.example.demo.entities.LoginEntity;

//ログイン
@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Integer>, LoginCustomRepository {

}