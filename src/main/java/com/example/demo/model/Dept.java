package com.example.demo.model;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Dept {
	private Long id;
	@NotNull(message="이름은 필수입니다.")
	private String name;
	private String tel;
	public Dept() {
		
	}
	public Dept(Long id, String name, String tel) {
		this.id = id;
		this.name = name;
		this.tel = tel;
	}
	public void setValues(Dept dept) {
		if(dept.name != null)
		this.name = dept.name;
		if(dept.tel != null)
		this.tel = dept.tel;
	}
	
}
