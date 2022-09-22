package com.student.details.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;

import com.student.details.entity.Student;

public interface IStudentService {

	public Student saveStudent(Student student);
	public List<Student> getAllStudent();
	public Student getStudentById(long studentId);
	public Student updateStudentById(Student student,long studentId);
	public void deleteStudentById(long studentId);
	public Page<Student> findStudentWithSortingAndPaging(int offset,int pageSize,String field);
}
