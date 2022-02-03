package com.studentapp.studentApp.model.compositeKeysIds;


import lombok.Builder;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Builder //
@Embeddable //dla klucza zlozonego
public class WordPriorityCompositeKey implements Serializable {

    private Long idStudent;
    private String word;

    public WordPriorityCompositeKey(Long idStudent, String word) {
        this.idStudent = idStudent;
        this.word = word;
    }

    public WordPriorityCompositeKey() {
        //mozna dodac ids, jesli bedzie pluc
    }
}