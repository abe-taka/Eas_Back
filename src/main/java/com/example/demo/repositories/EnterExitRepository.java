package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.EnterExitEntity;

@Repository
public interface EnterExitRepository extends JpaRepository<EnterExitEntity,Integer>{

}
