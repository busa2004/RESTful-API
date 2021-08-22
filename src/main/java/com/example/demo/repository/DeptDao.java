package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Dept;

@Mapper
@Repository
public interface DeptDao {
	
	public List<Dept> getDepts();

	public Dept getDept(Long id);

	public void insertDept(Dept dept);

	public void deleteDept(Long id);

	public void updateDept(Dept dept);

	public boolean isExistSubDept(Long id);

	

}
