package com.studentapp.studentApp.service;

import com.studentapp.studentApp.model.Student;
import com.studentapp.studentApp.repository.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentRepositoryUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public StudentRepositoryUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> student = studentRepository.findStudentByUsername(username);

        if(student.isPresent()) {
            return student.get();
        } else {
            throw new UsernameNotFoundException("Student o podanym loginie: " + username + " nie istnieje w systemie.");
        }
    }

}
