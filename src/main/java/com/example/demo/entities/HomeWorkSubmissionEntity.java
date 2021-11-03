package com.example.demo.entities;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//宿題提示テーブル
@Entity
@Table(name = "homeworksubmission_table")
public class HomeWorkSubmissionEntity {
	// 宿題提示ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "homeworksubmission_id")
	private int homeworksubmissionid;
	// 宿題ID
	@ManyToOne
	@JoinColumn(name = "homework_id")
	private HomeWorkManageEntity homeworkmanage;
	// クラスID
	@ManyToOne
	@JoinColumn(name = "class_id")
	private ClassEntity classentity;
	// ゲッター、セッター
	public int getHomeworksubmissionid() {
		return homeworksubmissionid;
	}
	public void setHomeworksubmissionid(int homeworksubmissionid) {
		this.homeworksubmissionid = homeworksubmissionid;
	}
	public HomeWorkManageEntity getHomeworkmanage() {
		return homeworkmanage;
	}
	public void setHomeworkmanage(HomeWorkManageEntity homeworkmanage) {
		this.homeworkmanage = homeworkmanage;
	}
	public ClassEntity getClassentity() {
		return classentity;
	}
	public void setClassentity(ClassEntity classentity) {
		this.classentity = classentity;
	}
}