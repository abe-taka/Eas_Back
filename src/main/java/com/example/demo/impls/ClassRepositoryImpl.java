package com.example.demo.impls;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.ClassCustomRepository;
import com.example.demo.entities.ClassEntity;

@Repository
public class ClassRepositoryImpl implements ClassCustomRepository {

	@Autowired
	EntityManager entityManager;

	// 学年ごとの組データを取得
	@SuppressWarnings("unchecked")
	@Override
	public List<String> SearchSchoolyearBySchoolcode(int school_code) {
		// SQL
		String jpql = "SELECT DISTINCT x.school_year FROM class_table x WHERE school_code=:school_code";
		// 検索
		TypedQuery<String> query = (TypedQuery<String>) entityManager.createNativeQuery(jpql,ClassEntity.class);
		query.setParameter("school_code", school_code);
		
		return query.getResultList();
	}

	// 学年ごとの組データを取得
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassEntity> SearchSchoolclassBySchoolyear(int school_code, int school_year) {
		// SQL
		String jpql = "SELECT * FROM class_table WHERE school_code = :school_code AND school_year=:school_year";
		// 検索
		TypedQuery<ClassEntity> query = (TypedQuery<ClassEntity>) entityManager.createNativeQuery(jpql,
				ClassEntity.class);
		query.setParameter("school_code", school_code);
		query.setParameter("school_year", school_year);

		return query.getResultList();
	}
}