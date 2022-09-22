package com.student.details.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.student.details.entity.Student;
import com.student.details.exceptionhandle.ErrorMessage;
import com.student.details.repository.StudentRepo;
import com.student.details.serviceInterface.IStudentService;

@Service
public class StudentService implements IStudentService {
	@Autowired
	private StudentRepo studentRepo;
	Logger logger = LoggerFactory.getLogger(StudentService.class);

	@Override
	public Student saveStudent(Student student) {
		try {
			if (student.getStudentName().isEmpty() || student.getStudentName().isBlank()
					|| student.getStudentName().length() == 0) {
				throw new ErrorMessage("Please fill student name");
			} else if (student.getStudentAddress().isEmpty() || student.getStudentAddress().isBlank()
					|| student.getStudentAddress().length() == 0) {
				throw new ErrorMessage("Please fill student address");
			} else {
				Student addStudent = studentRepo.save(student);
				return addStudent;
			}
		} catch (Exception e) {
			throw new ErrorMessage("Please fill student details properley " + e.getMessage());
		}
	}

	@Override
	public List<Student> getAllStudent() {
		try {
			List<Student> listStudent = studentRepo.findAll();
			if (listStudent.isEmpty()) {
				throw new ErrorMessage("There is no student data, please add some new data");
			} else {
				return listStudent;
			}
		} catch (Exception e) {
			throw new ErrorMessage("Somtething went wrong or " + e.getMessage());
		}
	}

	@Override
	public Student getStudentById(long studentId) {
		try {
			return studentRepo.findById(studentId).get();
		} catch (NoSuchElementException e) {
			throw new ErrorMessage("please enter a valid student id , " + e.getMessage() + "with id => " + studentId);
		}

	}

	@Override
	public Student updateStudentById(Student student, long studentId) {
		try {
			if (student.getStudentName().isEmpty() || student.getStudentName().length() == 0
					|| student.getStudentName().isBlank()) {
				throw new ErrorMessage("please add stuednt name");
			} else if (student.getStudentAddress().isEmpty() || student.getStudentAddress().isBlank()
					|| student.getStudentAddress().length() == 0) {
				throw new ErrorMessage("please add stuednt address");
			} else {
				Student existingStudent = studentRepo.findById(studentId).get();
				existingStudent.setStudentName(student.getStudentName());
				existingStudent.setStudentAddress(student.getStudentAddress());
				existingStudent.setAdmissionDate(student.getAdmissionDate());
				studentRepo.save(existingStudent);
				return existingStudent;
			}
		} catch (Exception e) {
			throw new ErrorMessage("Something went wrong " + e.getMessage() + " with id => " + studentId);

		}

	}

	@Override
	public void deleteStudentById(long studentId) {
		try {
			studentRepo.deleteById(studentId);
		} catch (Exception e) {
			throw new ErrorMessage("There is no student record with id => " + studentId);

		}

	}

	@Override
	public Page<Student> findStudentWithSortingAndPaging(int offset, int pageSize, String field) {
		Page<Student> studentPage = studentRepo.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return studentPage;
	}

}
