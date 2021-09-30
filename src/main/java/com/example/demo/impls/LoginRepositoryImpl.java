package com.example.demo.impls;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customRepositories.LoginCustomRepository;
import com.example.demo.entities.LoginEntity;

//ログイン
@SuppressWarnings("rawtypes")
@Repository
@Transactional
public class LoginRepositoryImpl implements LoginCustomRepository {
	
	@Autowired
	EntityManager entityManager;

	//ログイン検索
	@SuppressWarnings("unchecked")
	@Override
	public LoginEntity Login(@Param("id") int id,@Param("name") String name) {     
		String jpql = "SELECT * FROM login_table WHERE userid = :id AND username = :name"; 
		
		TypedQuery<LoginEntity> query = (TypedQuery<LoginEntity>) entityManager.createNativeQuery(jpql, LoginEntity.class);
		query.setParameter("id", id);
		query.setParameter("name", name);
		
		return query.getSingleResult();
	}
	
}