package com.vishnu.techm.student.management.service.impl;

import com.vishnu.techm.student.management.model.Student;
import com.vishnu.techm.student.management.repository.StudentRepository;
import com.vishnu.techm.student.management.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student stud = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        if(student.getName() != null)
            stud.setName(student.getName());
        if(student.getAddress() != null)
            stud.setAddress(student.getAddress());
        if(student.getEmail() != null)
            stud.setEmail(student.getEmail());
        if(student.getStudentClass() != null)
            stud.setStudentClass(student.getStudentClass());
        if(student.getAge() != null)
            stud.setAge(student.getAge());
        return studentRepository.save(stud);
    }

    @Override
    public void deleteStudent(Long id) {
        getStudentById(id);
        studentRepository.deleteById(id);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> searchByName(String keyword, Pageable pageable) {
        return studentRepository.findByNameContainingIgnoreCaseOrStudentClassContainingIgnoreCase(keyword, keyword, pageable);
    }

    @Override
    public List<Student> filterByClass(String studentClass) {
        return studentRepository.findAllByStudentClass(studentClass);
    }
}
