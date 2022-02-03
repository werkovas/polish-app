package com.studentapp.studentApp.dto;

import com.studentapp.studentApp.model.WordsStudentProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class WordPriorityDto {
    private Integer correctAnswers;
    private Integer triesNumber;
    private StudentDto student;
    private WordDto task;

    public WordPriorityDto(WordsStudentProperties wordsStudentProperties){
//        this.correctAnswers = wordsStudentProperties.getCorrectAnswers();
//        this.triesNumber = wordsStudentProperties.getTriesNumber();
//        this.student = mapStudent(wordsStudentProperties.getStudent());
//        this.task = mapTask(wordsStudentProperties.getTask());

    }


//    private TaskPolishTypeDto mapTask(Word task){
//        return Optional.ofNullable(task).map(TaskPolishTypeDto::new).orElse(null);
//    }

}
