package com.student.details.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.student.details.entity.Student;


public interface StudentRepo extends JpaRepository<Student, Long>{

}
