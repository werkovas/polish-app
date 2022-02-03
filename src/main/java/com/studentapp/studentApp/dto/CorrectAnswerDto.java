package com.studentapp.studentApp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor

public class CorrectAnswerDto {

    private String word;
    private Boolean correct;

    public CorrectAnswerDto(String word, Boolean correct) {
        this.word = word;
        this.correct = correct;
    }
}
