package com.studentapp.studentApp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class StudentRegisterDto {

    @NotBlank(message = "Username is cannot be blank")
    @Column(name = "username")
    private String username;

    @Size(min = 8, max = 32)
    @NotBlank(message = "Password is cannot be blank")
    private String password;

    public StudentRegisterDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
