package com.studentapp.studentApp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
public class StudentPlot {

    List<Timestamp> X;
    List<Integer> Y;
}
