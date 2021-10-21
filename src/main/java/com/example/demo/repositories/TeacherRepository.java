package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.TeacherCustomRepository;
import com.example.demo.entities.TeacherEntity;

//教師
@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity,String>,TeacherCustomRepository{

}
