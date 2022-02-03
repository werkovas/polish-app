package com.studentapp.studentApp.repository;

import com.studentapp.studentApp.model.WordsStudentProperties;
import com.studentapp.studentApp.model.compositeKeysIds.WordPriorityCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordsStudentPropertiesRepository extends JpaRepository<WordsStudentProperties, WordPriorityCompositeKey> {

    @Query("select WSP from WordsStudentProperties WSP " +
            "where WSP.word.word in ?1 and WSP.student.id = ?2")
    List<WordsStudentProperties> findByWordListAndStudentId(List<String> wordList, Long studentId);


    @Query("select sum(W.pointsToGet) from WordsStudentProperties WSP " +
            "join WSP.word W " +
            "where WSP.learnt=true and WSP.student.id=?1")
    Optional<Integer> sumPointWordLearntForStudentId(Long studentId);

}
