package com.example.demo.impls;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.ClassCustomRepository;
import com.example.demo.entities.ClassEntity;
import com.example.demo.entities.TeacherEntity;

@Repository
public class ClassRepositoryImpl implements ClassCustomRepository{

	@Autowired
	EntityManager entityManager;

	// ログイン検索
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassEntity> SearchSchoolYear(int school_code,int school_year) {
		//SQL
		String jpql = "SELECT * FROM class_table WHERE school_code = :school_code AND school_year=:school_year";
		//検索
		TypedQuery<ClassEntity> query = (TypedQuery<ClassEntity>) entityManager.createNativeQuery(jpql,ClassEntity.class);
		query.setParameter("school_code", school_code);
		query.setParameter("school_year", school_year);

		return query.getResultList();
	}
}