package com.studentapp.studentApp.controller;

import com.studentapp.studentApp.dto.StudentCredentials;
import com.studentapp.studentApp.dto.StudentRegisterDto;
import com.studentapp.studentApp.exception.StudentAlreadyExistsException;
import com.studentapp.studentApp.model.Student;
import com.studentapp.studentApp.service.StudentService;
import com.studentapp.studentApp.util.JwtToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    private final StudentService studentService;


    public AuthenticationController(AuthenticationManager authenticationManager, JwtToken jwtToken, StudentService studentService) {
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
        this.studentService = studentService;
    }

    @PostMapping(path = "/login", consumes = "application/json")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid StudentCredentials studentCredentials) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    studentCredentials.getUsername(),
                                    studentCredentials.getPassword()
                            )
                    );

            Student student = (Student) authenticate.getPrincipal();
            return ResponseEntity.ok(jwtToken.getAccessToken(student));

        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(path = "/create", consumes = "application/json")
    public ResponseEntity<Void>  createStudent(@RequestBody @Valid StudentRegisterDto studentRegisterDto) {
        try {
            studentService.createStudent(studentRegisterDto);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (StudentAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


}
