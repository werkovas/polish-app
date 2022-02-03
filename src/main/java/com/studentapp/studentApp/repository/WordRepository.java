package com.studentapp.studentApp.repository;

import com.studentapp.studentApp.model.Word;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {


    @Query("select W from Word W " +
            "join W.wordsStudentPropertiesList WSP " +
            "where WSP.student.id=?1 and WSP.priorityLevel > 0 " +
            "order by WSP.priorityLevel desc")
    List<Word> findWordWithPriority(Long idStudent, PageRequest pageRequest);

    @Query("select W from Word W where W.word not in ?1 ")
    List<Word> findWordNotInWord(List<String> selectedWord);

    @Query("select W from Word W where W.word in ?1 ")
    List<Word> findWordInWord(List<String> words);



}
