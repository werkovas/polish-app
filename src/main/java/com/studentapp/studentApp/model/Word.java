package com.studentapp.studentApp.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
public class Word {

    @Id
    private String word;


    @Column(nullable = false)
    private Integer pointsToGet;


    @Column(nullable = false)
    private String answer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "word")
    private List<WordsStudentProperties> wordsStudentPropertiesList;


    public Word(String word, Integer pointsToGet, String answer, List<WordsStudentProperties> wordsStudentPropertiesList) {
        this.word = word;
        this.pointsToGet = pointsToGet;
        this.answer = answer;
        this.wordsStudentPropertiesList = wordsStudentPropertiesList;
    }

    public Word() {

    }
}
