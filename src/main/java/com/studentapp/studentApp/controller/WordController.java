package com.studentapp.studentApp.controller;

import com.studentapp.studentApp.dto.CorrectAnswerDto;
import com.studentapp.studentApp.dto.WordDto;
import com.studentapp.studentApp.model.Student;
import com.studentapp.studentApp.service.StudentService;
import com.studentapp.studentApp.service.WordService;
import com.studentapp.studentApp.service.WordStudentPropertiesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("answers")
@AllArgsConstructor
public class WordController {

    private final WordService wordService;
    private final WordStudentPropertiesService wordStudentPropertiesService;
    private final StudentService studentService;

    @GetMapping("/getAll")
    public ResponseEntity<List<WordDto>> getAll() throws Exception {
        return new ResponseEntity<>(wordService.getWordsPointsAnswer(),HttpStatus.OK);
    }


 ///dnmx

    @PostMapping("/addCorrectAnswers")
    public ResponseEntity<?> addCorrectAnswers(@RequestBody List<CorrectAnswerDto> correctAnswerDtoList) {

        try {
            wordStudentPropertiesService.saveScore(correctAnswerDtoList);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }
}
