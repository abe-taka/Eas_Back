package com.example.demo.impls;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.TimetabletimeCustomRepository;
import com.example.demo.entities.TimetabletimeEntity;

@Repository
public class TimetabletimeRepositoryImpl implements TimetabletimeCustomRepository {

	@Autowired
	EntityManager entityManager;

	//時限数、学校コードを基にデータを取得
	@SuppressWarnings("unchecked")
	@Override
	public TimetabletimeEntity SearchIdByPeriodAndSchoolCode(int time_period,int school_code) {
		// SQL
		String jpql = "SELECT * FROM timetabletime_table WHERE time_period = :time_period AND school_id = :school_code";
		// 検索
		TypedQuery<TimetabletimeEntity> query = (TypedQuery<TimetabletimeEntity>) entityManager.createNativeQuery(jpql,TimetabletimeEntity.class);
		query.setParameter("time_period", time_period);
		query.setParameter("school_code", school_code);

		return query.getSingleResult();
	}
}