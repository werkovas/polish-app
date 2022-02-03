package com.studentapp.studentApp.dto;

import com.studentapp.studentApp.model.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class StudentDto {

    private Long id;
    private String username;
    private Integer points;

    public StudentDto(Long id, String username, Integer points) {
        this.id = id;
        this.username = username;
        this.points = points;
    }
}
