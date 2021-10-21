package com.example.demo.customRepositories;

import java.util.List;

import com.example.demo.entities.ClassEntity;

//教師
public interface ClassCustomRepository {
	//学年検索
	public List<ClassEntity> SearchSchoolYear(int school_code,int school_year);
}