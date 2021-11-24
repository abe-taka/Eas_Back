package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.TimetabletimeCustomRepository;
import com.example.demo.entities.TimetabletimeEntity;

//時間割
@Repository
public interface TimetabletimeRepository extends JpaRepository<TimetabletimeEntity, Integer>, TimetabletimeCustomRepository {

}