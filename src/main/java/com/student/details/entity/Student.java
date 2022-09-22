package com.student.details.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_tbl", uniqueConstraints = @UniqueConstraint(columnNames = "studentName"))
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long studentId;
	@Column(unique = true,nullable = false)
	private String studentName;
	@Column(unique = true,nullable = false)
	private String studentAddress;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-mm-yyyy")
	private Date admissionDate;
}
