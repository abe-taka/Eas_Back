package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.StudentCustomRepository;
import com.example.demo.entities.StudentEntity;

//学生
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,Integer>,StudentCustomRepository{

}