package com.studentapp.studentApp.controller;

import com.studentapp.studentApp.dto.StudentDto;
import com.studentapp.studentApp.dto.StudentPlot;
import com.studentapp.studentApp.model.*;
import com.studentapp.studentApp.repository.StudentRepository;
import com.studentapp.studentApp.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/students")
@AllArgsConstructor

public class StudentController {

    private final StudentService studentService;


    @GetMapping("/bestStudents")
    public ResponseEntity<List<StudentDto>> getBestStudents() {
        return new ResponseEntity<>(studentService.theBestStudent(), HttpStatus.OK);
    }


    @GetMapping("/getStudent")
    private ResponseEntity<StudentDto> xd() {
        return new ResponseEntity<>(studentService.getCurrentUser(), HttpStatus.OK);
    }

    @GetMapping("/getStudentPlotData")
    private ResponseEntity<StudentPlot> getPlotData() {
        return new ResponseEntity<>(studentService.getPlotData(), HttpStatus.OK);
    }


}
