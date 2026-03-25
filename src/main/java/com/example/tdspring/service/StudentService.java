package com.example.tdspring.service;

import com.example.tdspring.model.Student;

import java.util.ArrayList;
import java.util.List;


public class StudentService {
    private final List<Student> studentsInMemory = new ArrayList<>();

    public List<Student> addStudents(List<Student> newStudents) {
        studentsInMemory.addAll(newStudents);
        return studentsInMemory;
    }

    public List<Student> getAllStudents() {
        return studentsInMemory;
    }
}
