package com.example.demo.impls;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.demo.customRepositories.StudentCustomRepository;
import com.example.demo.entities.StudentEntity;
import com.example.demo.entities.TeacherEntity;

@Repository
public class StudentRepositoryImpl implements StudentCustomRepository {

	@Autowired
	EntityManager entityManager;

	// メールアドレスを基に学生データを取得
	@SuppressWarnings("unchecked")
	@Override
	public StudentEntity SearchStudent(String mailaddress) {
		// SQL
		String jpql = "SELECT * FROM student_table WHERE student_address = :mailaddress";
		// 検索
		TypedQuery<StudentEntity> query = (TypedQuery<StudentEntity>) entityManager.createNativeQuery(jpql,
				StudentEntity.class);
		query.setParameter("mailaddress", mailaddress);

		return query.getSingleResult();
	}

	// 検索ユーザーの存在有無
	@SuppressWarnings("unchecked")
	@Override
	public Boolean CheckStudent(String mailaddress) {
		// SQL
		String jpql = "SELECT * FROM student_table WHERE student_address = :mailaddress";
		// 検索
		TypedQuery<StudentEntity> query = (TypedQuery<StudentEntity>) entityManager.createNativeQuery(jpql,
				StudentEntity.class);
		query.setParameter("mailaddress", mailaddress);

		if (query.getResultList().size() > 0) {
			return true;
		}
		return false;
	}
}