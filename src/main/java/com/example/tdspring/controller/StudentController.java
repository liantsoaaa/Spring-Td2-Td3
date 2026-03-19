package com.example.tdspring.controller;

import com.example.tdspring.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    // A) GET /welcome
    @GetMapping("/welcome")
    public String welcome(@RequestParam String name) {
        return "Welcome " + name;
    }

    // B) POST /students
    @PostMapping("/students")
    public String addStudents(@RequestBody List<Student> newStudents) {
        students.addAll(newStudents);
        return students.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining(", "));
    }

    // C) GET /students
    @GetMapping("/students")
    public org.springframework.http.ResponseEntity<String> getStudents(
            @RequestHeader(value = "Accept", defaultValue = "") String accept) {

        if ("text/plain".equals(accept)) {
            String names = students.stream()
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .collect(Collectors.joining(", "));
            return org.springframework.http.ResponseEntity.ok()
                    .header("Content-Type", "text/plain")
                    .body(names);
        } else {
            return org.springframework.http.ResponseEntity
                    .status(415)
                    .body("Format non supporté");
        }
    }
}
