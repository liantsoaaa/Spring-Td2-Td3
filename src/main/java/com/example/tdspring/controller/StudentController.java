package com.example.tdspring.controller;

import com.example.tdspring.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    // a) GET /welcome
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(
            @RequestParam(required = false) String name) {

        if (name == null || name.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Paramètre 'name' vide");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Welcome " + name);
    }

    // b) POST /students
    @PostMapping("/students")
    public ResponseEntity<List<Student>> addStudents(
            @RequestBody List<Student> newStudents) {

        try {
            students.addAll(newStudents);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(students);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // c) GET /students
    @GetMapping("/students")
    public ResponseEntity<?> getStudents(
            @RequestHeader(value = "Accept", required = false) String accept) {

        if (accept == null || accept.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Entête 'Accept' vide");
        }

        try {
            if (accept.contains(MediaType.TEXT_PLAIN_VALUE)) {
                String names = students.stream()
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
                        .body(students);

            } else {
                return ResponseEntity
                        .status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED)
                        .body("Format non supporté");
            }

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}