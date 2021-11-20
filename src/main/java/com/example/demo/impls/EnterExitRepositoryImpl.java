package com.example.demo.impls;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.EnterExitCustomRepository;
import com.example.demo.entities.EnterExitEntity;

@Repository
public class EnterExitRepositoryImpl implements EnterExitCustomRepository {

	@Autowired
	EntityManager entityManager;
	
	// 最新入室時刻を取得
	@SuppressWarnings("unchecked")
	@Override
	public EnterExitEntity SearchIdAndTimeForEnterTime(String mailaddress) {
		// SQL
		String jpql = "SELECT MAX(enterexit_id) AS enterexit_id,MAX(enter_time) AS enter_time,exit_time,student_address FROM enterexit_table WHERE student_address = :mailaddress";
		// 検索
		TypedQuery<EnterExitEntity> query = (TypedQuery<EnterExitEntity>) entityManager.createNativeQuery(jpql, EnterExitEntity.class);
		query.setParameter("mailaddress", mailaddress);

		return query.getSingleResult();
	}
}
