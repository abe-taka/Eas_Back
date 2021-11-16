package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ClassIssueEntity;

@Repository
public interface ClassIssueRepository extends JpaRepository<ClassIssueEntity,Integer>{

}
