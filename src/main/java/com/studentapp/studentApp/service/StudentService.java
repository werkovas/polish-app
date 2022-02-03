package com.studentapp.studentApp.service;

import com.studentapp.studentApp.configuration.PasswordEncoder;
import com.studentapp.studentApp.dto.StudentDto;
import com.studentapp.studentApp.dto.StudentPlot;
import com.studentapp.studentApp.dto.StudentRegisterDto;
import com.studentapp.studentApp.exception.StudentAlreadyExistsException;
import com.studentapp.studentApp.model.Score;
import com.studentapp.studentApp.model.Student;
import com.studentapp.studentApp.repository.ScoreRepository;
import com.studentapp.studentApp.repository.StudentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {


    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ScoreRepository scoreRepository;


    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder, BCryptPasswordEncoder bCryptPasswordEncoder, ScoreRepository scoreRepository) {
        this.studentRepository = studentRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        this.scoreRepository = scoreRepository;
    }

    private boolean studentAlreadyExists(String username) {
        return studentRepository.findStudentByUsername(username).isPresent();
    }


    public Student getCurrentStudent() {
        return ((Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }


    public List<StudentDto> theBestStudent() {
        return studentRepository.getTopUser().stream()
                .map(x -> StudentDto.builder()
                        .id(x.getId())
                        .username(x.getUsername())
                        .points(x.getPoints())
                        .build())
                .collect(Collectors.toList());
    }

    public StudentPlot getPlotData() {

        Student student = getCurrentStudent();

        List<Score> scores = scoreRepository.getScoreForUser(student.getId(), PageRequest.of(0, 20)).stream()
                .sorted(Comparator.comparing(x -> x.getTestTime().getTime()))
                .collect(Collectors.toList());

        return StudentPlot.builder()
                .X(scores.stream().map(x -> x.getTestTime()).collect(Collectors.toList()))
                .Y(scores.stream().map(x -> x.getTheHighestScore()).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void createStudent(StudentRegisterDto studentRegisterDto) {

        if (studentAlreadyExists(studentRegisterDto.getUsername()))
            throw new StudentAlreadyExistsException(studentRegisterDto.getUsername());


        Student student = Student.builder()
                .username(studentRegisterDto.getUsername())
                .password(bCryptPasswordEncoder.encode(studentRegisterDto.getPassword()))
                .points(0)
                .enabled(true)
                .build();
        studentRepository.save(student);


    }


    public StudentDto getCurrentUser() {
        Student currentUser = getCurrentStudent();
        return StudentDto.builder()
                .id(currentUser.getId())
                .points(currentUser.getPoints())
                .username(currentUser.getUsername())
                .build();
    }


}
