package com.vishnu.techm.student.management.service;

import com.vishnu.techm.student.management.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    Student saveStudent(Student student);
    Student updateStudent(Long id, Student student);
    void deleteStudent(Long id);
    Student getStudentById(Long id);
    Page<Student> getAllStudents(Pageable pageable);
    Page<Student> searchByName(String keyword, Pageable pageable);
    List<Student> filterByClass(String studentClass);

}
