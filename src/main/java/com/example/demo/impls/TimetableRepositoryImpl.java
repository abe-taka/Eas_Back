package com.example.demo.impls;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.TimetableCustomRepository;
import com.example.demo.entities.ClassEntity;
import com.example.demo.entities.TimetableEntity;

@Repository
public class TimetableRepositoryImpl implements TimetableCustomRepository {

	@Autowired
	EntityManager entityManager;

	//今日のスケジュールを取得(先生)
	@SuppressWarnings("unchecked")
	@Override
	public List<TimetableEntity> SearchTodayTeacherSchedule(String dayofweek, String mailaddress) {
		// SQL
		String jpql = "SELECT * FROM timetable_table WHERE dayofweek = :dayofweek AND teacher_address = :mailaddress";
		// 検索
		TypedQuery<TimetableEntity> query = (TypedQuery<TimetableEntity>) entityManager.createNativeQuery(jpql,
				TimetableEntity.class);
		query.setParameter("dayofweek", dayofweek);
		query.setParameter("mailaddress", mailaddress);

		return query.getResultList();
	}

	//今日のスケジュールを取得(学生)
	@SuppressWarnings("unchecked")
	@Override
	public List<TimetableEntity> SearchTodayStudentSchedule(String dayofweek,String classid) {
		// SQL
		String jpql = "SELECT * FROM timetable_table WHERE dayofweek = :dayofweek AND class_id = :classid";
		// 検索
		TypedQuery<TimetableEntity> query = (TypedQuery<TimetableEntity>) entityManager.createNativeQuery(jpql,TimetableEntity.class);
		query.setParameter("dayofweek", dayofweek);
		query.setParameter("classid", classid);

		return query.getResultList();
	}
}