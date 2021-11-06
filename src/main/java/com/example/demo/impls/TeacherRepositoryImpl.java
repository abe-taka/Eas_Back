package com.example.demo.impls;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.TeacherCustomRepository;
import com.example.demo.entities.LoginEntity;
import com.example.demo.entities.TeacherEntity;

@Repository
public class TeacherRepositoryImpl implements TeacherCustomRepository {

	@Autowired
	EntityManager entityManager;

	// メールアドレスを基に先生データを取得
	@SuppressWarnings("unchecked")
	@Override
	public TeacherEntity SearchTeacher(String teacheraddress) {
		// SQL
		String jpql = "SELECT * FROM teacher_table WHERE teacher_address = :teacheraddress";
		// 検索
		TypedQuery<TeacherEntity> query = (TypedQuery<TeacherEntity>) entityManager.createNativeQuery(jpql,
				TeacherEntity.class);
		query.setParameter("teacheraddress", teacheraddress);

		return query.getSingleResult();
	}

	// 検索ユーザーの存在有無
	@SuppressWarnings("unchecked")
	@Override
	public Boolean CheckTeacher(String teacheraddress) {
		// SQL
		String jpql = "SELECT * FROM teacher_table WHERE teacher_address = :teacheraddress";
		// 検索
		TypedQuery<TeacherEntity> query = (TypedQuery<TeacherEntity>) entityManager.createNativeQuery(jpql,
				TeacherEntity.class);
		query.setParameter("teacheraddress", teacheraddress);

		if (query.getResultList().size() > 0) {
			return true;
		}
		return false;
	}
}