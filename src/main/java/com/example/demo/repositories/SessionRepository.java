package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.SessionCustomRepository;
import com.example.demo.entities.SessionEntity;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Integer>,SessionCustomRepository{

}