package com.example.demo.impls;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.demo.customRepositories.TimetableCustomRepository;
import com.example.demo.entities.TimetableEntity;

@Repository
public class TimetableRepositoryImpl implements TimetableCustomRepository {

	@Autowired
	EntityManager entityManager;

	// 今日のスケジュールを取得(先生)
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

	// 今日のスケジュールを取得(学生)
	@SuppressWarnings("unchecked")
	@Override
	public List<TimetableEntity> SearchTodayStudentSchedule(String dayofweek, String classid) {
		// SQL
		String jpql = "SELECT * FROM timetable_table WHERE dayofweek = :dayofweek AND class_id = :classid";
		// 検索
		TypedQuery<TimetableEntity> query = (TypedQuery<TimetableEntity>) entityManager.createNativeQuery(jpql,
				TimetableEntity.class);
		query.setParameter("dayofweek", dayofweek);
		query.setParameter("classid", classid);

		return query.getResultList();
	}

	// 開始する授業データの取得
	@SuppressWarnings("unchecked")
	@Override
	public TimetableEntity SearchStartClass(String dayofweek, String classid, String current_time,
			String tenafter_time) {
		// SQL
		String jpql = "SELECT * FROM timetable_table WHERE class_id = :classid AND dayofweek = :dayofweek AND timetable_id in (select timetable_id from timetabletime_table where (start_time BETWEEN :current_time AND :tenafter_time) OR (start_time <= :current_time AND :current_time <= end_time))";
		// 検索
		TypedQuery<TimetableEntity> query = (TypedQuery<TimetableEntity>) entityManager.createNativeQuery(jpql,
				TimetableEntity.class);
		query.setParameter("classid", classid);
		query.setParameter("dayofweek", dayofweek);
		query.setParameter("current_time", current_time);
		query.setParameter("tenafter_time", tenafter_time);

		return query.getSingleResult();
	}

	// 授業データを取得
	@SuppressWarnings("unchecked")
	@Override
	public TimetableEntity SearchClass(String dayofweek, String classid, String periodid) {
		// SQL
		String jpql = "SELECT * FROM timetable_table WHERE class_id = :classid AND dayofweek = :dayofweek AND timetable_id = :periodid";
		// 検索
		TypedQuery<TimetableEntity> query = (TypedQuery<TimetableEntity>) entityManager.createNativeQuery(jpql,
				TimetableEntity.class);
		query.setParameter("classid", classid);
		query.setParameter("dayofweek", dayofweek);
		query.setParameter("periodid", periodid);

		return query.getSingleResult();
	}
}