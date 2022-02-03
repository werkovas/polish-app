package com.studentapp.studentApp.model;

import com.studentapp.studentApp.model.compositeKeysIds.ScoreCompositeKey;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    //dolacza rekord ze studenta
    @ManyToOne(fetch = FetchType.LAZY, optional = false) //lazy wyciaga, gdy zostaje wywolany getter
    @JoinColumn(name = "idStudent", referencedColumnName = "id")
    private Student student;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp testTime;

    @Column(nullable = false)
    private Integer theHighestScore;

    public Score(Long scoreId, Student student, Timestamp testTime, Integer theHighestScore) {
        this.scoreId = scoreId;
        this.student = student;
        this.testTime = testTime;
        this.theHighestScore = theHighestScore;
    }
}
