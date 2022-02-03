package com.studentapp.studentApp.dto;

import com.studentapp.studentApp.model.Word;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class WordDto {

    private Integer pointsToGet;
    private String word;
    private String answer;


    public WordDto(Integer pointsToGet, String word, String answer) {
        this.pointsToGet = pointsToGet;
        this.word = word;
        this.answer = answer;
    }

}
