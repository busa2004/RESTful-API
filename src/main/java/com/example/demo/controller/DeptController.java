package com.example.demo.controller;


import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.demo.model.Dept;
import com.example.demo.repository.DeptDao;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/depts")
@RequiredArgsConstructor
public class DeptController {
	
	private final DeptDao deptDao;
	
	
	/*
	 *                               
	 *   전체조회 GET     /depts       200
	 *   일부조회 GET     /depts/{id}  200
	 *   추가    POST    /depts       201
	 *   삭제    DELETE  /depts/{id}  204
	 *   수정    PUT     /depts/{id}  200
	 *   일부수정 PATCH   /depts/{id}  200
	 */
	
	@GetMapping
    public ResponseEntity<List<Dept>> getDepts() {
		List<Dept> depts = deptDao.getDepts();
		return new ResponseEntity<>(depts, HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Dept> getDept(@PathVariable Long id) throws Exception   {
		Dept dept = deptDao.getDept(id);
		
		if(dept == null) {
			throw new NotFoundException(String.format("ID[%s] not found", id));
		}
		
		return new ResponseEntity<>(dept, HttpStatus.OK);
    }
	
	@PostMapping
    public ResponseEntity<Dept> insertDept(@Valid @RequestBody Dept dept) {
		deptDao.insertDept(dept);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dept.getId())
                .toUri();
		
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDept(@PathVariable Long id) {
		
		 // HATEOAS, 429, jwt
//        Resource<>  resource = new Resource<>();
//        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
//        resource.add(linkTo.withRel("all-users"));
        
		if(deptDao.isExistSubDept(id)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		deptDao.deleteDept(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } 
	
	@PutMapping("/{id}")
    public ResponseEntity<Dept> updateDept(@PathVariable Long id,@RequestBody Dept dept) {
		dept.setId(id);
		deptDao.updateDept(dept);
		return new ResponseEntity<>(dept, HttpStatus.OK);
    } 
	
	@PatchMapping("/{id}")
    public ResponseEntity<Dept> partUpdateDept(@PathVariable Long id,@RequestBody Dept dept) {
		Dept savedDept = deptDao.getDept(id);
		savedDept.setValues(dept);
		deptDao.updateDept(savedDept);
		return new ResponseEntity<>(savedDept, HttpStatus.OK);
    } 
	
	
	
	
}
