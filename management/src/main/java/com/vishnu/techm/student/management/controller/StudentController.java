package com.vishnu.techm.student.management.controller;

import com.vishnu.techm.student.management.model.Student;
import com.vishnu.techm.student.management.repository.StudentRepository;
import com.vishnu.techm.student.management.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;
    @GetMapping()
    public String listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Student> studentPage;

        if (keyword != null && !keyword.isEmpty()) {
            studentPage = studentRepository.findByNameContainingIgnoreCaseOrClassNameContainingIgnoreCase(keyword, keyword, pageable);
        } else {
            studentPage = studentRepository.findAll(pageable);
        }

        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "students/list";  // Important: Must match `templates/students/list.html`
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/add";
    }

    @PostMapping("/add")
    public String addStudent(@Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) return "students/add";
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "students/edit";
    }

    @PostMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, @Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) return "students/edit";
        studentService.updateStudent(id, student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        model.addAttribute("students", studentService.searchByName(keyword, Pageable.unpaged()));
        return "students/search";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam String studentClass, Model model) {
        model.addAttribute("students", studentService.filterByClass(studentClass));
        return "students/filter";
    }
}
