package com.studentapp.studentApp.model;

import com.studentapp.studentApp.model.compositeKeysIds.WordPriorityCompositeKey;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
public class WordsStudentProperties {

    @EmbeddedId
    private WordPriorityCompositeKey wordPriorityCompositeKey;

    @Column(nullable = false)
    private Integer priorityLevel;

    private Boolean learnt;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idStudent", referencedColumnName = "id")
    @MapsId("idStudent")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word", referencedColumnName = "word")
    @MapsId("word")
    private Word word;

    public WordsStudentProperties(WordPriorityCompositeKey wordPriorityCompositeKey, Integer priorityLevel, Boolean learnt, Student student, Word word) {
        this.wordPriorityCompositeKey = wordPriorityCompositeKey;
        this.priorityLevel = priorityLevel;
        this.learnt = learnt;
        this.student = student;
        this.word = word;
    }
}
