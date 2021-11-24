package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customRepositories.SessionCustomRepository;
import com.example.demo.entities.SessionEntity;
import com.example.demo.entities.StudentEntity;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Integer>,SessionCustomRepository{

	//メールアドレスを基に削除
	@Transactional
	public void deleteByStudent(StudentEntity student);
}