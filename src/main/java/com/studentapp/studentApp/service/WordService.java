package com.studentapp.studentApp.service;

import com.studentapp.studentApp.dto.WordDto;
import com.studentapp.studentApp.model.Student;
import com.studentapp.studentApp.model.Word;
import com.studentapp.studentApp.repository.WordRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WordService {


    private final WordRepository wordRepository;
    private final StudentService studentService;

    public WordService(WordRepository wordRepository, StudentService studentService) {
        this.wordRepository = wordRepository;
        this.studentService = studentService;
    }

    public List<WordDto> getWordsPointsAnswer() {

        Student student = studentService.getCurrentStudent();

        List<Word> wordWithPriority = wordRepository.findWordWithPriority(student.getId(), PageRequest.of(0, 10));


        List<String> words = wordWithPriority.stream().map(x -> x.getWord()).collect(Collectors.toList());
        words.add("");
        List<Word> wordWithoutPriority = wordRepository.findWordNotInWord(words);


        Set<String> randWord = new HashSet<>();

        while (randWord.size() < 30 - wordWithPriority.size() && wordWithoutPriority.size() != 0) {

            int index = (int) ((Math.random() * (wordWithoutPriority.size() - 1) - 0) + 0);
            randWord.add(wordWithoutPriority.get(index).getWord());

        }


        List<Word> result = Stream.concat(wordWithPriority.stream(), wordWithoutPriority.stream()
                        .filter(word -> randWord.contains(word.getWord())))
                .collect(Collectors.toList());


        Collections.shuffle(result);

        return result.stream()
                .map(x -> WordDto.builder()
                        .word(x.getWord())
                        .answer(x.getAnswer())
                        .pointsToGet(x.getPointsToGet())
                        .build()).collect(Collectors.toList());
    }


}
