package com.vishnu.techm.student.management.repository;

import com.vishnu.techm.student.management.model.Student;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByName(@NotBlank(message = "Name is required") String name);

    List<Student> findAllByStudentClass(String studentClass);

    Page<Student> findByNameContainingIgnoreCaseOrStudentClassContainingIgnoreCase(String keyword, String keyword1, Pageable pageable);

    Page<Student> findByNameContainingIgnoreCaseOrClassNameContainingIgnoreCase(String keyword, String keyword1, Pageable pageable);
}
