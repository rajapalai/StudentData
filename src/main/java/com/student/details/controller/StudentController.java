package com.student.details.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.student.details.entity.Student;
import com.student.details.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@PostMapping("/postStudent")
	public ResponseEntity<Student> saveStudent(@RequestBody Student student){
		Student saveStudent = studentService.saveStudent(student);
		return new ResponseEntity<Student>(saveStudent,HttpStatus.CREATED);
	}
	@GetMapping("/listStudent")
	public ResponseEntity<List<Student>> getAllStudent(){
		List<Student> getStudentList = studentService.getAllStudent();
		return new ResponseEntity<List<Student>>(getStudentList,HttpStatus.OK);
	}
	@GetMapping("/getById/{studentId}")
	public ResponseEntity<Student> getStudentById(@PathVariable (value="studentId")long studentId){
		Student getstudentid = studentService.getStudentById(studentId);
		return new ResponseEntity<Student>(getstudentid,HttpStatus.FOUND);
	}
	@PutMapping("/updateStudent/{studentId}")
	public ResponseEntity<Student> updateStudent(@PathVariable(value = "studentId") long studentId,@RequestBody Student student){
		Student updateExistingStudent = studentService.updateStudentById(student, studentId);
		return new ResponseEntity<Student>(updateExistingStudent,HttpStatus.CREATED);
	}
	@DeleteMapping("/deleteStudent/{studentId}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "studentId") long studentId){
		studentService.deleteStudentById(studentId);
		return new ResponseEntity<String>("delete Sucessfully",HttpStatus.OK);
	}
	@GetMapping("/pagingAndSorting/{offset}/{pageSize}/{field}")
	public ResponseEntity<Page<Student>> getStudentWithSortAndPaging(@PathVariable int offset,@PathVariable int pageSize,@PathVariable String field){
		Page<Student> studentWithPagingSorting = studentService.findStudentWithSortingAndPaging(offset, pageSize, field);
		return new ResponseEntity<Page<Student>>(studentWithPagingSorting,HttpStatus.OK);
	}
	
}
