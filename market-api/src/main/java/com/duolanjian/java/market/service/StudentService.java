package com.duolanjian.java.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duolanjian.java.market.bean.Student;
import com.duolanjian.java.market.mapperlog.StudentMapper;

@Service("studentService")
public class StudentService {

	@Autowired
	private StudentMapper studentMapper;
	
	public long insert(Student student) {
		studentMapper.insert(student);
		studentMapper.delete(4l);
		System.out.println(1/0);
		
		return student.getId();
	}
	
}
