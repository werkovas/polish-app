package com.studentapp.studentApp;

import com.studentapp.studentApp.model.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StudentAppApplication.class, args);



    }

    @Override
    public void run(String... args) throws Exception {
        //        Student xd = Student.builder()
//                .id(1L)
//                .password("xd")
//                .build();

        //        Student xd1 = new Student()
    }
}
