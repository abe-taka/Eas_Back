package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.EnterExitCustomRepository;
import com.example.demo.entities.EnterExitEntity;

//入室、退出
@Repository
public interface EnterExitRepository extends JpaRepository<EnterExitEntity,Integer>,EnterExitCustomRepository{
	//id検索
	public EnterExitEntity findById(int id);
}