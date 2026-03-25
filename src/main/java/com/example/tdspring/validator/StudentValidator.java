package com.example.tdspring.validator;

import com.example.tdspring.model.Student;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class StudentValidator {
    public void validate(List<Student> students) {
        for (Student student : students) {
            validateOne(student);
        }
    }

    private void validateOne(Student student) {

        if (student.getReference() == null || student.getReference().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Il faut une reference"
            );
        }

        if (student.getFirstName() == null || student.getFirstName().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Il faut un prenom"
            );
        }

        if (student.getLastName() == null || student.getLastName().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Il faut un nom"
            );
        }
    }
}
