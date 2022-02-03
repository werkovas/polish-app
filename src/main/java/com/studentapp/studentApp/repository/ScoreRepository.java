package com.studentapp.studentApp.repository;

import com.studentapp.studentApp.model.Score;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score,Long> {

    @Query("select S from Score S where S.student.id=?1 order by S.testTime desc ")
    List<Score> getScoreForUser(Long userId, PageRequest pageRequest);

}
