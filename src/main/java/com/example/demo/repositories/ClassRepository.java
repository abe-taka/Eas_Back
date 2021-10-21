package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.ClassCustomRepository;
import com.example.demo.entities.ClassEntity;

//教師
@Repository
public interface ClassRepository extends JpaRepository<ClassEntity,String>,ClassCustomRepository{

}