package com.studentapp.studentApp.service;

import com.studentapp.studentApp.dto.CorrectAnswerDto;
import com.studentapp.studentApp.model.Score;
import com.studentapp.studentApp.model.Student;
import com.studentapp.studentApp.model.Word;
import com.studentapp.studentApp.model.WordsStudentProperties;
import com.studentapp.studentApp.model.compositeKeysIds.WordPriorityCompositeKey;
import com.studentapp.studentApp.repository.ScoreRepository;
import com.studentapp.studentApp.repository.StudentRepository;
import com.studentapp.studentApp.repository.WordRepository;
import com.studentapp.studentApp.repository.WordsStudentPropertiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordStudentPropertiesService {

    private final StudentService studentService;
    private final WordsStudentPropertiesRepository wordsStudentPropertiesRepository;
    private final WordRepository wordRepository;
    private final ScoreRepository scoreRepository;
    private final StudentRepository studentRepository;


    @Transactional
    public void saveScore(List<CorrectAnswerDto> correctAnswerDtoList) {

        Student currentStudent = studentService.getCurrentStudent();

        Map<String, CorrectAnswerDto> answerMap = correctAnswerDtoToMap(correctAnswerDtoList);

        Map<String, Integer> pointMap = pointAllocatedToMap(answerMap.keySet().stream().collect(Collectors.toList()));


        List<WordsStudentProperties> wordsStudentPropertiesList = wordsStudentPropertiesRepository.findByWordListAndStudentId(answerMap.keySet().stream().collect(Collectors.toList()), currentStudent.getId());

        Integer score = answerMap.values().stream()
                .map(x -> x.getCorrect() ? pointMap.getOrDefault(x.getWord(), 0) : 0)
                .reduce((x, y) -> x + y)
                .orElse(0);


        scoreRepository.save(Score.builder()
                .student(currentStudent)
                .theHighestScore(score)
                .build());


        List<WordsStudentProperties> wordsStudentPropertiesListResult = answerMap.values().stream()
                .map(x -> wordsStudentPropertiesList.stream()
                        .filter(y -> y.getWord().getWord().equals(x.getWord()))
                        .findAny()
                        .orElse(WordsStudentProperties.builder()
                                .priorityLevel(0)
                                .learnt(false)
                                .student(currentStudent)
                                .wordPriorityCompositeKey(WordPriorityCompositeKey.builder()
                                        .idStudent(currentStudent.getId())
                                        .word(x.getWord())
                                        .build())
                                .word(Word.builder()
                                        .word(x.getWord())
                                        .build())
                                .build()))
                .collect(Collectors.toList());


        wordsStudentPropertiesListResult.forEach(x -> {


                    Boolean isCorrect = answerMap.getOrDefault(x.getWord().getWord(), CorrectAnswerDto.builder()
                            .correct(false)
                            .build()).getCorrect();

                    if (isCorrect)
                        x.setPriorityLevel(x.getPriorityLevel() - 1);
                    else
                        x.setPriorityLevel(x.getPriorityLevel() + 1);

                    x.setLearnt(x.getPriorityLevel() < 0 && isCorrect);

                }
        );

        wordsStudentPropertiesRepository.saveAll(wordsStudentPropertiesListResult);

        currentStudent.setPoints(wordsStudentPropertiesRepository.sumPointWordLearntForStudentId(currentStudent.getId()).orElse(0));

        studentRepository.save(currentStudent);

    }

    private Map<String, Integer> pointAllocatedToMap(List<String> words) {

        Map<String, Integer> pointMap = new HashMap<>();
        List<Word> wordList = wordRepository.findWordInWord(words);

        wordList.forEach(x -> pointMap.put(x.getWord(), x.getPointsToGet()));

        return pointMap;
    }

    private Map<String, CorrectAnswerDto> correctAnswerDtoToMap(List<CorrectAnswerDto> correctAnswerDtoList) {

        Map<String, CorrectAnswerDto> correctAnswerDtoMap = new HashMap<>();

        correctAnswerDtoList.forEach(x -> correctAnswerDtoMap.put(x.getWord(), x));
        return correctAnswerDtoMap;

    }


}
