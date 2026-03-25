package com.example.tdspring.controller;

import com.example.tdspring.model.Student;
import com.example.tdspring.service.StudentService;
import com.example.tdspring.validator.StudentValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {
    private final StudentService studentService = new StudentService();
    private final StudentValidator studentValidator = new StudentValidator();

    // GET /welcome?name=xxx
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(
            @RequestParam(required = false) String name) {

        if (name == null || name.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Paramètre 'name'vide");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Welcome " + name);
    }

    // POST /students
    @PostMapping("/students")
    public ResponseEntity<?> addStudents(
            @RequestBody List<Student> newStudents) {

        try {
            studentValidator.validate(newStudents);
            List<Student> allStudents = studentService.addStudents(newStudents);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(allStudents);
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // GET /students
    @GetMapping("/students")
    public ResponseEntity<?> getStudents(
            @RequestHeader(value = "Accept", required = false) String accept) {

        if (accept == null || accept.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Entête 'Accept' vide");
        }

        try {
            List<Student> allStudents = studentService.getAllStudents();

            if (accept.contains(MediaType.TEXT_PLAIN_VALUE)) {
                String names = allStudents.stream()
                        .map(s -> s.getFirstName() + " " + s.getLastName())
                        .collect(Collectors.joining(", "));

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(names);

            } else if (accept.contains(MediaType.APPLICATION_JSON_VALUE)) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(allStudents);

            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_IMPLEMENTED)
                        .body("Format non supporté");
            }

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}