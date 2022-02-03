package com.studentapp.studentApp.model.compositeKeysIds;

import lombok.Builder;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Builder //
@Embeddable //dla klucza zlozonego
public class ScoreCompositeKey implements Serializable {

    private Long idStudent;
    private Long idTask;

    public ScoreCompositeKey(Long idStudent, Long idTask) {
        this.idStudent = idStudent;
        this.idTask = idTask;
    }

    public ScoreCompositeKey() {
        //mozna dodac ids, jesli bedzie pluc
    }
}