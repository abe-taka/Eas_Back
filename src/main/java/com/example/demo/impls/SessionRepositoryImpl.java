package com.example.demo.impls;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.SessionCustomRepository;
import com.example.demo.entities.SessionEntity;
import com.example.demo.entities.StudentEntity;

@Repository
public class SessionRepositoryImpl implements SessionCustomRepository {

	@Autowired
	EntityManager entityManager;

	// メールアドレスを基に学生データを取得
	@SuppressWarnings("unchecked")
	@Override
	public List<SessionEntity> SearchStudentInClass(String classid) {
		// SQL
		String jpql = "SELECT * FROM session_table WHERE student_address in (select student_address from student_table where class_id = :classid)";
		// 検索
		TypedQuery<SessionEntity> query = (TypedQuery<SessionEntity>) entityManager.createNativeQuery(jpql,
				SessionEntity.class);
		query.setParameter("classid", classid);

		return query.getResultList();
	}

	// メールアドレスを基に学生データを取得
	@SuppressWarnings("unchecked")
	@Override
	public SessionEntity SearchIdByStudent(String mailaddress) {
		// SQL
		String jpql = "SELECT * FROM session_table WHERE student_address = :mailaddress";
		// 検索
		TypedQuery<SessionEntity> query = (TypedQuery<SessionEntity>) entityManager.createNativeQuery(jpql,SessionEntity.class);
		query.setParameter("mailaddress", mailaddress);

		return query.getSingleResult();
	}
}