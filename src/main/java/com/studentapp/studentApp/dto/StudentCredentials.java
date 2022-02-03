package com.studentapp.studentApp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentCredentials {

    private String username;
    private String password;

    public StudentCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
